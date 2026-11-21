package model;

public class DialysisMachine extends Service{

    public DialysisMachine(){
        super("Dialysis", "Henderson");
    }

    @Override
    public int getDuration(){
        return 4;
    }
}
