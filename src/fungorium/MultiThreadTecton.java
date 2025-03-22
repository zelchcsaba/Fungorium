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
        t.toCall("getThreads");
        t.from=this;
        t.returnValue=new ArrayList<>(threads);
        t.toReturn();
        return threads;
    }

    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for(Tecton tecton : neighbors){
            t.caller=this;
            t.called=tecton;
            t.parameters.clear();
            t.to=this;
            tecton.getThreads();
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
