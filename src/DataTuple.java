import java.io.Serializable;

public class DataTuple implements Serializable {

    private int A1;
    private String A2;

    public DataTuple(int A1, String A2) {
        this.A1 = A1;
        this.A2  =A2;
    }

    public void setA1(int a1) {
        A1 = a1;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public int getA1() {
        return A1;
    }

    public String getA2() {
        return A2;
    }

    public void print() {
        System.out.println("( " + this.A1 + " , " + this.A2 + " )");
    }
}
