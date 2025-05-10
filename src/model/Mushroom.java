package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Mushroom osztály egy gombát modellez a játékban, amely spórákat
 * képes generálni és kilőni egy adott tektonra. A gomba különböző
 * állapotokban lehet, és működéséhez teszter osztályt használ.
 */
public class Mushroom implements IMushroomController, IMushroomView{

    private Tecton position;
    private List<Spore> spores;
    private FungalThread thread;
    private MushroomState state;
    private int shootedSporesCount;


    /**
     * A Mushroom osztály alapértelmezett konstruktora.
     * <p>
     * Inicializálja a Mushroom objektum alapértelmezett állapotát, beleértve a következőket:
     * - A pozíció kezdetben null értéken van.
     * - A spórák üres listaként inicializálódnak.
     * - A szál (thread) alaphelyzetben null értéket kap.
     * - Az állapot a MushroomState.UNEVOLVED értékre van állítva, jelezve a kezdeti fejletlen állapotot.
     * - A kilőtt spórák száma (shootedSporesCount) kezdetben 0.
     */
    public Mushroom() {
        position = null;
        spores = new ArrayList<>();
        thread = null;
        state = MushroomState.UNEVOLVED;
        shootedSporesCount = 0;
    }


    /**
     * Beállítja a pozíciót egy adott Tecton objektum alapján.
     *
     * @param position az új pozíciót meghatározó Tecton objektum
     */
    public void setPosition(Tecton position) {
        this.position = position;
    }


    /**
     * Visszaadja a gomba aktuális pozícióját.
     *
     * @return A gomba aktuális pozícióját reprezentáló Tecton objektum.
     */
    public Tecton getPosition() {
        return position;
    }


    /**
     * Beállítja az aktuális szálat a gomba számára.
     *
     * @param thread Az új FungalThread objektum, amelyet az osztály szála gyanánt állít be.
     */
    public void setThread(FungalThread thread) {
        this.thread = thread;
    }


    /**
     * A Mushroom osztályban lévő FungalThread típusú szál lekérdezésére szolgál.
     *
     * @return Visszaadja a Mushroom példányhoz tartozó FungalThread objektumot.
     */
    public FungalThread getThread() {
        return thread;
    }


    /**
     * Beállítja a spórák listáját a gomba számára.
     *
     * @param spores A spórák listája, amely a gombához tartozik és
     *               a spórákat (Spore típusú objektumokat) tartalmazza.
     */
    public void setSpores(List<Spore> spores) {
        this.spores = spores;
    }


    /**
     * Visszaadja a Mushroom objektumhoz tartozó spórák listáját.
     *
     * @return A Mushroom objektumhoz tartozó spórák listája (List<Spore> típusként).
     */
    public List<Spore> getSpores() {
        return spores;
    }


    /**
     * Beállítja a kilőtt spórák számát.
     *
     * @param shootedSporesCount A már kilőtt spórák száma, amelyet be kell állítani.
     */
    public void setShootedSporesCount(int shootedSporesCount) {
        this.shootedSporesCount = shootedSporesCount;
    }


    /**
     * Visszaadja a kilőtt spórák számát.
     *
     * @return a kilőtt spórák száma, egész számként
     */
    public int getShootedSporesCount() {
        return shootedSporesCount;
    }


    /**
     * Beállítja a gomba aktuális állapotát a megadott értékre.
     *
     * @param s Az új állapot, amely a MushroomState értékei közül választható.
     */
    public void setState(MushroomState s) {
        state = s;
    }


    /**
     * Visszaadja a Mushroom objektum aktuális állapotát.
     *
     * @return a MushroomState típusú állapot, amely lehet UNEVOLVED vagy EVOLVED.
     */
    public MushroomState getState() {
        return state;
    }


    /**
     * Kilövi a spórát egy megadott tektonra, és frissíti a gomba állapotát
     *
     * @param t A Tecton, amelyre a spórát lőjük.
     * @return true, ha a spóralövés sikeres volt; false, ha nem sikerült a spórát kilőni.
     */
    public boolean shootSpore(Tecton t) {
        //ha nincs spóránk, amit kilőjünk, akkor nem lőhetünk
        if (spores.isEmpty()) {

            return false;
        }

        boolean returnV = false;
        if (state == MushroomState.UNEVOLVED) {
            returnV = t.putSpore(spores.get(0), position);
        } else {
            returnV = t.putEvolvedSpore(spores.get(0), position);
        }
        if (!returnV) {
            //ha nem, akkor false-val térünk vissza
            return false;

        } else {
            //ha sikerült a spórát lerkani
            //növeljük a shooted spores countot 1-el
            shootedSporesCount += 1;
            spores.remove(0);
            //ha a 10. spórát is kilőtte, akkor a gombatestnek meg kell halnia
            if (shootedSporesCount == 10) {

                //töröljük a tektonról
                position.removeMushroom();
                //töröljük azon gombafonál részeket, amelyekhez nem kapcsolódik gombatest
                thread.deleteUnnecessaryThreads();
            }
            //true értékkel térünk vissza
            return true;
        }

    }


    /**
     * Az evolve metódus a gomba állapotát EVOLVED állapotra állítja,
     * jelezve, hogy a gomba fejlődése befejeződött.
     *
     * @return true értékkel tér vissza, ha az állapot sikeresen módosult EVOLVED értékre.
     */
    public boolean evolve() {
        state = MushroomState.EVOLVED;
        return true;
    }


    /**
     * Hozzáad egy új spórát a gomba spóra listájához.
     *
     * @param sp A hozzáadandó spóra objektum.
     * @return true, ha a spóra sikeresen hozzá lett adva.
     */
    public boolean generateSpore(Spore sp) {
        sp.setThread(this.thread);
        spores.add(sp);
        return true;
    }

}
