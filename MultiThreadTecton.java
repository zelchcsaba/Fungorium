import java.util.ArrayList;

public class MultiThreadTecton {

    private Mushroom mushroom;
    private ArrayList<FungalThread> threads;

    public MultiThreadTecton(){

    }

    public void setMushroom(Mushroom m){
        mushroom = m;
    }

    public Mushroom getMushroom(){
        return mushroom;
    }

    public ArrayList<FungalThread> getThreads(){
        return threads;
    }

    public boolean putMushroom(Mushroom m) {}
    
    public boolean putThread(FungalThread f) {
        threads.add(f);
        return true;
    }
    public boolean removeMushroom() {
        mushroom = null;
        return true;
    }
    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }

    public boolean breakTecton() {}
    public boolean putFirstMushroom() {}

}
