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
     * Konstruktor, amely inicializálja a SingleThreadTecton objektumot.
     * A konstruktor beállítja a tektonon található gombafonalakat és gombatestet.
     */
    public SingleThreadTecton() {
        super();
        mushroom = null;
        thread = null;
    }


    /**
     * Beállítja a tektonon található gombafonalat.
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
            return list;
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
    public boolean setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
        return true;
    }


    /**
     * Visszaadja a tektonon található gombatestet.
     *
     * @return A gombatest, amely a tektonon található.
     */
    public Mushroom getMushroom() {
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
                if(tecton != null){
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
     * Ez a metódus kettétöri a jelenlegi tektont, és létrehoz két új Tecton objektumot.
     * Az újonnan létrehozott Tecton objektumok megfelelő kapcsolódásait és szomszédsági listáikat
     * a jelenlegi tekton szomszédos elemei alapján állítja be. Az eredeti tekton szomszédsági
     * listáját és struktúráját is frissíti a művelet során. Továbbá eltávolítja azokat a fonálrészeket,
     * amelyek a törés után nincs kapcsolatban egy gombatörzshöz.
     *
     * @return A két új, kettétörés során létrehozott Tecton objektumot tartalmazó lista.
     */
    public List<Tecton> breakTecton() {
        List<Tecton> ret = new ArrayList<>();
        if(mushroom == null){

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

        for (int i = 0; i < centre; i++) {
            if(neighbors.get(i)!=null){
                neighbors.get(i).exchange(this, t6);
            }
        }

        for (int i = centre; i < neighbors.size(); i++) {
            if(neighbors.get(i)!=null){
                neighbors.get(i).exchange(this, t7);
            }
        }

        if(i!=null){
            // a tektonon levő bogarat ráhelyezem a t6-ra
            t6.setInsect(i);
            // beállítom a bogár pozícióját
            i.setPosition(t6);
        }
        // kitörlöm a tektont a fonálról
        if(thread!=null){
            thread.removeTecton(this);

        // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
        // gombatesthez ezt eltávolítom

            thread.deleteUnnecessaryThreads();
        }
    }
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


    public void absorb() {
    }


    /**
     * Megvizsgálja, hogy egy adott FungalThread objektum kapcsolódik-e
     * a tektonon található gombatesthez.
     *
     * @param f A vizsgálandó FungalThread objektum.
     * @return true, ha az adott gombafonal össze van kapcsolva a tekton gombatestével,
     * különben false.
     */
    public boolean isConnected(FungalThread f) {
        if (mushroom != null && mushroom.getThread() == f) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Ellenőrzi, hogy létrehozható-e egy gombatest a tektonon.
     * A metódus igaz értéket ad vissza, ha a tektonon jelenleg nincs gombatest.
     *
     * @return true, ha lehet gombatestet ráhelyezni a tektonra;
     * false, ha már van gombatest a tektonon.
     */
    public boolean canPutMushroom() {
        if (mushroom == null) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Egy FungalThread típusú gombafonal hozzáadása a tektonhoz.
     *
     * @param f A hozzáadni kívánt gombafonal (FungalThread objektum).
     */
    public void addThread(FungalThread f) {
        if (thread == null) {
            thread = f;
        }
    }

}
