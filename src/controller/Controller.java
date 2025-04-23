package controller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.lang.reflect.Field;
import model.*;
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

    public Controller(){
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

    public void processCmd(String cmd){
        String[] command = cmd.split(" ");
        switch(command[0]){

            case "createTecton":{
                int type = randomize(4);
                ITectonController t;
                switch(type){
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

            case "createSpore":{//<Spórafajta> <Tektonnév> <Fonálnév>
		        //  Paraméterek kinyerése
			    String type = command[1]; 
			    String tectonName = command[2];
			    String fungalName = command[3];

			    // Megfelelő objektum inicializálása
                Spore spore;
			    switch(type){
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
                Tecton tecton = (Tecton)objects.get(tectonName);
                tecton.addSpore(spore);

                FungalThread fungal = (FungalThread)objects.get(fungalName);
                spore.setThread(fungal);

                // Konténerbe bele
                String name = getNewSporeName();
                objects.put(name, spore);
                break;
            }	

            case "setNeighbors":{
                Tecton t = (Tecton)objects.get(command[1]);
                List<Tecton> neighborList= new ArrayList<>();
                for(int i=2; i<command.length;i++){
                    neighborList.add((Tecton)objects.get(command[i]));
                }
                t.setNeighbors(neighborList);
            break;
            }

            case "createShortLifeThread":{
                FungalThread f = new ShortLifeThread();
                
                List<Tecton> tlist = new ArrayList<>();
                tlist.add((Tecton)objects.get(command[1]));
                f.setTectons(tlist);
                
                FungusPlayer fplayer =(FungusPlayer)objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
            break;
            }
            
            case "createLongLifeThread":{
                FungalThread f = new LongLifeThread();
                
                List<Tecton> tlist = new ArrayList<>();
                tlist.add((Tecton)objects.get(command[1]));
                f.setTectons(tlist);

                FungusPlayer fplayer =(FungusPlayer)objects.get(command[2]);
                fplayer.setThread(f);

                String name = getNewThreadName();
                objects.put(name, f);
            break;
            }

            case "createMushroom":{
                Mushroom m = new Mushroom();
                Tecton t = (Tecton)objects.get(command[1]);
                FungalThread f = (FungalThread)objects.get(command[2]);
                FungusPlayer fplayer = (FungusPlayer)objects.get(command[3]);
                m.setThread(f);
                m.setPosition(t);
                if(t.setMushroom(m)){
                    fplayer.addMushroom(m);
                    String name = getNewMushroomName();
                    objects.put(name, m);
                }else{
                    System.out.println("Sikertelen");
                }
                break;
            }

            case "createEvolvedMushroom":{ // <Tektonnév> <Fonálnév> <Játékosnév>
                // Paraméterek kinyerése
                String tectonName = command[1];
                String fungalName = command[2];
                String playerName = command[3];

                Tecton t = (Tecton)objects.get(tectonName);
                FungalThread f = (FungalThread)objects.get(fungalName);
                FungusPlayer fplayer = (FungusPlayer)objects.get(playerName);

                Mushroom m = new Mushroom();

                if(t.setMushroom(m)){
                    m.setState(EVOLVED);
                    m.setThread(f);
                    m.setPosition(t);
                    MushroomAssociation mAssociation = new MushroomAssociation();
                    mAssociation.setMushroom(m);
                    mAssociation.setAge(6);
                    fplayer.addMushroomAssociation(mAssociation);
                    String name = getNewMushroomName();
                    objects.put(name, m);
                }else{
                    System.out.println("Sikertelen");
                }
                break;
            }

            case "createInsect":{ // <Tektonnév> <Játékosnév>
		        // Paraméterek kinyerése
    		    String tectonName = command[1];
   	    	    String playerName = command[2];

		        // Megfelelő objektum inicializálása
			    Insect insect = new Insect();

			    // Asszociációk beállítása
                Tecton tecton = (Tecton)objects.get(tectonName);
    		    insect.setPosition(tecton);
                tecton.setInsect(insect);

                InsectPlayer iPlayer = (InsectPlayer)objects.get(playerName);
		        iPlayer.addInsect(insect);

			    //Konténerbe bele
                String name = getNewInsectName();
                objects.put(name, insect);
            break;
            }
            
            case "createFungusPlayers":{//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
		        // Ellenőrizzük a parancs helyességét
                if(command.length>5 || command.length!=fungusPlayerCount+1){
                    System.out.println("túl sok paraméter");
                    return;
                }

		        // Objektumok incializálása
                for(int i = 0; i<command.length; i++){
                    FungusPlayer fPlayer = new FungusPlayer(); 
                    String name = command[i];
                    objects.put(name, fPlayer);
                    fungusPlayers.add(fPlayer);
                }
            break;
            }

            case "createInsectPlayers":{//<Játékosnév><Játékosnév><Játékosnév><Játékosnév>
		        // Ellenőrizzük a parancs helyességét
                if(command.length>5 || command.length!=insectPlayerCount+1){
                    System.out.println("túl sok paraméter");
                    return;
                }

		        // Objektumok incializálása
                for(int i = 0; i<command.length; i++){
                    InsectPlayer iPlayer = new InsectPlayer(); 
                    String name = command[i];
                    objects.put(name, iPlayer);
                    insectPlayers.add(iPlayer);
                }
            break;
            }

            case "cut":{ //<Rovarnév> <Tektonnév>
                if (!(currentPlayer instanceof InsectPlayer)) { // Ebbe nem vagyok biztos, hogy szép e így, vagy, hogy egyáltalán kell e
                    System.out.println("A 'cut' parancs csak rovarász játékosoknak engedélyezett!");
                    return;
                }

                // Paraméterek kinyerése
                String insectName = command[1];
                String tectonName = command[2];
                
                // Megfelelő objektumok előszedése
                Tecton tecton = (Tecton)objects.get(tectonName);
                Insect insect = (Insect)objects.get(insectName);

                // Segéd objektumok
                InsectPlayer insectPlayer = (InsectPlayer) currentPlayer;
                InsectAssociation insectAssociation = insectPlayer.getInsectAssociation(insect);
                
                // Az ő rovarával akar vágni?
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

            case "saveResult":{
                try{
                    writeObjectsToFile(objects, new FileWriter("result.txt"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }

            case "setShotSpores":{
                Mushroom m = (Mushroom) objects.get(command[1]);

                int shotSporesCount = Integer.parseInt(command[2]);
                m.setShootedSporesCount(shotSporesCount);
                break;
            }

            case "setMushroomAge":{
                Mushroom m = (Mushroom) objects.get(command[1]);

                int newAge = Integer.parseInt(command[2]);
                //Nem tudom mi a faszt kéne a mushroomAssotiation-el
                //Ha az age nagyobb mint 6 evolvolni is kell???
                break;
            }

            case "stepGameRound":{
                int r = Integer.parseInt(command[1]);
                round += r;
                break;
            }

            case "evolve":{
                Mushroom m = (Mushroom) objects.get(command[1]);
                boolean returnV = m.evolve();
                if(!returnV){
                    System.out.println("Sikertelen!");
                }
                break;
            }

            case "divide":{
                Insect insect = (Insect) objects.get(command[1]);

                Insect insect2 = insect.divide();
                //Itt csináljak null vizsgálatot, mert a divide sosem fog null-al visszatérni???

                objects.put(getNewInsectName(), insect2);
                break;
            }

            case "closeStep":{
                setCurrentPlayer();
                break;
            }

            case "shootSpore":{
                Mushroom m = (Mushroom) objects.get(command[1]);
                Tecton t = (Tecton) objects.get(command[2]);
                boolean returnV = m.shootSpore(t);
                if(!returnV){
                    System.out.println("Sikertelen!");
                }
                break;
            }

            case "putFirstInsect":{
                Tecton t = (Tecton) objects.get(command[1]);
                Insect insect = new Insect();
                boolean returnV = t.putFirstInsect(insect);
                if(returnV){
                    objects.put(getNewInsectName(), insect);
                    //TODO: Hozzá kéne adni a játékoshoz a rovart
                }else{
                    System.out.println("Sikertelen!");
                }
                break;
            }


        }
    }
    public void setCurrentPlayer(){
        int indexCurrentPlayer = -1;

        if(fungusPlayers.contains(currentPlayer)){
            for(int i=0; i<fungusPlayers.size();i++){
                if (currentPlayer.equals(fungusPlayers.get(i))){
                    indexCurrentPlayer = i;
                }
            }

            if(indexCurrentPlayer == fungusPlayers.size()-1){
                if(!insectPlayers.isEmpty()){
                    currentPlayer = insectPlayers.get(0);
                }else{
                    currentPlayer = fungusPlayers.get(0);
                    initRound();
                }
            }else{
                currentPlayer = fungusPlayers.get(indexCurrentPlayer+1);
            }

        }else if(insectPlayers.contains(currentPlayer) && indexCurrentPlayer==-1){
            for(int i=0; i<insectPlayers.size();i++){
                if (currentPlayer.equals(insectPlayers.get(i))){
                    indexCurrentPlayer = i;
                }
            }
            if(indexCurrentPlayer == insectPlayers.size()-1){
                if(!fungusPlayers.isEmpty()){
                    currentPlayer = fungusPlayers.get(0);
                    initRound();
                }else{
                    currentPlayer = insectPlayers.get(0);
                    initRound();
                }
            }else{
                currentPlayer = insectPlayers.get(indexCurrentPlayer+1);
            }
        }
    }

    public void initRound(){
        
    }
    public String getNewMushroomName(){
        mushroomCount++;
        String name = "m"+mushroomCount;
        System.out.println(name);
        return name;
    }
    public String getNewThreadName(){
        fungalThreadCount++;
        String name = "f"+fungalThreadCount;
        System.out.println(name);
        return name;
    }
    public String getNewSporeName(){
        sporeCount++;
        String name = "s"+sporeCount;
        System.out.println(name);
        return name;
    }
    public String getNewInsectName(){
        insectCount++;
        String name = "i"+insectCount;
        System.out.println(name);
        return name;
    }
    public String getNewTectonName(){
        tectonCount++;
        String name = "t"+tectonCount;
        System.out.println(name);
        return name;
    }
    public int randomize(int domain){
        if(randomize == true){
            IView rand = new View();
            return (rand.randomize() % domain);
        }else{
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

}
