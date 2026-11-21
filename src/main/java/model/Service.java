package model;

public abstract class Service {
    protected String name;
    protected String bay;
    private int hoursRemaining;      // 0 = free; >0 = busy for that many more hours (starts at 0 automatically)

    public Service(String name, String bay){
        this.name = name;
        this.bay = bay;
    }

    public abstract int getDuration();
    public boolean isActive(){
        return hoursRemaining > 0;
        }

    public void deploy(String newBay) {      // the porter moved this service to newBay; its action starts now
        bay = newBay;
        hoursRemaining = getDuration();
    }                                        // end of deploy

    public void hourPassed() {               // called once every hospital hour
        if (hoursRemaining>0){
            hoursRemaining--;
        }
    }                                        // end of hourPassed

    public String getName() {                // read-only access to the name
        return name;
    }                                        // end of getName

    public String getBay() {                 // read-only access to the current bay
        return bay;
    }                                        // end of getBay
}
