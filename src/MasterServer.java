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


public class MasterServer extends UnicastRemoteObject implements MasterServerInterface {

    protected MasterServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<DataTuple> doJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        SubServerInterface service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        SubServerInterface service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");

        ArrayList<DataTuple> data1 = new ArrayList<>();
        ArrayList<DataTuple> data2 = new ArrayList<>();
        
        String csvFile1 = "./CSV/table1.csv";
        String csvFile2 = "./CSV/table2.csv";

        BufferedReader br = null;
        BufferedReader br2 = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile1));
            br2 = new BufferedReader(new FileReader(csvFile2));
            
            //System.out.print(br);
            while ((line = br.readLine()) != null) {

            	
            	
                // use comma as separator
                String[] table1 = line.split(cvsSplitBy);
                DataTuple1 tuple = new DataTuple1(Integer.parseInt(table1[0]),table1[1], table1[2]);
                data2.add(tuple);
                //System.out.println(table1[1]);

            }
while ((line = br2.readLine()) != null) {

            	
            	
                // use comma as separator
                String[] table2 = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table2[0]),Integer.parseInt(table2[1]), table2[2]);
                data1.add(tuple);
                //System.out.println(table2[1]);

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

        BloomFilter filter = new BloomFilter(10000, service1.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service1.getBF();
        ArrayList<DataTuple> dt = service2.getFilteredData(bf);
        for (DataTuple z : dt) {
            //z.print();
        }
        return dt;
    }
}