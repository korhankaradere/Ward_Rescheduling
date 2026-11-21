package model;

public class Masseur extends Service{
    public Masseur(){
        super("Massage", "Seacole");
    }

    @Override
    public int getDuration(){
        return 2;
    }
}
