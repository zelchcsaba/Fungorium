package fungorium;

import java.util.List;
import java.util.ArrayList;

public class AbsorbingTecton extends Tecton{

    private List<FungalThread> threads;

    //kontruktor
    public AbsorbingTecton(Tester t){
        super(t);
        threads = new ArrayList();
    }

    //setterek getterek
    public void setThreads(ArrayList<FungalThread> list){
        threads = list;
    }

    public List<FungalThread> getThreads(){
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


    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }

    //To do
    public boolean breakTecton() {
        return true;
    }

    //false, mert nem lehet gombát rárakni
    public boolean putFirstMushroom() {
        return false;
    }

}
