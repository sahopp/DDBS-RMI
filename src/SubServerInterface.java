import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubServerInterface extends Remote {

    void putData (DataTuple[] a) throws RemoteException;

    void setFilterConfig(int m, int p, int[] a, int[] b) throws RemoteException;

    boolean[] getBF() throws RemoteException;

    int getDataSize() throws RemoteException;

    DataTuple[] getFilteredData(boolean[] bf) throws RemoteException;
}
