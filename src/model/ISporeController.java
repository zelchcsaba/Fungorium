package model;

/**
 * Az ISporeController interfész egy egységes metódust biztosít a spóra osztályok számára.
 * Ez az interfész a spóra fonalának lekérdezésére alkalmas metódust definiál.
 */
public interface ISporeController {
    FungalThread getThread();
}
