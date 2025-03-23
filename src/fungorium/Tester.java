package fungorium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * A Tester osztály célja szimulált műveletek végrehajtása objektumok
 * és függvények között, kifejezetten szekvencia diagramokban
 * és belső kezelési logikákban.
 *
 * Az osztály többek között tartalmaz metódusokat függvényhívások
 * szimulálására, objektumok létrehozására és visszatérési értékek
 * megjelenítésére.
 *
 * A metódusok egy részében belső adatszerkezeteket használ, például
 * a map, list és parameters adattagokat, amelyek a manipulált objektumok
 * tárolását és visszakeresését segítik elő.
 *
 * Használati célok:
 * - Virtuális hívási láncok és objektumműveletek szimulálása.
 * - Folyamatok vizualizációja tesztelési forgatókönyvekben.
 * - Speciális adatkezelési logikák és algoritmusok tesztelése.
 *
 * Az osztály az input kezelésére scanner objektumot is alkalmaz.
 */
public class Tester {
    public Map<Object, String> map; // Ebben tároljuk a kulcs-érték párokat. kulcs=objektum érték=név. 1:1
                                    // megfeleltetés.
    public List<Object> parameters; // Paraméterek amiket átadtunk.
    public List<Object> returnValue;
    public Scanner scanner;
    public List<Object> list;//tároljuk az objektum hívások láncát


    /**
     * A Tester osztály konstruktora.
     * Példányosítja és inicializálja az osztályhoz tartozó összes adatstruktúrát,
     * valamint egy szkenner objektumot az input kezelésére.
     */
    public Tester() {
        map = new HashMap<>();
        parameters = new ArrayList<>();
        returnValue = new ArrayList<>();
        list = new ArrayList<>();
        scanner = new Scanner(System.in);
    }


    /**
     * Egy függvény meghívását szimuláló metódus, amely kiírja a hívó és a hívott objektum nevét,
     * valamint a meghívott függvény nevét és paramétereit a megfelelő szintaxisban.
     *
     * @param func A meghívott függvény neve.
     */
    public void toCall(String func) {

        if (!list.isEmpty()) {
            //a hívó objektum neve
            String caller = map.get(list.get(list.size() - 2));

            if (caller == null)
                caller = ""; // Ha null, mert pl a tester a hívó akkor nem írunk ki semmit balra.

            //annak az objektumnak a neve, amelynek a függvényét meghívták
            String called = map.get(list.get(list.size() - 1));

            if (called == null)
                called = "";

            String parameterString = "";

            int i = 0;

            //a kapott paraméterek neveiből is csinálunk egy String-et
            for (Object parameter : parameters) {
                //ha nincs benne a map-ben, amelyben az általunk létrehozott objektumokat tároljuk
                if (map.get(parameter) == null) {
                    parameterString += parameter.toString();
                } else {
                    //ha benne van
                    parameterString += map.get(parameter);
                }
                i++;
                //ha még van paraméter a Strng-hez hozzáadunk egy vesszőt
                if (i < parameters.size()) {
                    parameterString += ", ";
                }
            }
            //kiírjuk a konzolra a megfelelő szintaxisban
            if (caller != "" || called != "")
                System.out.println(caller + "->" + called + " : " + func + "(" + parameterString + ")");

        }
    }


    /**
     * Egy új objektum létrehozását szimuláló metódus, amely kiírja a hívó objektum nevét,
     * a létrehozott objektum nevét, valamint az "új létrehozás" szintaxisát.
     *
     * @param caller A hívó objektum, amely létrehozza az új objektumot.
     * @param called Az újonnan létrehozott objektum, amelyhez nevet rendelünk.
     * @param name Az újonnan létrehozott objektum neve.
     */
    public void toCreate(Object caller, Object called, String name) {
        String callerName = map.get(caller);
        map.put(called, name);
        //kiírjuk a megfelelő szintaxisban
        System.out.println(callerName + "-->" + name + " :  <<create>>");
    }


