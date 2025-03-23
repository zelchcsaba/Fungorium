package fungorium;

import java.util.ArrayList;
import java.util.List;


/**
 * Az AbsorbingTecton osztály a Tecton leszármazottja, amely olyan speciális viselkedéssel
 * rendelkezik, hogy nem lehet rá Mushroom objektumot helyezni. Ez az osztály a rajta lévő
 * FungalThread fonalakkal való műveletek kezelésére összpontosul.
 */
public class AbsorbingTecton extends Tecton {

    private List<FungalThread> threads;


    /**
     * konstruktor
     *
     * @param t tester objektum
     */
    public AbsorbingTecton(Tester t) {
        super(t);
        threads = new ArrayList();
    }

    /**
     * Nem lehet Mushroomot lehelyezni
     *
     * @param mushroom
     */
    public void setMushroom(Mushroom mushroom) {
    }


    /**
     * Visszaad egy Mushroom (gombatest) objektumot, amely az AbsorbingTecton típusú
     * objektumban található. Mivel az AbsorbingTecton esetében nem lehetséges gombatest
     * jelenléte, a visszatérési érték mindig null.
     *
     * @return null, mivel az AbsorbingTecton nem tartalmaz Mushroom objektumot.
     */
    public Mushroom getMushroom() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getMushroom"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(null);
        this.t.toReturn();
        // absorbing tektonon nem lehet gombatest, ezért a visszatérési érték null
        return null;
    }


    /**
     * Beállítja az aktuális objektumhoz tartozó gombafonalak listáját.
     *
     * @param list A gombafonalakat tartalmazó lista, amely az objektumhoz kerül hozzárendelésre.
     */
    public void setThreads(List<FungalThread> list) {
        threads = list;
    }


    /**
     * Visszaadja az objektumhoz tartozó gombafonalak listáját.
     *
     * @return A gombafonalak listája, amely ehhez az objektumhoz tartozik.
     */
    public List<FungalThread> getThreads() {
        t.toCall("getThreads");
        t.returnValue.clear();
        t.returnValue.addAll(threads);

        t.toReturn();

        return threads;
    }


    /**
     * Visszaadja az objektumhoz tartozó gombafonalak listáját.
     *
     * @return A gombafonalak listája, amely ehhez az objektumhoz tartozik.
     */
    public List<FungalThread> getThreadsWithoutCout() {
        return threads;
    }


    /**
     * Ez a metódus felelős az "AbsorbingTecton" típusú objektumhoz tartozó összes
     * gombafonal (FungalThread) felszívásáért. A felszívás folyamata során az objektumhoz
     * rendelt fonalakat eltávolítja a tektonról, és törli azokat a fonálrészeket,
     * amelyek nem kapcsolódnak azonos fajhoz tartozó gombatestekhez.
     * <p>
     * A metódus működése:
     * 1. Meghívja a "toCall" függvényt az aktuális művelet jelzésére.
     * 2. Iterál az "AbsorbingTecton" objektumhoz tartozó gombafonalak listáján, majd
     * a következőket hajtja végre:
     * - Hozzáadja magát és a fonalat egy "list" nevű belső szimulációs tárolóhoz.
     * - Eltávolítja a gombafonalhoz tartozó tektonról az aktuális objekumot
     * a "removeTecton" metódus segítségével.
     * 3. Létrehoz egy átmeneti lista objektumot, amelyhez hozzáadja az összes eredeti
     * gombafonalat, majd kiüríti az eredeti fonallista tartalmát.
     * 4. Iterál az átmeneti gombafonalakon, és a következőket hajtja végre:
     * - Hozzáadja az aktuális objektumot és az adott fonalat ugyanabba a "list"
     * nevű tárolóba.
     * - Meghívja a "deleteUnnecessaryThreads" metódust az adott fonál esetében
     * az irreleváns kapcsolatok eltávolítására.
     * 5. Az összes végrehajtott művelet után tisztítja az aktuális visszatérési érték
     * tárolót, majd meghívja a "toReturn" metódust a végrehajtás szimulációjára.
     */
    public void absorb() {

        t.toCall("absorb");

        for (int i = 0; i < threads.size(); i++) {

            this.t.list.add(this);
            this.t.list.add(threads.get(i));
            this.t.parameters.clear();
            this.t.parameters.add(this);
            // levesszük róla a fonalakat
            threads.get(i).removeTecton(this);
        }

        List<FungalThread> fungal = new ArrayList<>();
        fungal.addAll(threads);
        threads.clear();

        for (int i = 0; i < fungal.size(); i++) {
            this.t.list.add(this);
            this.t.list.add(fungal.get(i));
            this.t.parameters.clear();
            // töröljük azon fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból
            // származó gombatesthez
            fungal.get(i).deleteUnnecessaryThreads();
        }

        t.returnValue.clear();

        t.toReturn();
    }


    /**
     * Nem lehet Mushroom objektumot lehelyezni az aktuális objektumra.
     *
     * @param m A Mushroom objektum, amelyet le szeretnének helyezni.
     * @return false, mivel az aktuális objektumra nem lehet Mushroomot lehelyezni.
     */
    public boolean putMushroom(Mushroom m) {
        return false;
    }


    /**
     * Ellenőrzi, hogy a megadott FungalThread (gombafonal) már létezik-e valamelyik szomszéd tektonban,
     * és ennek megfelelően helyezi el a gombafonalat.
     *
     * @param f Az a FungalThread objektum, amelyet hozzá szeretnénk adni a tektonhoz.
     * @return true, ha a megadott FungalThread (gombafonal) létezik az egyik szomszéd tektonban,
     * false, ha nem található a megadott szomszédságokban.
     */
    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for (Tecton tecton : neighbors) {
            if (tecton.getThreads().contains(f)) {
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
     * Hozzáad egy új FungalThread (gombafonal) objektumot az AbsorbingTecton
     * aktuális objektumhoz tartozó gombafonalainak listájához.
     *
     * @param f A FungalThread objektum, amelyet hozzá szeretnénk adni az objektumhoz
     */
    public void addThread(FungalThread f) {
        threads.add(f);
    }


    /**
     * Eltávolítja a Mushroom objektumot az aktuális AbsorbingTecton objektumról.
     * Az AbsorbingTecton típusú objektumok esetében nem lehetséges Mushroom jelenléte,
     * ezért a metódus mindig false értékkel tér vissza, jelezve, hogy nem történt eltávolítás.
     *
     * @return mindig false, mert az AbsorbingTecton objektumban nem lehetséges Mushroom jelenléte.
     */
    // false mert nem tud Mushroom nőni rajta, így sose lesz mit kitörölni
    public boolean removeMushroom() {
        this.t.toCall("removeMushroom");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.FALSE);
        this.t.toReturn();

        return false;
    }


    /**
     * Eltávolítja a megadott gombafonalat az aktuális objektumhoz tartozó listából.
     *
     * @param f Az a FungalThread objektum, amelyet el szeretnénk távolítani az aktuális objektumhoz tartozó listából.
     * @return true, ha a művelet sikeres volt.
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
     * Nem lehet az aktuális objektumra Mushroom objektumot lehelyezni.
     * A metódus minden esetben hamis értékkel tér vissza.
     *
     * @return false, mivel az aktuális objektumra nem lehet gombát lehelyezni.
     */
    public boolean putFirstMushroom() {
        t.toCall("putFirstMushroom");
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.FALSE);
        this.t.toReturn();
        return false;
    }


    /**
     * A metódus kettétöri az aktuális tekton-t két különálló tekton-ra (t6 és t7).
     * Az eredeti szomszédságokat és kapcsolódó struktúrákat frissíti a művelet során.
     * Továbbá kezeli a kapcsolódó bogarak elhelyezkedését és a fonál kapcsolódásokat.
     *
     * @return true, ha a tekton sikeresen kettétört.
     */
    public boolean breakTecton() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("breakTecton");

        // létrejön a két új tekton
        Tecton t6 = new AbsorbingTecton(t);
        Tecton t7 = new AbsorbingTecton(t);

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
    }

}
