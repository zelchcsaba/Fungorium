package fungorium;

public class Fungorium {
    public static void main(String[] args) {
        Tester t = new Tester();
        //t.unevolvedShootSpore();
        System.out.println("\n\n");
        //t.putFirstMushroomTrue();
        System.out.println("\n\n");
        t.putFirstMushroomFalseAbsorb();
        System.out.println("\n\n");
        t.putFirstMushroomFalseIsMush();
        System.out.println("\n\n");
        t.unevolvedShootSporeTrue();
        System.out.println("\n\n");
        t.unevolvedShootSporeFalse();
        System.out.println("\n\n");
        t.simpleFungalThreadBranchingFalse();
        System.out.println("\n\n");
        t.simpleFungalThreadBranchingTrue();
    }
}
