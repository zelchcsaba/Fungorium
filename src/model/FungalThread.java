package model;

import static model.InsectState.PARALYZED;

import java.util.ArrayList;
import java.util.List;

/**
 * A FungalThread osztály egy fonál modellt definiál, amely gombák növekedését
 * és tektonok közötti kapcsolatot reprezentálja. Ez az osztály számos funkciót biztosít
 * a fonál részeinek kezelésére, például hozzáadására, eltávolítására, elágazására,
 * és a nem szükséges fonálrészek automatikus eltávolítására.
 */
public abstract class FungalThread implements IFungalThreadController{

    private List<Tecton> tectons;
    protected List<timeToDie> life;

    /**
     * Egy új FungalThread objektum létrehozása a megadott Tester objektummal.
     *
     * @param t A Tester példány, amely a FungalThread-hez kapcsolódik és annak működését felügyeli.
     */
    public FungalThread() {
        tectons = new ArrayList<>();
        life = new ArrayList<>();
    }


    /**
     * Beállítja a fonalhoz tartozó tectonok listáját.
     *
     * @param tectons A tectonok listája, amelyeket be kell állítani.
     */
    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }

        /**
     * Visszaadja a jelenlegi tectonok listáját.
     *
     * @return egy lista, amely tartalmazza az összes tectont
     */
    public List<Tecton> getTectons() {
        return tectons;
    }

    /**
     * Eltávolítja azokat a fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból származó gombatesthez.
     * <p>
     * A metódus végigiterál a tectons listán, és azokat a tektonokat, amelyek kapcsolódnak egy gombatestre,
     * összegyűjti egy ideiglenes connectedTectons listába. Azokon a tectonokon, amelyek nem szerepelnek a
     * connectedTectons listában, eltávolítja a fonálrészeket.
     * <p>
     * Függvényfolyamat:
     * 1. Kiválasztja azokat a tektonokat (Tecton példányokat), amelyeken gombatest található, és egy külön listába helyezi.
     * 2. Összegyűjti azokat a tektonokat, amelyek fonálrésszel kapcsolódnak ugyanolyan fajból származó gombatesthez.
     * 3. Azokból a tektonokból, amelyek nem szerepelnek a kapcsolódó tektonok listájában, eltávolítja a fonálrészeket.
     * <p>
     * A szimulációt segítő t.toCall() metódus különböző részfolyamatokat naplóz ki.
     */
    public void deleteUnnecessaryThreads() {

        // létrehozok két segéd listát
        List<Tecton> fungalList = new ArrayList<>();
        List<Tecton> connectedTectons = new ArrayList<>();

        // végigmegyek a tectons listán, megnézem melyik tektonon van gomba, ezt
        // elmentem a fungalList listába
        for (int i = 0; i < tectons.size(); i++) {

            // megnézem, ha van-e rajta gombatest
            if ((tectons.get(i).isConnected(this))) {
                fungalList.add(tectons.get(i));
            }
        }

        // megkeresem azokat a fonálrészeket, amelyek kapcsolatban vannak ugyanolyan
        // fajból származó gombatesttel
        while (!fungalList.isEmpty()) {
            connectedTectons.add(fungalList.get(0));

            // megnézem, hogy tectons.get(0) szomszédai közül melyeken van elágazva a fonál
            List<Tecton> list = fungalList.get(0).getThreadSection(this);
            for (int i = 0; i < list.size(); i++) {
                if (!connectedTectons.contains(list.get(i))) {
                    fungalList.add(list.get(i));
                }
            }
            fungalList.remove(0);
        }

        // Végigmegyek a tectons listán, majd azokról a tectonokról, amelyeken
        // olyanfonálrészek vannak,
        // amelyek nincsenek kapcsolatban ugyanolyan fajból származó gombatesttel
        // leszedjük a fonalat
        List<Tecton> removallist = new ArrayList<>();
        for (int i = 0; i < tectons.size(); i++) {
            if (!connectedTectons.contains(tectons.get(i))) {

                // leveszem a tektonokról a fonalat
                tectons.get(i).removeThread(this);
                removallist.add(tectons.get(i));
            }
        }
        for(int i=0; i<removallist.size();i++){
            tectons.remove(removallist.get(i));
        }
    }

    /**
     * Egy új ágazó fonalat hoz létre, amely a meglévő fonálból indul és egy másik tektonra kiterjed.
     *
     * @param t A céltekton, amelyhez az új fonalat csatlakoztatják.
     * @return Igaz, ha a fonál sikeresen létrejött és csatlakozott, hamis, ha a csatlakozás nem sikerült.
     */
    public boolean branchThread(Tecton t) {

        if (!t.putThread(this)) {
            return false;
        } else {
            tectons.add(t);
            return true;
        }
    }

    /**
     * Hozzáadja a megadott Tectont a jelenlegi FungalThread-hez tartozó tectons listához.
     *
     * @param t a Tecton példány, amelyet hozzá kell adni
     * @return true értéket ad vissza, ha a Tecton sikeresen hozzáadásra került; false egyébként
     */
    public boolean addTecton(Tecton t) {
        return tectons.add(t);
    }

    /**
     * Eltávolítja a megadott tektont a jelenlegi tectons listából.
     *
     * @param t A tektont, amelyet el kell távolítani a tectons listából.
     * @return true értéket ad vissza, ha a művelet sikeres.
     */
    public boolean removeTecton(Tecton t) {

        tectons.remove(t);
        return true;
    }

    /**
     * Növeszt egy új gombát a megadott Tecton objektummal kapcsolódva.
     * Ellenőrzi, hogy minden spóra a jelenlegi fonálrész segítségével működik-e,
     * majd létrehozza és megfelelően inicializálja a gombát.
     *
     * @param t A Tecton objektum, amelyhez kapcsolódva a gomba növekszik.
     * @return true, ha a gomba sikeresen létrejött és inicializálva lett,
     * false, ha az ellenőrzések során hiba történt.
     */
    public boolean growMushroom(Tecton t, Mushroom m) {

        List<Spore> slist = t.getSpores();
        int thisSporeCount = 0;

        for(int i=0;i<slist.size();i++){
            if(slist.get(i).getThread() == this){
                thisSporeCount+=1;
            }
        }

        boolean canGrow = false;
        if(thisSporeCount>=3){
            canGrow = true;
        }

        if (!canGrow) {
            return false;
        }

        m.setPosition(t);
        m.setThread(this);

        List<FungalThread> thisThread = new ArrayList<>();
        thisThread.add(this);
        t.setThreads(thisThread);

        t.putMushroom(m);

        return true;
    }

    public abstract boolean sendToDie(Tecton t);

    public void timeCheck(){
        for(int i=0; i<life.size();i++){
            life.get(i).setTime(life.get(i).getTime()-1);
        }
        int i=0;
        while(i<life.size()){
            if(life.get(i).getTime()==0){
                life.get(i).getTecton().removeThread(this);
                tectons.remove(life.get(i).getTecton());
                life.remove(i);
            }else{
                i+=1;
            }
        }
        deleteUnnecessaryThreads();
    }

    public boolean eatInsect(Insect i){
            if(tectons.contains(i.getPosition())){
                if(i.getPosition().removeInsect()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        
    }

}
