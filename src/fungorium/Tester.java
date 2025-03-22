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
        String f = map.get(from);
        if(f==null) f="";
        String t = map.get(to);
        if(t==null) t="";
        String rt ="";
        int i=0;
        for (Object returnv : returnValue) {
            rt+= map.get(returnv);
            i++;
            if(i<returnValue.size()){
                rt+=", ";
            }
        }
        System.out.println(f + "-->" + t + " : " + rt);
    }

    public void toReturnBool(boolean returnValue){
        String f = map.get(from);
        if(f==null) f="";
        String t = map.get(to);
        if(t==null) t="";
        String rt = returnValue ? "true" : "false";
        System.out.println(f + "-->" + t + " : " + rt);
    }

    

    //Gombafonal elágazása olyan tektonra, ahol van spóra-nak felel meg. Még nincs kész.
    public void FungalThreadBranching(){
        init3(); // Megtesszük a diagram 3-nak megfelelő kommunikációs diagramnban levő inicalizáló lépéseket.
        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        called = f; // Beállítjuk a tester attribútumait, hogy a kiíratás a valóságot tükrözze.
        parameters.add(t1);
        f.branchThread(t1); // Meghívjuk a fg-t. Ctrl+bal klikk a függvényre a folytatásért.
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
