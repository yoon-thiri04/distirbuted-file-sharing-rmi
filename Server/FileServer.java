package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Server (psv only)
public class FileServer {
    public static void main(String[] args){
      try{
        System.setProperty("java.rmi.serer.hostname","127.0.0.1");
        Registry registry=LocateRegistry.createRegistry(3500);
        FileServiceImpl service =new FileServiceImpl();
        registry.rebind("FileService" ,service);
        System.out.println("File Server is running...");

        /* If the above doesn't work!
         *FileServiceImpl service =new FileServiceImpl();
         * Naming.rebind("rmi://localhost:2000/FileService", service);
         */

    }catch(Exception e){
        e.printStackTrace();
    }
    }
}
