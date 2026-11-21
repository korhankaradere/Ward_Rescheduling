package app;
import hospital.HospitalInformation;
import hospital.MedicalNotification;
import model.ServiceManager;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import model.*;
import ui.BayGrid;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static int hour = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServiceManager manager = new ServiceManager();     // the brain
            manager.addService(new UltrasoundScanner());       // starts in Nightingale
            manager.addService(new ECGRecorder());             // starts in Seacole
            manager.addService(new Masseur());                 // starts in Seacole
            manager.addService(new DialysisMachine());         // starts in Henderson

            BayGrid grid = new BayGrid(HospitalInformation.bays); // the window with 8 bay cells
            grid.refresh(manager.getServices());               // show the starting state

            Timer timer = new Timer(3000, e -> {               // every 3 s = 1 hospital hour
                hour++;                                        // count the hour
                manager.runOneHour();                          // 1) doctor, porter, countdowns
                grid.refresh(manager.getServices());           // 2) update the grid
                if (hour % 24 == 0) manager.printReport(hour); // 3) report every 24 hours
            });                                                // end of the timer block
            timer.start();                                     // start the hospital clock
        });                                                    // end of invokeLater
    }
}