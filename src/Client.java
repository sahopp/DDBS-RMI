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
        ArrayList<DataTuple3> data1 = master.doUnionJoin(); 
        long time = System.currentTimeMillis()-a;
        System.out.println("Union Time: " + time);
        
        long b = System.currentTimeMillis();
        ArrayList<DataTuple3> data2 = master.doIntersectionJoin();
        long time2 = System.currentTimeMillis()-b;
        System.out.println("Intersection Time: " + time2);

        long c = System.currentTimeMillis();
        ArrayList<DataTuple3> data3 = master.do12Join();
        long time3 = System.currentTimeMillis()-b;
        System.out.println("12 Time: " + time3);
        
        
        for (DataTuple d:data1) {
              //d.print();
          }
        System.out.println("Union Size: " + data1.size());
        System.out.println("Intersection Size: " + data2.size());
        System.out.println("12 Size: " + data3.size());
    }
}
