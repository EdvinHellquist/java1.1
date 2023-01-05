import se.umu.cs.unittest.TestClass;

public class Test3 implements TestClass {
    private int num;

    public Test3(int param) {
        this.num = param;
    }
    public boolean testMultiplyWithFour() {
        int temp = 4 * 4;
        return temp == 16;
    }
}
