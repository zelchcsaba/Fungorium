package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.*;

import static model.InsectState.*;  // InsectState.NORMAL helyett lehet írni simán, hogy NORMAL
import static model.MushroomState.*;

import view.IView;
import view.View;

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
        randomize = false;
        insectPlayers = new ArrayList<>();
        fungusPlayers = new ArrayList<>();
        tList = new ArrayList<>();
    }

    public void processCmd(String cmd) {
        String[] command = cmd.split(" ");
        switch (command[0]) {

            case "createTecton": {
                int type = randomize(4);
                ITectonController t;
                switch (type) {
                    case 0:
                        t = new MultiThreadTecton();
                        break;
                    case 1:
                        t = new SingleThreadTecton();
                        break;
                    case 2:
                        t = new AbsorbingTecton();
                        break;
                    case 3:
                        t = new KeepThreadTecton();
                        break;
                    default:
                        t = new MultiThreadTecton();
                        break;
                }

                String name = getNewTectonName();
                objects.put(name, t);
                tList.add(t);
                break;
            }


            case "createSpore": {//<Spórafajta> <Tektonnév> <Fonálnév>
                //  Paraméterek kinyerése
                String type = command[1];
                String tectonName = command[2];
                String fungalName = command[3];

                // Megfelelő objektum inicializálása
                Spore spore;
                switch (type) {
                    case "SlowingSpore":
                        spore = new SlowingSpore();
                        break;
                    case "SpeedSpore":
                        spore = new SpeedSpore();
                        break;
                    case "ParalysingSpore":
                        spore = new ParalysingSpore();
                        break;
                    case "NoCutSpore":
                        spore = new NoCutSpore();
                        break;
                    case "DividingSpore":
                        spore = new DividingSpore();
                        break;
                    default:
                        System.out.println("helytelen parancs");
                        return;
                }

                // Asszociációk beállítása
                Tecton tecton = (Tecton) objects.get(tectonName);
                tecton.addSpore(spore);

                FungalThread fungal = (FungalThread) objects.get(fungalName);
                spore.setThread(fungal);

                // Konténerbe bele
                String name = getNewSporeName();
                objects.put(name, spore);
                break;
            }


            case "setNeighbors": {
                Tecton t = (Tecton) objects.get(command[1]);
                List<Tecton> neighborList = new ArrayList<>();
                for (int i = 2; i < command.length; i++) {
                    neighborList.add((Tecton) objects.get(command[i]));
                }
                t.setNeighbors(neighborList);
                break;
            }


            case "createShortLifeThread": {
                FungalThread f = new ShortLifeThread();

                List<Tecton> tlist = new ArrayList<>();
                tlist.add((Tecton) objects.get(command[1]));
                f.setTectons(tlist);

                FungusPlayer fplayer = (FungusPlayer) objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
                break;
            }


            case "createLongLifeThread": {
                FungalThread f = new LongLifeThread();

                List<Tecton> tlist = new ArrayList<>();
                tlist.add((Tecton) objects.get(command[1]));
                f.setTectons(tlist);

                FungusPlayer fplayer = (FungusPlayer) objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
                break;
            }


            case "createMushroom": {
                Mushroom m = new Mushroom();
                Tecton t = (Tecton) objects.get(command[1]);
                FungalThread f = (FungalThread) objects.get(command[2]);
                FungusPlayer fplayer = (FungusPlayer) objects.get(command[3]);

                m.setThread(f);
                m.setPosition(t);
                if (t.setMushroom(m)) {
                    fplayer.addMushroom(m);
                    String name = getNewMushroomName();
                    objects.put(name, m);
                } else {
                    System.out.println("Sikertelen");
                }
                break;
            }


            case "createEvolvedMushroom": { // <Tektonnév> <Fonálnév> <Játékosnév>
                // Paraméterek kinyerése
                String tectonName = command[1];
                String fungalName = command[2];
                String playerName = command[3];

                Tecton t = (Tecton) objects.get(tectonName);
                FungalThread f = (FungalThread) objects.get(fungalName);
                FungusPlayer fplayer = (FungusPlayer) objects.get(playerName);

                Mushroom m = new Mushroom();

                if (t.setMushroom(m)) {
                    m.setState(EVOLVED);
                    m.setThread(f);
                    m.setPosition(t);
                    MushroomAssociation mAssociation = new MushroomAssociation();
                    mAssociation.setMushroom(m);
                    mAssociation.setAge(6);
                    fplayer.addMushroomAssociation(mAssociation);
                    String name = getNewMushroomName();
                    objects.put(name, m);
                } else {
                    System.out.println("Sikertelen");
                }
                break;
            }


            case "createInsect": { // <Tektonnév> <Játékosnév>
                // Paraméterek kinyerése
                String tectonName = command[1];
                String playerName = command[2];

                // Megfelelő objektum inicializálása
                Insect insect = new Insect();

                // Asszociációk beállítása
                Tecton tecton = (Tecton) objects.get(tectonName);
                insect.setPosition(tecton);
                tecton.setInsect(insect);

                InsectPlayer iPlayer = (InsectPlayer) objects.get(playerName);
                iPlayer.addInsect(insect);

                //Konténerbe bele
                String name = getNewInsectName();
                objects.put(name, insect);
                break;
            }


            case "createFungusPlayers": {//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
                // Ellenőrizzük a parancs helyességét
                if (command.length > 5 || command.length != fungusPlayerCount + 1) {
                    System.out.println("túl sok paraméter");
                    return;
                }

                // Objektumok incializálása
                for (int i = 0; i < command.length; i++) {
                    FungusPlayer fPlayer = new FungusPlayer();
                    String name = command[i];
                    objects.put(name, fPlayer);
                    fungusPlayers.add(fPlayer);
                }
                break;
            }


            case "createInsectPlayers": {//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
                // Ellenőrizzük a parancs helyességét
                if (command.length > 5 || command.length != insectPlayerCount + 1) {
                    System.out.println("túl sok paraméter");
                    return;
                }

                // Objektumok incializálása
                for (int i = 0; i < command.length; i++) {
                    InsectPlayer iPlayer = new InsectPlayer();
                    String name = command[i];
                    objects.put(name, iPlayer);
                    insectPlayers.add(iPlayer);
                }
                break;
            }


            case "cut": { //<Rovarnév> <Tektonnév>
                // Paraméterek kinyerése
                String insectName = command[1];
                String tectonName = command[2];

                // Megfelelő objektumok előszedése
                Tecton tecton = (Tecton) objects.get(tectonName);
                Insect insect = (Insect) objects.get(insectName);

                // Ő következik?
                InsectPlayer insectPlayer = null;
                for (InsectPlayer iPlayer : insectPlayers) {
                    for (InsectAssociation insectA : iPlayer.getInsects()) {
                        if (insectA.getInsect() == insect) {
                            insectPlayer = iPlayer;
                        }
                    }
                }

                if (insectPlayer != currentPlayer) {
                    System.out.println("Ez nem a te rovarad!");
                    return;
                }

                // Segéd objektumok
                InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

                // Az ő rovarával akar vágni? (lehet fölösleges)
                if (insectAssociation == null) {
                    System.out.println("Ez a rovar nem a játékosodhoz tartozik.");
                    return;
                }
                // Tud vágni?
                if (insectAssociation.getCut()) {
                    System.out.println("Ez a rovar már vágott ebben a körben.");
                    return;
                }

                // Vágás
                insect.cut(tecton);
                insectAssociation.setCut(true);
                break;
            }


            case "saveResult": {
                try {
                    writeObjectsToFile(objects, new FileWriter("result.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }


            case "arrange": {
                break;
            }


            case "end": {
                break;
            }


            case "act": {
                if (!fungusPlayers.isEmpty()) {
                    currentPlayer = fungusPlayers.get(0);
                } else if (!insectPlayers.isEmpty()) {
                    currentPlayer = insectPlayers.get(0);
                } else {
                    System.out.println("Nincs jatekos");
                }
                break;
            }


            case "runTest": {
                String fileName = command[1];

                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    String comm;
                    while ((comm = br.readLine()) != null) {
                        System.out.println(comm);
                        processCmd(comm);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

<<<<<<< HEAD
            // uaz mint az előző, csak a szemantika más, ezt azért használjunk, hogy felépítsünk egy kezdő pályát
            case "loadInit":{ //<InitFájlneve>
                String fileName = command[1];
                try{
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    String line;
                    while((line = br.readLine()) != null){
                        processCmd(line); // Parancs végrehajtása
                        System.out.println(line); // kiírjuk milyen parancsot hajtottunk végre 
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }

            case "setShotSpores":{
=======

            case "setShotSpores": {
>>>>>>> 8f6cf1fe1af6c2606265f2e2541d0aef85686ffe
                Mushroom m = (Mushroom) objects.get(command[1]);

                int shotSporesCount = Integer.parseInt(command[2]);
                m.setShootedSporesCount(shotSporesCount);
                break;
            }


            case "setMushroomAge": {
                Mushroom m = (Mushroom) objects.get(command[1]);

                int newAge = Integer.parseInt(command[2]);
                for (int i = 0; i < fungusPlayers.size(); i++) {
                    for (MushroomAssociation mushroomAss : fungusPlayers.get(i).getMushrooms()) {
                        if (mushroomAss.getMushroom() == m) {
                            mushroomAss.setAge(newAge);
                        }
                    }
                }
                break;
            }


            case "stepGameRound": {
                int r = Integer.parseInt(command[1]);
                round += r;
                break;
            }


            case "evolve": {
                Mushroom m = (Mushroom) objects.get(command[1]);
                boolean returnV = m.evolve();
                if (!returnV) {
                    System.out.println("Sikertelen!");
                }
                break;
            }


            case "divide": {
                Insect insect = (Insect) objects.get(command[1]);

                Insect insect2 = insect.divide();
                if (insect2 != null) {
                    objects.put(getNewInsectName(), insect2);

                    for (InsectPlayer iPlayer : insectPlayers) {
                        for (InsectAssociation insectA : iPlayer.getInsects()) {
                            if (insectA.getInsect() == insect) {
                                iPlayer.addInsect(insect2);
                            }
                        }
                    }

                } else {
                    System.out.println("Sikertelen!");
                }
                break;
            }


            case "closeStep": {
                setCurrentPlayer();
                break;
            }


            case "shootSpore": {
                //Meg kell találni a gomba playerét, és meg kell nézni hogy a currentPlayer az-e

                Mushroom m = (Mushroom) objects.get(command[1]);
                Tecton t = (Tecton) objects.get(command[2]);

                FungusPlayer mushroomPlayer = null;
                for (FungusPlayer fPlayer : fungusPlayers) {
                    for (MushroomAssociation mushroomA : fPlayer.getMushrooms()) {
                        if (mushroomA.getMushroom() == m) {
                            mushroomPlayer = fPlayer;
                        }
                    }
                }

                if (mushroomPlayer != currentPlayer) {
                    System.out.println("Ez nem a te gombad!");
                    return;
                }

                boolean returnV = m.shootSpore(t);
                if (!returnV) {
                    System.out.println("Sikertelen!");
                }
                break;
            }


            case "putFirstInsect": {

                //itt csak ha kor 0
                //csak ha egy insectplayer

                Tecton t = (Tecton) objects.get(command[1]);
                Insect insect = new Insect();
                boolean returnV = t.putFirstInsect(insect);

                if (returnV) {
                    objects.put(getNewInsectName(), insect);
                    InsectPlayer iPlayer = (InsectPlayer) currentPlayer;
                    iPlayer.addInsect(insect);
                    setCurrentPlayer();
                } else {
                    System.out.println("Sikertelen!");
                }
                break;
            }


            case "move": { //<Rovarnév> <Tektonnév>
                // Paraméterek kinyerése
                String insectName = command[1];
                String tectonName = command[2];

                // Megfelelő objektumok előszedése
                Tecton tecton = (Tecton) objects.get(tectonName);
                Insect insect = (Insect) objects.get(insectName);

                // Ő következik?
                InsectPlayer insectPlayer = null;
                for (InsectPlayer iPlayer : insectPlayers) {
                    for (InsectAssociation insectA : iPlayer.getInsects()) {
                        if (insectA.getInsect() == insect) {
                            insectPlayer = iPlayer;
                        }
                    }
                }

                if (insectPlayer != currentPlayer) {
                    System.out.println("Ez nem a te rovarad!");
                    return;
                }

                // Segéd objektumok
                List<Spore> spores = tecton.getSpores();
                InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

                // Az ő rovarával akar lépni? (lehet fölösleges)
                if (insectAssociation == null) {
                    System.out.println("Ez a rovar nem a játékosodhoz tartozik.");
                    return;
                }

                // Tud lépni?
                if (insectAssociation.getMoved()) {
                    System.out.println("Ez a rovar már lépett ebben a körben.");
                    return;
                }

                // Lesz evés?
                boolean eat = false;
                if (!spores.isEmpty()) eat = true;

                // Lépés
                if (insect.move(tecton)) {
                    insectAssociation.setMoved(true);
                    if (eat) {
                        Spore spore = spores.getFirst();
                        objects.remove(spore);
                        spores.removeFirst();
                        insectPlayer.addPoint();
                    }
                }

                // Kapott effekt hatása
                if (insect.getState().equals(DIVIDED)) { // létrejön egy új rovar
                    Insect otherInsect = insect.divide();
                    if (otherInsect != null) {
                        String name = getNewInsectName();
                        objects.put(name, otherInsect);
                        insectPlayer.addInsect(otherInsect);
                    }
                }
                if (insect.getState().equals(SPEEDBOOST)) { // mintha nem is lépett volna
                    insectAssociation.setMoved(false);
                }
                if (insect.getState().equals(NOCUT) || insect.getState().equals(PARALYZED)) { // nocut = mintha már vágott volna
                    insectAssociation.setCut(true);                                       // paralyzed = mintha már vágott és lépett is volna (az utóbbi igaz is)      
                }
                // normal-t és slowed-et nem kell kezelni sztem
            }


            case "turnOnRandom": {
                randomize = true;
                break;
            }


            case "turnOffRandom": {
                randomize = false;
                break;
            }


            case "generateSpore": { // <Gombatest név> <Spórafajta>

                // Parancsok feldolgozása
                String mushroomName = command[1];
                String sporeType = command[2];

                // Gombatest lekérdezése
                Mushroom mushroom;
                if (objects.containsKey(mushroomName)) {
                    mushroom = (Mushroom) objects.get(mushroomName);
                } else {
                    System.out.println("Helytelen parancs! - Nincs ilyen nevű gombatest");
                    return;
                }


                //Spóra létrehozása, majd beállítása típusnak megfelelően
                Spore spore;

                switch (sporeType) {
                    case "SlowingSpore":
                        spore = new SlowingSpore();
                        break;
                    case "SpeedSpore":
                        spore = new SpeedSpore();
                        break;
                    case "ParalysingSpore":
                        spore = new ParalysingSpore();
                        break;
                    case "NoCutSpore":
                        spore = new NoCutSpore();
                        break;
                    case "DividingSpore":
                        spore = new DividingSpore();
                        break;
                    default:
                        System.out.println("Helytelen parancs! - Nincs ilyen Spóratípus");
                        return;
                }

                // Fonál beállítása a spórának
                spore.setThread(mushroom.getThread());

                // Spóra beállítása
                mushroom.generateSpore(spore);

                // Konténerbe bele
                objects.put(getNewSporeName(), spore);

                break;
            }


            case "setMaxRound": { // <Pozitív egész>
                int n = Integer.parseInt(command[1]);

                if (n > 0) {
                    maxRound = n;
                } // Max kör beállítása
                else {
                    System.out.println("Helytelen parancs! - csak pozitív szám fogadható el");
                }

                break;
            }


            case "setInsectPlayerCount": { // <Pozitív egész>
                int n = Integer.parseInt(command[1]);

                if (n >= 0) {
                    insectPlayerCount = n;
                } // Rovarászok számának beállítása
                else {
                    System.out.println("Helytelen parancs! - csak pozitív szám fogadható el");
                }
                break;
            }


            case "absorb": { // <Tektonnév>
                String tectonName = command[1];
                Tecton tecton;

                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Helytelen parancs! - Nincs ilyen nevű tekton");
                    return;
                }

                tecton.absorb();

                break;
            }


            case "timeCheck": {
                for (FungusPlayer fp : fungusPlayers) {   // Bejárjuk az Gombászok, és az összes fonáljára
                    fp.getThread().timeCheck();         // meghívjuk a timeCheck metódusát.
                }
                break;
            }


            case "branchThread": { // <Fonal név>  <Tektonnév>
                //meg kell nezni, hogy a currentplayer jatekose-e
                // Parancsok feldolgozása
                String threadName = command[1];
                String tectonName = command[2];

                // Megfelelő objektumok beállítása
                FungalThread thread;
                Tecton tecton;

                if (objects.containsKey(threadName) && objects.containsKey(tectonName)) {
                    thread = (FungalThread) objects.get(threadName);
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás fonál- vagy tektonnév");
                    return;
                }

                // Megnézzük, hogy annak a játékosnak a fonalával akarunk lépni amelyik most van soron
                FungusPlayer mushroomPlayer = null;
                for (FungusPlayer fPlayer : fungusPlayers) {
                    if (fPlayer.getThread() == thread) {
                        mushroomPlayer = fPlayer;
                    }
                }

                if (mushroomPlayer != currentPlayer) {
                    System.out.println("Ez nem a te gombad!");
                    return;
                }

                // Ha sikertelen akkor kiírja
                if (!thread.branchThread(tecton)) {
                    System.out.println("Sikertelen");
                }

                break;
            }


            case "eatInsect": { // <Fonal név>  <Rovarnév>
                String threadName = command[1];
                String insectName = command[2];

                // Megfelelő objektumok beállítása
                FungalThread thread;
                Insect insect;

                if (objects.containsKey(threadName) && objects.containsKey(insectName)) {
                    thread = (FungalThread) objects.get(threadName);
                    insect = (Insect) objects.get(insectName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás fonál- vagy rovarnév");
                    return;
                }

                // Megnézzük, hogy annak a játékosnak a fonalával akarunk lépni amelyik most van soron
                FungusPlayer mushroomPlayer = null;
                for (FungusPlayer fPlayer : fungusPlayers) {
                    if (fPlayer.getThread() == thread) {
                        mushroomPlayer = fPlayer;
                    }
                }

                if (mushroomPlayer != currentPlayer) {
                    System.out.println("Ez nem a te gombad!");
                    return;
                }

                // Ha sikertelen, akkor kiírja, egyébként kivesszi a rovart az objectsből
                if (!thread.eatInsect(insect)) {
                    System.out.println("Sikertelen");
                } else {
                    objects.remove(insectName, insect);
                }

                break;
            }


            case "assert": { //<Elvártkimenetfájlnév>
                // összehasonlítandó fájlok nevei
                String resultFile = "result.txt"; // Elmentett állapotot tartalmazza
                String expectedFile = command[1]; // Tesztesethez megadott fájlnév, a felhasználó adja meg, ezzel kell egyeznie a result.txt-nek

                try (
                        BufferedReader resultReader = new BufferedReader(new FileReader(resultFile));
                        BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFile));
                ) {
                    // Éppen olvasott sorok
                    String resultLine;
                    String expectedLine;

                    int lineNumber = 1;
                    boolean success = true;

                    while ((resultLine = resultReader.readLine()) != null && (expectedLine = expectedReader.readLine()) != null) {
                        System.out.println("r: " + resultLine); // Lehet ilyen sok mindent nem kéne kíírni, egyelőre jó lesz így debugolás miatt is
                        System.out.println("e: " + expectedLine);

                        if (!resultLine.equals(expectedLine)) {
                            System.out.println("Eltérés található a(z) " + lineNumber + ". sorban.");
                            success = false;
                            break;
                        }

                        lineNumber++;
                    }

                    if (success) {
                        System.out.println("Sikeres teszt: minden sor egyezik.");
                    } else {
                        System.out.println("Sikertelen teszt.");
                    }
                } catch (IOException e) {
                    System.out.println("Hiba a fájlok olvasása közben: " + e.getMessage());
                }
            }


            case "setTectons": {
                String threadName = command[1];

                FungalThread thread;
                if (objects.containsKey(threadName)) {
                    thread = (FungalThread) objects.get(threadName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás fonál név");
                    return;
                }

                List<Tecton> tectons = new ArrayList<>();

                for (int i = 2; i < command.length; i++) {
                    Tecton t = (Tecton) objects.get(command[i]);
                    tectons.add(t);
                }

                thread.setTectons(tectons);

                break;
            }


            case "setState": {
                String insectName = command[1];

                Insect insect;
                if (objects.containsKey(insectName)) {
                    insect = (Insect) objects.get(insectName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás rovar név");
                    return;
                }

                String state = command[2];

                switch (state) {
                    case "NORMAL":
                        insect.setState(NORMAL);
                        break;
                    case "SLOWED":
                        insect.setState(SLOWED);
                        break;
                    case "PARALYZED":
                        insect.setState(PARALYZED);
                        break;
                    case "DIVIDED":
                        insect.setState(DIVIDED);
                        break;
                    case "NOCUT":
                        insect.setState(NOCUT);
                        break;
                    case "SPEEDBOOST":
                        insect.setState(SPEEDBOOST);
                        break;
                    default:
                        System.out.println("Helytelen parancs! - Hibás állapot név");
                        return;
                }
                break;
            }


            case "setFungusPlayerCount": {
                int n = Integer.parseInt(command[1]);
                fungusPlayerCount = n;

                break;
            }


            case "break": {
                String tectonName = command[1];

                Tecton tecton;
                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Helytelen parancs! - Nincs ilyen nevű tekton");
                    return;
                }

                List<Tecton> tectons = new ArrayList<>(tecton.breakTecton());

                if (tectons == null) {
                    System.out.println("Sikertelen volt a parancs");
                    return;
                }

                String tectonName1 = getNewTectonName();
                objects.put(tectonName1, tectons.get(0));
                tList.add(tectons.get(0));

                String tectonName2 = getNewTectonName();
                objects.put(tectonName2, tectons.get(1));
                tList.add(tectons.get(1));

                objects.remove(tectonName, tecton);
                tList.remove(tecton);

                break;
            }


            case "deleteUnnecessaryThreads": {
                String threadName = command[1];
                FungalThread thread;
                if (objects.containsKey(threadName)) {
                    thread = (FungalThread) objects.get(threadName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás fonál név");
                    return;
                }

                thread.deleteUnnecessaryThreads();
                break;
            }


            case "putFirstMushroom": {
                if (round != 0)
                    return;

                String fungalType = command[1];
                String tectonName = command[2];

                Tecton tecton;
                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás tekton név");
                    return;
                }

                FungalThread thread;
                switch (fungalType) {
                    case "ShortLifeThread":
                        thread = new ShortLifeThread();
                        break;
                    case "LongLifeThread":
                        thread = new LongLifeThread();
                        break;
                    default:
                        System.out.println("Helytelen parancs! - Hibás fonál típus");
                        return;
                }

                Mushroom mushroom = new Mushroom();
                mushroom.setThread(thread);
                mushroom.setPosition(tecton);

                if (fungusPlayers.contains(currentPlayer)) {
                    FungusPlayer fungusPlayer = (FungusPlayer) currentPlayer;
                    fungusPlayer.addMushroom(mushroom);
                    fungusPlayer.setThread(thread);
                    currentPlayer.addPoint();
                    setCurrentPlayer();
                } else {
                    System.out.println("Helytelen parancs! - Jelenlegi játékos nem FungusPlayer típusú.");
                    return;
                }

                if (tecton.putFirstMushroom(thread, mushroom)) {
                    objects.put(getNewMushroomName(), mushroom);
                    objects.put(getNewThreadName(), thread);
                } else {
                    System.out.println("Sikertelen! Nem sikerült a gombát elhelyezni a tektonra.");
                    return;
                }

                break;
            }


            case "growMushroom": {
                String threadName = command[1];
                String tectonName = command[2];

                FungalThread thread;
                Tecton tecton;

                if (objects.containsKey(threadName) && objects.containsKey(tectonName)) {
                    thread = (FungalThread) objects.get(threadName);
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Helytelen parancs! - Hibás fonál- vagy tektonnév");
                    return;
                }

                Mushroom mushroom = new Mushroom();
                if (thread.growMushroom(tecton, mushroom)) {
                    objects.put(getNewMushroomName(), mushroom);
                } else {
                    System.out.println("Sikertelen! Nem sikerült a gombát elhelyezni a tektonra.");
                    return;
                }
                break;
            }


            case "loadResult": {
                readAndPrintFile("result.txt");
                break;
            }


            default: {
                System.out.println("Helytelen parancs");
            }
        }
    }


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
                } else {
                    currentPlayer = fungusPlayers.get(0);
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
                    initRound();
                } else {
                    currentPlayer = insectPlayers.get(0);
                    initRound();
                }
            } else {
                currentPlayer = insectPlayers.get(indexCurrentPlayer + 1);
            }
        }
    }


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
                        insectA.setMoved(false);
                        insectA.setCut(true);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == PARALYZED) {
                        insectA.setMoved(false);
                        insectA.setCut(false);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == NOCUT) {
                        insectA.setMoved(true);
                        insectA.setCut(false);
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == DIVIDED) {

                        insectA.getInsect().setState(NORMAL);
                    } else if (state == SPEEDBOOST) {
                        insectA.getInsect().setState(NORMAL);
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

                        } else { spore = new SpeedSpore(); }

                        //Hmmm jajjaj
                        spore.setThread((FungalThread) fungPlayer.getThread());
                        mushA.getMushroom().generateSpore(spore);
                    }
                }
            }

            if (round % 4 == 0) {
                for (ITectonController tecton : tList) {
                    tecton.absorb();
                }

                tList.get(randomize(tList.size())).breakTecton();
            }

        } else {
            FungusPlayer fWinner = null;
            InsectPlayer iWinner = null;
            int fMaxPoint = 0;
            int iMaxPoint = 0;
            for (FungusPlayer fPlayer : fungusPlayers) {
                if (fPlayer.getPoints() > fMaxPoint) {
                    fWinner = fPlayer;
                    fMaxPoint = fPlayer.getPoints();
                }
                System.out.println(fPlayer.getName() + "-" + fPlayer.getPoints());
            }

            for (InsectPlayer iPlayer : insectPlayers) {
                if (iPlayer.getPoints() > iMaxPoint) {
                    iWinner = iPlayer;
                    iMaxPoint = iPlayer.getPoints();
                }
                System.out.println(iPlayer.getName() + "-" + iPlayer.getPoints());
            }

            if (!(fWinner == null) || !(iWinner == null)) {
                System.out.println("Nyertesek: " + fWinner.getName() + " " + iWinner.getName());
            }
        }
    }


    public String getNewMushroomName() {
        mushroomCount++;
        String name = "m" + mushroomCount;
        System.out.println(name);
        return name;
    }


    public String getNewThreadName() {
        fungalThreadCount++;
        String name = "f" + fungalThreadCount;
        System.out.println(name);
        return name;
    }


    public String getNewSporeName() {
        sporeCount++;
        String name = "s" + sporeCount;
        System.out.println(name);
        return name;
    }


    public String getNewInsectName() {
        insectCount++;
        String name = "i" + insectCount;
        System.out.println(name);
        return name;
    }


    public String getNewTectonName() {
        tectonCount++;
        String name = "t" + tectonCount;
        System.out.println(name);
        return name;
    }


    public int randomize(int domain) {
        if (randomize == true) {
            IView rand = new View();
            return (rand.randomize() % domain);
        } else {
            return 0;
        }
    }


    private void writeObjectsToFile(Map<String, Object> objects, Writer writer) throws IOException {
        BufferedWriter bw = new BufferedWriter(writer);
        Map<Object, String> reverseLookup = new HashMap<>();
        Map<String, Object> sortedObjects = new TreeMap<>(objects); // ABC sorrend az objektumnevekre

        // 1. Objektumok listája
        bw.write("-begin-\n");
        for (Map.Entry<String, Object> entry : sortedObjects.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue().getClass().getSimpleName();
            bw.write(type + " " + name + "\n");
            reverseLookup.put(entry.getValue(), name);
        }
        bw.write("—\n");

        // 2. Attribútumok
        for (Map.Entry<String, Object> entry : sortedObjects.entrySet()) {
            String name = entry.getKey();
            Object obj = entry.getValue();
            Class<?> clazz = obj.getClass();

            bw.write(name + "\n");

            // Rendezett mezők
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    bw.write(field.getName());

                    if (value == null) {
                        bw.write(" -\n");
                    } else if (value instanceof Collection<?> col) {
                        if (col.isEmpty()) {
                            bw.write(" -\n");
                        } else {
                            List<String> names = new ArrayList<>();
                            for (Object item : col) {
                                names.add(reverseLookup.get(item));
                            }
                            Collections.sort(names);
                            for (String n : names) {
                                bw.write(" " + n);
                            }
                            bw.write("\n");
                        }
                    } else if (reverseLookup.containsKey(value)) {
                        bw.write(" " + reverseLookup.get(value) + "\n");
                    } else if (value.getClass().isEnum()) {
                        bw.write(" " + ((Enum<?>) value).name() + "\n");
                    } else {
                        bw.write(" " + value + "\n");
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Nem tudom elérni a mezőt: " + field.getName(), e);
                }
            }

            bw.write("\n");
        }

        bw.write("-end-\n");
        bw.flush();
    }


    public static void readAndPrintFile(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.err.println("Hiba a fájl beolvasása során: " + e.getMessage());
        }
    }
}
