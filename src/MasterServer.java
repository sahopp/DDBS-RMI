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

        ArrayList<DataTuple> data1 = new ArrayList<>();
        ArrayList<DataTuple> data2 = new ArrayList<>();
        ArrayList<DataTuple> data3 = new ArrayList<>();
        
        String csvFile1 = "./CSV/table1.csv";
        String csvFile2 = "./CSV/table2.csv";
        String csvFile3 = "./CSV/table3.csv";

        BufferedReader br = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile1));
            br2 = new BufferedReader(new FileReader(csvFile2));
            br3 = new BufferedReader(new FileReader(csvFile3));
            
            //System.out.print(br);
            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
                String[] table1 = line.split(cvsSplitBy);
                DataTuple1 tuple = new DataTuple1(Integer.parseInt(table1[0]),table1[1], table1[2]);
                data1.add(tuple);
                //System.out.println(table1[1]);

            }

            while ((line = br2.readLine()) != null) {

                // use comma as separator
                String[] table2 = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table2[0]),Integer.parseInt(table2[1]), table2[2]);
                data2.add(tuple);
                //System.out.println(table2[1]);
            }
            
            while ((line = br3.readLine()) != null) {

                // use comma as separator
                String[] table3 = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table3[0]),Integer.parseInt(table3[1]), table3[2]);
                data3.add(tuple);
                //System.out.println(tuple);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        service1.putData(data1);
        service2.putData(data2);
        service3.putData(data3);
        
        long a = System.currentTimeMillis();
        
        ArrayList<DataTuple1> dt1 = service1.getData();
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple3> j = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println(time);
        
        return j;
    }

    @Override
    public ArrayList<DataTuple3> doJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        SubServerInterface service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        ArrayList<DataTuple> data1 = new ArrayList<>();
        ArrayList<DataTuple> data2 = new ArrayList<>();
        ArrayList<DataTuple> data3 = new ArrayList<>();
        
        String csvFile1 = "./CSV/table1.csv";
        String csvFile2 = "./CSV/table2.csv";
        String csvFile3 = "./CSV/table3.csv";

        BufferedReader br = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile1));
            br2 = new BufferedReader(new FileReader(csvFile2));
            br3 = new BufferedReader(new FileReader(csvFile3));
            
            //System.out.print(br);
            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
                String[] table1 = line.split(cvsSplitBy);
                DataTuple1 tuple = new DataTuple1(Integer.parseInt(table1[0]),table1[1], table1[2]);
                data1.add(tuple);
                //System.out.println(table1[1]);

            }

            while ((line = br2.readLine()) != null) {

                // use comma as separator
                String[] table2 = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table2[0]),Integer.parseInt(table2[1]), table2[2]);
                data2.add(tuple);
                //System.out.println(table2[1]);
            }
            
            while ((line = br3.readLine()) != null) {

                // use comma as separator
                String[] table3 = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table3[0]),Integer.parseInt(table3[1]), table3[2]);
                data3.add(tuple);
                //System.out.println(tuple);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        service1.putData(data1);
        service2.putData(data2);
        service3.putData(data3);

        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service1.getBF();
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
}