    /**
     * A toReturn metódus a hívási lánc feldolgozására és a visszatérési értékek kijelzésére szolgál.
     *
     * Ez a metódus kiírja a hívó objektum és a hívott objektum nevét, valamint a visszatérési
     * értékeket. A hívásokat a list nevű adattag szerinti láncban dolgozza fel.
     *
     * Működése során:
     * - Létrehoz egy visszatérési értékeket tartalmazó sztringet.
     * - Ellenőrzi a visszatérési értékek meglétét és kezelését a map adattagban tárolt objektumok
     *   szerint.
     * - Ellenőrzi a hívó és hívott objektumok nevét, és ezek alapján formázza az eredményt.
     * - A hívási láncból eltávolítja a feldolgozott objektumokat.
     *
     * A metódus belső logikája alapján:
     * - Ha egy objektum nincs a map-ban, az nem létrehozott, és közvetlenül a visszatérési értéket
     *   használja fel.
     * - Ha az objektum benne van a map-ban, annak nevét használja a kiíráskor.
     * - A hívási lánc utolsó két objektumára végzi a kiértékelést és eltávolítást.
     */
    public void toReturn() {

        if (!list.isEmpty()) {

            String returnString = "";

            int i = 0;

            //a kapott visszatérési értékek neveiből csinálunk egy String-et
            for (Object returnv : returnValue) {
                //ha nincs benne a map-ban, amelyben az általunk létrehozott objektumokat tároljuk
                if (map.get(returnv) == null) {
                    //ha a visszatérési érték nem null
                    if (returnv != null) {
                        returnString += returnv.toString();
                    } else {
                        //ha a visszatérési érték null
                        returnString += "";
                    }

                } else {
                    //ha benne van a map-ben, amelyben az általunk létrehozott objektumokat tároljuk
                    returnString += map.get(returnv);
                }

                i++;
                //ha még van visszatérési érték
                if (i < returnValue.size()) {
                    returnString += ", ";
                }
            }

            //az objektum amelynek függvénye visszatér
            String fromString = map.get(list.get(list.size() - 1));
            if (fromString == null)
                fromString = "";
            //az objektum neve, amelynek függvényébe visszatérünk
            String toString = map.get(list.get(list.size() - 2));
            if (toString == null)
                toString = "";

            if (toString.equals("") && !fromString.equals("")) {
                System.out.println(toString + "<--" + fromString + " : " + returnString);
            } else if (!toString.equals("") && !fromString.equals("")) {
                System.out.println(fromString + "-->" + toString + " : " + returnString);
            }

            //kivesszük a listából amelyben az objektum hívások láncát tároljuk az utolsó két objektumot
            list.remove(list.size() - 1);
            list.remove(list.size() - 1);
        }
    }


    /**
     * A delete_Unnecessary_Threads metódus feladata a fölösleges gombafonalak eltávolításának
     * szimulálása. Az alábbiak szerint működik:
     *
     * 1. Inicializálja a kiindulási állapotot az `init2()` metódus meghívásával.
     * 2. A szekvencia diagram szintaxisának megfelelően kiírja az érintett objektumok és
     *    kapcsolódó metódusok nevét.
     * 3. Megkeresi az "f" nevű gombafonal objektumot a `getObjectByValue()` függvény segítségével.
     * 4. Az objektumot hozzáadja egy listához további feldolgozáshoz.
     * 5. Kiüríti a paramétereket, ezzel biztosítva a tiszta működést.
     * 6. Meghívja a `deleteUnnecessaryThreads()` függvényt a gombafonal objektum esetében,
     *    amely végrehajtja a fölösleges szálak eltávolításához szükséges logikát.
     *
     * A metódus célja a megfelelő belső logika feldolgozásának szimulációja a
     * gombafonalak hatékony kezelése érdekében.
     */
    public void delete_Unnecessary_Threads() {
        init2();

        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3");
        System.out.println("participant \"t5:MultiThreadTecton\" as t5");
        System.out.println("participant \"t6:MultiThreadTecton\" as t6");
        System.out.println();

        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        list.add(null);
        list.add(f);
        parameters.clear();

        f.deleteUnnecessaryThreads();
    }


    /**
     * A break_tecton metódus feladata a tecton kettétörésének szimulálása.
     *
     * A metódus végrehajtási lépései a következők:
     * - Inicializál egy belső állapotot az init1 metódus segítségével.
     * - Meghatározott résztvevőket (participant) definiál és kiírja őket.
     * - Egy "SingleThreadTecton" típusú objektum lekérése a getObjectByValue
     *   metódussal, amely "t3" névvel azonosítható érték alapján kerül kiválasztásra.
     * - A listához null értéket és ezt az objektumot adja hozzá.
     * - A "parameters" lista tartalmának törlése.
     * - A "SingleThreadTecton" típusú objektum breakTecton metódusának meghívása.
     *
     * Ez a metódus feltételezi, hogy a "getObjectByValue", a "list",
     * a "parameters" és az init1 metódus korábban definiálva lett az osztályban.
     */
    public void break_tecton() {
        init1();

        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3");
        System.out.println("participant \"t4:MultiThreadTecton\" as t4");
        System.out.println("participant \"t5:AbsorbingTecton\" as t5");
        System.out.println("participant \"t6:SingleThreadTecton\" as t6");
        System.out.println("participant \"t7:SingleThreadTecton\" as t7");
        System.out.println("participant \"i:Insect\" as i");
        System.out.println("participant \"s:SlowingSpore\" as s");
        System.out.println();

        SingleThreadTecton t3 = (SingleThreadTecton) getObjectByValue("t3");
        list.add(null);
        list.add(t3);
        parameters.clear();

        t3.breakTecton();
    }


