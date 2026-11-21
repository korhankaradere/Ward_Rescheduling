package ui;                                              // the UI package: only display code lives here

import model.Service;                                    // the grid shows Services, so it needs to know the type
import javax.swing.*;                                    // JFrame, JPanel, JLabel, BorderFactory, SwingConstants
import java.awt.*;                                       // GridLayout, BorderLayout, Color
import java.util.ArrayList;                              // the list of services passed in

public class BayGrid {                                   // shows one cell per bay; knows NOTHING about scheduling logic
    private final JFrame frame = new JFrame("ICU Ward Services"); // the window
    private final String[] bays;                         // bay names, in grid order
    private final JLabel[] labels;                       // one text label per bay (same order as bays)

    public BayGrid(String[] bays) {                      // build the window once, with an empty grid
        this.bays = bays;                                // remember the bay names
        labels = new JLabel[bays.length];                // an array with one slot per bay (8)
        frame.setLayout(new GridLayout(2, 4, 5, 5));     // 2 rows x 4 columns, 5px gaps: cells fill left->right, top->bottom
        for (int i = 0; i < bays.length; i++) {          // create one cell per bay
            JPanel cell = new JPanel(new BorderLayout()); // the cell is a panel...
            cell.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // ...with a visible border
            labels[i] = new JLabel("", SwingConstants.CENTER); // ...holding a centred label (text filled in by refresh)
            cell.add(labels[i], BorderLayout.CENTER);    // the label fills the cell
            frame.add(cell);                             // add the cell to the grid
        }                                                // end of loop
        frame.setSize(900, 400);                         // window size in pixels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closing the window ends the program
        frame.setVisible(true);                          // show it LAST, after everything is added
    }                                                    // end of constructor

    public void refresh(ArrayList<Service> services) {   // redraw every cell from the CURRENT state of the services
        for (int i = 0; i < bays.length; i++) {          // for each bay (cell)...
            String text = "<html><b>" + bays[i] + "</b>"; // ...start with the bay name in bold
            for (Service s : services) {                 // look at every service
                if (s.getBay().equals(bays[i])) {        // is this service in THIS bay? (equals, not ==)
                    text += "<br>" + s.getName() + (s.isActive() ? " - ACTIVE" : " - idle"); // add a line for it
                }                                        // end of if
            }                                            // end of inner loop
            text += "</html>";                           // close the HTML so <br> line breaks work
            labels[i].setText(text);                     // put the text into this bay's cell
        }                                                // end of outer loop
    }                                                    // end of refresh
}                                                        // end of BayGrid