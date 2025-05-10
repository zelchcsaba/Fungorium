package controller;

import java.util.ArrayList;
import java.util.List;
import model.IInsectController;
import model.Insect;


/**
 * Az InsectPlayer osztály a Player osztályból öröklődik, és
 * rovarokhoz kapcsolódó funkcionalitást biztosít. A játékoshoz társított
 * rovarokat és azok kapcsolódó információit kezeli.
 */
public class InsectPlayer extends Player {

    private List<InsectAssociation> insects;


    /**
     * Az InsectPlayer osztály alapértelmezett konstruktora.
     * Létrehozza és inicializálja a rovarokat tároló listát.
     */
    public InsectPlayer() {
        insects = new ArrayList<>();
    }


    /**
     * Visszaadja a játékoshoz társított rovarok listáját.
     *
     * @return A játékoshoz társított rovarokat tartalmazó lista.
     */
    public List<InsectAssociation> getInsects() {
        return insects;
    }


    /**
     * Hozzáad egy rovarvezérlőt az aktuális játékoshoz társított rovarok listájához.
     * Az adott rovarvezérlőt (IInsectController) egy új InsectAssociation objektumba csomagolja, majd
     * azt a listához (insects) adja hozzá.
     *
     * @param i Az IInsectController interfész egy implementációja, amely a hozzáadni kívánt rovar vezérlését végzi.
     */
    public void addInsect(IInsectController i) {
        InsectAssociation iAssoc = new InsectAssociation();
        iAssoc.setInsect(i);
        insects.add(iAssoc);
    }


    /**
     * Visszaadja a megadott indexen található rovar-asszociáció objektumot
     * az InsectPlayer rovarlistájából.
     *
     * @param i Az index, amely meghatározza a kért rovar-asszociáció helyét a listában.
     *          Az indexnek nem-negatívnak kell lennie, és kisebbnek kell lennie a rovarlista méreténél.
     * @return Az InsectAssociation típusú objektum, amely az 'i' indexhez tartozó rovar-asszociációt reprezentál.
     *         Ha az index kívül esik az érvényes tartományon, kivételt dob.
     */
    public InsectAssociation getInsectAt(int i) {
        return insects.get(i);
    }


    /**
     * Eltávolítja a megadott indexen lévő rovar-asszociációt a rovarok listájából.
     *
     * @param i az eltávolítandó rovar-asszociáció indexe a listában
     */
    public void removeInsectAt(int i) {
        insects.remove(i);
    }


    /**
     * Egy rovar asszociáció (InsectAssociation) eltávolítása a rovarok listájából.
     *
     * @param i Az eltávolítandó rovar asszociáció, amely az InsectAssociation típusú objektumot jelöli.
     */
    public void removeInsect(InsectAssociation i) {
        insects.remove(i);
    }


    /**
     * Eltávolítja a megadott rovart az asszociált rovarok listájából.
     * Ha a rovar létezik a listában, a megfelelő InsectAssociation objektumot
     * eltávolítja a listából.
     *
     * @param i A rovar (Insect) objektum, amelyet el kell távolítani a listából.
     */
    public void rm(Insect i) {
        InsectAssociation removable = null;
        for (InsectAssociation ins : insects) {
            if (ins.getInsect() == i) {
                removable = ins;
            }
        }
        removeInsect(removable);
    }


    /**
     * Meghatározza a jelenleg a játékoshoz társított rovarok számát.
     *
     * @return a rovarok számát, amelyek az insects listában találhatóak
     */
    public int getInsectSize() {
        return insects.size();
    }


    /**
     * Visszaadja a megadott rovarvezérlőhöz (IInsectController) tartozó InsectAssociation objektumot.
     *
     * @param insect Az IInsectController interfész egy implementációja, amely a keresett rovar vezérléséért felelős.
     * @return Az InsectAssociation objektum, amely megfelel a megadott rovarvezérlőnek, vagy null, ha nem található ilyen társítás.
     */
    public InsectAssociation getInsectAssociation(IInsectController insect) {
        for (InsectAssociation insectAssociation : insects) {
            if (insectAssociation.getInsect().equals(insect)) return insectAssociation;
        }
        return null;
    }

}
