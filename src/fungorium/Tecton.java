package fungorium;

import java.util.ArrayList;
import java.util.List;

public abstract class Tecton {

    Tester t;
    protected  List<Spore> spores;
    protected List<Tecton> neighbors;
    protected  Insect i;

    public Tecton(Tester t){
        this.t = t;
        spores = new ArrayList<>(); 
        neighbors = new ArrayList<>();
        i = null;
    }
    public void setNeighbors(List<Tecton> neighbors){
        this.neighbors=neighbors;
    }

    public abstract boolean putMushroom(Mushroom m);
    public abstract boolean putThread(FungalThread f);
    public abstract boolean removeMushroom();
    public abstract boolean removeThread(FungalThread f);
    public abstract boolean breakTecton();
    public abstract boolean putFirstMushroom();
    public abstract List<FungalThread> getThreads();

    //TO DO
    public boolean putSpore(Spore sp, Tecton t) {return true;}
    public List<Tecton> getThreadSection(FungalThread f) {return null;}
    public boolean putFirstInsect() {return true;}
    public boolean putInsect(Insect i, Tecton t) {return true;}
    public boolean removeInsect() {return true;}
    public boolean isNeighbor(Tecton t) {return true;}
    public boolean addNeighbor(List<Tecton> tlist) {return true;}
    public boolean putEvolvedSpore(Spore sp, Tecton t) {return true;}
    public boolean removeSpores(List<Spore> slist) {return true;}

}
