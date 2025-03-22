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

        t.returnValue.clear();
        t.returnValue.addAll(threads);

        t.toReturn();

        return threads;
    }

    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for(Tecton tecton : neighbors){
            t.list.add(this);
            t.list.add(tecton);
            t.parameters.clear();

            tecton.getThreads();
        }

        threads.add(f);
        t.returnValue.clear();

        t.returnValue.add(Boolean.TRUE);
        t.toReturn();
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
