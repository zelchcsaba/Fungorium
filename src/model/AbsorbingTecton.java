package model;

import java.util.ArrayList;
import java.util.List;


/**
 * Az AbsorbingTecton osztály a Tecton leszármazottja, amely olyan speciális viselkedéssel
 * rendelkezik, hogy nem lehet rá Mushroom objektumot helyezni. Ez az osztály a rajta lévő
 * FungalThread fonalakkal való műveletek kezelésére összpontosul.
 */
public class AbsorbingTecton extends Tecton {

    private List<FungalThread> threads;


    /**
     * Konstruktor, amely inicializálja az threads listát.
     */
    public AbsorbingTecton() {
        super();
        threads = new ArrayList();
    }


    /**
     * Nem lehet Mushroomot lehelyezni
     *
     * @param mushroom
     */
    public boolean setMushroom(Mushroom mushroom) {
        return false;
    }


    /**
     * Visszaad egy Mushroom (gombatest) objektumot, amely az AbsorbingTecton típusú
     * objektumban található. Mivel az AbsorbingTecton esetében nem lehetséges gombatest
     * jelenléte, a visszatérési érték mindig null.
     *
     * @return null, mivel az AbsorbingTecton nem tartalmaz Mushroom objektumot.
     */
    public Mushroom getMushroom() {
        // absorbing tektonon nem lehet gombatest, ezért a visszatérési érték null
        return null;
    }


    /**
     * Beállítja az aktuális objektumhoz tartozó gombafonalak listáját.
     *
     * @param list A gombafonalakat tartalmazó lista, amely az objektumhoz kerül hozzárendelésre.
     */
    public void setThreads(List<FungalThread> list) {
        threads = list;
    }


    /**
     * Visszaadja az objektumhoz tartozó gombafonalak listáját.
     *
     * @return A gombafonalak listája, amely ehhez az objektumhoz tartozik.
     */
    public List<FungalThread> getThreads() {
        //visszaadja a fonalakat
        return threads;
    }


    /**
     * Eltávolítja az aktuális objektum listájában található összes FungalThread (gombafonal)
     * objektumot, és törli a nem szükséges gombafonalakat.
     * <p>
     * A metódus először eltávolítja az aktuális objektumot az összes kapcsolódó
     * FungalThread objektum tartalmából. Ezután létrehoz egy új listát, amelybe átmásolja
     * az eredeti gombafonalakat, majd az eredeti listát kiüríti. A másolt elemekre végrehajtja
     * a törlési műveletet, amely eltávolítja azokat a fonalrészeket, amelyek nem kapcsolódnak
     * azonos fajhoz tartozó gombatesthez.
     */
    public void absorb() {

        for (int i = 0; i < threads.size(); i++) {
            // levesszük róla a fonalakat
            threads.get(i).removeTecton(this);
        }

        List<FungalThread> fungal = new ArrayList<>();
        fungal.addAll(threads);
        threads.clear();

        for (int i = 0; i < fungal.size(); i++) {
            // töröljük azon fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból
            // származó gombatesthez
            fungal.get(i).deleteUnnecessaryThreads();
        }
    }


    /**
     * Nem lehet Mushroom objektumot lehelyezni az aktuális objektumra.
     *
     * @param m A Mushroom objektum, amelyet le szeretnének helyezni.
     * @return false, mivel az aktuális objektumra nem lehet Mushroomot lehelyezni.
     */
    public boolean putMushroom(Mushroom m) {
        return false;
    }


