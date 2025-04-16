package model;

public class ShortLifeThread extends FungalThread{
    public ShortLifeThread(){
        super();
    }

    public boolean sendToDie(Tecton t){
        timeToDie ttd = new timeToDie();
        ttd.setTecton(t);
        ttd.setTime(1);
        life.add(ttd);
        return true;
    }
}
