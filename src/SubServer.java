import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SubServer extends UnicastRemoteObject implements SubServerInterface {

    protected SubServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    private DataTuple[] data;
    private BloomFilter filter;


    public void putData(DataTuple[] a) throws RemoteException{
        this.data = a;
    }

    public void setFilterConfig(int m, int p, int[] a, int[] b) throws RemoteException{
        this.filter = new BloomFilter(m, p, a, b);
    }

    public boolean[] getBF() throws RemoteException{
        for (DataTuple z:data) {
            this.filter.add(z.getA1());
        }
        return this.filter.getFilter();
    }

    public int getDataSize() throws RemoteException{
        return this.data.length;
    }

    public DataTuple[] getFilteredData(boolean[] bf){
        ArrayList<DataTuple> result =  new ArrayList();

        this.filter.setFilter(bf);
        for (DataTuple z:data) {
            if (this.filter.check(z.getA1())) {result.add(z);}
        }
        return result.toArray(new DataTuple[]{});
    }
}
