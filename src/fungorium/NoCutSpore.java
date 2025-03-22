package fungorium;

public class NoCutSpore extends Spore{

    public NoCutSpore(Tester t){
        super(t);
    }
    
    public void applyEffect(Insect i) {
        i.setState(SporeEffect.NOCUT);
    }

}
