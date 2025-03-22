package fungorium;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadTecton extends Tecton{
    
    private Mushroom mushroom;
    private FungalThread thread;

    public SingleThreadTecton(Tester t){
        super(t);
        mushroom = null;
        thread = null;
    }

    public void setThreads(List<FungalThread> list){
        if(list==null){
            thread=null;
        }
        else{
            thread = list.get(0);
        }
    }

    public List<FungalThread> getThreads(){
        if(thread == null) return null;
        else{
            ArrayList<FungalThread> list= new ArrayList<>();
            list.add(thread);
            return list;
        }       
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
        t.toCall("putThread");
        if(thread == null && !neighbors.isEmpty()){
            for(Tecton tecton : neighbors){
                List<FungalThread> threads = tecton.getThreads();
                if (threads != null) { // Csak akkor iterálj, ha nem null
                    for (FungalThread fungals : threads) {
                        if(fungals!=null && fungals.equals(f)){
                            thread = f;
                            t.returnValue.clear();
                            t.returnValue.add(Boolean.TRUE);
                            t.toReturn();
                            return true;
                        }
                    }
                }
            }
            thread = f;
            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();
            return false;
        }
        else{
            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();
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
