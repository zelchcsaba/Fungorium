package controller;
import model.IMushroomController;

public class mushroomAssotiation {
    private IMushroomController mushroom;
    private int age;

    public mushroomAssotiation(){
        mushroom = null;
        age = 0;
    }

    public IMushroomController getMushroom(){
        return mushroom;
    }

    public void setMushroom(IMushroomController m){
        mushroom = m;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int a){
        age = a;
    }
}
