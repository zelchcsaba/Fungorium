package fungorium;

import java.util.ArrayList;
import java.util.List;

/**
 * A Mushroom osztály egy gombát modellez a játékban, amely spórákat
 * képes generálni és kilőni egy adott tektonra. A gomba különböző
 * állapotokban lehet, és működéséhez teszter osztályt használ.
 */
public class Mushroom {

    Tester t;
    private Tecton position;
    private List<Spore> spores;
    private FungalThread thread;
    private MushroomState state;
    private int shootedSporesCount;


    /**
     * Létrehozza a Mushroom objektumot.
     *
     * @param t A teszteléshez használt Tester objektum.
     */
    public Mushroom(Tester t) {
        this.t = t;
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
        this.t.toCall("setPosition");
        this.t.returnValue.clear();
        this.t.toReturn();
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
        this.t.toCall("setThread");
        this.t.returnValue.clear();
        this.t.toReturn();
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
        //meghívjuk a teszter kiíró függvényt
        this.t.toCall("shootSpore");
        //ha nincs spóránk, amit kilőjünk, akkor nem lőhetünk
        if (spores.isEmpty()) {

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();

            return false;
        }
        boolean returnV = false;
        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(spores.get(0));
        this.t.parameters.add(position);
        if (state == MushroomState.UNEVOLVED) {
            returnV = t.putSpore(spores.get(0), position);
        } else {
            returnV = t.putEvolvedSpore(spores.get(0), position);
        }
        if (!returnV) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            //ha nem, akkor false-val térünk vissza
            return false;

        } else {
            //ha sikerült a spórát lerkani
            //növeljük a shooted spores countot 1-el
            shootedSporesCount += 1;
            //ha a 10. spórát is kilőtte, akkor a gombatestnek meg kell halnia
            if (shootedSporesCount == 10) {

                this.t.list.add(this);
                this.t.list.add(position);
                this.t.parameters.clear();
                //töröljük a tektonról
                position.removeMushroom();

                this.t.list.add(this);
                this.t.list.add(thread);
                this.t.parameters.clear();
                //töröljük azon gombafonál részeket, amelyekhez nem kapcsolódik gombatest
                thread.deleteUnnecessaryThreads();
            }

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
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
    // allapot beallitas
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
    // spora hozzaadas
    public boolean generateSpore(Spore sp) {
        spores.add(sp);
        return true;
    }

}
