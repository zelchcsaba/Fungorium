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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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


            case "saveResult": {
                try {
                    writeObjectsToFile(objects, new FileWriter("result.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "loadResult": {
                readAndPrintFile("result.txt");
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

            // ugyan az mint az előző, csak a szemantika más, ezt azért használjunk, hogy felépítsünk egy kezdő pályát
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
                break;
            }

            case "setMaxRound": { // <Pozitív egész>
                int n = Integer.parseInt(command[1]);

                if (n > 0) {
                    maxRound = n;
                } // Max kör beállítása
                else {
                    System.out.println("Sikertelen");
                }

                break;
            }

            case "stepGameRound": {
                int r = Integer.parseInt(command[1]);
                round += r;
                break;
            }


            case "turnOnRandom": {
                randomize = true;
                break;
            }


            case "turnOffRandom": {
                randomize = false;
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


            case "createTecton": {
                String type = command[1];
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

            case "setFungusPlayerCount": {
                int n = Integer.parseInt(command[1]);

                if (n >= 0) {
                    fungusPlayerCount = n;
                } // gombászok számának beállítása
                else {
                    System.out.println("Sikertelen");
                }
                break;
            }

            case "createFungusPlayers": {//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
                // Ellenőrizzük a parancs helyességét
                if (command.length > 5 || command.length != fungusPlayerCount + 1) {
                    System.out.println("Sikertelen");
                    return;
                }

		        // Objektumok incializálása
                for(int i = 1; i<command.length; i++){
                    FungusPlayer fPlayer = new FungusPlayer(); 
                    String name = command[i];
                    objects.put(name, fPlayer);
                    fungusPlayers.add(fPlayer);
                }
                break;
            }

            case "setInsectPlayerCount": { // <Pozitív egész>
                int n = Integer.parseInt(command[1]);

                if (n >= 0) {
                    insectPlayerCount = n;
                } // Rovarászok számának beállítása
                else {
                    System.out.println("Sikertelen");
                }
                break;
            }


            case "createInsectPlayers": {//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
                // Ellenőrizzük a parancs helyességét
                if (command.length > 5 || command.length != insectPlayerCount + 1) {
                    System.out.println("Sikertelen");
                    return;
                }

		        // Objektumok incializálása
                for(int i = 1; i<command.length; i++){
                    InsectPlayer iPlayer = new InsectPlayer(); 
                    String name = command[i];
                    objects.put(name, iPlayer);
                    insectPlayers.add(iPlayer);
                }
                break;
            }


            case "createShortLifeThread": {
                FungalThread f = new ShortLifeThread();
                Tecton t = (Tecton)objects.get(command[1]);

                List<Tecton> tlist = new ArrayList<>();
                List<FungalThread> flist = new ArrayList<>();

                tlist.add(t);
                flist.add(f);

                f.setTectons(tlist);
                t.setThreads(flist);
                
                FungusPlayer fplayer =(FungusPlayer)objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
                break;
            }


            case "createLongLifeThread": {  // ezt nézd meg hogy jo e
                FungalThread f = new LongLifeThread();
                Tecton t = (Tecton)objects.get(command[1]);
                
                List<Tecton> tlist = new ArrayList<>();
                List<FungalThread> flist = new ArrayList<>();

                tlist.add(t);
                flist.add(f);

                f.setTectons(tlist);
                t.setThreads(flist);

                FungusPlayer fplayer = (FungusPlayer) objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
                break;
            }


            case "setTectons": {
                String threadName = command[1];

                FungalThread thread;
                if (objects.containsKey(threadName)) {
                    thread = (FungalThread) objects.get(threadName);
                } else {
                    System.out.println("Sikertelen");
                    return;
                }

                for (int i = 2; i < command.length; i++) {
                    Tecton t = (Tecton) objects.get(command[i]);
                    thread.addTecton(t);
                    t.addThread(thread);
                }
                break;
            }


            case "createMushroom": {
                Mushroom m = new Mushroom();
                Tecton t = (Tecton) objects.get(command[1]);
                FungalThread f = (FungalThread) objects.get(command[2]);
                FungusPlayer fplayer = (FungusPlayer) objects.get(command[3]);

                if(f!= null){
                    m.setThread(f);
                    m.setPosition(t);
                    if (t.setMushroom(m)) {
                        fplayer.setPoints(fplayer.getPoints()+1);
                        fplayer.addMushroom(m);
                        String name = getNewMushroomName();
                        objects.put(name, m);
                    } else {
                        System.out.println("Sikertelen");
                    }
                }else{
                    System.out.println("Sikertelen");
                }
                break;
            }

            case "createEvolvedMushroom": { // <Tektonnév> <Fonálnév> <Játékosnév>
                // Paraméterek kinyerése
                String tectonName = command[1];
                String fungalName = command[2];
                String playerName = command[3];

                Mushroom m = new Mushroom();
                Tecton t = (Tecton) objects.get(tectonName);
                FungalThread f = (FungalThread) objects.get(fungalName);
                FungusPlayer fplayer = (FungusPlayer) objects.get(playerName);

                if(f!=null){
                    m.setState(EVOLVED);
                    m.setThread(f);
                    m.setPosition(t);
                    if (t.setMushroom(m)) {
                        fplayer.setPoints(fplayer.getPoints()+1);
                        MushroomAssociation mAssociation = new MushroomAssociation();
                        mAssociation.setMushroom(m);
                        mAssociation.setAge(6);
                        fplayer.addMushroomAssociation(mAssociation);
                        String name = getNewMushroomName();
                        objects.put(name, m);
                    } else {
                        System.out.println("Sikertelen");
                    }
                }else{
                    System.out.println("Sikertelen");
                }
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
                    System.out.println("Sikertelen");
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
                        spore = new SlowingSpore();
                        break;
                }
                // Spóra beállítása
                mushroom.generateSpore(spore);
                // Konténerbe bele
                objects.put(getNewSporeName(), spore);

                break;
            }

            case "setShotSpores":{
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


            case "setState": {
                String insectName = command[1];

                Insect insect;
                if (objects.containsKey(insectName)) {
                    insect = (Insect) objects.get(insectName);
                } else {
                    System.out.println("Sikertelen");
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
                        insect.setState(NORMAL);
                        break;
                }
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
                        spore = new SlowingSpore();
                        break;
                }
                // Asszociációk beállítása
                FungalThread fungal = (FungalThread) objects.get(fungalName);
                spore.setThread(fungal);

                Tecton tecton = (Tecton) objects.get(tectonName);
                tecton.addSpore(spore);

                // Konténerbe bele
                String name = getNewSporeName();
                objects.put(name, spore);
                break;
            }


            case "absorb": { // <Tektonnév>
                String tectonName = command[1];
                Tecton tecton;

                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Sikertelen");
                    return;
                }

                tecton.absorb();

                break;
            }

            case "deleteUnnecessaryThreads": {
                String threadName = command[1];
                FungalThread thread;
                if (objects.containsKey(threadName)) {
                    thread = (FungalThread) objects.get(threadName);
                } else {
                    System.out.println("Sikertelen");
                    return;
                }

                thread.deleteUnnecessaryThreads();
                break;
            }


            case "break": {
                String tectonName = command[1];

                Tecton tecton;
                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Sikertelen tektontores");
                    return;
                }

                List<Tecton> tectons = new ArrayList<>(tecton.breakTecton());

                if (tectons == null) {
                    System.out.println("Sikertelen tektontores");
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

                    InsectPlayer ip = null;
                    for (InsectPlayer iPlayer : insectPlayers) {
                        for (InsectAssociation insectA : iPlayer.getInsects()) {
                            if (insectA.getInsect() == insect) {
                                ip = iPlayer;
                            }
                        }
                    }

                    ip.addInsect(insect2);

                } else {
                    System.out.println("Sikertelen!");
                }
                break;
            }

            case "timeCheck": {
                for (FungusPlayer fp : fungusPlayers) {   // Bejárjuk az Gombászok, és az összes fonáljára
                    fp.getThread().timeCheck();         // meghívjuk a timeCheck metódusát.
                }
                break;
            }


            case "closestep":{
                setCurrentPlayer();
                break;
            }


            case "putFirstMushroom": {
                if (round != 0){
                    System.out.println("Sikertelen");
                    return;
                }

                String fungalType = command[1];
                String tectonName = command[2];

                Tecton tecton;
                if (objects.containsKey(tectonName)) {
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Sikertelen");
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
                        thread = new ShortLifeThread();
                        break;
                }

                Mushroom mushroom = new Mushroom();
                mushroom.setThread(thread);
                mushroom.setPosition(tecton);

                if (((fungusPlayers.contains(currentPlayer))&&tecton.putFirstMushroom(thread, mushroom))) {

                    objects.put(getNewMushroomName(), mushroom);
                    objects.put(getNewThreadName(), thread);

                    FungusPlayer fungusPlayer = (FungusPlayer) currentPlayer;
                    fungusPlayer.addMushroom(mushroom);
                    fungusPlayer.setThread(thread);
                    currentPlayer.addPoint();
                    setCurrentPlayer();

                } else {
                    System.out.println("Sikertelen");
                    return;
                }
                break;
            }

            case "putFirstInsect": {
                if (round != 0){
                    System.out.println("Sikertelen");
                    return;
                }
                    
                Tecton t = (Tecton) objects.get(command[1]);
                Insect insect = new Insect();

                if ((insectPlayers.contains(currentPlayer))&&(t.putFirstInsect(insect))) {
                    
                    objects.put(getNewInsectName(), insect);
                    InsectPlayer iPlayer = (InsectPlayer) currentPlayer;
                    iPlayer.addInsect(insect);
                    setCurrentPlayer();

                } else {
                    System.out.println("Sikertelen!");
                }
                break;
            }

            case "branchThread" : { // <Fonal név>  <Tektonnév>
                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }

                // Parancsok feldolgozása
                String threadName = command[1];
                String tectonName = command[2];

                // Megfelelő objektumok beállítása
                FungalThread thread=null;
                Tecton tecton=null;

                if (objects.containsKey(threadName) && objects.containsKey(tectonName)) {
                    thread = (FungalThread) objects.get(threadName);
                    tecton = (Tecton) objects.get(tectonName);
                }else{
                    System.out.println("Sikertelen");
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
                    System.out.println("Sikertelen");
                    return;
                }

                if(mushroomPlayer.getBranchThread()!=true){

                    // Ha sikertelen akkor kiírja
                    if (!thread.branchThread(tecton)) {
                        System.out.println("Sikertelen");
                    }else{
                        List<Spore> slist = new ArrayList<>();
                        slist = tecton.getSpores();
                        boolean isSpore = false;
                        for(int i=0; i< slist.size();i++){
                            if(slist.get(i).getThread()==thread){
                                isSpore = true;
                            }
                        }
                        if(!isSpore){
                            mushroomPlayer.setBranchThread(true);
                        }
                    }
                }else{
                    System.out.println("Sikertelen");
                }
                break;
            }


            case "shootSpore": {

                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }
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
                    System.out.println("Sikertelen");
                    return;
                }

                if (!m.shootSpore(t)) {
                    System.out.println("Sikertelen!");
                }else{
                    if(m.getShootedSporesCount()>=10){
                        m.getPosition().removeMushroom();
                        m.getThread().deleteUnnecessaryThreads();
                        mushroomPlayer.rm(m);
                        objects.remove(command[1], m);
                    }

                }
                break;
            }

            case "growMushroom": {
                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }
                String threadName = command[1];
                String tectonName = command[2];

                FungalThread thread;
                Tecton tecton;

                if (objects.containsKey(threadName) && objects.containsKey(tectonName)) {
                    thread = (FungalThread) objects.get(threadName);
                    tecton = (Tecton) objects.get(tectonName);
                } else {
                    System.out.println("Sikertelen");
                    return;
                }

                FungusPlayer mushroomPlayer = null;
                for (FungusPlayer fPlayer : fungusPlayers) {
                    if (fPlayer.getThread() == thread) {
                        mushroomPlayer = fPlayer;
                    }
                }

                if (mushroomPlayer != currentPlayer) {
                    System.out.println("Sikertelen");
                    return;
                }

                Mushroom mushroom = new Mushroom();

                List<Spore> slist = tecton.getSpores();
                List<Spore> removable = new ArrayList<>();

                if (thread.growMushroom(tecton, mushroom)) {

                    objects.put(getNewMushroomName(), mushroom);
                    mushroomPlayer.addMushroom(mushroom);
                    mushroomPlayer.addPoint();

                    int thisSporeCount = 0;

                    for(int i=0;i<slist.size();i++){
                        if(slist.get(i).getThread() == thread){
                            thisSporeCount+=1;
                            if(thisSporeCount<=3){
                            removable.add(slist.get(i));
                            }
                        }
                    }

                    for(int i=0; i<3;i++){
                    String str=null;
                    for (Map.Entry<String, Object> entry : objects.entrySet()) {
                        if (entry.getValue().equals(removable.get(i))) {
                            str = entry.getKey();
                            break;
                        }
                    }
                    objects.remove(str);
                    }
                    for(int i=0;i<3;i++){
                        tecton.removeSpores(removable);
                    }

                } else {
                    System.out.println("Sikertelen");
                    return;
                }
                break;
            }


            case "eatInsect": { // <Fonal név>  <Rovarnév>
                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }
                String threadName = command[1];
                String insectName = command[2];

                // Megfelelő objektumok beállítása
                FungalThread thread;
                Insect insect;

                if (objects.containsKey(threadName) && objects.containsKey(insectName)) {
                    thread = (FungalThread) objects.get(threadName);
                    insect = (Insect) objects.get(insectName);
                } else {
                    System.out.println("Sikertelen");
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
                    System.out.println("Sikertelen");
                    return;
                }

                boolean canEat=false;
                for (InsectPlayer iPlayer : insectPlayers) {
                    for (InsectAssociation insectA : iPlayer.getInsects()) {
                        if (insectA.getInsect() == insect) {
                            if((insectA.getCut()==true)&&(insectA.getMoved()==true)){
                                canEat = true;
                            }
                        }
                    }
                }
                
                if(canEat){
                // Ha sikertelen, akkor kiírja, egyébként kivesszi a rovart az objectsből
                    if (!thread.eatInsect(insect)) {
                        System.out.println("Sikertelen");
                    } else {
                        if(insect.getPosition().canPutMushroom()){
                            Mushroom m = new Mushroom();
                            m.setPosition(insect.getPosition());
                            m.setThread(thread);
                            if(insect.getPosition().setMushroom(m)){
                                objects.put(getNewMushroomName(),m);
                                mushroomPlayer.addMushroom(m);
                                mushroomPlayer.addPoint();
                            }
                        }
                        InsectPlayer insectPlayer = null;
                        for (InsectPlayer iPlayer : insectPlayers) {
                            for (InsectAssociation insectA : iPlayer.getInsects()) {
                                if (insectA.getInsect() == insect) {
                                    insectPlayer = iPlayer;
                                }
                            }
                        }
                        insectPlayer.rm(insect);
                        objects.remove(insectName, insect);
                    }
                }else{
                    System.out.println("Sikertelen");
                }

                break;
            }

            case "move": { //<Rovarnév> <Tektonnév>
                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }
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
                    System.out.println("Sikertelen");
                    return;
                }

                // Segéd objektumok
                List<Spore> spores = tecton.getSpores();
                InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

                // Az ő rovarával akar lépni? (lehet fölösleges)
                if (insectAssociation == null) {
                    System.out.println("Sikertelen");
                    return;
                }

                // Tud lépni?
                if (insectAssociation.getMoved()) {
                    System.out.println("Sikertelen");
                    return;
                }

                // Lesz evés?
                boolean eat = false;
                if (!spores.isEmpty()) eat = true;

                // Lépés
                if (insect.move(tecton)) {
                    insectAssociation.setMoved(true);
                    if (eat) {
                        Spore spore = spores.get(0);

                        String str=null;
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
                    }
                }else{
                    System.out.println("Sikertelen");
                }

                // Kapott effekt hatása
                if (insect.getState().equals(DIVIDED)) { // létrejön egy új rovar
                    String str = "divide "+insectName;
                    processCmd(str);
                }
                if (insect.getState().equals(SPEEDBOOST)) { // mintha nem is lépett volna
                    insectAssociation.setMoved(false);
                    insect.setState(NORMAL);
                }
                if (insect.getState().equals(NOCUT) || insect.getState().equals(PARALYZED)) { // nocut = mintha már vágott volna
                    insectAssociation.setCut(true);                                       // paralyzed = mintha már vágott és lépett is volna (az utóbbi igaz is)      
                }
                // normal-t és slowed-et nem kell kezelni sztem
                break;
            }


            case "cut": { //<Rovarnév> <Tektonnév>
                if (round == 0){
                    System.out.println("Sikertelen");
                    return;
                }
                
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
                    System.out.println("Sikertelen");
                    return;
                }

                // Segéd objektumok
                InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);

                // Az ő rovarával akar vágni? (lehet fölösleges)
                if (insectAssociation == null) {
                    System.out.println("Sikertelen");
                    return;
                }
                // Tud vágni?
                if (insectAssociation.getCut()) {
                    System.out.println("Ez a rovar már vágott ebben a körben.");
                    return;
                }

                // Vágás
                if(insect.cut(tecton)){
                    insectAssociation.setCut(true);
                }else{
                    System.out.println("Sikertelen");
                }
                
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
                        insectA.getInsect().setState(NORMAL);
                    } else if (state == SPEEDBOOST) {
                        insectA.getInsect().setState(NORMAL);
                    }else if(state == NORMAL){
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

                        } else { spore = new SpeedSpore(); }

                        mushA.getMushroom().generateSpore(spore);
                        objects.put(getNewSporeName(), spore);
                    }
                }
            }

            if (round % 4 == 0) {
                for (ITectonController tecton : tList) {
                    tecton.absorb();
                }
                int rnumb = randomize(tList.size())+1;
                String str = "break t"+rnumb;
                processCmd(str);
                
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
                String fPlayerName = null;
                for (Map.Entry<String, Object> entry : objects.entrySet()){
                    if(entry.getValue() == fPlayer){
                        fPlayerName = entry.getKey();
                    }
                }
                System.out.println(fPlayerName + " - " + fPlayer.getPoints() + " pont");
            }

            for (InsectPlayer iPlayer : insectPlayers) {
                if (iPlayer.getPoints() > iMaxPoint) {
                    iWinner = iPlayer;
                    iMaxPoint = iPlayer.getPoints();
                }
                String iPlayerName = null;
                for (Map.Entry<String, Object> entry : objects.entrySet()){
                    if(entry.getValue() == iPlayer){
                        iPlayerName = entry.getKey();
                    }
                }
                System.out.println(iPlayerName + " - " + iPlayer.getPoints() + " pont");
            }

            if (!(fWinner == null || iWinner == null)) {
                String fWinnerName = null;
                String iWinnerName = null;
                for (Map.Entry<String, Object> entry : objects.entrySet()){
                    if(entry.getValue() == fWinner){
                        fWinnerName = entry.getKey();
                    }

                    if(entry.getValue() == iWinner){
                        iWinnerName = entry.getKey();
                    }
                }
                System.out.println("Nyertesek: " + fWinnerName + " " + iWinnerName);
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
            List<Field> fields = getAllFields(clazz);
            fields.sort(Comparator.comparing(Field::getName));

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
                                if(item instanceof MushroomAssociation ma){
                                    String mushName = reverseLookup.get(ma.getMushroom());
                                    names.add(mushName + " " + ma.getAge());
                                }else if(item instanceof InsectAssociation ia){
                                    String insName = reverseLookup.get(ia.getInsect());
                                    names.add(insName + " " + ia.getCut() + " " + ia.getMoved());
                                }else if(item instanceof timeToDie timeToDie){
                                    String tectName = reverseLookup.get(timeToDie.getTecton());
                                    names.add(tectName + " " + timeToDie.getTime());
                                }else{
                                    names.add(reverseLookup.get(item));
                                }
                                
                            }
                            names.removeIf(Objects::isNull);
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

        bw.write("GameState\n");
        bw.write("round " + round + "\n");
        bw.write("maxRound " + maxRound + "\n");
        bw.write("currentPlayer ");
        for(Map.Entry<String, Object> entry : sortedObjects.entrySet()){
            if(entry.getValue() == currentPlayer){
                bw.write(entry.getKey() + "\n");
            }
        }

        bw.write("-end-");
        bw.flush();
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        while (type != null) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            type = type.getSuperclass();
        }
        return fields;
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
