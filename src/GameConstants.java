/*
   Shared constants for Battleship (console and future GUI).
*/

public final class GameConstants {

   private GameConstants() {
   }

   public static final int SIZE = 10;

   public static final char HIT = 'X';
   public static final char MISS = 'O';
   public static final char EMPTY = '-';

   public static final char DESTROYER = 'D';
   public static final char CRUISER = 'C';
   public static final char SUBMARINE = 'S';
   public static final char BATTLESHIP = 'B';
   public static final char CARRIER = 'A';

   public static final int DESTROYER_IDX = 0;
   public static final int CRUISER_IDX = 1;
   public static final int SUBMARINE_IDX = 2;
   public static final int BATTLESHIP_IDX = 3;
   public static final int CARRIER_IDX = 4;

   public static final int DESTROYER_SIZE = 2;
   public static final int CRUISER_SIZE = 3;
   public static final int SUBMARINE_SIZE = 3;
   public static final int BATTLESHIP_SIZE = 4;
   public static final int CARRIER_SIZE = 5;

   public static final String[] SHIP_NAMES = { "DESTROYER", "CRUISER", "SUBMARINE", "BATTLESHIP",
         "AIRCRAFT CARRIER" };
   public static final char[] SHIP_SYMBOLS = { DESTROYER, CRUISER, SUBMARINE, BATTLESHIP, CARRIER };
}
