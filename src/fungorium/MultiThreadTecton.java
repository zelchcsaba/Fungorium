package fungorium;

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
     * Konstruktor
     *
     * @param t A teszter objektum.
     */
    public MultiThreadTecton(Tester t) {
        super(t);
        mushroom = null;
        threads = new ArrayList<>();
    }


    /**
     * Beállítja a gombatest értékét a megadott Mushroom objektummal.
     *
     * @param mushroom A gombatest, amelyet hozzá kell rendelni.
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
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
        this.t.toCall("getMushroom"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(mushroom);
        this.t.toReturn();

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
        t.toCall("getThreads");

        t.returnValue.clear();
        t.returnValue.addAll(threads);

        t.toReturn();

        return threads;
    }


    /**
     * A megadott gombafonal hozzáadása a jelenlegi vagy valamelyik szomszédos tektonjához,
     * ha az a megadott fonalat tartalmazza.
     *
     * @param f A hozzáadni kívánt gombafonal.
     * @return true, ha a fonalat sikeresen hozzáadta a tektonhoz, különben false.
     */
    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for (Tecton tecton : neighbors) {
            t.list.add(this);
            t.list.add(tecton);
            t.parameters.clear();
            if (tecton.getThreads().contains(f)) {
                threads.add(f);
                t.returnValue.clear();
                t.returnValue.add(Boolean.TRUE);
                t.toReturn();
                return true;
            }
        }
        t.returnValue.clear();
        t.returnValue.add(Boolean.FALSE);
        t.toReturn();
        return false;
    }


    /**
     * Hozzáad egy gombafonalat a meglévő fonalak listájához.
     *
     * @param f az a FungalThread objektum, amelyet hozzá kell adni
     */
    public void addThread(FungalThread f) {
        threads.add(f);
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
     * Eltávolítja a gombatestet a tektonról, ha van rajta gombatest.
     */
    public boolean removeMushroom() {
        this.t.toCall("removeMushroom");

        if (mushroom != null) {
            mushroom = null;

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
     * A kapott fonalat eltávolítja a listájából.
     *
     * @param f A törölni kívánt fonal.
     */
    public boolean removeThread(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("removeThread");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        threads.remove(f);
        return true;
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
    public boolean putFirstMushroom() {
        this.t.toCall("putFirstMushroom");
        if (mushroom == null) {
            FungalThread ft = new FungalThread(t);
            this.t.toCreate(this, ft, "ft");
            Mushroom mush = new Mushroom(t);
            t.toCreate(this, mush, "mush");

            t.list.add(this);
            t.list.add(ft);
            t.parameters.clear();
            t.parameters.add(this);
            ft.addTecton(this);

            t.list.add(this);
            t.list.add(mush);
            t.parameters.clear();
            t.parameters.add(this);
            mush.setPosition(this);

            t.list.add(this);
            t.list.add(mush);
            t.parameters.clear();
            t.parameters.add(ft);
            mush.setThread(ft);

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.FALSE);
        this.t.toReturn();
        return false;
    }


    /**
     * Visszaadja a gombafonalak listáját, kiíratás nélkül.
     *
     * @return A gombafonalak listája.
     */
    public List<FungalThread> getThreadsWithoutCout() {
        return threads;
    }


    /**
     * A tekton kettétörését végző metódus. Ez a metódus két új tektont hoz létre,
     * a jelenlegi tekton szomszédait átrendezi és beállítja az új tektontípusokhoz,
     * valamint szükség esetén eltávolítja a fonálszálakat, amelyek már nem kapcsolódnak
     * egyetlen gombatesthez sem. A metódus hatására a jelenlegi tekton "megszűnik",
     * és helyette két új tekton jön létre.
     *
     * @return true, ha a tekton sikeresen kettétört; false, ha a kettétörés feltételei nem teljesülnek.
     */
    public boolean breakTecton() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("breakTecton");

        if (mushroom == null) {
            // létrejön a két új tekton
            Tecton t6 = new MultiThreadTecton(t);
            Tecton t7 = new MultiThreadTecton(t);

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
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).removeTecton(this);
            }

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
