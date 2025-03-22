import java.util.ArrayList;

public abstract class Tecton {

    private ArrayList<Spore> spores;
    private ArrayList<Tecton> neighbors;
    private Insect i;

    public Tecton(){

    }

    abstract void setMushroom();
    abstract Mushroom getMushroom();
    abstract ArrayList<FungalThread> getThreads();

    public boolean putSpore(Spore sp, Tecton t) {}
    abstract boolean putMushroom(Mushroom m);
    abstract boolean putThread(FungalThread f);
    abstract boolean removeMushroom();
    abstract boolean removeThread(FungalThread f);

    public ArrayList<Tecton> getThreadSection(FungalThread f){
        for(int i=0; i<neighbors.size();i++){
            neighbors.getThread
        }
    }

    abstract boolean breakTecton();
    abstract boolean putFirstMushroom();
    public boolean putFirstInsect() {}
    public boolean putInsect(Insect i, Tecton t) {}
    public boolean removeInsect() {}
    public boolean isNeighbor(Tecton t) {}
    public boolean addNeighbor(List<Tecton> tlist) {}
    public boolean putEvolvedSpore(Spore sp, Tecton t) {}
    public boolean removeSpores(List<Spore> slist) {}

}
