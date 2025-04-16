package model;

public class LongLifeThread extends FungalThread{
    public LongLifeThread(){
        super();
    }

    public boolean sendToDie(Tecton t){
        timeToDie ttd = new timeToDie();
        ttd.setTecton(t);
        ttd.setTime(2);
        life.add(ttd);
        return true;
    }
}
