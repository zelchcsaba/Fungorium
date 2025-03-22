package fungorium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.TreeSelectionListener;
import java.util.Scanner;

//Tester osztély a teszteléshez.
public class Tester {
    public Map<Object, String> map; // Ebben tároljuk a kulcs-érték párokat. kulcs=objektum érték=név. 1:1 megfeleltetés.
    public List<Object> parameters; // Paraméterek amiket átadtunk.
    public List<Object> returnValue;
    public Scanner scanner;
    public List<Object> list;

    public Tester(){
        map = new HashMap<>();
        parameters = new ArrayList<>();
        returnValue = new ArrayList<>();
        list = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    //Tester osztály egyik kíírató függvénye. A meghívott fg-k törzsében hívjuk meg.
    // Mindegyik osztálynak kell legyen egy Tester objektuma, hogy ezeket meghívhassa
    //A caller-t, called-t, parameters-t, függvényhívás előtt beállítottuk. 
    //Itt csak kikeressük a nevüket és kiíratjuk a doksiban lévő szintax alapján.
    public void toCall( String func){

        if(!list.isEmpty()){


            String caller = map.get(list.get(list.size()-2));

            if(caller==null) caller=""; // Ha null, mert pl a tester a hívó akkor nem írunk ki semmit balra.


         String called = map.get(list.get(list.size()-1));

            if(called==null) called="";

            String parameterString ="";

            int i=0;

         for (Object parameter : parameters) {
             if(map.get(parameter)==null){
                    parameterString+=parameter.toString();
             }else{
                  parameterString+= map.get(parameter);
             }
             i++;
                if(i<parameters.size()){
                   parameterString+=", ";
             }
         }
            if(caller!="" || called!="") System.out.println(caller + "->" + called + " : " + func + "(" + parameterString + ")");

        }
    }

    public void toCreate(Object o1, Object o2){

    }

    //Hasonló mint a toCall.
    public void toReturn(){

        if(!list.isEmpty()){

        String returnString ="";

        int i=0;
        for (Object returnv : returnValue) {
            if(map.get(returnv)==null){
                returnString+=returnv.toString();
            }else{
                returnString+= map.get(returnv);
            }
    
            i++;
            if(i<returnValue.size()){
                returnString+=", ";
            }
        }

        String fromString = map.get(list.get(list.size()-1));
        if(fromString==null) fromString="";
        String toString = map.get(list.get(list.size()-2));

        if(toString==null) {
            toString="";
            if(fromString!="" || toString!= "")System.out.println(toString + "<--" + fromString + " : " + returnString);
        }
        else if(fromString!="" || toString!="") System.out.println(fromString + "-->" + toString + " : " + returnString);

        list.remove(list.size() - 1);
        list.remove(list.size() - 1);

    }
    }

    

    //Gombafonal elágazása olyan tektonra, ahol van spóra-nak felel meg. Még nincs kész.
    public void FungalThreadBranching(){
        init3(); // Megtesszük a diagram 3-nak megfelelő kommunikációs diagramnban levő inicalizáló lépéseket.
        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        list.add(null);
        list.add(f);
        parameters.clear();
        parameters.add(t1);
        f.branchThread(t1); // Meghívjuk a fg-t. Ctrl+bal klikk a függvényre a folytatásért.

        System.out.println("Van t1-en spóra?");
        String select = scanner.next();

        if(select.equals("y")){
            list.add(null);
            list.add(f);
            MultiThreadTecton t3 = (MultiThreadTecton) getObjectByValue("t3");
            parameters.clear();
            parameters.add(t3);
            f.branchThread(t3); // Meghívjuk a fg-t. Ctrl+bal klikk a függvényre a folytatásért.
        }else{
            System.out.println(":3");
        }

        
    }

    public void init1(){

        map.clear();

        MultiThreadTecton t1 = new MultiThreadTecton(this);//1
        SingleThreadTecton t2 = new SingleThreadTecton(this);//2
        SingleThreadTecton t3 = new SingleThreadTecton(this);//3
        MultiThreadTecton t4 = new MultiThreadTecton(this);//4
        AbsorbingTecton t5 = new AbsorbingTecton(this);//5

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t3);
        t1.setNeighbors(t1Neighbors);//6.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors);//7.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3Neighbors.add(t4);
        t3Neighbors.add(t5);
        t3.setNeighbors(t3Neighbors);//8.

        List<Tecton> t4Neighbors = new ArrayList<>();
        t4Neighbors.add(t3);
        t4.setNeighbors(t4Neighbors);//9.

        List<Tecton> t5Neighbors = new ArrayList<>();
        t5Neighbors.add(t3);
        t5.setNeighbors(t5Neighbors);//10.

        FungalThread f = new FungalThread(this);//11.
        Mushroom m = new Mushroom(this);//12.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t3);
        fTectons.add(t2);
        f.setTectons(fTectons);//13.

        t1.putThread(f);//14.
        t3.putThread(f);//15.
        t2.putThread(f);//16.

        m.setPosition(t1);//17
        t1.setMushroom(m);//18
        m.setThread(f);//19

        SlowingSpore s = new SlowingSpore(this);//20
        
        List<Spore> spores = new ArrayList<>();
        spores.add(s);
        t3.setSpores(spores);//22
//21 az nincs
        Insect i = new Insect(this);//23
        i.setPosition(t3);//24
        t3.setInsect(i);//25
        SpeedSpore s2 = new SpeedSpore(this);//26
        s2.setThread(f);//27

        List<Spore> spores2 = new ArrayList<>();
        spores2.add(s2);
        m.setSpores(spores2);

        //Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(t4, "t4");
        map.put(t5, "t5");
        map.put(i,"i");
        map.put(s, "s");
        map.put(s2, "s2");

    }

