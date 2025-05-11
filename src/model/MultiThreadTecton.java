package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A MultiThreadTecton osztály a Tecton osztályból származik.
 * Ez az osztály a több szál kezelését, valamint a különféle gombafonalakhoz és gombatesthez kapcsolódó műveleteket valósítja meg.
 * Ez az osztály a Tester osztállyal való interakciókra külön figyelmet fordít a tesztelhetőség érdekében.
 */
public class MultiThreadTecton extends Tecton {

    private Mushroom mushroom;
    private List<FungalThread> threads;


    /**
     * Létrehoz egy új MultiThreadTecton példányt. A konstruktor inicializálja az
     * osztályhoz tartozó mezőket, beleértve az üres gombatestet (mushroom)
     * és a gombafonalak üres listáját (threads).
     */
    public MultiThreadTecton() {
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
     * Visszaadja a jelenlegi gombatestet a tektonról.
     *
     * @return A jelenleg a tektonon lévő Mushroom objektum, vagy null, ha nincs gombatest.
     */
    public Mushroom getMushroom() {
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
     * @param m A gomba objektum, amelyet le szeretnénk helyezni.
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
     * Felosztja a jelenlegi tektont két új tektorra, majd ezek szomszédsági kapcsolatait
     * és állapotát az eredeti tekton alapján állítja be. A metódus kezeli az adott tekton
     * törését az alábbi lépésekkel:
     * - Létrehozza a két új tektont.
     * - Beállítja az új tektontok szomszédait az eredeti szomszédok alapján, amelyeket
     *   az újonnan létrehozott tektontoken keresztül oszt szét.
     * - Áthelyezi a bogarat (ha van) az egyik új tektontokra, megadva a megfelelő helyzetet.
     * - A törés következtében eltávolítja az eredeti tektonhoz kapcsolódó szálakat,
     *   és törli azokat a szálakat, amelyek már nem vezetnek gombatesthez.
     *
     * @return Egy listát ad vissza, amely tartalmazza a két újonnan létrehozott tektont.
     */
    public List<Tecton> breakTecton() {

        List<Tecton> ret = new ArrayList<>();

        if(mushroom == null){

        // létrejön a két új tekton
        Tecton t6 = new MultiThreadTecton();
        Tecton t7 = new MultiThreadTecton();

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
     * Az adott metódus megpróbálja az első gombatestet (mushroom) elhelyezni a tektonon (MultiThreadTecton),
     * ha még nincs rajta másik gombatest. Amennyiben sikerül, a gombatest pozícióját és a hozzá
     * tartozó gombafonalat is beállítja, valamint hozzáadja a gombafonalat a tektonhoz.
     *
     * @param f A gombafonal (FungalThread) objektum, amely a gomba elhelyezése után hozzá lesz rendelve a tektonhoz.
     * @param m A gomba (Mushroom) objektum, amelyet el kell helyezni a tektonon.
     * @return true, ha a gombatest sikeresen le lett helyezve és a gombafonal hozzá lett adva a tektonhoz;
     * false, ha már létezik rajta egy gombatest, ezért az elhelyezés nem sikerült.
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
     * Ellenőrzi, hogy a megadott gombafonal (FungalThread) kapcsolódik-e a
     * jelenlegi gombatesthez (mushroom).
     *
     * @param f A vizsgált gombafonal (FungalThread) objektum.
     * @return true, ha a megadott gombafonal kapcsolódik a jelenlegi gombatesthez;
     * false, ha nem.
     */
    public boolean isConnected(FungalThread f) {
        if (mushroom != null && mushroom.getThread() == f) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Ellenőrzi, hogy a gombatest (mushroom) elhelyezhető-e a tektonon.
     * Amennyiben még nincs gombatest a tektonon, igaz értéket ad vissza.
     *
     * @return true, ha a gombatest elhelyezhető a tektonon; false, ha már van ott gombatest.
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
