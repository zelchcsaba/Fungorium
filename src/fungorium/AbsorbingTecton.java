package fungorium;

import java.util.ArrayList;
import java.util.List;

public class AbsorbingTecton extends Tecton{

    private List<FungalThread> threads;

    //kontruktor
    public AbsorbingTecton(Tester t){
        super(t);
        threads = new ArrayList();
    }

    //setterek getterek
    public void setMushroom(Mushroom mushroom){}

    //visszaadja a tektonon található gombatestet
    public Mushroom getMushroom(){
        //meghívja a tester kiíró függvényét
        this.t.toCall("getMushroom"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(null);
        this.t.toReturn();
        //absorbing tektonon nem lehet gombatest, ezért a visszatérési érték null
        return null;
    }

    public void setThreads(List<FungalThread> list){
        threads = list;
    }

    //visszaadja azokat a gombafonalakat, amelyek rajta vannak
    public List<FungalThread> getThreads(){
        t.toCall("getThreads");
        t.returnValue.clear();
        t.returnValue.addAll(threads);

        t.toReturn();

        return threads;
    }
    public List<FungalThread> getThreadsWithoutCout(){
        return threads;
    }

//To do
    public void absorb() {}

    //false, mert nem lehet Mushroomot lehelyezni
    public boolean putMushroom(Mushroom m) {
        return false;
    }

    public boolean putThread(FungalThread f) {
        threads.add(f);
        return true;
    }

    //false mert nem tud Mushroom nőni
    public boolean removeMushroom() {
        return false;
    }
//a kapott fonalat kitörli a listájából
    public boolean removeThread(FungalThread f) {
        //meghívja a tester kiíró függvényét
        this.t.toCall("removeThread"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        threads.remove(f);
        return true;
    }


    //false, mert nem lehet gombát rárakni
    public boolean putFirstMushroom() {
        t.toCall("putFirstMushroom");
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.FALSE);
        this.t.toReturn();
        return false;
    }

    
    //ketté törik a tekton
    public boolean breakTecton() {
        //meghívja a tester kiíró függvényét    
        this.t.toCall("removeThread");
    
        //létrejön a két új tekton
        Tecton t6 = new AbsorbingTecton(t);
        Tecton t7 = new AbsorbingTecton(t);
    
        t.toCreate(this, t6, "t6");
        t.toCreate(this, t7, "t7");
    
        //ez lesz a töréspont a tektonon
        int centre = neighbors.size()/2;
    
        //létrehozok egy listát, amelyben a t6 tekton szomszédai lesznek
        List<Tecton> neighborList = new ArrayList<>();
        for(int i=0; i<centre; i++){
            neighborList.add(neighbors.get(i));
        }
        neighborList.add(t7);
    
        this.t.list.add(this);
        this.t.list.add(t6);
        this.t.parameters.clear();
        this.t.parameters.addAll(neighborList);
        //beállítom a t6 szomszédait
        t6.addNeighbor(neighborList);
        neighborList.clear();
    
        //létrehozok egy listát, amelyben a t7 szomszédai lesznek
        neighborList.add(t6);
        for(int i=centre; i<neighbors.size(); i++){
            neighborList.add(neighbors.get(i));
        }
    
        this.t.list.add(this);
        this.t.list.add(t7);
        this.t.parameters.clear();
        this.t.parameters.addAll(neighborList);
        //beállítom a t7 szomszédait
        t7.addNeighbor(neighborList);
        neighborList.clear();
    
        //a jelenlegi tekton szomszédait beállítom, hozzáadva szomszédsági listájukhoz a megfelelő létrejött tektont
        //valamint kivéve a kettétötött tektont
        neighborList.add(t6);
        for(int i=0; i<centre; i++){
    
            this.t.list.add(this);
            this.t.list.add(neighbors.get(i));
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
    
            neighbors.get(i).addNeighbor(neighborList);
    
            this.t.list.add(this);
            this.t.list.add(neighbors.get(i));
            this.t.parameters.clear();
            this.t.parameters.add(this);
    
            neighbors.get(i).removeNeighbor(this);
        }
    
        neighborList.clear();
    
        neighborList.add(t7);
        for(int i=centre; i<neighbors.size(); i++){
    
            this.t.list.add(this);
            this.t.list.add(neighbors.get(i));
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
    
            neighbors.get(i).addNeighbor(neighborList);
    
            this.t.list.add(this);
            this.t.list.add(neighbors.get(i));
            this.t.parameters.clear();
            this.t.parameters.add(this);
    
            neighbors.get(i).removeNeighbor(this);
        }
    
        this.t.list.add(this);
        this.t.list.add(t6);
        this.t.parameters.clear();
        this.t.parameters.add(i);
        //a tektonon levő bogarat ráhelyezem a t6-ra
        t6.setInsect(i);
    
        this.t.list.add(this);
        this.t.list.add(i);
        this.t.parameters.clear();
        this.t.parameters.add(t6);
        //beállítom a bogár pozícióját
        i.setPosition(t6);
    
        this.t.list.add(this);
        this.t.list.add(i);
        this.t.parameters.clear();
        this.t.parameters.add(t6);
        //kitörlöm a tektont a fonálról
        for(int i=0; i<threads.size();i++){
            threads.get(i).removeTecton(this);
        }
    
        //ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik gombatesthez ezt eltávolítom
        System.out.println("deleteUnnecessaryThreads");
    
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();
    
        return true;
    }

}
