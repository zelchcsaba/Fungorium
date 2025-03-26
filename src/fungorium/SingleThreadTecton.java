package fungorium;

import java.util.ArrayList;
import java.util.List;

/**
 * A SingleThreadTecton osztály a Tecton osztályból származik, és egy olyan különleges tektont reprezentál,
 * amelyen legfeljebb egy gomba (Mushroom) és egy fonál (FungalThread) lehet jelen.
 */
public class SingleThreadTecton extends Tecton {

    private Mushroom mushroom;
    private FungalThread thread;


    /**
     * Egy új SingleThreadTecton példányt hoz létre a megadott Tester példánnyal.
     *
     * @param t A Tester példány, amely kapcsolódik az új SingleThreadTecton objektumhoz.
     */
    public SingleThreadTecton(Tester t) {
        super(t);
        mushroom = null;
        thread = null;
    }


    /**
     * Beállítja a tektonon található gombafonalakat.
     *
     * @param list A beállítani kívánt gombafonalak listája.
     */
    public void setThreads(List<FungalThread> list) {
        t.toCall("setThreads");
        t.list.add(this);
        t.list.add(list);
        t.parameters.clear();
        t.parameters.add(this);

        thread = list.get(0);

        t.returnValue.clear();
        t.returnValue.add(Boolean.TRUE);
        t.toReturn();
    }


    /**
     * Visszaadja azokat a gombafonalakat, amelyek rajta vannak a tektonon.
     *
     * @return A rajta lévő gombafonalak listája. Ha nincs rajta gombafonal, akkor null-t ad vissza.
     */
    public List<FungalThread> getThreads() {

        t.toCall("getThreads");
        // berakom egy listába a fonalat
        ArrayList<FungalThread> list = new ArrayList<>();
        if (thread == null) {
            t.returnValue.clear();
            t.toReturn();
            return null;
        } else {
            list.add(thread);
        }

        t.returnValue.clear();
        t.returnValue.addAll(list);
        t.toReturn();
        return list;
    }


    /**
     * Beállítja a tektonon lévő gombatestet a megadott gombával.
     *
     * @param mushroom A beállítandó gomba objektum.
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }


    /**
     * Visszaadja a tektonon található gombatestet.
     *
     * @return A gombatest, amely a tektonon található.
     */
    public Mushroom getMushroom() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getMushroom");

        this.t.returnValue.clear();
        this.t.returnValue.add(mushroom);
        this.t.toReturn();