    /**
     * A break_tecton_with_mushroom metódus felelőssége a "breakTecton" logika végrehajtása,
     * ha a tektonon gombatest található.
     * Először inicializálja a szükséges paramétereket és kiírja a résztvevőket.
     *
     * Ezt követően megszerzi a "MultiThreadTecton" típusú objektumot egy előre definiált értékre
     * hivatkozva. Az objektum begyűjtése után frissíti a listát a megfelelő elemekkel, hozzáadja
     * az új objektumot, majd kiüríti a paraméterek tárolóját.
     *
     * Végül, meghívja a "MultiThreadTecton" breakTecton metódusát, hogy a tektonikus logikát
     * végrehajtsa.
     */
    public void break_tecton_with_mushroom(){
        init1();
        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");
        System.out.println();

        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        list.add(null);
        list.add(t1);
        parameters.clear();

        t1.breakTecton();
    }


    /**
     * Meghívja az aktuális AbsorbingTecton objektum absorb metódusát, amely alapvetően
     * folyamatelemként működik a rendszerben. Előkészíti a megfelelő résztvevőket
     * és beállítja az objektumokat ezen folyamat futtatására.
     *
     * Előkészítési lépések:
     * - Rendszer inicializálása az init4 metódussal.
     * - Tesztfolyamat résztvevőinek kiíratása a konzolra.
     *
     * Fő művelet:
     * - Meghívja a "t3" tekton absorb metódusát, ami a folyamat végrehajtását kezdeményezi.
     */
    public void absorb_from_tecton() {
        init4();

        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3");
        System.out.println("participant \"f1:FungalThread\" as f1");
        System.out.println("participant \"f2:FungalThread\" as f2");
        System.out.println();

        AbsorbingTecton t3 = (AbsorbingTecton) getObjectByValue("t3");
        list.add(null);
        list.add(t3);
        parameters.clear();

        t3.absorb();
    }

    /**
     * A mushroom_die metódus egy teszteset implementációja, amely a gomba elpusztulásának
     * szimulációját írja le a spóralövési folyamat után.
     *
     * A metódus inicializálja a szükséges objektumokat, majd interakcióval követi,
     * hogyan lövi ki a gombatest a spórákat, végül szimulálja az elpusztulási állapotot.
     *
     * A teszt során a következő interakciók kerülnek szimulálásra:
     *  - Tester részvétele.
     *  - Objektumok létrehozása és inicializálása.
     *  - A Mushroom entitás shootSpore metódusának hívása.
     */
    public void mushroom_die(){
        init3();

        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println("participant \"m:Mushroom\" as m");
        System.out.println();

        Mushroom m = (Mushroom) getObjectByValue("m");

        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        list.add(null);
        list.add(t1);
        parameters.clear();
        parameters.add(t1);

        m.shootSpore(t1);
    }


