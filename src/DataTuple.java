import java.io.Serializable;

public abstract class DataTuple implements Serializable {

    protected int A1;

    public DataTuple(int A1) {
        this.A1 = A1;

    }

    public void setA1(int a1) {
        A1 = a1;
    }

    public int getA1() {
        return A1;
    }
     public abstract void print();
}