        return mushroom;
    }


    /**
     * Ráhelyez egy gombatestet a tektonra, ha még nincs rajta másik gombatest.
     *
     * @param m a hozzáadni kívánt gombatest
     * @return igaz, ha sikerült a gombatestet ráhelyezni, hamis, ha már volt rajta egy másik gombatest
     */
    public boolean putMushroom(Mushroom m) {
        t.toCall("putMushroom");
        t.parameters.clear();
        t.parameters.add(this);

        if (mushroom == null) {
            mushroom = m;
            return true;
        }
        return false;
    }


    /**
     * Eltávolítja a tektonról a hozzá kapcsolt gombatestet, ha az létezik.
     *
     * @return true, ha sikeresen eltávolította a gombát, false, ha nem volt hozzá tartozó gombatest.
     */
    public boolean removeMushroom() {

        t.toCall("removeMushroom");

        if (mushroom != null) {
            mushroom = null;

            t.returnValue.clear();
            t.returnValue.add(Boolean.TRUE);
            t.toReturn();

            return true;
        } else {

            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();

            return false;
        }
    }


    /**
     * Ha nincs egy fonal se rajta és van szomszédos tekton, akkor lehet fonalat helyezni rá.
     * Ellenőrzi, hogy a szomszédos tektonokon az adott fonal már szerepel-e,
     * és ennek megfelelően adja hozzá a jelenlegi objektumhoz a fonalat, vagy elutasítja.
     *
     * @param f A hozzáadni kívánt gombafonal.
     * @return true, ha sikeresen el lett helyezve a gombafonal;
     * false, ha a feltételek nem teljesültek (pl.: már van rajta fonal, vagy "f" nem található a szomszédok között).
     */
    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        if (thread == null && !neighbors.isEmpty()) {
            for (Tecton tecton : neighbors) {
                List<FungalThread> threads = tecton.getThreadsWithoutCout();
                if (threads != null) { // Csak akkor iterálj, ha nem null
                    for (FungalThread fungals : threads) {
                        if (fungals != null && fungals.equals(f)) {
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
        } else {
            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();
            return false;
        }
    }


    /**
     * Egy FungalThread típusú gombafonal hozzáadása a tektonhoz.
     *
     * @param f A hozzáadni kívánt gombafonal (FungalThread objektum).
     */
    public void addThread(FungalThread f) {
        thread = f;
    }


    /**
     * Törli a megadott gombafonalat, ha megegyezik a tárolt gombafonallal.
     *
     * @param f A törölni kívánt FungalThread objektum.
     * @return Igaz értéket ad vissza, ha a törlés sikeres.
     */
    public boolean removeThread(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("removeThread");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        thread = null;
        return true;
    }


    /**
     * Ellenőrzi, hogy van-e gomba (mushroom) az aktuális tektonon (tecton).
     * Ha nincs gomba, a metódus sikeres végrehajtás esetén true értékkel tér vissza.
     *
     * @return true, ha nincs gomba az aktuális tektonon, különben false.
     */
    public boolean putFirstMushroom() {
        return mushroom == null;
    }


    /**
     * Visszaadja a tektonhoz tartozó gombafonalak listáját, a kiíratás mellőzésével.
     *
     * @return A tektonhoz tartozó gombafonalak listája, ha van; egyébként null.
     */
    public List<FungalThread> getThreadsWithoutCout() {
        ArrayList<FungalThread> list = new ArrayList<>();
        if (thread == null) {
            return null;
        } else {
            list.add(thread);
        }
        return list;
    }


    /**
     * A tekton kettétörését végző metódus, amely két új tekton objektumot hoz létre,
     * majd az eredeti tekton szomszédsági kapcsolatait felosztja az újonnan létrehozott
     * tektonok között. A metódus csak akkor hajtja végre a műveletet, ha az eredeti
     * tektonon nincs gomba.
     *
     * A művelet végrehajtása során:
     * - Létrehoz két új tekton objektumot.
     * - Szétosztja a szomszédos tektonokat az új tektonok között.
     * - Frissíti a szomszédsági kapcsolatokat a megfelelő szomszédlisták szerint.
     * - Az eredeti tektonon lévő bogarat és fonalakat az új tektonokra helyezi át.
     * - Eltávolítja az eredeti tekton objektumot a gombafonalból.
     * - Törli azokat a fonaldarabokat, amelyek a törés során már nem kapcsolódnak
     * egyetlen gombatesthez sem.
     *
     * @return true, ha a tekton sikeresen kettétörött, false, ha a tektonon
     * található gomba miatt a művelet nem hajtható végre.
     */
    public boolean breakTecton() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("breakTecton");

        if (mushroom == null) {

            // létrejön a két új tekton
            Tecton t6 = new SingleThreadTecton(t);
            Tecton t7 = new SingleThreadTecton(t);

            t.toCreate(this, t6, "t6");
            t.toCreate(this, t7, "t7");

            // ez lesz a töréspont a tektonon
            int centre = neighbors.size() / 2;

            // létrehozok egy listát, amelyben a t6 tekton szomszédai lesznek
            List<Tecton> neighborList = new ArrayList<>();
            for (int i = 0; i < centre; i++) {
                neighborList.add(neighbors.get(i));
            }
            neighborList.add(t7);

            this.t.list.add(this);
            this.t.list.add(t6);
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
            // beállítom a t6 szomszédait
            t6.addNeighbor(neighborList);
            neighborList.clear();

            // létrehozok egy listát, amelyben a t7 szomszédai lesznek
            neighborList.add(t6);
            for (int i = centre; i < neighbors.size(); i++) {
                neighborList.add(neighbors.get(i));
            }

            this.t.list.add(this);
            this.t.list.add(t7);
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
            // beállítom a t7 szomszédait
            t7.addNeighbor(neighborList);
            neighborList.clear();

            // a jelenlegi tekton szomszédait beállítom, hozzáadva szomszédsági listájukhoz
            // a megfelelő létrejött tektont
            // valamint kivéve a kettétötött tektont
            neighborList.add(t6);
            for (int i = 0; i < centre; i++) {

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
            for (int i = centre; i < neighbors.size(); i++) {

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
            // a tektonon levő bogarat ráhelyezem a t6-ra
            t6.setInsect(i);

            this.t.list.add(this);
            this.t.list.add(i);
            this.t.parameters.clear();
            this.t.parameters.add(t6);
            // beállítom a bogár pozícióját
            i.setPosition(t6);

            this.t.list.add(this);
            this.t.list.add(i);
            this.t.parameters.clear();
            this.t.parameters.add(t6);
            // kitörlöm a tektont a fonálról
            thread.removeTecton(this);

            // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
            // gombatesthez ezt eltávolítom
            System.out.println("deleteUnnecessaryThreads");

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        } else {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
    }

}
