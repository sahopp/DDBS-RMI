import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    void putData (int[] a) throws RemoteException;

    int getFirst () throws RemoteException;

    void setFilter (int m, int p, int[] a, int[] b) throws RemoteException;

    boolean[] getBF() throws RemoteException;
}

