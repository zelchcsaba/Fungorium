package fungorium;

import java.util.ArrayList;
import java.util.List;

public class Mushroom {

    Tester t;
    private Tecton position;
    private List<Spore> spores;
    private FungalThread thread;
    private MushroomState state;
    private int shootedSporesCount;

    //kontruktor
    public Mushroom(Tester t){
        this.t=t;
        position = null;
        spores=new ArrayList<>();
        thread = null;
        state = MushroomState.UNEVOLVED;
        shootedSporesCount = 0;
    }

//getter settter
    public void setPosition(Tecton position){
        this.position=position;
    }

    public Tecton getPosition(){
        return position;
    }

    public void setThread(FungalThread thread){
        this.thread=thread;
    }

    public FungalThread getThread(){
        return thread;
    }

    public void setSpores(List<Spore> spores){
        this.spores=spores;
    }

    public List<Spore> getSpores(){
        return spores;
    }

    public void setShootedSporesCount(int shootedSporesCount){
        this.shootedSporesCount=shootedSporesCount;
    }

    public int getShootedSporesCount(){
        return shootedSporesCount;
    }

    public void setState(MushroomState s){
        state=s;
    }

    public MushroomState getState(){
        return state;
    }

    //To do
    public boolean shootSpore(Tecton t) {
        return true;
    }

//allapot beallitas
    public boolean evolve() {
        state=MushroomState.EVOLVED;
        return true;
    }

//spora hozzaadas
    public boolean generateSpore(Spore sp) {
        spores.add(sp);
        return true;
    }

}
