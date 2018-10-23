import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerInterface {

    protected Server() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    private int[] data;
    private BloomFilter filter;


    public void putData(int[] a) throws RemoteException{
        this.data = a;
    }

    public int getFirst() throws RemoteException{
        return this.data[0];
    }

    public void setFilter(int m, int p, int[] a, int[] b) throws RemoteException{
        this.filter = new BloomFilter(m, p, a, b);
    }

    public boolean[] getBF() throws RemoteException{
        for (int z:data) {
            this.filter.add(z);
        }
        return this.filter.getFilter();
    }
}
