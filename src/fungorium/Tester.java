package fungorium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Tester osztély a teszteléshez.
public class Tester {
    public Map<Object, String> map = new HashMap<>(); // Ebben tároljuk a kulcs-érték párokat. kulcs=objektum érték=név. 1:1 megfeleltetés.
    public Object caller, called, from, to; //A függvény hívója, az objektum amin meghívták.
    public List<Object> parameters = new ArrayList<>(); // Paraméterek amiket átadtunk.
    public List<Object> returnValue = new ArrayList<>();

    //Tester osztály egyik kíírató függvénye. A meghívott fg-k törzsében hívjuk meg.
    // Mindegyik osztálynak kell legyen egy Tester objektuma, hogy ezeket meghívhassa
    //A caller-t, called-t, parameters-t, függvényhívás előtt beállítottuk. 
    //Itt csak kikeressük a nevüket és kiíratjuk a doksiban lévő szintax alapján.
    public void toCall(String func){
        String cr = map.get(caller);
        if(cr==null) cr=""; // Ha null, mert pl a tester a hívó akkor nem írunk ki semmit balra.
        String cd = map.get(called);
        if(cd==null) cd="";
        String ps ="";
        int i=0;
        for (Object parameter : parameters) {
            ps+= map.get(parameter);
            i++;
            if(i<parameters.size()){
                ps+=", ";
            }
        }
        System.out.println(cr + "->" + cd + " : " + func + "(" + ps + ")");
    }


    //Hasonló mint a toCall.
    public void toReturn(){
        String rt ="";
        int i=0;
        for (Object returnv : returnValue) {
            rt+= map.get(returnv);
            i++;
            if(i<returnValue.size()){
                rt+=", ";
            }
        }
        String f = map.get(from);
        if(f==null) f="";
        String t = map.get(to);
        if(t==null) {
            t="";
            System.out.println(t + "<--" + f + " : " + rt);
        }
        else System.out.println(f + "-->" + t + " : " + rt);
    }

    //Hasonló mint a toCall.
    public void toReturn(boolean booleanReturnValue){
        String rt = booleanReturnValue ? "true" : "false";
        String f = map.get(from);
        if(f==null) f="";
        String t = map.get(to);
        if(t==null) {
            t="";
            System.out.println(t + "<--" + f + " : " + rt);
        }
        else System.out.println(f + "-->" + t + " : " + rt);
    }
    

    //Gombafonal elágazása olyan tektonra, ahol van spóra-nak felel meg. Még nincs kész.
    public void FungalThreadBranching(){
        init3(); // Megtesszük a diagram 3-nak megfelelő kommunikációs diagramnban levő inicalizáló lépéseket.
        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        caller = null;
        called = f; // Beállítjuk a tester attribútumait, hogy a kiíratás a valóságot tükrözze.
        parameters.add(t1);
        f.branchThread(t1); // Meghívjuk a fg-t. Ctrl+bal klikk a függvényre a folytatásért.
        caller = null;
        called = t1;
        parameters.clear();
        to = this; 
        t1.getSpores();
    }

    public void init1(){
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

        Insect i = new Insect(this);//23
        i.setTec



    }

    //Diagram 3-nak felel meg.
    public void init3(){
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

        for (int i = 0; i < 50; i++) { // Kiír 50 üres sort jobb hijján, hogy ne látszódjanak a setterek kiírásai.
            System.out.println();
        }
    }


    public void init4() {
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
