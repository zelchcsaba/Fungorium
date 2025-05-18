package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A FungalThread osztály egy fonál modellt definiál, amely gombák növekedését
 * és tektonok közötti kapcsolatot reprezentálja. Ez az osztály számos funkciót
 * biztosít
 * a fonál részeinek kezelésére, például hozzáadására, eltávolítására,
 * elágazására,
 * és a nem szükséges fonálrészek automatikus eltávolítására.
 */
public abstract class FungalThread implements IFungalThreadController, IFungalThreadView {

    private List<Tecton> tectons;
    protected List<timeToDie> life;


    /**
     * A FungalThread konstruktora, amely inicializálja a szükséges adattagokat.
     * A konstruktor létrehozza és inicializálja a tectons és life listákat, amelyek
     * a fonál működéséhez és kapcsolódásainak kezeléséhez szükséges információkat
     * tárolják.
     */
    public FungalThread() {
        tectons = new ArrayList<>();
        life = new ArrayList<>();
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
     * Visszaadja a jelenlegi tectonok listáját.
     *
     * @return egy lista, amely tartalmazza az összes tectont
     */
    public List<Tecton> getTectons() {
        return tectons;
    }


    /**
     * Eltávolítja a felesleges fonálszálakat, amelyek már nincsenek kapcsolatban olyan gombákat tartó
     * tectonokkal, amelyek az adott FungalThread objektumból származnak.
     * A metódus célja, hogy biztosítsa, hogy csak azok a fonálszálak és tectonok maradjanak meg,
     * amelyek ténylegesen kapcsolódnak egy gombatesthez és aktívan hozzájárulnak a fonál működéséhez.
     */
    public void deleteUnnecessaryThreads() {

        // létrehozok két segéd listát
        List<Tecton> fungalList = new ArrayList<>();
        List<Tecton> connectedTectons = new ArrayList<>();

        // végigmegyek a tectons listán, megnézem melyik tektonon van gomba, ezt
        // elmentem a fungalList listába
        for (int i = 0; i < tectons.size(); i++) {

            // megnézem, ha van-e rajta gombatest
            if ((tectons.get(i).isConnected(this))) {
                fungalList.add(tectons.get(i));
            }
        }

        // megkeresem azokat a fonálrészeket, amelyek kapcsolatban vannak ugyanolyan
        // fajból származó gombatesttel
        while (!fungalList.isEmpty()) {
            connectedTectons.add(fungalList.get(0));

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
        List<Tecton> removallist = new ArrayList<>();
        for (int i = 0; i < tectons.size(); i++) {
            if (!connectedTectons.contains(tectons.get(i))) {

                // leveszem a tektonokról a fonalat
                tectons.get(i).removeThread(this);
                removallist.add(tectons.get(i));
            }
        }
        for (int i = 0; i < removallist.size(); i++) {
            tectons.remove(removallist.get(i));
        }
    }


    /**
     * Egy új ágazó fonalat hoz létre, amely a meglévő fonálból indul és egy másik
     * tektonra kiterjed.
     *
     * @param t A céltekton, amelyhez az új fonalat csatlakoztatják.
     * @return Igaz, ha a fonál sikeresen létrejött és csatlakozott, hamis, ha a
     * csatlakozás nem sikerült.
     */
    public boolean branchThread(Tecton t) {
        if (t.getThreads().contains(this)) {
            return false;
        }
        if (!t.putThread(this)) {
            return false;
        } else {
            tectons.add(t);
            return true;
        }
    }


    /**
     * Hozzáadja a megadott Tectont a jelenlegi FungalThread-hez tartozó tectons
     * listához.
     *
     * @param t a Tecton példány, amelyet hozzá kell adni
     * @return true értéket ad vissza, ha a Tecton sikeresen hozzáadásra került;
     * false egyébként
     */
    public boolean addTecton(Tecton t) {
        return tectons.add(t);
    }


    /**
     * Eltávolítja a megadott tektont a jelenlegi tectons listából.
     *
     * @param t A tektont, amelyet el kell távolítani a tectons listából.
     * @return true értéket ad vissza, ha a művelet sikeres.
     */
    public boolean removeTecton(Tecton t) {

        tectons.remove(t);
        return true;
    }


    /**
     * Megpróbál egy új gombát növeszteni a megadott tektonon, amennyiben a
     * megfelelő feltételek teljesülnek.
     *
     * @param t A tekton, amelyen a gomba növekedni fog.
     * @param m A gomba, amelyet növeszteni kell.
     * @return Igaz értékkel tér vissza, ha a gomba sikeresen megnőtt a tektonon;
     * hamis egyébként.
     */
    public boolean growMushroom(Tecton t, Mushroom m) {

        if (t.canPutMushroom() && t.getThreads().contains(this)) {
            if (t.getMushroom() == null) {
                List<Spore> slist = t.getSpores();
                int thisSporeCount = 0;

                for (int i = 0; i < slist.size(); i++) {
                    if (slist.get(i).getThread() == this) {
                        thisSporeCount += 1;
                    }
                }

                boolean canGrow = false;
                if (thisSporeCount >= 3) {
                    canGrow = true;
                }

                if (!canGrow) {
                    return false;
                }

                m.setPosition(t);
                m.setThread(this);

                List<FungalThread> thisThread = new ArrayList<>();
                thisThread.add(this);
                t.setThreads(thisThread);

                t.putMushroom(m);

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * A megadott Tectonnak jelzi, hogy megsemmisítésre küldendő. A metódus
     * célja, hogy meghatározza, a Tecton alkalmas-e ezen folyamat végrehajtására,
     * és végrehajtja, ha igen.
     *
     * @param t A Tecton példány, amelyet megsemmisítésre kell vizsgálni és
     *          esetlegesen kezelni.
     * @return true értéket ad vissza, ha a Tecton sikeresen kezelésre került,
     * hamis, ha a folyamat sikertelen volt.
     */
    public abstract boolean sendToDie(Tecton t);


    /**
     * Csökkenti az életciklusban lévő objektumok idejét, majd eltávolítja azokat az
     * elemeket,
     * amelyek ideje nullára csökkent.
     * <p>
     * A metódus iterál az életlistában szereplő objektumokon, és az adott elemekhez
     * tartozó időt csökkenti.
     * Ha egy elem ideje nullára csökken, akkor az adott elemhez tartozó Tectonról
     * is
     * eltávolítja a fonalat (FungalThread), majd a Tectont és az elemet törli a
     * megfelelő listákból.
     * A metódus garantálja, hogy az iteráció a törlés után is helyesen folytatódik.
     * <p>
     * Ezen kívül meghívja a deleteUnnecessaryThreads() metódust, amely további
     * feldolgozásokat végez
     * a felesleges fonalak eltávolítása érdekében.
     */
    public void timeCheck() {
        for (int i = 0; i < life.size(); i++) {
            life.get(i).setTime(life.get(i).getTime() - 1);
        }
        int i = 0;
        while (i < life.size()) {
            if (life.get(i).getTime() == 0) {
                life.get(i).getTecton().removeThread(this);
                tectons.remove(life.get(i).getTecton());
                life.remove(i);
            } else {
                i += 1;
            }
        }
        deleteUnnecessaryThreads();
    }


    /**
     * Megpróbálja megenni a megadott rovart egy olyan tecton helyzetében, amely a
     * jelenlegi gombafonálhoz tartozik.
     * Ellenőrzi, hogy a rovart tartalmazó hely beletartozik-e a tectonok listájába,
     * majd megpróbálja eltávolítani a rovart.
     *
     * @param i A rovar (Insect példány), amelyet a fonálnak meg kell ennie.
     * @return true, ha a rovar sikeresen eltávolításra és elfogyasztásra került;
     * false, ha a folyamat nem sikerült.
     */
    public boolean eatInsect(Insect i) {
        if (tectons.contains(i.getPosition())) {
            if (i.getPosition().removeInsect()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
