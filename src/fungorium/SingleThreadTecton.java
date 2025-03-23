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

    public void setThreads(List<FungalThread> list){
        t.toCall("setThreads");
        t.list.add(this);
        t.list.add(list);
        t.parameters.clear();
        t.parameters.add(this);

        thread = list.getFirst();

        t.returnValue.clear();
        t.returnValue.add(Boolean.TRUE);
        t.toReturn();
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


//ha nincs meg mushroom rarakjuk
    public boolean putMushroom(Mushroom m) {
        t.toCall("putMushroom");
        t.list.add(this);
        t.list.add(m);
        t.parameters.clear();
        t.parameters.add(this);

        if(mushroom == null) {
            mushroom = m;
            t.returnValue.clear();
            t.returnValue.add(Boolean.TRUE);
            t.toReturn();
            return true;
        }
        return false;
    }

    //ha van rajta mushroom toroljuk
    public boolean removeMushroom() {
        if(mushroom != null){
            mushroom = null;
            return true;
        }else{
            return false;
        }
    }

    //ha nincs egy fonal se rajta, akkor lehet fonalat helyezni ra
    public boolean putThread(FungalThread f) {
        if(thread == null){
            thread = f;
            return true;
        }else{
            return false;
        }
    }


//ha a kapott thread megegyezik az eltarolt threaddel
    public boolean removeThread(FungalThread f) {
        if(thread.equals(f)){
            thread = null;
            return true;
        }else{
            return false;
        }
    }

    //to do
    public boolean breakTecton() {
        return true;
    }

//To do
    public boolean putFirstMushroom() {
        if(mushroom == null){
           return true;
        }else{
            return false;
        }
    }

}
