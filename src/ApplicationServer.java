import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException{
        // TODO Auto-generated method stub
        Registry registry5099 =LocateRegistry.createRegistry(5099);
        registry5099.rebind("hello", new HelloServant());

        Registry registry5098 =LocateRegistry.createRegistry(5098);
        registry5098.rebind("blabla", new HelloServant());
    }

}