    /**
     * Ellenőrzi, hogy a megadott FungalThread (gombafonal) már létezik-e valamelyik szomszéd tektonban,
     * és ennek megfelelően helyezi el a gombafonalat.
     *
     * @param f Az a FungalThread objektum, amelyet hozzá szeretnénk adni a tektonhoz.
     * @return true, ha a megadott FungalThread (gombafonal) létezik az egyik szomszéd tektonban,
     * false, ha nem található a megadott szomszédságokban.
     */
    public boolean putThread(FungalThread f) {
        for (Tecton tecton : neighbors) {
            if(tecton != null){
                if (tecton.getThreads().contains(f)) {
                    threads.add(f);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Eltávolítja a Mushroom objektumot az aktuális AbsorbingTecton objektumról.
     * Az AbsorbingTecton típusú objektumok esetében nem lehetséges Mushroom jelenléte,
     * ezért a metódus mindig false értékkel tér vissza, jelezve, hogy nem történt eltávolítás.
     *
     * @return mindig false, mert az AbsorbingTecton objektumban nem lehetséges Mushroom jelenléte.
     */
    public boolean removeMushroom() {
        return false;
    }


    /**
     * Eltávolítja a megadott gombafonalat az aktuális objektumhoz tartozó listából.
     *
     * @param f Az a FungalThread objektum, amelyet el szeretnénk távolítani az aktuális objektumhoz tartozó listából.
     * @return true, ha a művelet sikeres volt.
     */
    public boolean removeThread(FungalThread f) {
        threads.remove(f);
        return true;
    }


    /**
     * A metódus kettétöri az aktuális tekton-t két különálló tekton-ra (t6 és t7).
     * Az eredeti szomszédságokat és kapcsolódó struktúrákat frissíti a művelet során.
     * Továbbá kezeli a kapcsolódó bogarak elhelyezkedését és a fonál kapcsolódásokat.
     *
     * @return true, ha a tekton sikeresen kettétört.
     */
    public List<Tecton> breakTecton() {

        List<Tecton> ret = new ArrayList<>();
        // létrejön a két új tekton
        Tecton t6 = new AbsorbingTecton();
        Tecton t7 = new AbsorbingTecton();

        ret.add(t6);
        ret.add(t7);

        // ez lesz a töréspont a tektonon
        int centre = neighbors.size() / 2;

        // létrehozok egy listát, amelyben a t6 tekton szomszédai lesznek
        List<Tecton> neighborList = new ArrayList<>();
        for (int i = 0; i < centre; i++) {
            neighborList.add(neighbors.get(i));
        }
        neighborList.add(t7);

        // beállítom a t6 szomszédait
        t6.addNeighbor(neighborList);
        neighborList.clear();

        // létrehozok egy listát, amelyben a t7 szomszédai lesznek
        neighborList.add(t6);
        for (int i = centre; i < neighbors.size(); i++) {
            neighborList.add(neighbors.get(i));
        }

        // beállítom a t7 szomszédait
        t7.addNeighbor(neighborList);
        neighborList.clear();

        // a jelenlegi tekton szomszédait beállítom, hozzáadva szomszédsági listájukhoz
        // a megfelelő létrejött tektont
        // valamint kivéve a kettétörött tektont
        for (int i = 0; i < centre; i++) {
            if(neighbors.get(i)!=null){
                neighbors.get(i).exchange(this, t6);
            }
        }

        for (int i = centre; i < neighbors.size(); i++) {
            if(neighbors.get(i)!=null){
                neighbors.get(i).exchange(this, t7);
            }
        }

        if(i!=null){
            // a tektonon levő bogarat ráhelyezem a t6-ra
            t6.setInsect(i);
            // beállítom a bogár pozícióját
            i.setPosition(t6);
        }

        // kitörlöm a tektont a fonálról
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).removeTecton(this);
        }

        // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
        // gombatesthez ezt eltávolítom
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).deleteUnnecessaryThreads();
        }

        return ret;
    }


    /**
     * Nem lehet az aktuális objektumra Mushroom objektumot lehelyezni.
     * A metódus minden esetben hamis értékkel tér vissza.
     *
     * @param f A FungalThread objektum, amelyet le szeretnénk helyezni.
     * @param m A Mushroom objektum, amelyet le szeretnénk helyezni.
     * @return false, mivel az aktuális objektumra nem lehet gombát lehelyezni.
     */
    public boolean putFirstMushroom(FungalThread f, Mushroom m) {
        return false;
    }


    /**
     * Megvizsgálja, hogy egy adott FungalThread objektum kapcsolódik-e
     * a tektonon található gombatesthez.
     *
     * @param f A vizsgálandó FungalThread objektum.
     * @return false, mivel az aktuális objektumra nem lehet gombát lehelyezni
     */
    public boolean isConnected(FungalThread f) {
        return false;
    }


    /**
     * Hozzáad egy új FungalThread (gombafonal) objektumot az AbsorbingTecton
     * aktuális objektumhoz tartozó gombafonalainak listájához.
     *
     * @param f A FungalThread objektum, amelyet hozzá szeretnénk adni az objektumhoz
     */
    public void addThread(FungalThread f) {
        threads.add(f);
    }


    /**
     * Ellenőrzi, hogy az aktuális objektumra lehet-e Mushroom (gombatest) objektumot lehelyezni.
     *
     * @return mindig false, mivel az aktuális objektumra nem lehet gombatestet lehelyezni.
     */
    public boolean canPutMushroom() {
        return false;
    }

}