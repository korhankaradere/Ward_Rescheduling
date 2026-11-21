package model;

import hospital.HospitalInformation;
import hospital.MedicalNotification;
import java.util.ArrayList;

public class ServiceManager {

    private final ArrayList<Service> services = new ArrayList<>();
    private final ArrayList<MedicalNotification> waiting = new ArrayList<>();

    public void addService(Service s){
        services.add(s);
    }

    public ArrayList<Service> getServices() {   // return TYPE goes before the name: "this gives back a list of Services"
        return new ArrayList<>(services);       // the VALUE to give back: a copy of our list
    }                                           // end of getServices

    private Service findService(String name) {    // gives back the Service whose name matches, or null
        for (Service s : services) {              // look at each service in turn
            if (s.getName().equals(name)) {       // is THIS the one we want? (equals, not ==)
                return s;                         // yes: hand it back and stop searching
            }                                     // end of if
        }                                         // end of loop
        return null;                              // checked them all, none matched: "nothing found"
    }                                             // end of findService

    public void runOneHour() {                    // everything that happens in ONE hospital hour
        for (Service s : services){
            s.hourPassed();
        }
        // STEP 2: ask the doctor
        MedicalNotification n = HospitalInformation.getMedicalNotificationFromDoctor(); // the doctor's request for this hour
        if (!n.getService().equals("None")) {        // "None" means nothing is needed this hour
            waiting.add(n);                          // otherwise put the request in the waiting list
        }                                            // end of if

        // STEP 3: serve every waiting request whose service is free
        for (int i = 0; i < waiting.size(); i++) {   // go through the waiting requests, oldest first
            MedicalNotification req = waiting.get(i); // the request at position i
            Service s = findService(req.getService()); // which service does it need?
            if (s != null && !s.isActive()) {        // it exists AND is free (never call the porter while busy!)
                HospitalInformation.requestPorter(req.getBay(), req.getService()); // tell the real world (porter)
                s.deploy(req.getBay());              // update OUR records: new bay + countdown starts
                waiting.remove(i);                   // served: take it off the list
                i--;                                 // the list shifted left, so check this position again
            }                                        // end of if
        }                                            // end of loop
    }

    public void printReport(int hour) {              // the 24-hour console report; hour = current hospital hour
        System.out.println("===== Report at hospital hour " + hour + " ====="); // a header line
        for (Service s : services) {                 // one line per service
            System.out.println(s.getName() + " in " + s.getBay()
                    + (s.isActive() ? " - ACTIVE" : " - idle")); // name, location and status
        }                                            // end of loop
        System.out.println("Waiting requests: " + waiting.size()); // how many requests are still queued
    }                                                // end of printReport

}
