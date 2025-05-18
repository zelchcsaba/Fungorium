package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.*;
import static model.InsectState.*;
import view.*;


/**
 * A Controller osztály a játék vezérléséért és logikájáért felelős.
 * Kezeli a játékosokat, a játéktáblát, a gombákat és a rovarokat,
 * valamint végrehajtja a játék során szükséges műveleteket.
 */
public class Controller {
    private int round;
    private HashMap<String, Object> objects;
    private int maxRound;
    private Player currentPlayer;

    private int mushroomCount;
    private int fungalThreadCount;
    private int sporeCount;
    private int insectCount;
    private int tectonCount;
    private boolean randomize;

    private List<InsectPlayer> insectPlayers;
    private List<FungusPlayer> fungusPlayers;
    private List<ITectonController> tList;
    private int fungusPlayerCount;
    private int insectPlayerCount;
    private GamePanel gPanel;


    /**
     * A Controller osztály konstruktora.
     * Inicializálja az osztály mezőit alapértelmezett értékekkel.
     * Meghatározza a játék logikájához szükséges alapállapotot.
     */
    public Controller() {
        round = 0;
        objects = new HashMap<>();
        maxRound = 5;
        currentPlayer = null;
        mushroomCount = 0;
        fungalThreadCount = 0;
        sporeCount = 0;
        insectCount = 0;
        tectonCount = 0;
        fungusPlayerCount = 0;
        insectCount = 0;
        randomize = true;
        insectPlayers = new ArrayList<>();
        fungusPlayers = new ArrayList<>();
        tList = new ArrayList<>();
        gPanel = null;
    }


    /**
     * Visszaadja az aktuális játékos nevét a játékosok nevéből és objektumaiból álló Map alapján.
     *
     * @return Az aktuális játékos neve, ha megtalálható, különben null.
     */
    public String getCurrentPlayerName() {
        for (Map.Entry<String, Object> entry : objects.entrySet())
            if (entry.getValue() == currentPlayer)
                return entry.getKey();

        return null;
    }


    /**
     * Visszaadja az aktuális játékkört.
     *
     * @return Az aktuális játékkört reprezentáló egész szám.
     */
    public int getRound() {
        return round;
    }


    /**
     * Visszaad egy Map-et, amely a rovarok azonosítóit párosítja a pontszámaikkal.
     *
     * @return Egy Map, amelyben a kulcsok rovar azonosítók,
     * az értékek pedig rovarokhoz tartozó pontszámok.
     */
    public Map<String, Integer> getInsectScores() {
        Map<String, Integer> insectScores = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : objects.entrySet())
            if (entry.getValue() instanceof InsectPlayer player)
                insectScores.put(entry.getKey(), player.getPoints());

