import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class MasterServer extends UnicastRemoteObject implements MasterServerInterface {

    protected MasterServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<DataTuple> doJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/hello");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/blabla");

        ArrayList<DataTuple> data1 = new ArrayList<>();
        data1.add(new DataTuple(3, "a"));
        data1.add(new DataTuple(2, "b"));
        data1.add(new DataTuple(7, "c"));
        data1.add(new DataTuple(3, "d"));

        service1.putData(data1);


        ArrayList<DataTuple> data2 = new ArrayList<>();
        data1.add(new DataTuple(16, "e"));
        data1.add(new DataTuple(4, "f"));
        data1.add(new DataTuple(3, "g"));
        data1.add(new DataTuple(2, "h"));
        data1.add(new DataTuple(7, "i"));
        data1.add(new DataTuple(3, "j"));
        data1.add(new DataTuple(9, "k"));
        

        service2.putData(data2);

        BloomFilter filter = new BloomFilter(32, service1.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service1.getBF();
        ArrayList<DataTuple> dt = service2.getFilteredData(bf);
        for (DataTuple z : dt) {
            z.print();
        }
        return dt;
    }
}