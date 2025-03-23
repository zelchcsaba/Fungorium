package fungorium;

import java.util.ArrayList;
import java.util.List;

/**
 * A Tecton osztály egy absztrakt osztály, amely a játék tektonjait
 * reprezentálja, ezek játékterület alapvető egységei.
 */
public abstract class Tecton {

    Tester t;
    protected List<Spore> spores;
    protected List<Tecton> neighbors;
    protected Insect i;


    /**
     * Létrehozza egy Tecton osztály példányát a megfelelő mezők inicializálásával.
     *
     * @param t A kapcsolódó Tester objektum, amely a Tecton-hoz tartozik.
     */
    public Tecton(Tester t) {
        this.t = t;
        spores = new ArrayList<>();
        neighbors = new ArrayList<>();
        i = null;
    }


    /**
     * Beállítja a Tecton-hoz tartozó spórák listáját.
     *
     * @param list A spórák listája, amelyet a Tecton példányhoz társítani kell.
     */
    public void setSpores(List<Spore> list) {
        spores = list;
    }


    /**
     * Visszaadja a Tecton objektumhoz tartozó spórák listáját.
     *
     * @return A Tecton-hoz tartozó spórák listája.
     */
    public List<Spore> getSpores() {
        t.toCall("getSpores");
        t.returnValue.clear();
        t.returnValue.addAll(spores);
        t.toReturn();
        return spores;
    }


    /**
     * Beállítja a Tecton objektumhoz tartozó szomszédos Tecton-ok listáját.
     *
     * @param neighbors A szomszédos Tecton-ok listája, amelyet a Tecton példányhoz társítani kell.
     */
    public void setNeighbors(List<Tecton> neighbors) {
        this.neighbors = neighbors;
    }


    /**
     * Visszaadja a Tecton szomszédos objektumainak listáját.
     *
     * @return A szomszédos Tecton objektumokat tartalmazó lista.
     */
    public List<Tecton> getNeighbors() {
        t.toCall("getNeighbors");
        this.t.returnValue.clear();
        this.t.returnValue.addAll(neighbors);
        this.t.toReturn();
        return neighbors;
    }


    /**
     * Beállítja a Tecton-hoz tartozó rovar objektumot.
     *
     * @param i Az új rovar objektum, amelyet a Tecton-hoz rendelünk.
     */
    public void setInsect(Insect i) {
        t.toCall("setInsect");
        t.returnValue.clear();
        t.toReturn();

        this.i = i;
    }


    /**
     * Visszaadja a Tecton objektumhoz társított rovart.
     *
     * @return Az Insect objektum, amely a Tecton-hoz tartozik.
     */
    public Insect getInsect() {
        return i;
    }


    // -- Absztrakt Metódusok -- //

    public abstract void setMushroom(Mushroom mushroom);

    public abstract Mushroom getMushroom();

    public abstract void setThreads(List<FungalThread> threads);

    public abstract List<FungalThread> getThreads();

    public abstract List<FungalThread> getThreadsWithoutCout(); // Ez eskü jól jön

    public abstract boolean putMushroom(Mushroom m);

    public abstract boolean putThread(FungalThread f);

    public abstract void addThread(FungalThread f); // Csak simán add-olja az f-et nem végez ellenőrzést

    public abstract boolean removeMushroom();

    public abstract boolean removeThread(FungalThread f);

    public abstract boolean breakTecton();

    public abstract boolean putFirstMushroom();

    // -- //


