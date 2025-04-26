package program;
import controller.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Controller clr = new Controller();
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            clr.processCmd(input);
        }
        

        
    }
}
