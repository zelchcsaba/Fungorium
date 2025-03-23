package fungorium;

import java.util.Scanner;

public class Fungorium {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Tester t = new Tester();

        boolean loop = true;
        while (loop == true) {
            System.out.println("1 - Első gombatest lehelyezése olyan tektonra, amelyre le lehet helyezni");
            System.out.println("2 - Első gombatest lehelyezése olyan tektonra, amelyre nem lehet lehelyezni (AbsorbingTecton)");
            System.out.println("3 - Első gombatest lehelyezése olyan tektonra, amelyre nem lehet lehelyezni (van gombatest a tektonon)");
            System.out.println("4 - Fejletlen gombatest spóra szórása olyan tektonra, amelyre tud");
            System.out.println("5 - Fejlett gombatest spóra szórása olyan tektonra, amelyre tud");
            System.out.println("6 - Sikertelen spóraszórás, mert nem szomszédos a céltekton");
            System.out.println("7 - Gombafonál sikeres elágaztatása tektonra");
            System.out.println("8 - Gombafonál sikertelen elágaztatása egyfonalas tektonra, mert már van rajta");
            System.out.println("9 - Új spóra termelődése");
            System.out.println("10 - Gombatest elpusztulása");
            System.out.println("11 - Gombatesthez nem kapcsolódó fonálrész eltávolítása");
            System.out.println("12 - Tekton kettétörése");
            System.out.println("13 - Tekton kettétörése, ha a tektonon gombatest van");
            System.out.println("14 - Fonál felszívódása tektonról");
            System.out.println("15 - Gombafonál elvágása");
            System.out.println("16 - Rovar lehelyezése tektonra sikeres");
            System.out.println("17 - Rovar lehelyezése tektonra sikertelen");
            System.out.println("18 - Rovar mozgása és spóraevése");
            System.out.println("19 - Rovar mozgása sikertelen");
            System.out.println("20 - Új gombatest lerakása");
            System.out.println("21 - Gombafonal elágazása olyan tektonra, ahol van spóra");
            System.out.println("22 - KILÉPÉS");
            System.out.print("Kérek egy számot: ");

            int select = scanner.nextInt();
            System.out.println();
            switch (select) {
                case 1:
                    t.putFirstMushroomTrue();
                    System.out.println("\n\n");
                    break;
                case 2:
                    t.putFirstMushroomFalseAbsorb();
                    System.out.println("\n\n");
                    break;
                case 3:
                    t.putFirstMushroomFalseIsMush();
                    System.out.println("\n\n");
                    break;
                case 4:
                    t.unevolvedShootSporeTrue();
                    System.out.println("\n\n");
                    break;
                case 5:
                    t.evolvedShootSporeTrue();
                    System.out.println("\n\n");
                    break;
                case 6:
                    t.unevolvedShootSporeFalse();
                    System.out.println("\n\n");
                    break;
                case 7:
                    t.simpleFungalThreadBranchingTrue();
                    System.out.println("\n\n");
                    break;
                case 8:
                    t.simpleFungalThreadBranchingFalse();
                    System.out.println("\n\n");
                    break;
                case 9:
                    //Új spóra termelődése
                    System.out.println("\n\n");
                break;
                case 10:
                    t.mushroom_die();
                    System.out.println("\n\n");
                    break;
                case 11:
                    t.delete_Unnecessary_Threads();
                    System.out.println("\n\n");
                break;
                case 12:
                    t.break_tecton();
                    System.out.println("\n\n");
                break;
                case 13:
                    t.break_tecton_with_mushroom();
                    System.out.println("\n\n");
                break;
                case 14:
                    t.absorb_from_tecton();
                    System.out.println("\n\n");
                break;
                case 15:
                    t.cut_fungalthread();
                    System.out.println("\n\n");
                break;
                case 17:

                break;
                case 18:

                break;
                case 19:

                break;
                case 20:
                    t.growingNewMushroom();
                    System.out.println("\n\n");
                break;
                case 21:
                    t.fungalThreadBranching();
                    System.out.println("\n\n");
                break;
                case 22:
                    loop = false;
                break;
            }
        }
    }
}
