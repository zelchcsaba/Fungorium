package fungorium;

import java.util.Scanner;

public class Fungorium {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Tester t = new Tester();

        boolean loop = true;
        while (loop == true) {
            System.out.println("7 - Fonál felszívódása tektonról");
            System.out.println("8 - Tekton kettétörése");
            System.out.println("9 - Tekton kettétörése, ha a tektonon gombatest van");
            System.out.println("10 - Gombatesthez nem kapcsolódó fonálrész eltávolítása");
            System.out.println("11 - Gombatest elpusztulása");
            System.out.println("12 - Gombafonál elvágása");
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
                    t.putFirstMushroomFalseIsMush();
                    System.out.println("\n\n");
                    break;
                case 5:
                    t.unevolvedShootSporeTrue();
                    System.out.println("\n\n");
                    break;
                case 6:
                    t.evolvedShootSporeTrue();
                    System.out.println("\n\n");
                    break;
                case 7:
                    t.absorb_form_tecton();
                    System.out.println("\n\n");
                    break;
                case 8:
                    t.break_tecton();
                    System.out.println("\n\n");
                    break;
                case 9:
                    t.break_tecton_with_mushroom();
                    System.out.println("\n\n");
                break;
                case 10:
                    t.delete_Unnecessary_Threads();
                    System.out.println("\n\n");
                    break;
                case 11:
                    t.mushroom_die();
                    System.out.println("\n\n");
                break;
                case 12:
                    t.cut_fungalthread();
                    System.out.println("\n\n");
                break;
                case 13:
                    loop = false;
                    break;
            }
        }
    }
}
