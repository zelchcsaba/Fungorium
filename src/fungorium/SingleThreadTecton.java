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

        this.t.toCall("getMushroom"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(mushroom);
        this.t.toReturn();

        return mushroom;
    }


//ha nincs meg mushroom rarakjuk
    public boolean putMushroom(Mushroom m) {
        if(mushroom == null){
            mushroom = m;
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

        this.t.toCall("deleteUnnecessaryThreads"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        thread = null;
        return true;
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
