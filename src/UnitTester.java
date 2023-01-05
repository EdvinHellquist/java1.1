import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UnitTester. A class which runs all methods containing the word 'test' and returns their results.
 * Course: Applikationsutveckling(Java)
 * File: UnitTester.java
 * @author Edvin Hellquist (id20eht)
 */
public class UnitTester {
    private int fail = 0;
    private int failedByException = 0;
    private int success = 0;
    private Method setUp = null;
    private Method tearDown = null;
    private Object instanceOfTestClass = null;
    private final List<String> results = new ArrayList<>();
    private Method[] methods;

    /**
     * Method which sets necessary variables to run test-methods. Catching various exceptions and adds a suitable
     * error-message to the list depending on which was caught. If everything goes well the list is returned empty.
     * @param className The name of the class which test-methods should be run.
     * @return A list of strings containing error-messages if any occurred.
     */
    public List<String> setupTests (String className) {
        try {
            Class<?> testClass = Class.forName(className);
            this.instanceOfTestClass = testClass.getDeclaredConstructor().newInstance();
            this.methods = testClass.getMethods();
            if(!implementsInterface(instanceOfTestClass.getClass())) {
                results.add("Faulty test class: Does not implement interface 'TestClass'\n");
                return this.results;
            }
            checkForSetUpAndTearDown();
        } catch (ClassNotFoundException e) {
            results.add("ERROR: Could not find class: " + className + " aborting\n");
        } catch (NoSuchMethodException e) {
            results.add("ERROR: No constructor without parameters found, aborting\n");
        } catch (IllegalAccessException e) {
            results.add("ERROR: Can not access constructor, aborting\n");
        } catch (InvocationTargetException e) {
            results.add("ERROR: " + e.getCause() +" thrown by " + className + "'s constructor, aborting\n");
        } catch (InstantiationException e) {
            results.add("ERROR: Class can not be instantiated, aborting\n");
        } catch (NoClassDefFoundError e) {
            results.add("ERROR: Could not find class " + className + " aborting\n");
        }

        return results;
    }

    /**
     * Runs all methods in an earlier specified class which methods have the word 'test' in them. If set up and tear
     * down methods are provided by the class being tested it runs these before and after each test method.
     * @return A list of strings containing results for each test-method as well as error-messages if any occured.
     */
    public List<String> runTests () {
        for (Method i : this.methods) {
            try {
                if (i.getName().contains("test")) {
                    if (!i.getReturnType().equals(boolean.class)) {
                        this.results.add(i.getName() + ": does not return a boolean, skipping");
                        continue;
                    }
                    if (this.setUp != null) {
                        this.setUp.invoke(instanceOfTestClass);
                    }
                    Object o = i.invoke(instanceOfTestClass);
                    if (this.tearDown != null) {
                        this.tearDown.invoke(instanceOfTestClass);
                    }
                    if (!(Boolean) o) {
                        this.results.add(i.getName() + ": " + " FAIL");
                        fail++;
                    } else {
                        this.results.add(i.getName() + ": " + " SUCCESS");
                        success++;
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                String temp = e.getCause().toString();
                int split = temp.indexOf(":");
                temp = temp.substring(0,split);
                results.add(i.getName() + ": FAIL Generated a(n) " + temp);
                failedByException++;
            }
        }
        return this.results;
    }

    /**
     * Controls that a class implements the interface 'TestClass'
     * @param testClass Class to be checked.
     * @return True if the class implements the interface otherwise false.
     */
    private static Boolean implementsInterface(Class<?> testClass) {
        Class<?>[] interfaces = testClass.getInterfaces();
        for (Class<?> i : interfaces) {
            if (i.getName().contains("TestClass")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a class has the methods 'setUp' and/or 'tearDown'. If it has, it sets its own attribute accordingly.
     */
    private void checkForSetUpAndTearDown() {
        for (Method i : this.methods) {
            try {
                if (i.getName().equals("setUp")) {
                    this.setUp = instanceOfTestClass.getClass().getMethod("setUp");
                }
                if (i.getName().equals("tearDown")) {
                    this.tearDown = instanceOfTestClass.getClass().getMethod("tearDown");
                }
            } catch (NoSuchMethodException e) {
                System.err.println(e.getStackTrace());
            }
        }
    }

    /**
     * Various get-methods used in testing.
     */
    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    public Method getTearDown() {
        return tearDown;
    }

    public Method getSetUp() {
        return setUp;
    }

    public int getFailedByException() {
        return failedByException;
    }
    public Object getInstanceOfTestClass() {
        return instanceOfTestClass;
    }
    public Method[] getMethods() {
        return methods;
    }
}
