import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class UnitTesterTests {
    @Test
    public void createUnitTester() {
        UnitTester ut = new UnitTester();
        assertNotNull(ut);
    }
    @Test
    public void checkCorrectVariablesInNewUnitTester() {
        UnitTester ut = new UnitTester();
        assertAll(
                ()-> assertNull(ut.getSetUp()),
                ()-> assertNull(ut.getTearDown()),
                ()-> assertEquals(0, ut.getSuccess()),
                ()-> assertEquals(0, ut.getFail()),
                ()-> assertNull(ut.getInstanceOfTestClass()),
                ()-> assertNull(ut.getMethods())
        );
    }
    @Test
    public void setUpTestsWithFaultyClass () {
        UnitTester ut = new UnitTester();
        ut.setupTests("ClassThatDoesNotExists");
        assertNull(ut.getInstanceOfTestClass());
        assertNull(ut.getMethods());
    }
    @Test
    public void setUpTestsWithClassWithNoInterface() {
        UnitTester ut = new UnitTester();
        List<String> results;
        results = ut.setupTests("Test2");
        assertFalse(results.isEmpty());
    }
    @Test
    public void setUpTestsWithCorrectClass () {
        UnitTester ut = new UnitTester();
        List<String> results;
        results = ut.setupTests("Test1");
        assertTrue(results.isEmpty());
    }
    @Test
    public void testThatSetupAndTeardownIsTrue() {
        UnitTester ut = new UnitTester();
        ut.setupTests("Test1");
        assertNotNull(ut.getTearDown());
        assertNotNull(ut.getSetUp());
    }
    @Test
    public void testThatSetupAndTeardownIsFalse() {
        UnitTester ut = new UnitTester();
        ut.setupTests("Test6");
        assertNull(ut.getSetUp());
        assertNull(ut.getTearDown());
    }
    @Test
    public void testRunTests() {
        UnitTester ut = new UnitTester();
        List<String> results;
        results = ut.setupTests("Test1");
        assertTrue(results.isEmpty());
        results = ut.runTests();
        List<String> finalResults = results;
        assertAll(
                () -> assertFalse(finalResults.isEmpty()),
                () -> assertEquals(ut.getFail(),1),
                () -> assertEquals(ut.getSuccess(), 3),
                () -> assertEquals(ut.getFailedByException(), 1),
                () -> assertNotNull(ut.getSetUp()),
                () -> assertNotNull(ut.getTearDown()),
                () -> assertNotNull(ut.getMethods())
        );

    }
}
