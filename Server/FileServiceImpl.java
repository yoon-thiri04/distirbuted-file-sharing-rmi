package Server;

import java.io.*;
import java.nio.file.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;
import common.FileService;

// Implement FileService class (Implements the methods defined in the interface )
// Constructor create, and function implementations
public class FileServiceImpl extends UnicastRemoteObject implements FileService{
    private final String dirPath="server_files/";
    private final Map<String, String> users = new HashMap<>();
    private final String logPath = "server.log";
    private final String userFile = "users.txt";
    
    // Constructor -> load the existing users once the constructor is called to check the login
    protected FileServiceImpl() throws RemoteException{
        try{
            Files.createDirectories(Paths.get(dirPath));
            loadUsersFromFile(userFile);

        }
        catch(IOException e){
            throw new RemoteException("Initialization error",e);
        }
    }
    
    //Load user data to prevent recompiling everytime when a new user put
    private void loadUsersFromFile(String filename) throws IOException{
        Path path = Paths.get(filename);
        if (!Files.exists(path)){
            Files.writeString(path, "yoon,1234\nadmin,adminpassword\n"); 
        }
        List<String> lines = Files.readAllLines(path);
        for (String line:lines){
            String[] parts =line.trim().split(",");
            if (parts.length == 2){
                // trim's 0 part would be username, 1 part is password
                users.put(parts[0].trim(),parts[1].trim());
            }
        }
    }

    // Login user
    public boolean login(String username, String password) throws RemoteException{
        return users.containsKey(username) && users.get(username).equals(password);
    }

    //Register user
    public synchronized boolean register(String username, String password) throws RemoteException{
        if (users.containsKey(username)){

         return false;
        }
        users.put(username,password);

        // Save the new register user to userFile(users.txt) to use again
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(userFile),StandardOpenOption.APPEND )){
            bw.write(username+","+password+"\n");
            bw.newLine();

            //keep the system log (written in below)
            log("New user Registered"+username);
            return true;
        }
        catch(IOException e){
            throw new RemoteException("Registration failed", e);

        }
    }
    // Get file lists by username
    public List<String> listFiles(String username) throws RemoteException{
        log(username+" listed files");
        Path userDir = Paths.get(dirPath,username);
        if (!Files.exists(userDir) || !Files.isDirectory(userDir)) {
        // User folder doesn't exist → no files uploaded yet
        return List.of(); // return empty list, caller should handle this
    }
        try(Stream<Path> stream = Files.list(userDir)){
            return stream.map(Path::getFileName).map(Path::toString).toList();

        }catch(IOException e){
            throw new RemoteException("Error Listing Files",e);
        }
    }

    // Download the file by username
    public byte[] downloadFile(String username, String fileName) throws RemoteException{
        Path userFile =Paths.get(dirPath,username, fileName);

        if (!Files.exists(userFile)){
            throw new RemoteException("File not found"+fileName);
        } 
        try{
            byte[] data = Files.readAllBytes(userFile);
            log(username+" downloaded "+fileName);
            return data;
        }
        catch(IOException e){
            throw new RemoteException("Download Error",e);
        }
    }

    // Upload file
    public String uploadFile(String username, String fileName, byte[] data)throws RemoteException{
        Path userDir = Paths.get(dirPath,username);
        try{
            Files.createDirectories(userDir);
            Files.write(userDir.resolve(fileName),data);
            log(username+" uploaded "+fileName);
            return "Upload successful";
        }
        catch(IOException e){
            throw new RemoteException("Upload Error",e);
        }
    }

    // Manual log with timestamp 
    private void log(String action){
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(logPath), StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = timestamp.format(formatter);
            bw.write("["+formattedTimestamp+"]"+action);
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}