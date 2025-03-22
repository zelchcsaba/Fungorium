package fungorium;

import java.util.ArrayList;
import java.util.List;

public abstract class Tecton {

    Tester t;
    protected  List<Spore> spores;
    protected List<Tecton> neighbors;
    protected  Insect i;

//konstruktor
    public Tecton(Tester t){
        this.t = t;
        spores = new ArrayList<>(); 
        neighbors = new ArrayList<>();
        i = null;
    }

    //getter setter
    public void setSpores(List<Spore> list){
        spores = list;
    }
    public List<Spore> getSpores(){
        return spores;
    }

    public void setNeighbors(List<Tecton> neighbors){
        this.neighbors=neighbors;
    }

    public List<Tecton> getNeighbors(){
        return neighbors;
    }

    public void setInsect(Insect i){
        this.i = i;
    }

    public Insect getInsect(){
        return i;
    }

    //abstract metodusok
    public abstract void setMushroom(Mushroom mushroom);
    public abstract Mushroom getMushroom();
    public abstract void setThreads(List<FungalThread> threads);
    public abstract List<FungalThread> getThreads();
    
    public abstract boolean putMushroom(Mushroom m);
    public abstract boolean putThread(FungalThread f);
    public abstract boolean removeMushroom();
    public abstract boolean removeThread(FungalThread f);
    public abstract boolean breakTecton();
    public abstract boolean putFirstMushroom();
    

    //TO DO
    public boolean putSpore(Spore sp, Tecton t) {return true;}
//To do
    public List<Tecton> getThreadSection(FungalThread f) {return null;}
//to do
    public boolean putFirstInsect() {
        if(i == null){
            return true;
         }else{
             return false;
         }
    }

    //to do
    public boolean putInsect(Insect i, Tecton t) {return true;}

    public boolean removeInsect() {
        if(i!=null){
            i=null;
            return true;
        }else{
            return false;
        }
    }


    public boolean isNeighbor(Tecton t) {
        if(neighbors.contains(t)){
            return true;
        }else{
            return false;
        }
    }

    public boolean addNeighbor(List<Tecton> tlist) {
        neighbors.addAll(tlist);
        return true;
    }

    //to do
    public boolean putEvolvedSpore(Spore sp, Tecton t) {return true;}


    public boolean removeSpores(List<Spore> slist) {
        spores.removeAll(slist);
        return true;
    }

}
