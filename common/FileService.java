package common;
import java.rmi.RemoteException;
import java.util.List;
import java.rmi.Remote;

// Interface for FileService(API) which extends Remote
public interface FileService extends Remote{
    boolean login(String username, String password) throws RemoteException;
    boolean register(String username,String password) throws RemoteException;
    List<String> listFiles(String username) throws RemoteException;
    byte[] downloadFile(String username, String fileName) throws RemoteException;
    String uploadFile(String username, String fileName, byte[] data) throws RemoteException;
}