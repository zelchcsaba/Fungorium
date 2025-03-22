package fungorium;

public class Insect {

    Tester t;
    private Tecton position;
    private SporeEffect state;

    //konstruktor
    public Insect(Tester t){
        this.t = t;
        position = null;
        state = SporeEffect.NORMAL;
    }

    //getter setter
    public void setPosition(Tecton t){
        position = t;
        this.t.toCall("setPosition");
        this.t.returnValue.clear();
        this.t.toReturn();
    }

    public Tecton getPosition(){
        return position;
    }

    public void setState(SporeEffect s){
        state = s;
    }

    public SporeEffect getState(){
        return state;
    }


 //To do
    public boolean move(Tecton t) {
        return true;
    }

    //To do
    public boolean cut(Tecton t) {
        return true;
    }

}
