package program;
import controller.*;

public class Main {
    public static void main(String[] args){
        Controller clr = new Controller();
        clr.processCmd("runTest test.txt");
    }
}
