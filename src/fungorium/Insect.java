package fungorium;

import java.util.List;
import java.util.ArrayList;

/**
 * Ez az osztály egy rovart reprezentál, amely különböző műveleteket tud
 * végrehajtani egy "Tecton" típusú pozíción és gombafonalakon.
 * Az osztály a mozgásra, pozíció megváltoztatására és fonál vágására vonatkozó
 * függvényeket valósít meg.
 */
public class Insect {

    Tester t;
    private Tecton position;
    private SporeEffect state;


    /**
     * Létrehoz egy új Insect objektumot.
     *
     * @param t a teszteléshez vagy vezérléshez használt Tester objektum
     */
    public Insect(Tester t) {
        this.t = t;
        position = null;
        state = SporeEffect.NORMAL;
    }


    /**
     * Beállítja a rovar pozícióját a megadott Tecton objektumra.
     *
     * @param t a Tecton objektum, amelyre a rovar pozícióját be kell állítani
     */
    public void setPosition(Tecton t) {
        position = t;
        this.t.toCall("setPosition");
        this.t.returnValue.clear();
        this.t.toReturn();
    }


    /**
     * Visszaadja a rovar aktuális helyzetét reprezentáló Tecton-t.
     *
     * @return a rovar aktuális pozíciója (Tecton objektum)
     */
    public Tecton getPosition() {
        return position;
    }


    /**
     * Beállítja a rovar aktuális állapotát a megadott értékre.
     *
     * @param s Az új állapot, amely a SporeEffect enum értékei közül választandó.
     */
    public void setState(SporeEffect s) {
        this.t.toCall("setState");
        state = s;
        this.t.returnValue.clear();
        this.t.toReturn();
    }


    /**
     * Visszaadja a rovar aktuális állapotát jelző spórahatás értékét.
     *
     * @return a SporeEffect típusú aktuális állapot.
     */
    public SporeEffect getState() {
        return state;
    }


    /**
     * Megpróbálja a rovarat (Insect) áthelyezni a megadott tektonra (Tecton).
     *
     * @param t A cél tekton, ahova a rovar áthelyezésre kerül.
     * @return true, ha az áthelyezés sikeres; false, ha nem sikerült az áthelyezés.
     */
    public boolean move(Tecton t) {
        this.t.toCall("move");

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(this);
        this.t.parameters.add(position);
        if(t.putInsect(this,position)){
            this.t.list.add(this);
            this.t.list.add(t);
            List<Spore> tSpores = t.getSpores();

            this.t.list.add(this);
            this.t.list.add(tSpores.get(0));
            tSpores.get(0).applyEffect(this);

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
        else{
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
    }


    /**
     * Elvágja a fonalakat a kijelölt tektonon, ha az szomszédos a rovar aktuális pozíciójával.
     *
     * @param t Az a Tecton objektum, amelyen a fonalakat el kell vágni.
     * @return True, ha sikeresen elvágták a fonalakat, különben False.
     */
    //elvágja a fonalakat a kijelölt tektonon
    public boolean cut(Tecton t) {
        //tester kiíró függvénye
        this.t.toCall("cut");

        //leellenőrizzük, hogy a kapott tekton az szomszédos-e a position tektonnal

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(position);

        if (t.isNeighbor(position)) {
            //létrehozunk két listát, az egyikben a t, a másikban a position tektonon levő fonalakat tároljuk
            List<FungalThread> list1;
            List<FungalThread> list2;

            //lekérjük a fonalakat
            this.t.list.add(this);
            this.t.list.add(t);
            this.t.parameters.clear();

            list1 = t.getThreads();

            this.t.list.add(this);
            this.t.list.add(position);
            this.t.parameters.clear();

            list2 = position.getThreads();

            //megnézzük, hogy a list2-ben mely elemek szerepelnek a list1-ből
            for (int i = 0; i < list1.size(); i++) {

                //ha valamely elem a list1-ből szerepel a list2-ben is
                if (list2.contains(list1.get(i))) {
                    //kivesszük a fonál tekton listájából a t tektont
                    this.t.list.add(this);
                    this.t.list.add(list1.get(i));
                    this.t.parameters.clear();
                    this.t.parameters.add(t);

                    list1.get(i).removeTecton(t);
                    //levesszük a t tektonról a fonalat
                    this.t.list.add(this);
                    this.t.list.add(t);
                    this.t.parameters.clear();
                    this.t.parameters.add(list1.get(i));

                    t.removeThread(list1.get(i));
                    //töröljük azon fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból származó gombatesthez
                    this.t.list.add(this);
                    this.t.list.add(list1.get(i));
                    this.t.parameters.clear();

                    list1.get(i).deleteUnnecessaryThreads();
                }
            }
            //ha sikerült fonalat vágni true értékkel tér vissza
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        } else {
            //ha nem akkor false-val
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
    }

}
