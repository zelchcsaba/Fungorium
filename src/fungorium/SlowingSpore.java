package fungorium;

public class SlowingSpore extends Spore{

    public SlowingSpore(Tester t){
        super(t);
    }

    public void applyEffect(Insect i) {
        i.setState(SporeEffect.SLOWED);
    }

}
