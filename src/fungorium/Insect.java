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
    public void setTecton(Tecton t){
        position = t;
    }

    public Tecton getTecton(){
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
