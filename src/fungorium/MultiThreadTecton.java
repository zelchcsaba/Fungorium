package fungorium;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadTecton extends Tecton{

    private Mushroom mushroom;
    private List<FungalThread> threads;

    public MultiThreadTecton(Tester t){
        super(t);
        mushroom = null;
        threads = new ArrayList<>();
    }

    public void setMushroom(Mushroom mushroom){
        this.mushroom=mushroom;
    }

    public void setThreads(List<FungalThread> threads){
        this.threads=threads;
    }
    
    public List<FungalThread> getThreads(){
        return threads;
    }

    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for(Tecton t : neighbors){
            t.getThreads();
        }
        
        threads.add(f);
        return true;
    }

    // TO DO
    public boolean putMushroom(Mushroom m) {return true;}
    public boolean removeMushroom() {return true;}
    public boolean removeThread(FungalThread f) {return true;}
    public boolean breakTecton() {return true;}
    public boolean putFirstMushroom() {return true;}

}
