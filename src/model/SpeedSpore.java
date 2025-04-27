package model;

/**
 * A SpeedSpore osztály a Spore absztrakt osztály leszármazottja.
 * Ez az osztály egy hatást alkalmaz egy rovarra, amely sebességnövekedést idéz elő.
 */
public class SpeedSpore extends Spore {

    /**
     * A SpeedSpore konstruktor egy új SpeedSpore objektumot hoz létre.
     */
    public SpeedSpore() {
        super();
    }


    /**
     * A megadott rovarra (Insect) egy sebességnövelő (SPEEDBOOST) hatást alkalmaz.
     * A hatás a rovar állapotát (state) módosítja a SporeEffect enum SPEEDBOOST értékére.
     *
     * @param i A rovar (Insect), amelyre a hatást alkalmazni kell.
     */
    public void applyEffect(Insect i) {
        i.setState(InsectState.SPEEDBOOST);
    }


}
