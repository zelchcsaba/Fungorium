package model;

/**
 * A DividingSpore egy olyan spóra implementáció, amely a rovarokat egy
 * különleges állapotba helyezi, ahol a rovarok osztódási képességet kapnak.
 */
public class DividingSpore extends Spore {

    /**
     * A DividingSpore osztály konstruktora, amely inicializálja a spórát.
     */
    public DividingSpore() {
        super();
    }


    /**
     * A metódus speciális hatást alkalmaz egy megadott rovaron (Insect),
     * amelynek eredményeképp a rovar állapota osztott (DIVIDED) lesz.
     *
     * @param i A rovar objektum, amelyre a hatás alkalmazásra kerül.
     */
    public void applyEffect(Insect i) {
        i.setState(InsectState.DIVIDED);
    }

}