    /**
     * A cut_fungalthread metódus célja, hogy a rovar által végzett fonál elvágási
     * folyamatot szimulálja. A metódus előkészíti a szükséges objektumokat,
     * inicializálja a tesztelési környezetet, és végrehajtja a rovar `cut`
     * metódusának hívását a megfelelő paraméterekkel.
     *
     * A metódus működése a következő fő lépéseken alapul:
     * 1. Inicializáló metódus meghívása (init4).
     * 2. Résztvevő entitások konzolra írása
     *    a szekvencia-diagram nyomon követéséhez.
     * 3. Az 'Insect' típusú objektum és a 'SingleThreadTecton' objektum
     *    lefoglalása a megfelelő értékekkel.
     * 4. Az objektumok paraméterként való előkészítése.
     * 5. A rovar `cut` metódusának meghívása, amely elvégzi az adott fonálszerkezet
     *    törését.
     */
    public void cut_fungalthread(){
        init4();

        System.out.println("participant \"Tester\" as t");
        System.out.println("participant \"i:Insect\" as i");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3");
        System.out.println("participant \"f1:FungalThread\" as f1");
        System.out.println();

        Insect i = (Insect) getObjectByValue("i");
        SingleThreadTecton t2 = (SingleThreadTecton) getObjectByValue("t2");
        list.add(null);
        list.add(i);
        parameters.clear();
        parameters.add(t2);
        //meghívom a rovar cut metódusát 
        i.cut(t2);
    }

  
    /**
     * A gombafonal elágazásának folyamatát kezeli olyan tektonra, ahol spóra található.
     *
     * Ez a metódus a következőkre szolgál:
     * - A szükséges inicializáló lépéseket végrehajtja.
     * - Beállítja a résztvevő objektumokat a kommunikációs diagram alapján.
     * - Meghatározza, hogy van-e spóra egy adott MultiThreadTecton tektonon.
     * - Ha spóra található, a gombafonal tovább ágazik a megfelelő szomszédos tektonokra.
     *
     * A folyamat során a következő logika érvényesül:
     * - A metódus inicializálja az objektumokat és beállítja az interakciókat.
     * - Ha az első kiválasztott tekton rendelkezik spórával, a funkció további elágazásokat hajt végre a szomszédos tektonokra.
     * - Ellenkező esetben értesítést ad arról, hogy nincs spóra az adott tektonon.
     */
    public void fungalThreadBranching() {
        init3(); // Megtesszük a diagram 3-nak megfelelő kommunikációs diagramnban levő
                 // inicalizáló lépéseket.
        System.out.println("participant \"Tester\" as \"\"");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");
        System.out.println("participant \"t2:MultiThreadTecton\" as t2");
        System.out.println("participant \"t3:MultiThreadTecton\" as t3");
        System.out.println();

        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");

        list.add(null);
        list.add(f);
        parameters.clear();
        parameters.add(t1);
        f.branchThread(t1); // Meghívjuk a fg-t. Ctrl+bal klikk a függvényre a folytatásért.

        System.out.println("Van t1-en spóra?");
        String select = scanner.next();
        if (select.equals("y")) {
            MultiThreadTecton t3 = (MultiThreadTecton) getObjectByValue("t3");//Kicsit mókolni kell, hogy úgy írjon ki, mint a szekvenciadia
            MultiThreadTecton t2 = (MultiThreadTecton) getObjectByValue("t2");//Tehát most a kom. dia.-al ellentétben t3 t1-el is szomszédos, nem csak t2-el
            List<Tecton> t3Neighbors = new ArrayList<>();//Mivel az a játékszabály, hogy a spórával szomszédos tektonokra mehet tovább
            t3Neighbors.add(t1);
            t3Neighbors.add(t2);
            t3.setNeighbors(t3Neighbors);

            list.add(null);
            list.add(f);
            parameters.clear();
            parameters.add(t3);
            f.branchThread(t3); // Meghívjuk a fg-t.
        } else {
            System.out.println("t1-en nincsen spóra");
        }
    }


    /**
     * Ez a metódus egy gombafonal sikertelen elágaztatását szimulálja
     * egy egyfonalas tektonra, mivel a folyamat akadályba ütközik,
     * például azért, mert a megadott tekton már foglalt.
     *
     * A metódus kezdeti beállításokat végez az init4() meghívásával, majd a konzolon
     * megjeleníti a szekvenciadiagram megfelelő részeit. Ezt követően a szükséges
     * objektumokat kiolvassa a megfelelő értékmegadással, és egy
     * próbálkozást végez az elágaztatás végrehajtására az `branchThread` metódus
     * meghívásával.
     *
     * A szimuláció eredményeképpen demonstrálja, hogy a gombafonal nem tud sikeresen
     * elágaztatódni arra a konkrét egyfonalas tektonra.
     */
    public void simpleFungalThreadBranchingFalse(){
        init4();

        System.out.println("participant \"Tester\" as \"\"");
        System.out.println("participant \"f2:FungalThread\" as f2");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println();

        FungalThread f2 = (FungalThread) getObjectByValue("f2"); // Előszedjük a megfelelő nevű objektumokat.
        SingleThreadTecton t2 = (SingleThreadTecton) getObjectByValue("t2");

        list.add(null);
        list.add(f2);
        parameters.clear();
        parameters.add(t2);
        f2.branchThread(t2); 
    }


    /**
     * A gombafonal sikeres elágaztatását valósítja meg egy megadott MultiThreadTecton-ra.
     *
     * Használati eset:
     * - A módszer egyik résztvevője egy 'FungalThread' objektum.
     * - Továbbá tartalmaz egy 'MultiThreadTecton' objektumot, amelyen az elágaztatás történik.
     *
     * Funkcionalitás:
     * - Inicializálja a szükséges környezetet az elágaztatás előtt.
     * - Kiírja a szekvenciadiagramhoz használható résztvevőket.
     * - Az FungalThread példány és a MultiThreadTecton példány közötti kapcsolatot alakítja ki az 'branchThread' hívás segítségével.
     */
    public void simpleFungalThreadBranchingTrue(){
        init1();

        System.out.println("participant \"Tester\" as \"\"");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t4:MultiThreadTecton\" as t4");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3"); //t4 szomszédja
        System.out.println();

        FungalThread f = (FungalThread) getObjectByValue("f"); // Előszedjük a megfelelő nevű objektumokat.
        MultiThreadTecton t4 = (MultiThreadTecton) getObjectByValue("t4");

