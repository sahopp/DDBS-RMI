import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class Client {


    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");
        
        long a = System.currentTimeMillis();
        ArrayList<DataTuple3> data = master.doNaiveJoin(); 
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        long b = System.currentTimeMillis();
        ArrayList<DataTuple3> data1 = master.doJoin();
        long time2 = System.currentTimeMillis()-b;
        System.out.println(time2);
        
        
        for (DataTuple d:data) {
              //d.print();
          }
          System.out.print(data.size());
    }
}
