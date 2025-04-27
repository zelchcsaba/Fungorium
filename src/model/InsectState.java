package model;

/**
 * A SporeEffect enum a különböző spóra hatásokat reprezentálja,
 * amelyek alkalmazhatók rovarokra (Insect). Ezek az állapotok
 * meghatározzák, hogy a spóra milyen módon befolyásolja a rovart.
 * <p>
 * Enum értékek:
 * - SPEEDBOOST: A rovar mozgását gyorsító hatás.
 * - SLOWED: A rovar mozgását lassító hatás.
 * - PARALYZED: A rovar mozgását megbénító hatás.
 * - NOCUT: A rovar képtelenné válik fonalak elvágására.
 * - NORMAL: Nincs spóra hatás, a rovar normál állapotban van.
 */
public enum InsectState {
    SPEEDBOOST,
    SLOWED,
    PARALYZED,
    NOCUT,
    NORMAL,
    DIVIDED
}
