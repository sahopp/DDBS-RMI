import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class MasterServer extends UnicastRemoteObject implements MasterServerInterface {

    protected MasterServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public DataTuple[] doJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/hello");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/blabla");

        DataTuple[] data1 = new DataTuple[]{new DataTuple(3, "a"),
                new DataTuple(2, "b"),
                new DataTuple(7, "c"),
                new DataTuple(3, "d")};

        service1.putData(data1);

        DataTuple[] data2 = new DataTuple[]{new DataTuple(16, "e"),
                new DataTuple(4, "f"),
                new DataTuple(3, "g"),
                new DataTuple(2, "h"),
                new DataTuple(7, "i"),
                new DataTuple(3, "j"),
                new DataTuple(9, "k")};

        service2.putData(data2);

        BloomFilter filter = new BloomFilter(32, service1.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service1.getBF();
        DataTuple[] dt = service2.getFilteredData(bf);
        for (DataTuple z : dt) {
            z.print();
        }
        return dt;
    }
}