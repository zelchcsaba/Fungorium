package fungorium;

import java.util.Scanner;

public class Fungorium {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Tester t = new Tester();

        boolean loop = true;
        while (loop == true) {
            System.out.println("Kell egy szam");
            int select = scanner.nextInt();
            switch (select) {
                case 1:
                    t.unevolvedShootSpore();
                    System.out.println("\n\n");
                    break;
                case 2:
                    t.putFirstMushroomTrue();
                    System.out.println("\n\n");
                    break;
                case 3:
                    t.putFirstMushroomFalseAbsorb();
                    System.out.println("\n\n");
                    break;
                case 4:
                    t.absorb_form_tecton();
                    System.out.println("\n\n");
                    break;
                case 5:
                    t.break_tecton();
                    System.out.println("\n\n");
                    break;
                case 6:
                    t.delete_Unnecessary_Threads();
                    System.out.println("\n\n");
                    break;
                case 7:
                    t.break_tecton_with_mushroom();
                    System.out.println("\n\n");
                    break;
                case 8:
                    loop = false;
                    break;
            }
        }
    }
}
