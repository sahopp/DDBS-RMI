import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MasterServerInterface extends Remote {

    ArrayList<DataTuple> doJoin() throws MalformedURLException, RemoteException, NotBoundException;
    ArrayList<DataTuple3> doNaiveJoin(ArrayList<DataTuple1> d1, ArrayList<DataTuple2> d2) throws MalformedURLException, RemoteException, NotBoundException;
}
