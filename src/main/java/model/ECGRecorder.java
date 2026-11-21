package model;

public class ECGRecorder extends Service{

    public ECGRecorder(){
        super("ECG", "Seacole");
    }

    @Override
    public int getDuration(){
        return 1;
    }
}
