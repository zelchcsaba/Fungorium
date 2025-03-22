public class Insect {

    Tester t;
    private Tecton position;
    private SporeEffect state;

    public Insect(Tester t){
        this.t = t;
        position = null;
        state = NORMAL;
    }
    //getter setter
    public void setInsect(Tecton t){
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



    public boolean move(Tecton t) {}
    public boolean cut(Tecton t) {}
    public void setState(Spore sp) {}

}
