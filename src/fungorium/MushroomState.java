package fungorium;

/**
 * A MushroomState a gomba fejlődési állapotainak reprezentálására szolgál.
 *
 * Az alábbi állapotokat különbözteti meg:
 * - **UNEVOLVED**: A gomba kezdeti, fejletlen állapota.
 * - **EVOLVED**: A gomba fejlett, átalakult állapota.
 *
 * Ezt az osztályt különböző gomba-alapú logikákhoz lehet felhasználni,
 * ahol jelentősége van a gombák fejlődési szakaszainak.
 */
public enum MushroomState {
    UNEVOLVED, EVOLVED
}
