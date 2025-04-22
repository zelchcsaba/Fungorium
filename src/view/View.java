package view;

import java.util.Random;

public class View implements IView{

    Random rand = new Random();

    public int randomize(){
        return rand.nextInt(1000);
    }
}
