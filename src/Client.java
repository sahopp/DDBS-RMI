import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class Client {


    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5097/master");

        ArrayList<DataTuple> data = master.doJoin();
        Collections.sort(data);
        for (DataTuple d:data) {
              d.print();
          }
          System.out.print(data.size());
    }
}
