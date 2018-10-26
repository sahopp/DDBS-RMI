import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SubServerInterface extends Remote {

    void putData (ArrayList<DataTuple> a) throws RemoteException;

    void setFilterConfig(int m, int p, int[] a, int[] b) throws RemoteException;

    boolean[] getBF() throws RemoteException;

    int getDataSize() throws RemoteException;

    ArrayList<DataTuple> getFilteredData(boolean[] bf) throws RemoteException;
}
