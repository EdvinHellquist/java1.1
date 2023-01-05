import javax.swing.*;

/**Beskriv översiktligt hur programmet är uppbyggt och hur det löser problemet. Ska inkludera minst ett UML-klassdiagram över systemet.
 * Class MyUnitTester. Main class. Starts a controller.
 * Course: Applikationsutveckling(Java)
 * File: MyUnitTester.java
 * @author Edvin Hellquist (id20eht)
 */
public class MyUnitTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            MyController controller = new MyController();
        });
    }
}