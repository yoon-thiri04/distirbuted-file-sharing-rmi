package Client;

import java.io.File;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import common.FileService;

public class FileClient {
    private static FileService service;
    private static String username;

    public static void main(String[] args){
        try(Scanner s = new Scanner(System.in)){
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",3500);
            service = (FileService) registry.lookup("FileService");

            // service =(FileService) Naming.lookup("rmi://localhost:2000/FileService");
            
            //Log in or register user
            System.out.println("Type 1 to Login\n Type 2 to Register:");
            int choice = Integer.parseInt(s.nextLine());

            
            System.out.print("Username: ");
            String user = s.nextLine();

            System.out.print("Password:");
            String password = s.nextLine();
            System.out.println(user);
            System.out.println(password);
            if (choice == 1){
                if (service.login(user, password)){
                    username =user;
                    System.out.println("Login successful");
                }
                else{
                    
                    System.out.println("Invalid Credentials");
                    return;
                }
            }
            else if (choice ==2){
                if (service.register(user, password)){
                    System.out.println("Registered Sucessfully. Please run again to log in.");
                    return;
                }
                else{
                    System.out.println("Username already exists.");
                    return;
                }
                
            }
            else{
                System.out.println("Invalid Choice");
                return;
                
            }
            
            while(true){
                System.out.println("\nMenu:");
                System.out.println("1. List Files");
                System.out.println("2. Upload File");
                System.out.println("3. Download File");
                System.out.println("4. Exit");

                System.out.print("Choice: ");
                int opt = Integer.parseInt(s.nextLine());

                switch (opt) {
                    case 1 -> {
                            List<String> files = service.listFiles(username);
                            if (files.isEmpty()) {
                                System.out.println("You have no files uploaded yet.");
                                 System.out.println("Wanna start uploading? Choose option 2 in the menu.");
                            } else {
                                System.out.println("Your files:");
                                files.forEach(System.out::println);
                            }
                    }
                    case 2-> {
                        System.out.print("Enter path to upload: ");
                        String path = s.nextLine();
                        File f = new File(path);
                        if (f.exists()){
                            byte[] data = Files.readAllBytes(f.toPath());
                            String response = service.uploadFile(username, f.getName(), data);
                            System.out.println(response);
                        }
                        else{
                            System.out.println("File does not exist.");
                        }
                    }
                    case 3 ->{
                        System.out.print("Enter filename to download: ");
                        String fname = s.nextLine();
                        try{
                            byte[] data = service.downloadFile(username, fname);
                            
                            File downloadDir = new File("client_files/"+username);
                            downloadDir.mkdirs();

                            File outFile = new File(downloadDir,fname);
                            Files.write(outFile.toPath(),data);
                            System.out.print("Downloaded to:"+ outFile.getPath());
                            }
                            catch(RemoteException e){
                                System.out.println("Download failed:"+e.getCause().getMessage());
                            }
                        catch(Exception e){
                            System.out.println("Failed to download file.");
                        }
                    }
                    case 4 -> {
                        System.out.println("Good Bye");
                        
                        return;
                        
                    }
                    default -> System.out.println("Invalid option");
                        
                }
            }


            
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
