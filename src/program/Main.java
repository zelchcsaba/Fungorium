package program;

import controller.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);

        System.out.println("test: Teszteles");
        System.out.println("play: Uj jatek kezdese");

        String input = scanner.nextLine();


        if (input.equals("test")) {
            boolean isTesting = true;
            while(isTesting){
                Controller clr = new Controller();
                System.out.println("\n\n");
                System.out.println("1: 0. kor tesztelese");
                System.out.println("2: Gombasz es rovarasz alap lepesei");
                System.out.println("3: Gombasz es rovarasz alap lepesei olyan esetekben, mikor nem tudja vegrehajtani");
                System.out.println("4: Fonalgyorsitas, 10 spora kiloves, bogareves");
                System.out.println("5: Gombatest novesztese, bogar gyorsitasa, LongLifeThread vagasa");
                System.out.println("6: Rovar SlowingSpore es NoCutSpore evese, es ennek hatasa a kovetkezo korben");
                System.out.println("7: Fonalak felszivodasa, tekton kettetorese, gombatest fejlodese");
                System.out.println("8: Rovar DividingSpore evese");
                System.out.println("9: LongLifeThread vagasa, rovar megevese mikor AbsorbingTecton-on van");
                System.out.println("10: Jatek befejezesenek leellenorzese");
                System.out.println("11: Kilepes");
    
                
                input = scanner.nextLine();
                switch (input) {
                    case "1":
                        clr.processCmd("runTest test1.txt");
                        clr.processCmd("assert test1assert.txt");
                        break;
                    case "2":
                        clr.processCmd("runTest test2.txt");
                        clr.processCmd("assert test2assert.txt");
                        break;
                    case "3":
                        clr.processCmd("runTest test3.txt");
                        clr.processCmd("assert test3assert.txt");
                        break;
                    case "4":
                        clr.processCmd("runTest test4.txt");
                        clr.processCmd("assert test4assert.txt");
                        break;
                    case "5":
                        clr.processCmd("runTest test5.txt");
                        clr.processCmd("assert test5assert.txt");
                        break;
                    case "6":
                        clr.processCmd("runTest test6.txt");
                        clr.processCmd("assert test6assert.txt");
                        break;
                    case "7":
                        clr.processCmd("runTest test7.txt");
                        clr.processCmd("assert test7assert.txt");
                        break;
                    case "8":
                        clr.processCmd("runTest test8.txt");
                        clr.processCmd("assert test8assert.txt");
                        break;
                    case "9":
                        clr.processCmd("runTest test9.txt");
                        clr.processCmd("assert test9assert.txt");
                        break;
                    case "10":
                        clr.processCmd("runTest test10.txt");
                        clr.processCmd("assert test10assert.txt");
                        break;
                    case "11":
                        isTesting = false;
                        break;
                }
            }

           
        } else if (input.equals("play")) {
            Controller clr = new Controller();
            boolean isPlay = true;
            while (isPlay) {
                input = scanner.nextLine();
                if (input.equals("exit")) {
                    isPlay = false;
                } else {
                    clr.processCmd(input);
                }
            }
        }

    }
}