    /**
     * Spóra hozzáadása egy Tecton objektumhoz, feltéve, hogy a Tecton szomszédos.
     *
     * @param sp A Spore objektum, amelyet hozzá kívánunk adni.
     * @param t A Tecton objektum, amelyhez a spórát hozzá szeretnénk adni.
     * @return Igaz, ha a művelet sikeres, hamis, ha a Tecton nem szomszédos, és a spóra nem lett hozzáadva.
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
     * Visszaadja azon Tecton-ok listáját, amelyek szomszédosak a jelenlegi Tecton-nal,
     * és amelyek threads listájában megtalálható az adott FungalThread objektum.
     *
     * @param f Az a FungalThread objektum, amely alapján a keresést végrehajtjuk.
     * @return Azon Tecton objektumokat tartalmazó lista, amelyek megfelelnek a feltételeknek.
     */
    public List<Tecton> getThreadSection(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getThreadSection");

        List<Tecton> tectons = new ArrayList<>();

        // végigmegy a szomzsédokon, és lekéri a threads tömbjüket, ha ebben benne van
        // f, akkor hozzáadja a tectons listához
        for (int i = 0; i < neighbors.size(); i++) {
            List<FungalThread> list = neighbors.get(i).getThreadsWithoutCout();
            if (list != null) {
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


    /**
     * Ellenőrzi, hogy az aktuális Tecton objektumhoz nincs-e hozzárendelve rovar,
     * és ha nincs, az első rovart beállítja. Ha már létezik hozzárendelt rovar,
     * a metódus nem végez műveletet.
     *
     * @return true, ha sikeresen beállította az első rovart, vagy már volt hozzárendelve rovar;
     * false, ha az inicializálatlan állapot vagy más okok miatt nem lehetett műveletet végrehajtani.
     */
    // to do
    public boolean putFirstInsect() {
        return i == null;
    }


    /**
     * Hozzáad egy rovar objektumot egy adott Tecton objektumhoz.
     *
     * @param i Az Insect típusú objektum, amelyet hozzá kívánunk adni.
     * @param t Az a Tecton objektum, amelyhez a rovart hozzáadjuk.
     * @return Igaz értéket ad vissza, ha a rovar sikeresen hozzá lett adva a megadott Tecton-hoz,
     *         különben hamis értéket.
     */
    public boolean putInsect(Insect i, Tecton t) {
        return true;
    }


    /**
     * Eltávolítja a Tecton objektumhoz társított rovart, ha van ilyen.
     *
     * @return true, ha a rovar sikeresen eltávolításra került, false, ha nem volt eltávolítandó rovar
     */
    public boolean removeInsect() {
        if (i != null) {
            i = null;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Ellenőrzi, hogy a megadott Tecton objektum a hívó Tecton szomszédja-e.
     *
     * @param t A Tecton objektum, amelynek szomszédosságát ellenőrizzük.
     * @return true, ha a megadott Tecton szomszédos a hívó Tecton-nal, false különben.
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
     * Hozzáad egy listát a Tecton típusú objektumokból a meglévő szomszédos Tecton-ok listájához.
     * A metódus hívást szimulál, frissíti a kapcsolódó objektum állapotát, majd visszatérési értéket ad.
     *
     * @param tlist A Tecton objektumok listája, amelyet a szomszédok listájához hozzá kell adni.
     * @return true értéket ad vissza, ha a művelet sikeres volt.
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
     * Eltávolít egy megadott szomszédos Tecton objektumot az aktuális Tecton szomszédai közül.
     *
     * @param t A Tecton objektum, amelyet el akarunk távolítani az aktuális Tecton szomszédos objektumai közül.
     * @return Igaz értéket ad vissza, ha a szomszéd sikeresen eltávolításra került.
     */
    public boolean removeNeighbor(Tecton t) {
        this.t.toCall("removeNeighbor");
        neighbors.remove(t);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        return true;
    }


    /**
     * A metódus megpróbál egy kifejlődött spórát (Evolved Spore) hozzáadni
     * egy adott Tecton példányhoz, ha az megfelelő szomszédsági kritériumoknak megfelel.
     *
     * @param sp A hozzáadni kívánt kifejlődött Spore objektum.
     * @param t Az a cél Tecton példány, amelyhez a Spore-t hozzá kell adni.
     * @return Igaz (true), ha a spóra hozzáadása sikeres volt, hamis (false), ha nem.
     */
    public boolean putEvolvedSpore(Spore sp, Tecton t) {
        this.t.toCall("putEvolvedSpore");
        if (!neighbors.contains(t)) {
            for (int i = 0; i < neighbors.size(); i++) {
                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                if (neighbors.get(i).getNeighbors().contains(t)) {
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


    /**
     * Eltávolítja a megadott spórákat a Tecton objektumhoz tartozó spórák listájából.
     *
     * @param slist A spórák listája, amelyeket el szeretnénk távolítani.
     * @return Igaz értéket ad vissza, ha a művelet végrehajtása sikeres.
     */
    public boolean removeSpores(List<Spore> slist) {
        spores.removeAll(slist);
        return true;
    }

}
