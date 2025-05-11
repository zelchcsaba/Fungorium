package model;

import java.util.List;

/**
 * Ez az osztály egy rovart reprezentál, amely különböző műveleteket tud
 * végrehajtani egy "Tecton" típusú pozíción és gombafonalakon.
 * Az osztály a mozgásra, pozíció megváltoztatására és fonál vágására vonatkozó
 * függvényeket valósít meg.
 */
public class Insect implements IInsectController, IInsectView {

    private Tecton position;
    private InsectState state;


    /**
     * Az Insect osztály konstruktora, amely létrehoz egy új rovar objektumot.
     * Az inicializálás során beállítja a pozíciót null értékre, és az állapotot
     * alapértelmezett módon InsectState.NORMAL értékre állítja.
     */
    public Insect() {
        position = null;
        state = InsectState.NORMAL;
    }


    /**
     * Beállítja a rovar pozícióját a megadott Tecton objektumra.
     *
     * @param t A Tecton objektum, amely a rovar új pozícióját jelöli.
     */
    public void setPosition(Tecton t) {
        position = t;
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
    public void setState(InsectState s) {
        state = s;
    }


    /**
     * Visszaadja a rovar aktuális állapotát jelző spórahatás értékét.
     *
     * @return a SporeEffect típusú aktuális állapot.
     */
    public InsectState getState() {
        return state;
    }


    /**
     * Megpróbálja a rovarat (Insect) áthelyezni a megadott tektonra (Tecton).
     *
     * @param t A cél tekton, ahova a rovar áthelyezésre kerül.
     * @return true, ha az áthelyezés sikeres; false, ha nem sikerült az áthelyezés.
     */
    public boolean move(Tecton t) {

        if (t.putInsect(this, position)) {

            List<Spore> tSpores = t.getSpores();

            if (!tSpores.isEmpty()) {
                tSpores.get(0).applyEffect(this);
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Megpróbál elvágni egy fonalat a megadott Tecton objektumnál.
     * Először ellenőrzi, hogy a megadott tekton szomszédos-e a rovar aktuális pozíciójával,
     * majd végrehajtja az elvágási műveletet, ha lehetséges.
     *
     * @param t A Tecton objektum, amelynél a fonál elvágását próbálja.
     * @return true, ha sikerült fonalat vágni, false, ha nem sikerült (pl. nincs szomszéd, vagy nem vágott fonalat).
     */
    public boolean cut(Tecton t) {
        if (t.getMushroom() != null) {
            return false;
        }
        //leellenőrizzük, hogy a kapott tekton az szomszédos-e a position tektonnal

        if (t.isNeighbor(position)) {
            //létrehozunk két listát, az egyikben a t, a másikban a position tektonon levő fonalakat tároljuk
            List<FungalThread> list1;
            List<FungalThread> list2;

            list1 = t.getThreads();
            list2 = position.getThreads();
            boolean isCut = false;

            //megnézzük, hogy a list2-ben mely elemek szerepelnek a list1-ből
            for (int i = 0; i < list1.size(); i++) {

                //ha valamely elem a list1-ből szerepel a list2-ben is
                if (list2.contains(list1.get(i))) {
                    //kivesszük a fonál tekton listájából a t tektont
                    isCut = true;
                    list1.get(i).sendToDie(t);
                }
            }
            //ha sikerült fonalat vágni true értékkel tér vissza
            if(isCut){
                return true;
            }else{
                return false;
            }
        } else {
            //ha nem akkor false-val
            return false;
        }
    }


    /**
     * Létrehoz egy új rovar objektumot (Insect), amely a szomszédos szabad pozíciók közül
     * egyre kerül, ha van ilyen elérhető pozíció. Ha nincs szabad szomszédos hely,
     * nem jön létre új rovar.
     *
     * @return Az újonnan létrehozott rovar objektumot adja vissza, ha sikerült egy
     * szabad szomszédos pozícióra elhelyezni. Ha nincs elérhető szabad hely, null értéket ad vissza.
     */
    public Insect divide() {
        Insect i = null;
        List<Tecton> tlist = position.getNeighbors();
        boolean attached = false;
        int j = 0;
        while (j < tlist.size() && attached == false) {
            if(tlist.get(j)!=null){
                if (tlist.get(j).getInsect() == null) {
                    i = new Insect();
                    i.setPosition(tlist.get(j));
                    tlist.get(j).setInsect(i);
                    attached = true;
                }
                j = j + 1;
            }
        }
        return i;
    }

}
