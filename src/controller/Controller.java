package controller;
import java.util.ArrayList;
import java.util.List;

import model.*;
import view.IView;
import view.View;

import java.util.HashMap;


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
}
