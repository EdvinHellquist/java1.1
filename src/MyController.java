import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * Class MyController. Controller, interface between the GUI and the model UnitTester.
 * Course: Applikationsutveckling(Java)
 * File: MyController.java
 * @author Edvin Hellquist (id20eht)
 */
public class MyController {
    private UnitTesterGUI view;

    /**
     * Creates a new instance of a controller, adds a view and adds an action listener to that views button.
     */
    public MyController() {
        this.view = new UnitTesterGUI();
        this.view.show();
        this.view.addEnterListener(new EnterListener());

    }

    /**
     * Inner class which defines what should happen when the button in the view is pressed. In this case a swing worker
     * is started and runs its doInBackground-method.
     */
    private class EnterListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            UnitTestWorker worker = new UnitTestWorker();
            worker.doInBackground();
        }
    }

    /**
     * Implementation of a swing worker. Added an attribute to provide the thread with a text area to write its results
     * to.
     */
    private class UnitTestWorker extends SwingWorker<String, String> {
        private final JTextArea output = view.getResults();

        /**
         * Creates a new instance of a UnitTester, calls methods in it which sets up and runs test-methods. Passes
         * result to the publishResults method which in turn passes it to the GUI.
         * @return Null.
         */
        @Override
        protected String doInBackground() {
            UnitTester model = new UnitTester();
            List<String> results;
            results = model.setupTests(view.getInput().getText());
            publish("Running test-methods in: " + view.getInput().getText());

            if(!results.isEmpty()) {
                publishResults(results);
                return null;
            }
            results = model.runTests();
            publishResults(results);
            printSummary(model);
            return null;
        }

        /**
         * Implementation of swing workers process method. Adds the lines of results to the GUI text area.
         * @param chunks List of strings containing intermediate results to process.
         *
         */
        @Override
        protected void process(List<String> chunks) {
            for (String line: chunks) {
                output.append(line);
                output.append("\n");
            }
        }

        /**
         * Iterates over a list of strings and publishes each of them.
         * @param results A list of string to be published.
         */
        private void publishResults(List<String> results) {
            for (String i: results) {
                publish(i);
            }
        }

        /**
         * Prints a summary of the test results.
         * @param model An instance of a UnitTester class.
         */
        private void printSummary(UnitTester model) {
            publish("\n --- Test summary ---");
            publish(model.getSuccess() + " test(s) succeeded");
            publish(model.getFail() + " test(s) failed");
            publish(model.getFailedByException() + " test(s) failed by an exception\n");
        }
    }
}
