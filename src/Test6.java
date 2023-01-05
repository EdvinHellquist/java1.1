import se.umu.cs.unittest.TestClass;
import se.umu.cs.unittest.TestClass2;

import static java.lang.Thread.sleep;

public class Test6 implements TestClass, TestClass2 {
    private String test = "Ett test";

    public boolean testEqual() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (test.equals("Ett test")) {
            return true;
        }
        test = null;
        return false;
    }
    public int testWrongReturnType() {
        return 10;
    }
    public boolean testLength() {
        return test.length()==8;
    }
    public boolean testException() {
        test = null;
        test.toUpperCase();
        return true;
    }
}
