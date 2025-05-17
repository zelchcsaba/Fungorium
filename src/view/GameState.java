package view;

/**
 * A GameState enum a játékban előforduló különböző állapotokat
 * reprezentálja. Ez az osztály a játék logikai folyamatainak sorrendjét és
 * eseményeit kezeli, amely alapvető elem a játékvezérlésben.
 */
public enum GameState {
    PUTFIRSTINSECT,
    PUTFIRSTMUSHROOM,
    WAITFUNGALCOMMAND,
    WAITINSECTCOMMAND,
    SELECTINSECTFORMOVE, MOVEINSECT,
    SELECTINSECTFORCUT, CUTTHREAD,
    BRANCHTHREAD,
    EATINSECT,
    SELECTMUSHROOMFORSHOOT, SHOOTSPORE,
    GROWMUSHROOM,
    TECTON_INFO
}
