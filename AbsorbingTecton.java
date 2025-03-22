import java.util.ArrayList;

public class AbsorbingTecton {

    private ArrayList<FungalThread> threads;

    public AbsorbingTecton(){

    }

    public void setMushroom(){}

    public Mushroom getMushroom(){
        return null;
    }

    public ArrayList<FungalThread> getThreads(){
        return threads;
    }

    public void absorb() {}
    public boolean putMushroom(Mushroom m) {
        return false;
    }
    public boolean putThread(FungalThread f) {
        threads.add(f);
        return true;
    }
    public boolean removeMushroom() {
        return false;
    }
    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }
    //implementálni
    public boolean breakTecton() {}

    public boolean putFirstMushroom() {
        return false;
    }

}
