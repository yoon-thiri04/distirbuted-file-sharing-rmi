package Client;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import common.FileService;
public class FileClientGUI {
    private static FileService service;
    private static String username;

    public static void main(String[] args){
        try{
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",3500);
            service = (FileService) registry.lookup("FileService");

            loginScreen();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void loginScreen(){
        JFrame frame =new JFrame("Login / Register");
        JTextField userField = new JTextField(10);
        JPasswordField passField = new JPasswordField(10);
        JButton loginBtn =new JButton("Login");
        JButton registerBtn =new JButton("Register");
        
        loginBtn.addActionListener(e ->{
            try{
                if (service.login(userField.getText(), new String(passField.getPassword()))){
                    username = userField.getText();
                    frame.dispose();
                    mainScreen();
                }
                else{
                    JOptionPane.showMessageDialog(frame,"Invalid Credentials");

                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        registerBtn.addActionListener(e->{
            try{
                if (service.register(userField.getText(), new String(passField.getPassword()))){
                    JOptionPane.showMessageDialog(frame, "Registration successful. Please login.");

                }
                else{
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                }

            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        frame.setLayout(new GridLayout(4,2));
        frame.add(new JLabel("Username"));
        frame.add(userField);
        frame.add(new JLabel("Password"));
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(registerBtn);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void mainScreen(){
        JFrame frame =new JFrame("File Client - Logged in as "+username);
        JButton uploadBtn =new JButton("Upload File");
        JButton downloadBtn =new JButton("Download File");
        JButton listBtn =new JButton("List Files");
        JButton logoutBtn =new JButton("Logout");

        JTextArea fileList =new JTextArea(10,30);
        fileList.setEditable(false);

        uploadBtn.addActionListener(e ->{
            JFileChooser fc =new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File f = fc.getSelectedFile();
                try{
                    byte[] data = Files.readAllBytes(f.toPath());
                    String msg = service.uploadFile(username, f.getName(), data);
                    JOptionPane.showMessageDialog(frame, msg);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        downloadBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter filename to download:");
            if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Filename cannot be empty.");
            return;
        }

        try {
            byte[] data = service.downloadFile(username, name);  

            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File(name)); // Suggest a default file name

            int result = fc.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                Files.write(fc.getSelectedFile().toPath(), data);
                JOptionPane.showMessageDialog(frame, "Downloaded successfully to:\n" + fc.getSelectedFile().getPath());
            }
        } catch (RemoteException rex) {
            JOptionPane.showMessageDialog(frame, "File not found on server: " + name, "Download Failed", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioex) {
            JOptionPane.showMessageDialog(frame, "Error saving file locally.", "Download Failed", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Unexpected error: " + ex.getMessage(), "Download Failed", JOptionPane.ERROR_MESSAGE);
        }
});


        listBtn.addActionListener(e->{
            try{
                List<String> files =service.listFiles(username);
                fileList.setText(String.join("\n", files));

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        logoutBtn.addActionListener(e ->{
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?",
            "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION){
                frame.dispose();
                loginScreen();
            }
        });
        frame.setLayout(new BorderLayout());
        JPanel buttons =new JPanel();
        buttons.add(uploadBtn);
        buttons.add(downloadBtn);
        buttons.add(listBtn);
        buttons.add(logoutBtn);
        frame.add(buttons, BorderLayout.NORTH);
        frame.add(new JScrollPane(fileList), BorderLayout.CENTER);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
