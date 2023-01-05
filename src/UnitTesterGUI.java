import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Class UnitTesterGUI. A graphic interface for the program MyUnitTester.
 * Course: Applikationsutveckling(Java)
 * File: UnitTesterGUI.java
 * @author Edvin Hellquist (id20eht)
 */
public class UnitTesterGUI {
    private JFrame frame;
    private JTextArea results;
    private JButton enter;
    private JTextField input;

    /**
     * Contructor. Creates a frame and three panels, placing them in the frame.
     */
    public UnitTesterGUI() {
        JFrame frame = buildFrame();
        JPanel middle = buildMiddle();
        JPanel upper = buildUpper();
        JPanel lower = buildLower();

        frame.add(lower, BorderLayout.SOUTH);
        frame.add(upper, BorderLayout.NORTH);
        frame.add(middle, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates the frame, sets its default close operation and size.
     * @return A JFrame object.
     */
    private JFrame buildFrame() {
        frame = new JFrame("Unit Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        return frame;
    }
    /**
     * Creates the lower panel with a button that clears the text area showing results from test-methods.
     * @return A JPanel object with a button.
     */
    private JPanel buildLower() {
        JPanel lower = new JPanel();
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            results.selectAll();
            results.replaceSelection("");
        });
        lower.add(clear);
        return lower;
    }

    /**
     * Builds the upper panel containing a text field where the user can input which class he/she wants to run tests in
     * and a button to send the input to the program.
     * @return A JPanel object.
     */
    private JPanel buildUpper() {
        JPanel upper = new JPanel();
        upper.setLayout(new BorderLayout());
        input = new JTextField("Class name");
        enter = new JButton("Run Tests");
        upper.add(input, BorderLayout.CENTER);
        upper.add(enter, BorderLayout.EAST);
        return upper;
    }

    /**
     * Byilds the central part of the GUI. Contains a text area where results of the test-methods are presented.
     * @return A JPanel object.
     */
    private JPanel buildMiddle() {
        JPanel middle = new JPanel();
        middle.setLayout(new BorderLayout());
        results = new JTextArea();
        results.setEditable(false);
        results.setHighlighter(null);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(results);
        middle.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return middle;
    }

    /**
     * Adds an action listener to the enter button.
     * @param listenForEnter An actionListener.
     */
    public void addEnterListener(ActionListener listenForEnter) {
        enter.addActionListener(listenForEnter);
    }

    public JTextField getInput() {
        return input;
    }
    public JTextArea getResults() {
        return results;
    }

    public void show() {
        frame.setVisible(true);
    }

}
