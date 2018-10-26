import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MasterServerInterface extends Remote {

    DataTuple[] doJoin() throws MalformedURLException, RemoteException, NotBoundException;
}
