import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MasterServer extends UnicastRemoteObject implements MasterServerInterface {

    protected MasterServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    private ArrayList<DataTuple3> join(ArrayList<DataTuple1> data1, ArrayList<DataTuple2> data2) {
        Collections.sort(data1);
        Collections.sort(data2);

        ArrayList<DataTuple3> result = new ArrayList<>();
        int a = 0;
        int b = 0;

        while (a < data1.size() && b < data2.size()) {
            if (data1.get(a).getA1() > data2.get(b).getA1()){
                b += 1;
            }
            else if (data1.get(a).getA1() < data2.get(b).getA1()) {
                a += 1;
            }
            else {

                for (int i = b; i < data2.size(); i++) {
                    if ((data1.get(a).getA1() != data2.get(i).getA1())) {break;}
                    result.add(new DataTuple3(data2.get(i).getA1(), data1.get(a).getA2(), data1.get(a).getA3(), data2.get(i).getA2(), data2.get(i).getA3()));
                }

                for (int i = a+1; i < data1.size(); i++) {
                    if ((data1.get(i).getA1() != data2.get(b).getA1())) {break;}
                    result.add(new DataTuple3(data1.get(i).getA1(), data1.get(i).getA2(), data1.get(i).getA3(), data2.get(b).getA2(), data2.get(b).getA3()));
                }
                a+= 1;
                b += 1;
            }
        }
        return result;
    }


    @Override
    public ArrayList<DataTuple3> doNaiveJoin()  throws MalformedURLException, RemoteException, NotBoundException {
        
    	SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        SubServerInterface service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        service1.readData1("./CSV/table1.csv");
        service2.readData2("./CSV/table2.csv");
        service3.readData2("./CSV/table3.csv");
        
        
        long a = System.currentTimeMillis();
        
        ArrayList<DataTuple1> dt1 = service1.getData();
        ArrayList<DataTuple2> dt2 = service2.getData();
        
        ArrayList<DataTuple3> j = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        return j;
    }

    @Override
    public ArrayList<DataTuple3> do12Join()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        SubServerInterface service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        service1.readData1("./CSV/table1.csv");
        service2.readData2("./CSV/table2.csv");
        service3.readData2("./CSV/table3.csv");

        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service2.getBF();
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }
    
    @Override
    public ArrayList<DataTuple3> doIntersectionJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        SubServerInterface service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        service1.readData1("./CSV/table1.csv");
        service2.readData2("./CSV/table2.csv");
        service3.readData2("./CSV/table3.csv");

        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize() + service3.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf = new boolean[bf2.length];
        for (int i=0; i<bf2.length; i++) {
        	bf[i] = bf2[i] && bf3[i];
        }
        
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }
    
    @Override
    public ArrayList<DataTuple3> doUnionJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        SubServerInterface service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        service1.readData1("./CSV/table1.csv");
        service2.readData2("./CSV/table2.csv");
        service3.readData2("./CSV/table3.csv");

        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize() + service3.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf = new boolean[bf2.length];
        for (int i=0; i<bf2.length; i++) {
        	bf[i] = bf2[i] || bf3[i];
        }
        
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }
}