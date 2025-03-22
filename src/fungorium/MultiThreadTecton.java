package fungorium;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadTecton extends Tecton{

    private Mushroom mushroom;
    private List<FungalThread> threads;

    //konstruktor
    public MultiThreadTecton(Tester t){
        super(t);
        mushroom = null;
        threads = new ArrayList<>();
    }

    //getter setter
    public void setMushroom(Mushroom mushroom){
        this.mushroom=mushroom;
    }

    public Mushroom getMushroom(){
        return mushroom;
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

    //lerak gomba ha meg nincs
    public boolean putMushroom(Mushroom m) {
        if(mushroom == null){
            mushroom = m;
            return true;
        }
        return false;
    }

    //kitorol gomba ha van
    public boolean removeMushroom() {
        if(mushroom != null){
            mushroom = null;
            return true;
        }else{
            return false;
        }
    }

    //kitorol kapott fonal
    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }

    //todo
    public boolean breakTecton() {return true;}

    public boolean putFirstMushroom() {
        if(mushroom == null){
            return true;
         }else{
             return false;
         }
    }

}
