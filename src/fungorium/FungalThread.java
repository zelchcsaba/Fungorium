package fungorium;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A FungalThread osztály egy fonál modellt definiál, amely gombák növekedését
 * és tektonok közötti kapcsolatot reprezentálja. Ez az osztály számos funkciót biztosít
 * a fonál részeinek kezelésére, például hozzáadására, eltávolítására, elágazására,
 * és a nem szükséges fonálrészek automatikus eltávolítására.
 */
public class FungalThread {

    Tester t;
    private List<Tecton> tectons;


    /**
     * Egy új FungalThread objektum létrehozása a megadott Tester objektummal.
     *
     * @param t A Tester példány, amely a FungalThread-hez kapcsolódik és annak működését felügyeli.
     */
    public FungalThread(Tester t) {
        this.t = t;
        tectons = new ArrayList<>();
    }


    /**
     * Beállítja a fonalhoz tartozó tectonok listáját.
     *
     * @param tectons A tectonok listája, amelyeket be kell állítani.
     */
    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }


    /**
     * Egy új ágazó fonalat hoz létre, amely a meglévő fonálból indul és egy másik tektonra kiterjed.
     *
     * @param t A céltekton, amelyhez az új fonalat csatlakoztatják.
     * @return Igaz, ha a fonál sikeresen létrejött és csatlakozott, hamis, ha a csatlakozás nem sikerült.
     */
    public boolean branchThread(Tecton t) {
        this.t.toCall("branchThread"); // És itt iratjuk a testerrel.
        // Nincs megállás
        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(this);

        if (!t.putThread(this)) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        } else {
            tectons.add(t);
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
    }


    /**
     * Hozzáadja a megadott Tectont a jelenlegi FungalThread-hez tartozó tectons listához.
     *
     * @param t a Tecton példány, amelyet hozzá kell adni
     * @return true értéket ad vissza, ha a Tecton sikeresen hozzáadásra került; false egyébként
     */
    public boolean addTecton(Tecton t) {
        this.t.toCall("addTecton");
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();
        return tectons.add(t);
    }


    /**
     * Eltávolítja a megadott tektont a jelenlegi tectons listából.
     *
     * @param t A tektont, amelyet el kell távolítani a tectons listából.
     * @return true értéket ad vissza, ha a művelet sikeres.
     */
    public boolean removeTecton(Tecton t) {
        this.t.toCall("removeTecton");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        tectons.remove(t);
        return true;
    }


    /**
     * Növeszt egy új gombát a megadott Tecton objektummal kapcsolódva.
     * Ellenőrzi, hogy minden spóra a jelenlegi fonálrész segítségével működik-e,
     * majd létrehozza és megfelelően inicializálja a gombát.
     *
     * @param t A Tecton objektum, amelyhez kapcsolódva a gomba növekszik.
     * @return true, ha a gomba sikeresen létrejött és inicializálva lett,
     * false, ha az ellenőrzések során hiba történt.
     */
    public boolean growMushroom(Tecton t) {
        this.t.toCall("growMushroom");

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();

        t.getSpores();

        boolean allSporesUseThisThread = true;
        for (Spore s : t.spores) {
            if (s.getThread() != this) {
                allSporesUseThisThread = false;
                break;
            }
        }

        if (!allSporesUseThisThread) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }

        Mushroom m = (Mushroom) this.t.getObjectByValue("m");
        this.t.toCreate(this, m, "m");

        this.t.list.add(this);
        this.t.list.add(m);
        this.t.parameters.clear();
        this.t.parameters.add(t);

        m.setPosition(t);

        this.t.list.add(this);
        this.t.list.add(m);
        this.t.parameters.clear();
        this.t.parameters.add(this);

        m.setThread(this);

        List<FungalThread> thisThread = new ArrayList<>();
        thisThread.add(this);

        this.t.list.add(this);
        this.t.list.add(t);

        t.setThreads(thisThread);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(m);

        t.putMushroom(m);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        return true;
    }


    /**
     * Eltávolítja azokat a fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból származó gombatesthez.
     * <p>
     * A metódus végigiterál a tectons listán, és azokat a tektonokat, amelyek kapcsolódnak egy gombatestre,
     * összegyűjti egy ideiglenes connectedTectons listába. Azokon a tectonokon, amelyek nem szerepelnek a
     * connectedTectons listában, eltávolítja a fonálrészeket.
     * <p>
     * Függvényfolyamat:
     * 1. Kiválasztja azokat a tektonokat (Tecton példányokat), amelyeken gombatest található, és egy külön listába helyezi.
     * 2. Összegyűjti azokat a tektonokat, amelyek fonálrésszel kapcsolódnak ugyanolyan fajból származó gombatesthez.
     * 3. Azokból a tektonokból, amelyek nem szerepelnek a kapcsolódó tektonok listájában, eltávolítja a fonálrészeket.
     * <p>
     * A szimulációt segítő t.toCall() metódus különböző részfolyamatokat naplóz ki.
     */
    public void deleteUnnecessaryThreads() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("deleteUnnecessaryThreads"); // És itt iratjuk a testerrel.

        // létrehozok két segéd listát
        List<Tecton> fungalList = new ArrayList<>();
        List<Tecton> connectedTectons = new ArrayList<>();

        // végigmegyek a tectons listán, megnézem melyik tektonon van gomba, ezt
        // elmentem a fungalList listába
        for (int i = 0; i < tectons.size(); i++) {

            this.t.list.add(this);
            this.t.list.add(tectons.get(i));
            this.t.parameters.clear();

            // megnézem, ha van-e rajta gombatest
            if (tectons.get(i).getMushroom() != null) {
                fungalList.add(tectons.get(i));
            }
        }

        // megkeresem azokat a fonálrészeket, amelyek kapcsolatban vannak ugyanolyan
        // fajból származó gombatesttel
        while (!fungalList.isEmpty()) {
            connectedTectons.add(fungalList.get(0));

            this.t.list.add(this);
            this.t.list.add(fungalList.get(0));
            this.t.parameters.clear();
            this.t.parameters.add(this);

            // megnézem, hogy tectons.get(0) szomszédai közül melyeken van elágazva a fonál
            List<Tecton> list = fungalList.get(0).getThreadSection(this);
            for (int i = 0; i < list.size(); i++) {
                if (!connectedTectons.contains(list.get(i))) {
                    fungalList.add(list.get(i));
                }
            }
            fungalList.remove(0);
        }

        // Végigmegyek a tectons listán, majd azokról a tectonokról, amelyeken
        // olyanfonálrészek vannak,
        // amelyek nincsenek kapcsolatban ugyanolyan fajból származó gombatesttel
        // leszedjük a fonalat
        for (int i = 0; i < tectons.size(); i++) {
            if (!connectedTectons.contains(tectons.get(i))) {

                this.t.list.add(this);
                this.t.list.add(tectons.get(i));
                this.t.parameters.clear();
                this.t.parameters.add(this);
                // leveszem a tektonokról a fonalat
                tectons.get(i).removeThread(this);
            }
        }

        // visszatér a függvény
        this.t.returnValue.clear();
        this.t.toReturn();

    }


    /**
     * Visszaadja a jelenlegi tectonok listáját.
     *
     * @return egy lista, amely tartalmazza az összes tectont
     */
    public List<Tecton> getTectons() {
        return tectons;
    }

}
