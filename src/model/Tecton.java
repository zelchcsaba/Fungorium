package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Tecton osztály egy absztrakt osztály, amely a játék tektonjait
 * reprezentálja, ezek játékterület alapvető egységei.
 */
public abstract class Tecton implements ITectonController, ITectonView {

    protected List<Spore> spores;
    protected List<Tecton> neighbors;
    protected Insect i;

    /**
     * Létrehozza egy Tecton osztály példányát a megfelelő mezők inicializálásával.
     */
    public Tecton() {
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
        return spores;
    }

    /**
     * Beállítja a Tecton objektumhoz tartozó szomszédos Tecton-ok listáját.
     *
     * @param neighbors A szomszédos Tecton-ok listája, amelyet a Tecton példányhoz
     *                  társítani kell.
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
        return neighbors;
    }

    /**
     * Beállítja a Tecton-hoz tartozó rovar objektumot.
     *
     * @param i Az új rovar objektum, amelyet a Tecton-hoz rendelünk.
     */
    public void setInsect(Insect i) {
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

    public abstract boolean setMushroom(Mushroom mushroom);

    public abstract Mushroom getMushroom();

    public abstract void setThreads(List<FungalThread> threads);

    public abstract List<FungalThread> getThreads();

    public abstract boolean putMushroom(Mushroom m);

    public abstract boolean putThread(FungalThread f);

    public abstract void addThread(FungalThread f); // Csak simán add-olja az f-et nem végez ellenőrzést

    public abstract boolean removeMushroom();

    public abstract boolean removeThread(FungalThread f);

    public abstract List<Tecton> breakTecton();

    public abstract boolean putFirstMushroom(FungalThread f, Mushroom m);

    public abstract boolean isConnected(FungalThread f);

    public abstract void absorb();

    public abstract boolean canPutMushroom();

    // -- //

    /**
     * Spóra hozzáadása egy Tecton objektumhoz, feltéve, hogy a Tecton szomszédos.
     *
     * @param sp A Spore objektum, amelyet hozzá kívánunk adni.
     * @param t  A Tecton objektum, amelyhez a spórát hozzá szeretnénk adni.
     * @return Igaz, ha a művelet sikeres, hamis, ha a Tecton nem szomszédos, és a
     *         spóra nem lett hozzáadva.
     */
    public boolean putSpore(Spore sp, Tecton t) {
        if (!neighbors.contains(t)) {
            return false;
        } else {
            spores.add(sp);
            return true;
        }
    }

    /**
     * Spóra hozzáadása a listához.
     *
     * @param sp A Spore objektum, amelyet hozzá kívánunk adni.
     */
    public void addSpore(Spore sp) {
        spores.add(sp);
    }

    /**
     * Visszaadja azon Tecton-ok listáját, amelyek szomszédosak a jelenlegi
     * Tecton-nal,
     * és amelyek threads listájában megtalálható az adott FungalThread objektum.
     *
     * @param f Az a FungalThread objektum, amely alapján a keresést végrehajtjuk.
     * @return Azon Tecton objektumokat tartalmazó lista, amelyek megfelelnek a
     *         feltételeknek.
     */
    public List<Tecton> getThreadSection(FungalThread f) {

        List<Tecton> tectons = new ArrayList<>();

        // végigmegy a szomzsédokon, és lekéri a threads tömbjüket, ha ebben benne van
        // f, akkor hozzáadja a tectons listához
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i) != null) {
                List<FungalThread> list = neighbors.get(i).getThreads();
                if (list != null) {
                    if (list.contains(f)) {
                        tectons.add(neighbors.get(i));
                    }
                }
            }
        }

        return tectons;
    }

    /**
     * Ellenőrzi, hogy az aktuális Tecton objektumhoz nincs-e hozzárendelve rovar,
     * és ha nincs, az első rovart beállítja. Ha már létezik hozzárendelt rovar,
     * a metódus nem végez műveletet.
     *
     * @return true, ha sikeresen beállította az első rovart, vagy már volt
     *         hozzárendelve rovar;
     *         false, ha az inicializálatlan állapot vagy más okok miatt nem
     *         lehetett műveletet végrehajtani.
     */
    public boolean putFirstInsect(Insect ins) {
        if (i == null) {
            ins.setPosition(this);
            i = ins;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Egy rovart próbál helyezni az adott Tecton objektumba.
     *
     * @param ins A rovar objektum, amelyet a Tecton-ba kell helyezni.
     * @param t   Az a Tecton objektum, amelybe a rovar behelyezésre kerül.
     * @return Igaz, ha a rovar sikeresen behelyezésre került, hamis, ha a
     *         behelyezés sikertelen.
     */
    public boolean putInsect(Insect ins, Tecton t) {
        if (!isNeighbor(t)) {
            return false;
        }

        if (i == null) {
            List<FungalThread> list1;
            List<FungalThread> list2;

            // lekérjük a fonalakat

            list1 = this.getThreads();
            list2 = t.getThreads();

            boolean connected = false;
            for (int idx = 0; idx < list1.size(); idx++) {
                if (list2.contains(list1.get(idx))) {
                    connected = true;
                }
            }
            if (connected) {
                t.removeInsect();
                i = ins;
                i.setPosition(this);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Eltávolítja a Tecton objektumhoz társított rovart, ha van ilyen.
     *
     * @return true, ha a rovar sikeresen eltávolításra került, false, ha nem volt
     *         eltávolítandó rovar
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
     * @return true, ha a megadott Tecton szomszédos a hívó Tecton-nal, false
     *         különben.
     */
    public boolean isNeighbor(Tecton t) {
        if (neighbors.contains(t)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hozzáad egy listát a Tecton típusú objektumokból a meglévő szomszédos
     * Tecton-ok listájához.
     * A metódus hívást szimulál, frissíti a kapcsolódó objektum állapotát, majd
     * visszatérési értéket ad.
     *
     * @param tlist A Tecton objektumok listája, amelyet a szomszédok listájához
     *              hozzá kell adni.
     * @return true értéket ad vissza, ha a művelet sikeres volt.
     */
    public boolean addNeighbor(List<Tecton> tlist) {
        neighbors.addAll(tlist);
        return true;
    }

    /**
     * Eltávolít egy megadott szomszédos Tecton objektumot az aktuális Tecton
     * szomszédai közül.
     *
     * @param t A Tecton objektum, amelyet el akarunk távolítani az aktuális Tecton
     *          szomszédos objektumai közül.
     * @return Igaz értéket ad vissza, ha a szomszéd sikeresen eltávolításra került.
     */
    public boolean removeNeighbor(Tecton t) {
        neighbors.remove(t);
        return true;
    }

    /**
     * A metódus megpróbál egy kifejlődött spórát (Evolved Spore) hozzáadni
     * egy adott Tecton példányhoz, ha az megfelelő szomszédsági kritériumoknak
     * megfelel.
     *
     * @param sp A hozzáadni kívánt kifejlődött Spore objektum.
     * @param t  Az a cél Tecton példány, amelyhez a Spore-t hozzá kell adni.
     * @return Igaz (true), ha a spóra hozzáadása sikeres volt, hamis (false), ha
     *         nem.
     */
    public boolean putEvolvedSpore(Spore sp, Tecton t) {
        if (!neighbors.contains(t)) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i) != null) {
                    if (neighbors.get(i).getNeighbors().contains(t)) {
                        spores.add(sp);
                        return true;
                    }
                }
            }

            return false;
        } else {
            spores.add(sp);
            return true;
        }
    }

    /**
     * Eltávolítja a megadott spórákat a Tecton objektumhoz tartozó spórák
     * listájából.
     *
     * @param slist A spórák listája, amelyeket el szeretnénk távolítani.
     * @return Igaz értéket ad vissza, ha a művelet végrehajtása sikeres.
     */
    public boolean removeSpores(List<Spore> slist) {
        spores.removeAll(slist);
        return true;
    }

    public void exchange(Tecton t1, Tecton t2) {
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i) == t1) {
                neighbors.set(i, t2);
                break;
            }
        }
    }

}
