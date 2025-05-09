package controller;

import model.IInsectController;


/**
 * Az InsectAssociation osztály egy rovarvezérlőhöz (IInsectController) kapcsolódó asszociációt reprezentál.
 * A beállítható attribútumokkal nyomon követhető, hogy történt-e bármilyen mozgás vagy vágás a rovaron.
 */
public class InsectAssociation {

    private IInsectController insect;
    private boolean moved;
    private boolean cut;


    /**
     * Létrehoz egy új, alapállapotú InsectAssociation objektumot.
     * Az inicializálás során az 'insect' mező null értéket kap,
     * a 'moved' és 'cut' mezők hamis értékre vannak állítva.
     */
    public InsectAssociation() {
        insect = null;
        moved = false;
        cut = false;
    }


    /**
     * Beállítja az InsectAssociation osztályhoz kapcsolódó rovarvezérlőt.
     *
     * @param i Az IInsectController interfész egy implementációja, amely a rovar funkcionalitását kezeli.
     */
    public void setInsect(IInsectController i) {
        insect = i;
    }


    /**
     * Visszaadja az aktuálisan kezelt rovar (insect) vezérlő objektumát.
     *
     * @return Az aktuális IInsectController típusú példány, amely a rovar vezérléséért felelős,
     *         vagy null, ha nincs beállítva.
     */
    public IInsectController getInsect() {
        return insect;
    }


    /**
     * Beállítja a mozgás állapotát.
     *
     * @param m A mozgás állapotát jelölő logikai érték.
     *          Ha true, akkor mozgás történt; ha false, akkor nem történt mozgás.
     */
    public void setMoved(boolean m) {
        moved = m;
    }


    /**
     * Visszaadja, hogy az objektum mozgásban volt-e.
     *
     * @return igaz, ha az objektum mozgott, különben hamis
     */
    public boolean getMoved() {
        return moved;
    }


    /**
     * Beállítja a cut attribútum értékét.
     *
     * @param c Az új érték, amely meghatározza a cut állapotot.
     */
    public void setCut(boolean c) {
        cut = c;
    }


    /**
     * Megadja, hogy az attribútum "cut" értéke igaz-e.
     *
     * @return a "cut" attribútum jelenlegi értéke, ami igaz (true), ha már vágott,
     * vagy hamis (false), ha nem.
     */
    public boolean getCut() {
        return cut;
    }

}
