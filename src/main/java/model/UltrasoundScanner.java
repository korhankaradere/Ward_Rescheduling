package model;

public class UltrasoundScanner extends Service{

    public UltrasoundScanner(){
        super("Ultrasound", "Nightingale");
    }

    @Override
    public int getDuration(){
        return 3;
    }
}
