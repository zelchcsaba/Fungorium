package fungorium;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadTecton extends Tecton{
    
    Tester t;
    private Mushroom mushroom;
    private FungalThread thread;

    public SingleThreadTecton(Tester t){
        super(t);
        mushroom = null;
        thread = null;
    }

    public void setThreads(ArrayList<FungalThread> list){
        thread = list.get(0);
    }

    public List<FungalThread> getThreads(){
        ArrayList<FungalThread> list= new ArrayList<>();
        list.add(thread);
        return list;
    }

    public void setMushroom(Mushroom mushroom){
        this.mushroom=mushroom;
    }

    public Mushroom getMushroom(){
        return mushroom;
    }


//To do
    public boolean putMushroom(Mushroom m) {}
    public boolean putThread(FungalThread f) {
        if(thread == null){
            thread = f;
        }
    }
    public boolean removeMushroom() {}
    public boolean removeThread(FungalThread f) {}
    public boolean breakTecton() {}
    public boolean putFirstMushroom() {}

}
