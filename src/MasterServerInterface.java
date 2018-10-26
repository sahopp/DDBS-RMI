import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MasterServerInterface extends Remote {

    ArrayList<DataTuple> doJoin() throws MalformedURLException, RemoteException, NotBoundException;
}
