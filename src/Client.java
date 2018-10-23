import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class Client {

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        ServerInterface service =  (ServerInterface) Naming.lookup("rmi://localhost:5099/hello");

        int[] data = new int[]{3, 7, 1, 7, 13};
        service.putData(data);

        BloomFilter filter = new BloomFilter(32, 5);
        service.setFilter(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service.getBF();
        int q = service.getFirst();
        System.out.println(Arrays.toString(bf));

        for (int z:data) {
            filter.add(z);
        }
        System.out.println(Arrays.toString(filter.getFilter()));
        //HelloService service2 =  (HelloService) Naming.lookup("rmi://localhost:5098/blabla");
        //System.out.println("---" + service2.echo("hey server 98"));
    }
}
