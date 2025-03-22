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

    //To do
    public boolean breakTecton() {
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

}
