import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        HelloService service =  (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println("---" + service.echo("hey server 99"));
        HelloService service2 =  (HelloService) Naming.lookup("rmi://localhost:5098/blabla");
        System.out.println("---" + service2.echo("hey server 98"));
    }
}
