import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SubServerInterface extends Remote {

    void putData (ArrayList<DataTuple> a) throws RemoteException;

    void setFilterConfig(int m, int p, int[] a, int[] b) throws RemoteException;

    boolean[] getBF() throws RemoteException;

    int getDataSize() throws RemoteException;

    <T> ArrayList<T> getFilteredData(boolean[] bf) throws RemoteException;
    
    <T> ArrayList<T> getData() throws RemoteException;
}