        list.add(null);
        list.add(f);
        parameters.clear();
        parameters.add(t4);
        f.branchThread(t4); 
    }


    /**
     * Ez a metódus a gomba (Mushroom) spóraszórási próbálkozását szimulálja egy céltektonra
     * (SingleThreadTecton), amely nem szomszédos. A megcélzott tekton ebben az esetben
     * nem elérhető, így a spóraszórás sikertelen.
     *
     * A metódus:
     *  - Inicializálja a rendszer releváns objektumait.
     *  - Szimulációt készít a résztvevők közötti interakciókról.
     *  - Meghív egy spóraszórási folyamatot, amely egy sikertelen kísérletet demonstrál.
     *
     * A megvalósítás specifikusan:
     *  - Inicializálja az objektumokat az init1() metódus meghívásával.
     *  - KIíratja a kontextushoz tartozó szekvenciadiagram szereplőit és azokat a környezetet.
     *  - Interakciót hajt végre a Mushroom osztály shootSpore metódusával, amely hibás működést eredményez,
     *    mivel a céltekton nem szomszédos.
     */
    public void unevolvedShootSporeFalse(){
        init1();

        System.out.println("participant \"Tester\" as \"\"");
        System.out.println("participant \"m:Mushroom\" as m");
        System.out.println("participant \"t2:SingleThreadTecton\" as t2");
        System.out.println("participant \"t1:MultiThreadTecton\" as t1");//ezen van
        System.out.println("participant \"s2:SpeedSpore\" as s2");//m spórája
        System.out.println();

        Mushroom m = (Mushroom) getObjectByValue("m");
        SingleThreadTecton t2 = (SingleThreadTecton) getObjectByValue("t2");

        list.add(null);
        list.add(m);
        parameters.clear();
        parameters.add(t2);
        m.shootSpore(t2);
    }


    /**
     * A gomba növesztési folyamatának inicializálását és végrehajtását végzi.
     *
     * Ez a metódus meghatározott szereplők definiálását,
     * valamint az interakciójukat tartalmazza egy bizonyos gomba növesztése kapcsán.
     *
     * Feladatok:
     * 1. A szereplők inicializálása.
     * 2. A növekedési folyamat adatainak listákba és paraméterekbe való összegyűjtése.
     * 3. A "growMushroom" metódus meghívása, amely végrehajtja a tényleges növekedési folyamatot.
     */
    public void growingNewMushroom() {
        init5();

        System.out.println("participant \"Tester\" as \"\"");
        System.out.println("participant \"f:FungalThread\" as f");
        System.out.println("participant \"t3:SingleThreadTecton\" as t3");
        System.out.println("participant \"m:Mushroom\" as m");
        System.out.println("participant \"s1:SlowingSpore\" as s1");
        System.out.println("participant \"s2:NoCutSpore\" as s2");
        System.out.println("participant \"s3:ParalysingSpore\" as s3");
        System.out.println();

        FungalThread f = (FungalThread) getObjectByValue("f");
        Tecton t3 = (SingleThreadTecton) getObjectByValue("t3");

        list.add(null);
        list.add(f);
        parameters.clear();
        parameters.add(t3);

        f.growMushroom(t3);
    }


    /**
     * Az első gombát olyan tectonra helyezi le, amelyre lehelyezhető.
     *
     * Ez a metódus inicializálja a szükséges állapotokat, majd kiválasztja azt a
     * MultiThreadTecton objektumot, amelyikre az első gombát el kell helyezni.
     * Az objektum hozzáadódik egy listához, és beállításain keresztül az első gomba
     * lehelyezésre kerül.
     */
    public void putFirstMushroomTrue() {
        init1();
        MultiThreadTecton t4 = (MultiThreadTecton) getObjectByValue("t4");
        list.add(null);
        list.add(t4);
        parameters.clear();
        t4.putFirstMushroom();
    }


    /**
     * Az első gombatest elhelyezése egy AbsorbingTecton-ra, amelyre nem lehet
     * gombatestet lehelyezni. A metódus inicializálja az állapotokat, majd megpróbálja
     * a "t5" objektumra elhelyezni a gombatestet, amely sikertelen lesz az
     * AbsorbingTecton jellegének köszönhetően.
     *
     * A folyamat lépései:
     * - Az inicializáló metódus lefut (init1).
     * - A "t5" AbsorbingTecton objektum azonosítása és lekérése az objektumlistából.
     * - A lista frissítése egy null elem és az AbsorbingTecton hozzáadásával.
     * - Paraméterlista kiürítése.
     * - Az AbsorbingTecton putFirstMushroom metódusának meghívása, amely a sikertelen elhelyezési próbálkozást hajtja végre.
     */
    public void putFirstMushroomFalseAbsorb() {
        init1();
        AbsorbingTecton t5 = (AbsorbingTecton) getObjectByValue("t5");
        list.add(null);
        list.add(t5);
        parameters.clear();
        t5.putFirstMushroom();
    }


    /**
     * A metódus az első gombatest lehelyezését szimulálja egy tektonra olyan esetben,
     * amikor arra már nem lehet újabb gombatestet lehelyezni, mert már van rajta egy.
     *
     * A metódus:
     * - Inicializálja a szükséges állapotot az `init1` meghívásával.
     * - Lekéri a "t1" tekton objektumot a `getObjectByValue` metódus segítségével.
     * - A `list` objektumhoz hozzáad egy `null` értéket és a tekton objektumot.
     * - Tisztítja a felhasznált `parameters` listát.
     * - Meghívja a `putFirstMushroom` metódust a "t1" tekton objektumon.
     *
     * Használatos olyan szituációk tesztelésére, amikor megvizsgáljuk, hogy a művelet
     * megfelelően kezeli-e azt az esetet, ha már nem helyezhető további gombatest a tektonra.
     */
    public void putFirstMushroomFalseIsMush(){
        init1();
        MultiThreadTecton t1 = (MultiThreadTecton) getObjectByValue("t1");
        list.add(null);
        list.add(t1);
        parameters.clear();
        t1.putFirstMushroom();
    }


    /**
     * Fejletlen gombatest spóra szórása egy meghatározott SingleThreadTecton-ra, ahova lehetséges.
     *
     * Ez a metódus inicializál bizonyos értékeket, lekéri a megfelelő
     * Mushroom és SingleThreadTecton objektumokat, majd egy spórát juttat
     * a kiválasztott SingleThreadTecton-ra a Mushroom objektum shootSpore
     * metódusának segítségével.
     *
     * Metódus lépései:
     * 1. Értékek inicializálása az init1() metódus segítségével.
     * 2. Mushroom objektum lekérése az aktuális környezetből.
     * 3. SingleThreadTecton objektum lekérése az aktuális környezetből.
     * 4. Objektumok regisztrálása az aktuális listában.
     * 5. Paraméterek beállítása a spóra szórásához.
     * 6. Spóra szórása a kiválasztott SingleThreadTecton-ra a Mushroom objektum shootSpore() metódusával.
     */
    public void unevolvedShootSporeTrue(){
        init1();
        Mushroom m = (Mushroom) getObjectByValue("m");
        SingleThreadTecton t3 = (SingleThreadTecton) getObjectByValue("t3");
        list.add(null);
        list.add(m);
        parameters.clear();
        parameters.add(t3);
        m.shootSpore(t3);
    }


    /**
     * A fejlett gombatest által végrehajtott spóra szórási művelet.
     *
     * Ez a metódus egy fejlett gombatest spóraszórási folyamatát hajtja végre egy adott
     * SingleThreadTecton objektumra, amennyiben az lehetséges.
     * Először inicializálja a szükséges adatokat, majd lekérdezi
     * a spóraszóráshoz használt Mushroom és SingleThreadTecton objektumokat.
     * Ezt követően hozzáadja a szükséges paramétereket, és végrehajtja a gombatest
     * spóra szórási műveletét az adott célobjektumra.
     */
    public void evolvedShootSporeTrue(){
        init5();
        Mushroom m =  (Mushroom) getObjectByValue("m");
        SingleThreadTecton t2 = (SingleThreadTecton) getObjectByValue("t2");
        list.add(null);
        list.add(m);
        parameters.clear();
        parameters.add(t2);
        m.shootSpore(t2);
    }


    /**
     * 1. Kommunikációs diagram szerinti inicializálás
     */
    public void init1(){


        MultiThreadTecton t1 = new MultiThreadTecton(this);// 1
        SingleThreadTecton t2 = new SingleThreadTecton(this);// 2
        SingleThreadTecton t3 = new SingleThreadTecton(this);// 3
        MultiThreadTecton t4 = new MultiThreadTecton(this);// 4
        AbsorbingTecton t5 = new AbsorbingTecton(this);// 5

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t3);
        t1.setNeighbors(t1Neighbors);// 6.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors);// 7.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3Neighbors.add(t4);
        t3Neighbors.add(t5);
        t3.setNeighbors(t3Neighbors);// 8.

        List<Tecton> t4Neighbors = new ArrayList<>();
        t4Neighbors.add(t3);
        t4.setNeighbors(t4Neighbors);// 9.

        List<Tecton> t5Neighbors = new ArrayList<>();
        t5Neighbors.add(t3);
        t5.setNeighbors(t5Neighbors);// 10.

        FungalThread f = new FungalThread(this);// 11.
        Mushroom m = new Mushroom(this);// 12.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t3);
        fTectons.add(t2);
        f.setTectons(fTectons);// 13.

        t1.addThread(f);// 14.
        t3.addThread(f);// 15.
        t2.addThread(f);// 16.

        m.setPosition(t1);// 17
        t1.setMushroom(m);// 18
        m.setThread(f);// 19

        SlowingSpore s = new SlowingSpore(this);// 20

        List<Spore> spores = new ArrayList<>();
        spores.add(s);
        t3.setSpores(spores);// 22
        // 21 az nincs
        Insect i = new Insect(this);// 23
        i.setPosition(t3);// 24
        t3.setInsect(i);// 25
        SpeedSpore s2 = new SpeedSpore(this);// 26
        s2.setThread(f);// 27

        List<Spore> spores2 = new ArrayList<>();
        spores2.add(s2);
        m.setSpores(spores2);

        // Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a
        // függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(t4, "t4");
        map.put(t5, "t5");
        map.put(i, "i");
        map.put(s, "s");
        map.put(s2, "s2");

    }


    /**
     * 2. Kommunikációs diagram szerinti inicializálás
     */
    public void init2() {
        // A kommunikációs diagrammnak megfelelő sorrendben és módon inicializáljuk az
        // objektumokat.
        // Ehhez implementálni kellett pár setter-t.
        map.clear();

        MultiThreadTecton t1 = new MultiThreadTecton(this);// 1.
        SingleThreadTecton t2 = new SingleThreadTecton(this);// 2.
        SingleThreadTecton t3 = new SingleThreadTecton(this);// 3.
        MultiThreadTecton t4 = new MultiThreadTecton(this);// 4.
        MultiThreadTecton t5 = new MultiThreadTecton(this);// 5.
        MultiThreadTecton t6 = new MultiThreadTecton(this);// 6.

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t2);
        t1.setNeighbors(t1Neighbors); // 7.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t1);
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors); // 8.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors); // 9.

        List<Tecton> t4Neighbors = new ArrayList<>();
        t4Neighbors.add(t2);
        t4Neighbors.add(t5);
        t4.setNeighbors(t4Neighbors); // 10.

        List<Tecton> t5Neighbors = new ArrayList<>();
        t5Neighbors.add(t4);
        t5Neighbors.add(t6);
        t5.setNeighbors(t5Neighbors); // 11.

        List<Tecton> t6Neighbors = new ArrayList<>();
        t6Neighbors.add(t5);
        t6.setNeighbors(t6Neighbors); // 12.

        FungalThread f = new FungalThread(this); // 13.
        Mushroom m = new Mushroom(this); // 14.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t2);
        fTectons.add(t3);
        fTectons.add(t5);
        fTectons.add(t6);
        f.setTectons(fTectons); // 15.

        t1.addThread(f); // 16.
        t2.addThread(f); // 17.
        t3.addThread(f); // 18.
        t5.addThread(f); // 19.
        t6.addThread(f); // 20.

        m.setPosition(t2); // 21.
        t2.setMushroom(m); // 22.
        m.setThread(f); // 23.

        // Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a
        // függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(t4, "t4");
        map.put(t5, "t5");
        map.put(t6, "t6");

    }


    /**
     * 3. Kommunikációs diagram szerinti inicializálás
     */
    public void init3() {

        map.clear();

        // A kommunikációs diagrammnak megfelelő sorrendben és módon inicializáljuk az
        // objektumokat.
        // Ehhez implementálni kellett pár setter-t.
        MultiThreadTecton t1 = new MultiThreadTecton(this);// 1.
        MultiThreadTecton t2 = new MultiThreadTecton(this);// 2.
        MultiThreadTecton t3 = new MultiThreadTecton(this);// 3.

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t2);
        t1.setNeighbors(t1Neighbors);// 4.

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t1);
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors);// 5.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors);// 6.

        FungalThread f = new FungalThread(this);// 7.
        Mushroom m = new Mushroom(this);// 8.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t2);
        f.setTectons(fTectons);// 9.

        t2.addThread(f);// 10.
        m.setPosition(t2);// 11.
        t2.setMushroom(m);// 12.
        m.setThread(f);// 13.
        SpeedSpore s = new SpeedSpore(this);// 14.
        s.setThread(f);// 15.

        List<Spore> spores = new ArrayList<>();
        spores.add(s);
        m.setSpores(spores);// 16.
        m.setShootedSporesCount(9);// 17.

        // Végül betesszük a map-be, hogy elő tudjuk őket szedni, hogy meghívjuk a
        // függvényeiket
        map.put(f, "f");
        map.put(m, "m");
        map.put(t1, "t1");
        map.put(t2, "t2");
        map.put(t3, "t3");
        map.put(s, "s");
    }


    /**
     * 4. Kommunikációs diagram szerinti inicializálás
     */
    public void init4() {
        map.clear();

        Tecton t1 = new MultiThreadTecton(this); // 1.
        Tecton t2 = new SingleThreadTecton(this); // 2.
        Tecton t3 = new AbsorbingTecton(this); // 3.
        Tecton t4 = new MultiThreadTecton(this); // 4.
        Tecton t5 = new MultiThreadTecton(this); // 5.

        List<Tecton> t1Neighbors = new ArrayList<>();
        t1Neighbors.add(t3);

        t1.setNeighbors(t1Neighbors); // 6

        List<Tecton> t2Neighbors = new ArrayList<>();
        t2Neighbors.add(t3);
        t2.setNeighbors(t2Neighbors); // 7.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3Neighbors.add(t4);
        t3Neighbors.add(t5);

        t3.setNeighbors(t3Neighbors); // 8.
        List<Tecton> t4Neighbors = new ArrayList<>();
        t4Neighbors.add(t3);
        t4.setNeighbors(t4Neighbors); // 9.

        List<Tecton> t5Neighbors = new ArrayList<>();
        t5Neighbors.add(t3);
        t5.setNeighbors(t5Neighbors); // 10.

        FungalThread f1 = new FungalThread(this); // 11.
        Mushroom m1 = new Mushroom(this); // 12.

        List<Tecton> f1Tectons = new ArrayList<>();
        f1Tectons.add(t1);
        f1Tectons.add(t2);
        f1Tectons.add(t3);

        f1.setTectons(f1Tectons); // 13.
        t1.addThread(f1); // 14.
        t2.addThread(f1); // 15.
        t3.addThread(f1); // 16.

        m1.setPosition(t1); // 17.
        t1.setMushroom(m1); // 18.
        m1.setThread(f1); // 19.

        FungalThread f2 = new FungalThread(this); // 20.
        Mushroom m2 = new Mushroom(this); // 21.

        List<Tecton> f2Tectons = new ArrayList<>();
        f2Tectons.add(t3);
        f2Tectons.add(t5);

        f2.setTectons(f2Tectons); // 22.
        t5.addThread(f2); // 23.
        t3.addThread(f2); // 24.
        m2.setPosition(t5); // 25.
        t5.setMushroom(m2); // 26.
        m2.setThread(f2); // 27.

        Insect i = new Insect(this); // 28.
        i.setPosition(t3); // 29.
        t3.setInsect(i); // 30.

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


    /**
     * 5. Kommunikációs diagram szerinti inicializálás
     */
    public void init5() {
        map.clear();

        FungalThread f = new FungalThread(this); // 1.
        Tecton t1 = new MultiThreadTecton(this); // 2.
        Tecton t2 = new SingleThreadTecton(this); // 3.
        Tecton t3 = new SingleThreadTecton(this); // 4.

        Mushroom m = new Mushroom(this); // 5.
        Spore s1 = new SlowingSpore(this); // 6.
        Spore s2 = new NoCutSpore(this); // 7.
        Spore s3 = new ParalysingSpore(this); // 8.
        Spore s4 = new SpeedSpore(this); // 9.

        List<Tecton> fTectons = new ArrayList<>();
        fTectons.add(t1);
        fTectons.add(t2);
        fTectons.add(t3);

        f.setTectons(fTectons); // 10. (10.1)
        m.setPosition(t1); // 10. (10.2)
        m.setThread(f); // 11.

        List<Spore> s4List = new ArrayList<>();
        s4List.add(s4);

        m.setSpores(s4List); // 12.
        m.evolve(); // 13.

        List<Tecton> t3List = new ArrayList<>();
        t3List.add(t3);
        t1.setNeighbors(t3List); // 14.
        t1.addThread(f); // 15.
        t1.setMushroom(m); // 16.
        t2.setNeighbors(t3List); // 17.
        t2.addThread(f); // 18.

        List<Tecton> t3Neighbors = new ArrayList<>();
        t3Neighbors.add(t1);
        t3Neighbors.add(t2);
        t3.setNeighbors(t3Neighbors); // 19.
        t3.addThread(f); // 20.

        List<Spore> t3Spores = new ArrayList<>();
        t3Spores.add(s1);
        t3Spores.add(s2);
        t3Spores.add(s3);
        t3.setSpores(t3Spores); // 21.

        s1.setThread(f); // 22.
        s2.setThread(f); // 23.
        s3.setThread(f); // 24.
        s4.setThread(f); // 25.

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


    /**
     * Kikeres egy objektumot a megadott érték alapján.
     *
     * @param value A keresett érték, amely alapján az objektumot meg kell találni.
     * @return Az objektum, amely megfelel a megadott értéknek, vagy null, ha nincs ilyen objektum.
     */
    public Object getObjectByValue(String value) {
        for (Map.Entry<Object, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey(); // Visszaadjuk az objektumot
            }
        }
        return null; // Ha nincs ilyen érték, null-t adunk vissza
    }

}
