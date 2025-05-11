package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály a Tecton osztály leszármazottja, amely felelős olyan tekton objektumok viselkedéséért és kezeléséért,
 * amelyek esetében több gombafonal (FungalThread) egyszerre kapcsolódhat és olyan fonalakat tartanak meg,
 * amelyek sem közvetlenül, sem közvetve nem kapcsolódnak gombatesthez (Mushroom).
 */
public class KeepThreadTecton extends Tecton {

    private Mushroom mushroom;
    private List<FungalThread> threads;


    /**
     * Konstruktor, amely inicializálja a threads mezőt
     * és a mushroom értékét null-ra állítja.
     */
    public KeepThreadTecton() {
        super();
        mushroom = null;
        threads = new ArrayList<>();
    }


    /**
     * Beállítja a gombatest értékét a megadott Mushroom objektummal.
     *
     * @param mushroom A gombatest, amelyet hozzá kell rendelni.
     */
    public boolean setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
        return true;
    }


    /**
     * Visszaadja a tectonhoz kapcsolódó gombatestet.
     * Meghívja a tester kiíró függvényét, eltárolja a visszatérési értéket a tester számára,
     * majd visszatér a megfelelő `Mushroom` objektummal.
     *
     * @return A tectonhoz tartozó gombatest (Mushroom), vagy null, ha nincs hozzárendelt gombatest.
     */
    public Mushroom getMushroom() {
        // meghívja a tester kiíró függvényét
        return mushroom;
    }


    /**
     * Beállítja a gombafonalak listáját a megadott listára.
     *
     * @param threads A gombafonalak listája.
     */
    public void setThreads(List<FungalThread> threads) {
        this.threads = threads;
    }


    /**
     * Visszaadja azokat a gombafonalakat, amelyek a tektonon találhatók.
     *
     * @return A tektonon lévő gombafonalakat tartalmazó lista.
     */
    public List<FungalThread> getThreads() {
        return threads;
    }


    /**
     * Lerak egy gombaobjektumot a tektonon, ha még nincs ott másik gomba.
     * Csak akkor sikeres, ha a megadott helyen nincs már gomba.
     *
     * @param m A gombaobjektum, amelyet le szeretnénk helyezni.
     * @return Igaz értéket ad vissza, ha a gomba sikeresen le lett helyezve,
     * ellenkező esetben hamis.
     */
    public boolean putMushroom(Mushroom m) {
        if (mushroom == null) {
            mushroom = m;
            return true;
        }
        return false;
    }


    /**
     * A megadott gombafonal hozzáadása a jelenlegi vagy valamelyik szomszédos tektonjához,
     * ha az a megadott fonalat tartalmazza.
     *
     * @param f A hozzáadni kívánt gombafonal.
     * @return true, ha a fonalat sikeresen hozzáadta a tektonhoz, különben false.
     */
    public boolean putThread(FungalThread f) {
        for (Tecton tecton : neighbors) {
            if(tecton != null){
                if (tecton.getThreads().contains(f)) {
                    threads.add(f);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Eltávolítja a gombatestet a tektonról, ha van rajta gombatest.
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
     * A kapott fonalat eltávolítja a listájából.
     *
     * @param f A törölni kívánt fonal.
     */
    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }


    /**
     * Egy megadott tekton kettétörése során létrehozza az új tektonokat és
     * azok szomszédsági, valamint fonalbeli kapcsolatait átrendezi.
     * Biztosítja, hogy a régi tektonon lévő bogár, valamint az ahhoz tartozó
     * gombafonal megfelelően áthelyezésre kerüljön az újonnan létrejött tektonokra,
     * és eltávolítja a már nem szükséges fonalrészeket.
     *
     * @return A kettétört tektonból létrejött új tektonok listája.
     */
    public List<Tecton> breakTecton() {

        List<Tecton> ret = new ArrayList<>();

        if(mushroom == null){

        // létrejön a két új tekton
        Tecton t6 = new KeepThreadTecton();
        Tecton t7 = new KeepThreadTecton();

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
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).removeTecton(this);
        }

        // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
        // gombatesthez ezt eltávolítom
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).deleteUnnecessaryThreads();
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
        if (mushroom == null) {

            mushroom = m;
            mushroom.setPosition(this);
            mushroom.setThread(f);

            threads.add(f);
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
        return true;
    }


    /**
     * Ellenőrzi, hogy lehet-e új gombatestet (mushroom) elhelyezni a tektonon.
     * Csak akkor lehet gombatestet lerakni, ha jelenleg nincs másik gombatest rajta.
     *
     * @return true, ha nincs gombatest a tektonon és elhelyezhető egy új gombatest,
     * különben false.
     */
    public boolean canPutMushroom() {
        if (mushroom == null) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Hozzáad egy gombafonalat a meglévő fonalak listájához.
     *
     * @param f az a FungalThread objektum, amelyet hozzá kell adni
     */
    public void addThread(FungalThread f) {
        threads.add(f);
    }

}
