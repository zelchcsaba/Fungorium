package model;

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
     */
    public SingleThreadTecton() {
        super();
        mushroom = null;
        thread = null;
    }


    /**
     * Beállítja a tektonon található gombafonalakat.
     *
     * @param list A beállítani kívánt gombafonalak listája.
     */
    public void setThreads(List<FungalThread> list) {

        thread = list.get(0);

    }


    /**
     * Visszaadja azokat a gombafonalakat, amelyek rajta vannak a tektonon.
     *
     * @return A rajta lévő gombafonalak listája. Ha nincs rajta gombafonal, akkor null-t ad vissza.
     */
    public List<FungalThread> getThreads() {

        // berakom egy listába a fonalat
        ArrayList<FungalThread> list = new ArrayList<>();
        if (thread == null) {
            return null;
        } else {
            list.add(thread);
        }

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

        return mushroom;
    }


    /**
     * Ráhelyez egy gombatestet a tektonra, ha még nincs rajta másik gombatest.
     *
     * @param m a hozzáadni kívánt gombatest
     * @return igaz, ha sikerült a gombatestet ráhelyezni, hamis, ha már volt rajta egy másik gombatest
     */
    public boolean putMushroom(Mushroom m) {

        if (mushroom == null) {
            mushroom = m;
            return true;
        }
        return false;
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
        if (thread == null && !neighbors.isEmpty()) {
            for (Tecton tecton : neighbors) {
                List<FungalThread> threads = tecton.getThreads();
                if (threads != null) { // Csak akkor iterálj, ha nem null
                    for (FungalThread fungals : threads) {
                        if (fungals != null && fungals.equals(f)) {
                            thread = f;
                            return true;
                        }
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Eltávolítja a tektonról a hozzá kapcsolt gombatestet, ha az létezik.
     *
     * @return true, ha sikeresen eltávolította a gombát, false, ha nem volt hozzá tartozó gombatest.
     */
    public boolean removeMushroom() {


        if (mushroom != null) {
            mushroom = null;

            return true;
        } else {

            return false;
        }
    }

    /**
     * Törli a megadott gombafonalat, ha megegyezik a tárolt gombafonallal.
     *
     * @param f A törölni kívánt FungalThread objektum.
     * @return Igaz értéket ad vissza, ha a törlés sikeres.
     */
    public boolean removeThread(FungalThread f) {

        thread = null;
        return true;
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
    public List<Tecton> breakTecton() {
        List<Tecton> ret = new ArrayList<>();

            // létrejön a két új tekton
            Tecton t6 = new SingleThreadTecton();
            Tecton t7 = new SingleThreadTecton();

            ret.add(t6);
            ret.add(t7);

            // ez lesz a töréspont a tektonon
            int centre = neighbors.size() / 2;

            // létrehozok egy listát, amelyben a t6 tekton szomszédai lesznek
            List<Tecton> neighborList = new ArrayList<>();
            for (int i = 0; i < centre; i++) {
                neighborList.add(neighbors.get(i));
            }
            neighborList.add(t7);

            // beállítom a t6 szomszédait
            t6.addNeighbor(neighborList);
            neighborList.clear();

            // létrehozok egy listát, amelyben a t7 szomszédai lesznek
            neighborList.add(t6);
            for (int i = centre; i < neighbors.size(); i++) {
                neighborList.add(neighbors.get(i));
            }

            // beállítom a t7 szomszédait
            t7.addNeighbor(neighborList);
            neighborList.clear();

            // a jelenlegi tekton szomszédait beállítom, hozzáadva szomszédsági listájukhoz
            // a megfelelő létrejött tektont
            // valamint kivéve a kettétötött tektont
            neighborList.add(t6);
            for (int i = 0; i < centre; i++) {

                neighbors.get(i).addNeighbor(neighborList);

                neighbors.get(i).removeNeighbor(this);
            }

            neighborList.clear();

            neighborList.add(t7);
            for (int i = centre; i < neighbors.size(); i++) {

                neighbors.get(i).addNeighbor(neighborList);

                neighbors.get(i).removeNeighbor(this);
            }

            // a tektonon levő bogarat ráhelyezem a t6-ra
            t6.setInsect(i);
            // beállítom a bogár pozícióját
            i.setPosition(t6);

            // kitörlöm a tektont a fonálról
            thread.removeTecton(this);

            // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
            // gombatesthez ezt eltávolítom
            
            thread.deleteUnnecessaryThreads();

            return ret;
    }

    /**
     * Lerakja az első gombatestet a tektonon, amennyiben az még nem létezik.
     * A módszer ellenőrzi, hogy már létezik-e gombatest a tektonon.
     * Ha nem, létrehoz egy új gombatestet és egy új gombafonalat,
     * majd azokat megfelelően beállítja és hozzáadja a tekton adatszerkezetéhez.
     * Ha azonban már van gombatest, akkor a művelet nem hajtható végre, és visszaad egy hamis értéket.
     *
     * @return true, ha az első gombatest sikeresen lerakásra kerül; false, ha már létezik gombatest.
     */
    public boolean putFirstMushroom(FungalThread f, Mushroom m) {
        if (mushroom == null && thread == null) {

            mushroom = m;
            mushroom.setPosition(this);
            mushroom.setThread(f);
            
            thread = f;
            f.addTecton(this);

            return true;
        }
        return false;
    }

    public void absorb(){}

    public boolean isConnected(FungalThread f){
        if(mushroom!=null && mushroom.getThread() == f){
            return true;
        }else{
            return false;
        } 
    }

    public boolean canPutMushroom(){
        if(mushroom == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Egy FungalThread típusú gombafonal hozzáadása a tektonhoz.
     *
     * @param f A hozzáadni kívánt gombafonal (FungalThread objektum).
     */
    public void addThread(FungalThread f) {
        if(thread == null){
            thread = f;
        }
    }

}