        return insectScores;
    }


    /**
     * Visszaadja a gombákhoz tartozó pontszámokat egy Map-ben.
     * A Map kulcsai a játékosok nevei, az értékek pedig a hozzájuk tartozó pontszámok.
     *
     * @return Egy Map objektum, amely a játékosok neveit társítja a gombákhoz tartozó pontszámokhoz.
     */
    public Map<String, Integer> getFungalScores() {
        Map<String, Integer> fungalScores = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : objects.entrySet())
            if (entry.getValue() instanceof FungusPlayer player)
                fungalScores.put(entry.getKey(), player.getPoints());

        return fungalScores;
    }


    /**
     * Visszaadja az összes rendelkezésre álló rovar-játékost.
     *
     * @return A rovar-játékosok listája.
     */
    public List<InsectPlayer> getInsectPlayers() {
        return insectPlayers;
    }


    /**
     * Visszaadja a FungusPlayer objektumok listáját.
     *
     * @return A FungusPlayer objektumokat tartalmazó lista.
     */
    public List<FungusPlayer> getFungusPlayers() {
        return fungusPlayers;
    }


    /**
     * Lekéri a megfelelő FungusPlayer példányt, amely az adott IMushroomController példányhoz tartozik.
     *
     * @param m Az IMushroomController példány, amelyhez tartozó FungusPlayer-t meg kell találni.
     * @return A megtalált FungusPlayer példány, vagy null, ha nem található ilyen.
     */
    public FungusPlayer getMushroomPlayer(IMushroomController m) {
        for (FungusPlayer fPlayer : fungusPlayers)
            for (MushroomAssociation mAssociation : fPlayer.getMushrooms())
                if (m == mAssociation.getMushroom())
                    return fPlayer;

        return null;
    }


    /**
     * Megkeresi és visszaadja azt az InsectPlayer példányt, amelyikhez
     * a megadott IInsectController tartozik.
     *
     * @param i Az IInsectController példány, amelyhez tartozó InsectPlayer-t keresni kell.
     * @return Az InsectPlayer példány, amelyikhez a megadott IInsectController tartozik,
     * vagy null, ha nincs ilyen.
     */
    public InsectPlayer getInsectPlayer(IInsectController i) {
        for (InsectPlayer iPlayer : insectPlayers)
            for (InsectAssociation insectAssociation : iPlayer.getInsects())
                if (i == insectAssociation.getInsect())
                    return iPlayer;

        return null;
    }


    /**
     * Visszaadja a fonalhoz tartozó játékost.
     *
     * @param f Az IFungalThreadController objektum, amely alapján a játékos kikeresésre kerül.
     * @return A fonalhoz tartozó FungusPlayer objektum, vagy null, ha nem található ilyen játékos.
     */
    public FungusPlayer getThreadPlayer(IFungalThreadController f) {
        for (FungusPlayer fPlayer : fungusPlayers)
            for (MushroomAssociation mAssociation : fPlayer.getMushrooms())
                if (f == mAssociation.getMushroom().getThread())
                    return fPlayer;

        return null;
    }


    /**
     * Beállítja a játékpanelt.
     *
     * @param gPanel a játékpanel objektum, amelyet beállítunk
     */
    public void setGPanel(GamePanel gPanel) {
        this.gPanel = gPanel;
    }


    /**
     * Beállítja a maximális fordulók számát.
     *
     * @param n Az új maximális fordulószám, amelyet be kell állítani. Csak pozitív szám megengedett.
     */
    public void setMaxRound(int n) {
        if (n > 0)
            maxRound = n;
    }


    /**
     * Meghatározza a játék aktuális játékosát a rendelkezésre álló játékosok listái alapján.
     * Ha van gombász, az első lesz az aktuális játékos.
     * Ha nincsenek gombászok, de vannak rovarászok, akkor az első rovarász lesz az aktuális játékos.
     * Emellett ha rovarász kerül kiválasztásra, a játék állapota frissül a "PUTFIRSTINSECT" értékre.
     */
    public void act() {
        if (!fungusPlayers.isEmpty()) {
            currentPlayer = fungusPlayers.get(0);
        } else if (!insectPlayers.isEmpty()) {
            currentPlayer = insectPlayers.get(0);
            gPanel.setState(GameState.PUTFIRSTINSECT);
        }
    }


    /**
     * Visszaadja a gombászok számát.
     *
     * @return A gombászok száma.
     */
    public int getFungusPlayerCount() {
        return fungusPlayerCount;
    }


    /**
     * Beállítja a FungusPlayer játékosok számát.
     *
     * @param n a játékosok száma, amelyet be szeretnénk állítani. Csak nem negatív számot lehet megadni.
     */
    public void setFungusPlayerCount(int n) {
        if (n >= 0)
            fungusPlayerCount = n;
    }


    /**
     * Létrehozza az új FungusPlayer játékosokat a megadott nevek alapján.
     *
     * @param names A játékosok neveit tartalmazó lista, amely alapján az új FungusPlayer játékosok példányosításra kerülnek.
     *              A lista méretének egyeznie kell a fungusPlayerCount változó értékével, és nem haladhatja meg a 4-et.
     */
    public void createFungusPlayers(List<String> names) {
        if (names.size() > 4 || names.size() != fungusPlayerCount)
            return;

        for (String name : names) {
            FungusPlayer fPlayer = new FungusPlayer();
            objects.put(name, fPlayer);
            fungusPlayers.add(fPlayer);
        }
    }


    /**
     * Visszaadja a rovarászok számát.
     *
     * @return A rovarászok száma.
     */
    public int getInsectPlayerCount() {
        return insectPlayerCount;
    }


    /**
     * Beállítja a rovarászok számát.
     *
     * @param n A rovarászok száma (nemnegatív), amit be kell állítani.
     */
    public void setInsectPlayerCount(int n) {
        if (n >= 0)
            insectPlayerCount = n;
    }


    /**
     * Létrehozza a rovarászokat az adott nevek alapján.
     *
     * @param names A rovarászok neveit tartalmazó lista. A lista mérete nem haladhatja meg a 4-et,
     *              és pontosan meg kell egyeznie az elvárt rovarászok számával.
     */
    public void createInsectPlayers(List<String> names) {
        if (names.size() > 4 || names.size() != insectPlayerCount)
            return;

        for (String name : names) {
            InsectPlayer iPlayer = new InsectPlayer();
            objects.put(name, iPlayer);
            insectPlayers.add(iPlayer);
        }
    }


    /**
     * Egy adott tecton elemet tör szét, ha megfelelnek a körülmények.
     *
     * @param tecton Az a tecton vezérlő interfész, amely az adott tecton-t kezeli.
     * @return Igaz, ha a tecton sikeresen széttörésre került két darabra; hamis, ha a folyamat nem sikerült.
     */
    public boolean breakTecton(ITectonController tecton) {
        if (gPanel.canBreakTecton((Tecton) tecton)) {

            if (tecton == null || !objects.containsValue(tecton)) {
                return false;
            }

            List<Tecton> pieces = tecton.breakTecton();
            if (pieces == null || pieces.size() != 2) {
                return false;
            }

            // register new pieces
            Tecton a = pieces.get(0), b = pieces.get(1);
            String nameA = getNewTectonName();
            String nameB = getNewTectonName();

            gPanel.breakTecton((Tecton) tecton, a, b);

            objects.put(nameA, a);
            objects.put(nameB, b);
            tList.add(a);
            tList.add(b);
            // remove old
            objects.values().removeIf(o -> o == tecton);
            tList.remove(tecton);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Az adott rovar osztódását végzi el. Ha az osztódás sikeres
     * (az új rovar nem null), akkor az új rovart hozzáadja az objektumokhoz,
     * a rovarászhoz, valamint a grafikus panelhez.
     *
     * @param insect A rovar példány, amely osztódni fog.
     */
    public void divide(Insect insect) {
        Insect insect2 = insect.divide();
        if (insect2 != null) {
            objects.put(getNewInsectName(), insect2);

            InsectPlayer ip = null;

            for (InsectPlayer iPlayer : insectPlayers)
                for (InsectAssociation insectA : iPlayer.getInsects())
                    if (insectA.getInsect() == insect)
                        ip = iPlayer;

            ip.addInsect(insect2);
            gPanel.addInsect(insect2);
        }
    }


    /**
     * Lehelyezi az első gombát egy adott pozícióba a megadott típus alapján,
     * feltéve, hogy ez az első körben történik, és a művelet érvényes.
     *
     * @param type   A gombafonal típusának neve.
     * @param tecton Az a TectonController példány, amely a pozíció vezérléséért felelős.
     * @return true, ha a gomba sikeresen lehelyezésre került, különben false.
     */
    public boolean putFirstMushroom(String type, ITectonController tecton) {
        if (round != 0) {
            return false;
        }

        FungalThread thread = switch (type) {
            case "ShortLifeThread" -> new ShortLifeThread();
            case "LongLifeThread" -> new LongLifeThread();
            default -> new ShortLifeThread();
        };

        Mushroom mushroom = new Mushroom();
        mushroom.setThread(thread);
        mushroom.setPosition((Tecton) tecton);
        if (((fungusPlayers.contains(currentPlayer)) && tecton.putFirstMushroom(thread, mushroom))) {
            objects.put(getNewMushroomName(), mushroom);
            objects.put(getNewThreadName(), thread);

            FungusPlayer fungusPlayer = (FungusPlayer) currentPlayer;
            fungusPlayer.addMushroom(mushroom);
            fungusPlayer.setThread(thread);
            currentPlayer.addPoint();

            gPanel.addMushroom(mushroom);
            setCurrentPlayer();
            gPanel.repaint();
            return true;
        } else {
            gPanel.showError("Sikertelen gombatest lehelyezés");
            return false;
        }
    }


    /**
     * Az első rovar elhelyezése a játéktábla megfelelő helyére, ha a feltételek teljesülnek.
     *
     * @param tecton az ITectonController objektum, amely biztosítja a rovar lehelyezését.
     */
    public void putFirstInsect(ITectonController tecton) {
        if (round != 0) {
            return;
        }

        Insect insect = new Insect();

        if ((insectPlayers.contains(currentPlayer)) && (tecton.putFirstInsect(insect))) {
            objects.put(getNewInsectName(), insect);
            InsectPlayer iPlayer = (InsectPlayer) currentPlayer;
            iPlayer.addInsect(insect);

            gPanel.addInsect(insect);
            setCurrentPlayer();
            gPanel.repaint();
        } else {
            gPanel.showError("Sikertelen rovar lehelyezés");
        }
    }


    /**
     * Elágaztatja a játékos gombafonalát, amennyiben lehetséges. Ellenőrzi, hogy a
     * megfelelő játékos gombafonalát próbálják-e elágaztatni, és kezeli az ehhez
     * kapcsolódó feltételeket és hibákat.
     *
     * @param tecton az ITectonController interfészt megvalósító példány, amely a
     *               gombafonalak és spórák kezeléséhez szükséges információkat
     *               biztosítja.
     */
    public void branchThread(ITectonController tecton) {
        FungalThread thread;

        if (fungusPlayers.contains(currentPlayer)) {
            FungusPlayer fp = (FungusPlayer) currentPlayer;
            thread = (FungalThread) fp.getThread();
        } else return;

        if (round == 0)
            return;

        // Megnézzük, hogy annak a játékosnak a fonalával akarunk lépni amelyik most van
        // soron
        FungusPlayer mushroomPlayer = null;
        for (FungusPlayer fPlayer : fungusPlayers)
            if (fPlayer.getThread() == thread)
                mushroomPlayer = fPlayer;

        if (mushroomPlayer != currentPlayer)
            return;

        if (!mushroomPlayer.getBranchThread()) {

            // Ha sikertelen akkor kiírja
            if (!thread.branchThread((Tecton) tecton)) {
                gPanel.showError("Sikertelen gombafonál elágaztatás");
            } else {
                List<Spore> slist = new ArrayList<>();
                slist = tecton.getSpores();

                boolean isSpore = false;
                for (int i = 0; i < slist.size(); i++)
                    if (slist.get(i).getThread() == thread)
                        isSpore = true;

                if (!isSpore)
                    mushroomPlayer.setBranchThread(true);

                gPanel.repaint();
            }
        } else {
            gPanel.showError("Ebben a körben már ágaztattál el fonalat");
        }
    }


    /**
     * Kilövi a spórát egy adott gombából egy kiválasztott tecton célpontba.
     * A metódus ellenőrzi, hogy a kijelölt gomba az éppen aktuális játékoshoz tartozik-e,
     * valamint kezeli azokat a feltételeket, amelyek szükségesek a spóralövéshez.
     *
     * @param m Az a Mushroom objektum, amelyből a spóra kilövése történik.
     * @param t Az a ITectonController objektum, amely a spóra célpontját képviseli.
     */
    public void shootSpore(Mushroom m, ITectonController t) {
        if (round == 0)
            return;

        // Meg kell találni a gomba playerét, és meg kell nézni hogy a currentPlayer
        // az-e
        FungusPlayer mushroomPlayer = null;
        for (FungusPlayer fPlayer : fungusPlayers)
            for (MushroomAssociation mushroomA : fPlayer.getMushrooms())
                if (mushroomA.getMushroom() == m)
                    mushroomPlayer = fPlayer;

        if (mushroomPlayer != currentPlayer) {
            gPanel.showError("Más játékos gombatestét jelölted ki vagy nem jelöltél ki gombatestet");
            return;
        }

        if (!m.shootSpore((Tecton) t)) {
            gPanel.showError("Nem sikerült a spórát kilőni");
        } else {
            if (m.getShootedSporesCount() >= 10) {
                m.getPosition().removeMushroom();
                m.getThread().deleteUnnecessaryThreads();
                mushroomPlayer.rm(m);

                gPanel.removeMushroom(m);

                objects.entrySet().removeIf(entry -> entry.getValue() == m);
                gPanel.repaint();
            }
        }
    }


    /**
     * Gombatestet növeszt egy adott ITectonController segítségével, amennyiben
     * a feltételek teljesülnek. Elvégzi a szükséges ellenőrzéseket, a gombák és spórák
     * frissítését, valamint értesítési és vizuális frissítési műveleteket.
     *
     * @param tecton az aktuális TectonController, amely a spórák és gombák kezelésére szolgál
     */
    public void growMushroom(ITectonController tecton) {
        FungalThread thread;

        if (fungusPlayers.contains(currentPlayer)) {
            FungusPlayer fp = (FungusPlayer) currentPlayer;
            thread = (FungalThread) fp.getThread();
        } else return;

        if (round == 0)
            return;

        FungusPlayer mushroomPlayer = null;
        for (FungusPlayer fPlayer : fungusPlayers)
            if (fPlayer.getThread() == thread)
                mushroomPlayer = fPlayer;

        if (mushroomPlayer != currentPlayer)
            return;

        Mushroom mushroom = new Mushroom();
        List<Spore> slist = tecton.getSpores();
        List<Spore> removable = new ArrayList<>();

        if (thread.growMushroom((Tecton) tecton, mushroom)) {
            objects.put(getNewMushroomName(), mushroom);
            mushroomPlayer.addMushroom(mushroom);
            mushroomPlayer.addPoint();

            gPanel.addMushroom(mushroom);
            int thisSporeCount = 0;

            for (int i = 0; i < slist.size(); i++) {
                if (slist.get(i).getThread() == thread) {
                    thisSporeCount += 1;
                    if (thisSporeCount <= 3) {
                        removable.add(slist.get(i));
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                String str = null;
                for (Map.Entry<String, Object> entry : objects.entrySet()) {
                    if (entry.getValue().equals(removable.get(i))) {
                        str = entry.getKey();
                        break;
                    }
                }
                objects.remove(str);
            }

            for (int i = 0; i < 3; i++)
                tecton.removeSpores(removable);

            gPanel.repaint();
        } else {
            gPanel.showError("Nem sikerült gombatestet növeszteni");
        }
    }


    /**
     * Egy adott rovart megevését hajtja végre a metódus. Ellenőrzi, hogy a megfelelő játékos
     * végzi-e a műveletet, és hogy a rovar megehető állapotban van-e. Ha sikeres a művelet,
     * a rovar eltávolításra kerül az objektumok közül, és gombát hoz létre a helyére,
     * amennyiben lehetséges.
     *
     * @param insect az Insect példány, amelyet meg szeretnénk enni
     */
    public void eatInsect(Insect insect) {
        FungalThread thread;

        if (fungusPlayers.contains(currentPlayer)) {
            FungusPlayer fp = (FungusPlayer) currentPlayer;
            thread = (FungalThread) fp.getThread();
        } else return;

        // Megnézzük, hogy annak a játékosnak a fonalával akarunk lépni amelyik most van
        // soron
        FungusPlayer mushroomPlayer = null;
        for (FungusPlayer fPlayer : fungusPlayers)
            if (fPlayer.getThread() == thread)
                mushroomPlayer = fPlayer;

        if (mushroomPlayer != currentPlayer)
            return;

        boolean canEat = false;
        for (InsectPlayer iPlayer : insectPlayers)
            for (InsectAssociation insectA : iPlayer.getInsects())
                if (insectA.getInsect() == insect)
                    if ((insectA.getCut()) && (insectA.getMoved()))
                        canEat = true;

        if (canEat) {
            // Ha sikertelen, akkor kiírja, egyébként kivesszi a rovart az objectsből
            if (!thread.eatInsect(insect)) {
                gPanel.showError("Nem sikerült a rovart megenni");
            } else {
                if (insect.getPosition().canPutMushroom()) {
                    Mushroom m = new Mushroom();
                    m.setPosition(insect.getPosition());
                    m.setThread(thread);
                    if (insect.getPosition().setMushroom(m)) {
                        objects.put(getNewMushroomName(), m);
                        mushroomPlayer.addMushroom(m);
                        mushroomPlayer.addPoint();

                        gPanel.addMushroom(m);
                    }
                }

                InsectPlayer insectPlayer = null;
                for (InsectPlayer iPlayer : insectPlayers)
                    for (InsectAssociation insectA : iPlayer.getInsects())
                        if (insectA.getInsect() == insect)
                            insectPlayer = iPlayer;

                insectPlayer.rm(insect);
                gPanel.removeInsect(insect);
                objects.entrySet().removeIf(entry -> entry.getValue() == insect);
                gPanel.repaint();
            }
        } else {
            gPanel.showError("Ezt a rovart nem tudod megenni");
        }
    }


    /**
     * A megadott rovarral történő mozgást hajtja végre a játékban a megadott tecton vezérlő alapján.
     * Ez a metódus egy sor ellenőrzést végez a mozgás jogosultságának megállapítására,
     * majd végrehajtja a mozgást, és kezeli a spórák evését.
     *
     * @param insect Az a rovar, amelyik lépni fog a játékban. Nem lehet null értékű, és a játékosnak birtokolnia kell.
     * @param tecton A játékban használt tecton vezérlő, amely biztosítja a megfelelő interakciókat
     *               a spórákkal és a lépés különböző eseményeivel.
     */
    public void move(Insect insect, ITectonController tecton) {
        if (round == 0)
            return;

        // Ő következik?
        InsectPlayer insectPlayer = null;
        for (InsectPlayer iPlayer : insectPlayers)
            for (InsectAssociation insectA : iPlayer.getInsects())
                if (insectA.getInsect() == insect)
                    insectPlayer = iPlayer;

        if (insectPlayer != currentPlayer) {
            gPanel.showError("Ez nem a te rovarad, vagy nem jelöltél ki rovart");
            return;
        }

        // Segéd objektumok
        List<Spore> spores = tecton.getSpores();
        InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

        // Az ő rovarával akar lépni?
        if (insectAssociation == null) {
            gPanel.showError("Ez nem a te rovarad, vagy nem jelöltél ki rovart");
            return;
        }

        // Tud lépni?
        if (insectAssociation.getMoved()) {
            gPanel.showError("Már léptél ebben a körben, vagy olyan állapotban vagy, ami ezt nem engedi");
            return;
        }

        // Lesz evés?
        boolean eat = false;
        if (!spores.isEmpty())
            eat = true;

        // Lépés
        if (insect.move((Tecton) tecton)) {
            insectAssociation.setMoved(true);
            if (eat) {
                Spore spore = spores.get(0);

                String str = null;
                for (Map.Entry<String, Object> entry : objects.entrySet()) {
                    if (entry.getValue().equals(spore)) {
                        str = entry.getKey();
                        break;
                    }
                }

                objects.remove(str);
                List<Spore> rm = new ArrayList<>();
                rm.add(spores.get(0));
                tecton.removeSpores(rm);
                insectPlayer.addPoint();
                gPanel.repaint();
            }
        } else {
            gPanel.showError("A lépés sikertelen");
        }

        // Kapott effekt hatása
        if (insect.getState().equals(DIVIDED))  // létrejön egy új rovar
            divide(insect);

        if (insect.getState().equals(SPEEDBOOST)) { // mintha nem is lépett volna
            insectAssociation.setMoved(false);
            insect.setState(NORMAL);
            gPanel.showInformation("A rovar SPEEDBOOST állapotba került");
        }

        if (insect.getState().equals(NOCUT)) { // nocut = mintha már vágott volna
            insectAssociation.setCut(true); // paralyzed = mintha már vágott és lépett is volna (az utóbbi igaz is)
            gPanel.showInformation("A rovar NOCUT állapotba került");
        }

        if (insect.getState().equals(PARALYZED)) {
            insectAssociation.setCut(true);
            gPanel.showInformation("A rovar PARALYZED állapotba került");
        }

        if (insect.getState().equals(SLOWED)) {
            gPanel.showInformation("A rovar SLOWED állapotba került");
        }
    }


    /**
     * Kezdeményezi egy rovar adott tekton objektumra való vágás műveletét.
     *
     * @param insect A rovar, amely végrehajtja a vágást.
     * @param tecton Az a tekton objektum, amelyen a vágást végre kell hajtani.
     */
    public void cut(Insect insect, ITectonController tecton) {
        if (round == 0)
            return;

        // Ő következik?
        InsectPlayer insectPlayer = null;
        for (InsectPlayer iPlayer : insectPlayers)
            for (InsectAssociation insectA : iPlayer.getInsects())
                if (insectA.getInsect() == insect)
                    insectPlayer = iPlayer;

        if (insectPlayer != currentPlayer) {
            gPanel.showError("Ez nem a te rovarad, vagy nem jelöltél ki rovart");
            return;
        }

        // Segéd objektumok
        InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

        // Az ő rovarával akar vágni?
        if (insectAssociation == null) {
            gPanel.showError("Ez nem a te rovarad, vagy nem jelöltél ki rovart");
            return;
        }

        // Tud vágni?
        if (insectAssociation.getCut()) {
            gPanel.showError("Már vágtál ebben a körben, vagy olyan állapotban vagy, ami ezt nem engedi");
            return;
        }

        // Vágás
        if (insect.cut((Tecton) tecton)) {
            insectAssociation.setCut(true);
            gPanel.repaint();
        } else {
            gPanel.showError("Sikertelen vágás");
        }
    }


    /**
     * Új Tecton objektumot hoz létre.
     *
     * @param type a létrehozandó Tecton objektum típusa
     * @return az újonnan létrehozott Tecton objektum
     */
    Tecton createTecton(String type) {
        Tecton t;
        switch (type) {
            case "MultiThreadTecton":
                t = new MultiThreadTecton();
                break;
            case "SingleThreadTecton":
                t = new SingleThreadTecton();
                break;
            case "AbsorbingTecton":
                t = new AbsorbingTecton();
                break;
            case "KeepThreadTecton":
                t = new KeepThreadTecton();
                break;
            default:
                t = new MultiThreadTecton();
                break;
        }

        String name = getNewTectonName();
        objects.put(name, t);
        tList.add(t);
        return t;
    }


    /**
     * Beállítja a szomszédokat a megadott lista alapján.
     *
     * @param neighborList A szomszédokat tartalmazó tömb, ahol az első elem azonosítja
     *                     azt az objektumot, amelyhez a szomszédokat be kell állítani. A listában
     *                     további elemek a szomszédokat jelölik, "null" megadása esetén a szomszéd értéke null lesz.
     */
    void setNeighbors(String[] neighborList) {
        ITectonController t = (ITectonController) objects.get(neighborList[0]);
        List<Tecton> nList = new ArrayList<>();

        for (int i = 1; i < neighborList.length; i++) {
            if (neighborList[i].equals("null")) {
                nList.add(null);
            } else {
                nList.add((Tecton) objects.get(neighborList[i]));
            }
        }
        t.setNeighbors(nList);
    }


    /**
     * Betölti a Tecton elemeket egy megadott fájlból, feldolgozza a
     * pontokat és szomszédságokat, majd hozzáadja az elemeket a grafikus
     * panelhez.
     *
     * @param filename Az a fájlnév, amelyből a Tecton elemeket be kívánjuk tölteni.
     */
    public void loadTecton(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            boolean readPoints = true;

            while ((line = br.readLine()) != null) {
                String[] str = line.split(" ");
                if (str[0].equals("neighbors"))
                    break;

                List<Integer> points = new ArrayList<>();
                for (int i = 0; i < str.length - 1; i++)
                    points.add(Integer.parseInt(str[i]));

                Tecton t = createTecton(str[str.length - 1]);
                gPanel.addTecton(points, t, str[str.length - 1]);
            }

            while ((line = br.readLine()) != null) {
                String[] str = line.split(" ");
                setNeighbors(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * A closestep metódus a játék körének egy lépését zárja le.
     * Ez a metódus beállítja az aktuális játékost, majd újrarajzoltatja a játékpanelt
     * annak érdekében, hogy frissítse a játék állapotának vizuális megjelenítését.
     */
    public void closestep() {
        setCurrentPlayer();
        gPanel.repaint();
    }


    /**
     * Beállítja az aktuális játékost a játékmenetben, figyelembe véve a gombász és
     * rovarász játékosok listáját.
     * A metódus ellenőrzi, hogy a jelenlegi játékos megtalálható-e a gombász vagy
     * rovarász játékosok között.
     * Ezen információ alapján lépteti előre az aktuális játékost a lista alapján,
     * vagy visszaáll a lista elejére,
     * ha elérte a lista végét. Ha szükséges, új kört is inicializál.
     */
    public void setCurrentPlayer() {
        int indexCurrentPlayer = -1;

        if (fungusPlayers.contains(currentPlayer)) {
            for (int i = 0; i < fungusPlayers.size(); i++) {
                if (currentPlayer.equals(fungusPlayers.get(i))) {
                    indexCurrentPlayer = i;
                }
            }

            if (indexCurrentPlayer == fungusPlayers.size() - 1) {
                if (!insectPlayers.isEmpty()) {
                    currentPlayer = insectPlayers.get(0);

                    if (gPanel.getState() == GameState.PUTFIRSTMUSHROOM) {
                        gPanel.setState(GameState.PUTFIRSTINSECT);
                    } else {
                        gPanel.setState(GameState.WAITINSECTCOMMAND);
                    }

                } else {
                    currentPlayer = fungusPlayers.get(0);

                    gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    initRound();
                }
            } else {
                currentPlayer = fungusPlayers.get(indexCurrentPlayer + 1);
            }

        } else if (insectPlayers.contains(currentPlayer) && indexCurrentPlayer == -1) {
            for (int i = 0; i < insectPlayers.size(); i++) {
                if (currentPlayer.equals(insectPlayers.get(i))) {
                    indexCurrentPlayer = i;
                }
            }
            if (indexCurrentPlayer == insectPlayers.size() - 1) {
                if (!fungusPlayers.isEmpty()) {
                    currentPlayer = fungusPlayers.get(0);

                    gPanel.setState(GameState.WAITFUNGALCOMMAND);

                    initRound();
                } else {
                    currentPlayer = insectPlayers.get(0);
                    gPanel.setState(GameState.WAITINSECTCOMMAND);
                    initRound();
                }
            } else {
                currentPlayer = insectPlayers.get(indexCurrentPlayer + 1);
            }
        }
    }


    /**
     * A játékkör inicializálását végző metódus.
     * <p>
     * Növeli a jelenlegi kör számlálóját, majd ellenőrzi, hogy az aktuális kör
     * kevesebb-e, mint a maximális körszám. Ha igen, a metódus több alfolyamatot
     * hajt végre a szereplők állapotának frissítése és az események kezelése
     * érdekében.
     */
    public void initRound() {
        round++;

        if (round < maxRound) {
            for (FungusPlayer fungPlayer : fungusPlayers) {
                fungPlayer.getThread().timeCheck();
            }

            for (InsectPlayer insPlayer : insectPlayers) {
                for (InsectAssociation insectA : insPlayer.getInsects()) {
                    InsectState state = insectA.getInsect().getState();
                    if (state == SLOWED) {
                        insectA.setMoved(true);
                        insectA.setCut(false);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == PARALYZED) {
                        insectA.setMoved(true);
                        insectA.setCut(true);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == NOCUT) {
                        insectA.setMoved(false);
                        insectA.setCut(true);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == DIVIDED) {
                        insectA.setMoved(false);
                        insectA.setCut(false);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == SPEEDBOOST) {
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == NORMAL) {
                        insectA.setMoved(false);
                        insectA.setCut(false);
                    }
                }
            }

            for (FungusPlayer fungPlayer : fungusPlayers) {
                fungPlayer.setBranchThread(false);

                for (MushroomAssociation mushA : fungPlayer.getMushrooms()) {
                    mushA.setAge(mushA.getAge() + 1);
                    if (mushA.getAge() >= 5) {
                        mushA.getMushroom().evolve();
                    }

                    if (round % 2 == 0) {
                        Spore spore = null;
                        if (randomize) {
                            int randNum = randomize(5);

                            switch (randNum) {
                                case 0: {
                                    spore = new SlowingSpore();
                                    break;
                                }

                                case 1: {
                                    spore = new SpeedSpore();
                                    break;
                                }

                                case 2: {
                                    spore = new ParalysingSpore();
                                    break;
                                }

                                case 3: {
                                    spore = new NoCutSpore();
                                    break;
                                }

                                case 4: {
                                    spore = new DividingSpore();
                                    break;
                                }

                                default: {
                                    spore = new SpeedSpore();
                                    break;
                                }
                            }

                        } else {
                            spore = new SpeedSpore();
                        }

                        mushA.getMushroom().generateSpore(spore);
                        objects.put(getNewSporeName(), spore);
                    }
                }
            }

            if (round % 4 == 0) {
                for (ITectonController tecton : tList)
                    tecton.absorb();

                int rnumb = randomize(tList.size() - 1);
                if (!breakTecton((Tecton) tList.get(rnumb))) {
                    rnumb = randomize(tList.size() - 1);
                    breakTecton((Tecton) tList.get(rnumb));
                }
            }
        } else {
            gPanel.endGame();
        }
    }


    /**
     * Új gomba név generálása növekvő sorszám alapján.
     * A metódus növeli a mushroomCount mező értékét, és az értéket
     * kombinálja az "m" előtaggal az új név előállításához.
     *
     * @return Az újonnan generált gomba név, amely "m" előtagból és a megnövelt
     *         számból áll.
     */
    public String getNewMushroomName() {
        mushroomCount++;
        String name = "m" + mushroomCount;
        return name;
    }


    /**
     * Új fonál név generálása növekvő sorszám alapján.
     * A metódus növeli a fungalThreadCount mező értékét, és az értéket
     * kombinálja az "f" előtaggal az új név előállításához.
     *
     * @return Az újonnan generált fonál név, amely "f" előtagból és a megnövelt
     *         számból áll.
     */
    public String getNewThreadName() {
        fungalThreadCount++;
        String name = "f" + fungalThreadCount;
        return name;
    }


    /**
     * Új spóra név generálása növekvő sorszám alapján.
     * A sporeCount változó értékét egyesével növeli,
     * majd az értéket kombinálja az "s" előtaggal az új név előállításához.
     *
     * @return Az újonnan generált spóra név, amely "s" előtagból és a megnövelt
     *         számból áll.
     */
    public String getNewSporeName() {
        sporeCount++;
        String name = "s" + sporeCount;
        return name;
    }


    /**
     * Új rovar név generálása növekvő sorszám alapján.
     * A metódus növeli az insectCount mező értékét, majd az értéket
     * kombinálja az "i" előtaggal az új név előállításához.
     *
     * @return Az új generált rovar név, amely "i" előtagból és a megemelt számból
     *         áll.
     */
    public String getNewInsectName() {
        insectCount++;
        String name = "i" + insectCount;
        return name;
    }


    /**
     * Új tecton név generálása növekvő sorszám alapján.
     * A tectonCount mező értékét egyesével növeli és azt használja
     * az új név előállításához a "t" előtaggal kombinálva.
     *
     * @return A generált új tecton név, amely "t" előtagból és a megemelt számból
     *         áll.
     */
    public String getNewTectonName() {
        tectonCount++;
        String name = "t" + tectonCount;
        return name;
    }


    /**
     * Egy véletlenszerű egész számot generál egy megadott domainen belül, feltéve,
     * hogy a randomizálás engedélyezett.
     *
     * @param domain Az a felső korlát (exkluzív), amelyen belül a véletlen számot
     *               generálni kell.
     * @return Egy véletlenszerű egész szám 0 és (domain-1) között, ha a
     *         randomizálás engedélyezett,
     *         különben 0.
     */
    public int randomize(int domain) {
        if (randomize) {
            return (gPanel.randomize() % domain);
        } else {
            return 0;
        }
    }

}