package fungorium;

import java.util.ArrayList;
import java.util.List;

public abstract class Tecton {

    Tester t;
    protected List<Spore> spores;
    protected List<Tecton> neighbors;
    protected Insect i;

    /**
     * konstruktor
     * @param t
     */
    public Tecton(Tester t) {
        this.t = t;
        spores = new ArrayList<>();
        neighbors = new ArrayList<>();
        i = null;
    }

    /**
     *
     * @param list
     */
    public void setSpores(List<Spore> list) {
        spores = list;
    }

    /**
     *
     * @return
     */
    public List<Spore> getSpores() {
        t.toCall("getSpores");
        t.returnValue.clear();
        t.returnValue.addAll(spores);
        t.toReturn();
        return spores;
    }

    public void setNeighbors(List<Tecton> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Tecton> getNeighbors() {
        t.toCall("getNeighbors");
        this.t.returnValue.clear();
        this.t.returnValue.addAll(neighbors);
        this.t.toReturn();
        return neighbors;
    }

    /**
     * rárakja a tektonra a bogarat
     * @param i
     */
    public void setInsect(Insect i) {
        t.toCall("setInsect");
        t.returnValue.clear();
        t.toReturn();

        this.i = i;
    }

    /**
     *
     * @return
     */
    public Insect getInsect() {
        return i;
    }

    // abstract metodusok
    public abstract void setMushroom(Mushroom mushroom);

    public abstract Mushroom getMushroom();

    public abstract void setThreads(List<FungalThread> threads);

    public abstract List<FungalThread> getThreads();

    public abstract List<FungalThread> getThreadsWithoutCout(); // Ez eskü jól jön

    public abstract boolean putMushroom(Mushroom m);

    public abstract boolean putThread(FungalThread f);

    public abstract boolean removeMushroom();

    public abstract boolean removeThread(FungalThread f);

    public abstract boolean breakTecton();

    public abstract boolean putFirstMushroom();

    // TO DO
    /**
     *
     * @param sp
     * @param t
     * @return
     */
    public boolean putSpore(Spore sp, Tecton t) {
        this.t.toCall("putSpore");
        if (!neighbors.contains(t)) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        } else {
            spores.add(sp);
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
    }

    /**
     * visszaadja azokat a szomszédos tektonokat, amelyeken el van ágazva az f fonál
     * @param f
     * @return
     */
    public List<Tecton> getThreadSection(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getThreadSection");

        List<Tecton> tectons = new ArrayList<>();

        // végigmegy a szomzsédokon, és lekéri a threads tömbjüket, ha ebben benne van
        // f, akkor hozzáadja a tectons listához
        for (int i = 0; i < neighbors.size(); i++) {



            List<FungalThread> list = neighbors.get(i).getThreadsWithoutCout();
            if(list!=null){
                if (list.contains(f)) {
                    tectons.add(neighbors.get(i));
                }
            }
        }

        this.t.returnValue.clear();
        this.t.returnValue.addAll(tectons);
        this.t.toReturn();

        return tectons;
    }

    // to do
    public boolean putFirstInsect() {
        if (i == null) {
            return true;
        } else {
            return false;
        }
    }

    // to do
    public boolean putInsect(Insect i, Tecton t) {
        return true;
    }

    public boolean removeInsect() {
        if (i != null) {
            i = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * true értéket ad vissza, ha a kapott tekton szomszédos
     * @param t
     * @return
     */
    public boolean isNeighbor(Tecton t) {
        //meghívja a tester kiíró függvényét
        this.t.toCall("isNeighbor");
        if (neighbors.contains(t)) {

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        } else {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return false;
        }
    }

    /**
     * hozzáadja a neighbors listához a kapott tektonokat
     * @param tlist
     * @return
     */
    public boolean addNeighbor(List<Tecton> tlist) {

        // meghívja a tester kiíró függvényét
        this.t.toCall("addNeighbor");

        neighbors.addAll(tlist);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        return true;
    }

    /**
     * kitörli a kapott tektont a neighbors listából
     * @param t
     * @return
     */
    public boolean removeNeighbor(Tecton t) {
        this.t.toCall("removeNeighbor");
        neighbors.remove(t);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        return true;
    }

    // to do
    public boolean putEvolvedSpore(Spore sp, Tecton t) {
        this.t.toCall("putEvolvedSpore");
        if (!neighbors.contains(t)) {
            for(int i = 0; i < neighbors.size(); i++){
                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                if(neighbors.get(i).getNeighbors().contains(t)){
                    spores.add(sp);
                    this.t.returnValue.clear();
                    this.t.returnValue.add(Boolean.TRUE);
                    this.t.toReturn();
                    return true;
                }
            }
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        } else {
            spores.add(sp);
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
    }

    public boolean removeSpores(List<Spore> slist) {
        spores.removeAll(slist);
        return true;
    }

}