    //Diagram 2-nek felel meg.
    public void init2(){
        // A kommunikációs diagrammnak megfelelő sorrendben és módon inicializáljuk az objektumokat.
        // Ehhez implementálni kellett pár setter-t.
        map.clear();


        MultiThreadTecton t1 = new MultiThreadTecton(this);//1.
        SingleThreadTecton t2 = new SingleThreadTecton(this);//2.
        SingleThreadTecton t3 = new SingleThreadTecton(this);//3.
        MultiThreadTecton t4 = new MultiThreadTecton(this);//4.
        MultiThreadTecton t5 = new MultiThreadTecton(this);//5.
        MultiThreadTecton t6 = new MultiThreadTecton(this);//6.

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t2);
        t1.setNeighbors(t1Neighbors); //7.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t1);
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors); //8.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors); //9.

        List<Tecton> t4Neighbors = new ArrayList<>();
        t4Neighbors.add(t2);
        t4Neighbors.add(t5);
        t4.setNeighbors(t4Neighbors); //10.

        List<Tecton> t5Neighbors = new ArrayList<>();
        t5Neighbors.add(t4);
        t5Neighbors.add(t6);
        t5.setNeighbors(t5Neighbors); //11.

        List<Tecton> t6Neighbors = new ArrayList<>();
        t6Neighbors.add(t5);
        t6.setNeighbors(t6Neighbors); //12.

        FungalThread f = new FungalThread(this); //13.
        Mushroom m = new Mushroom(this); //14.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t2);
        fTectons.add(t3);
        fTectons.add(t5);
        fTectons.add(t6);
        f.setTectons(fTectons); //15.

        t1.putThread(f); //16.
        t2.putThread(f); //17.
        t3.putThread(f); //18.
        t5.putThread(f); //19.
        t6.putThread(f); //20.

        m.setPosition(t2); //21.
        t2.setMushroom(m); //22.
        m.setThread(f); //23.

        //Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(t4, "t4");
        map.put(t5, "t5");
        map.put(t6, "t6");

    }

    //Diagram 3-nak felel meg.
    public void init3(){

        map.clear();

        // A kommunikációs diagrammnak megfelelő sorrendben és módon inicializáljuk az objektumokat.
        // Ehhez implementálni kellett pár setter-t.
        MultiThreadTecton t1 = new MultiThreadTecton(this);//1. 
        MultiThreadTecton t2 = new MultiThreadTecton(this);//2.
        MultiThreadTecton t3 = new MultiThreadTecton(this);//3.
        
        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t2);
        t1.setNeighbors(t1Neighbors);//4.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t1);
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors);//5.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors);//6.

        FungalThread f = new FungalThread(this);//7.
        Mushroom m = new Mushroom(this);//8.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t2);
        f.setTectons(fTectons);//9.

        t2.putThread(f);//10.
        m.setPosition(t2);//11.
        t2.setMushroom(m);//12.
        m.setThread(f);//13.
        SpeedSpore s = new SpeedSpore(this);//14.
        s.setThread(f);//15.

        List<Spore> spores = new ArrayList<>();
        spores.add(s);
        m.setSpores(spores);//16.
        m.setShootedSporesCount(9);//17.
        
        //Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(s, "s");
    }


    public void init4() {
        map.clear();
        caller = null;
        called = null;

        Tecton t1 = new MultiThreadTecton(this);    // 1.
        Tecton t2 = new SingleThreadTecton(this);   // 2.
        Tecton t3 = new AbsorbingTecton(this);      // 3.
        Tecton t4 = new MultiThreadTecton(this);    // 4.
        Tecton t5 = new MultiThreadTecton(this);    // 5.

        List<Tecton> t3Array = new ArrayList<>();
        t3Array.add(t3);

        t1.setNeighbors(t3Array);                       // 6.
        t2.setNeighbors(t3Array);                       // 7.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3Neighbors.add(t4);
        t3Neighbors.add(t5);

        t3.setNeighbors(t3Neighbors);                   // 8.
        t4.setNeighbors(t3Array);                       // 9.
        t5.setNeighbors(t3Array);                       // 10.

        FungalThread f1 = new FungalThread(this);    // 11.
        Mushroom m1 = new Mushroom(this);            // 12.

        List<Tecton> f1Tectons = new ArrayList<>();
        f1.addTecton(t1);
        f1.addTecton(t2);
        f1.addTecton(t3);

        f1.setTectons(f1Tectons);                       // 13.
        t1.putThread(f1);                               // 14.
        t2.putThread(f1);                               // 15.
        t3.putThread(f1);                               // 16.

        m1.setPosition(t1);                             // 17.
        t1.setMushroom(m1);                             // 18.
        m1.setThread(f1);                               // 19.

        FungalThread f2 = new FungalThread(this);    // 20.
        Mushroom m2 = new Mushroom(this);            // 21.

        List<Tecton> f2Tectons = new ArrayList<>();
        f2Tectons.add(t3);
        f2Tectons.add(t5);

        f2.setTectons(f2Tectons);                       // 22.
        t5.putThread(f2);                               // 23.
        t3.putThread(f2);                               // 24.
        m2.setPosition(t5);                             // 25.
        t5.setMushroom(m2);                             // 26.
        m2.setThread(f2);                               // 27.

        Insect i = new Insect(this);                 // 28.
        i.setPosition(t3);                              // 29.
        t3.setInsect(i);                                // 30.

        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(t4, "t4");
        map.put(t5, "t5");
        map.put(f1, "f1");
        map.put(f2, "f2");
        map.put(m1, "m1");
        map.put(m2, "m2");
        map.put(i, "i");
    }


    public void init5() {
        map.clear();
        caller = null;
        called = null;

        FungalThread f = new FungalThread(this);    // 1.
        Tecton t1 = new MultiThreadTecton(this);    // 2.
        Tecton t2 = new SingleThreadTecton(this);   // 3.
        Tecton t3 = new SingleThreadTecton(this);   // 4.

        Mushroom m = new Mushroom(this);            // 5.
        Spore s1 = new SlowingSpore(this);          // 6.
        Spore s2 = new NoCutSpore(this);            // 7.
        Spore s3 = new ParalysingSpore(this);       // 8.
        Spore s4 = new SpeedSpore(this);            // 9.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t2);
        fTectons.add(t3);

        f.setTectons(fTectons);                       // 10. (10.1)
        m.setPosition(t1);                            // 10. (10.2)
        m.setThread(f);                               // 11.

        List<Spore> s4List = new ArrayList<>();
        s4List.add(s4);

        m.setSpores(s4List);                          // 12.
        m.evolve();                                   // 13.

        List<Tecton> t3List = new ArrayList<>();
        t3List.add(t3);
        t1.setNeighbors(t3List);                      // 14.
        t1.putThread(f);                              // 15.
        t1.setMushroom(m);                            // 16.
        t2.setNeighbors(t3List);                      // 17.
        t2.putThread(f);                              // 18.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors);                 // 19.
        t3.putThread(f);                              // 20.

        List<Spore> t3Spores = new ArrayList<>();
        t3Spores.add(s1);
        t3Spores.add(s2);
        t3Spores.add(s3);
        t3.setSpores(t3Spores);                       // 21.

        s1.setThread(f);                              // 22.
        s2.setThread(f);                              // 23.
        s3.setThread(f);                              // 24.
        s4.setThread(f);                              // 25.

        map.put(f, "f");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(m, "m");
        map.put(s1, "s1");
        map.put(s2, "s2");
        map.put(s3, "s3");
        map.put(s4, "s4");
    }

    // Segéd függvény, hogy a neve alapján elő szedjünk egy objektumot
    public Object getObjectByValue(String value) {
        for (Map.Entry<Object, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey(); // Visszaadjuk az objektumot
            }
        }
        return null; // Ha nincs ilyen érték, null-t adunk vissza
    }
}
