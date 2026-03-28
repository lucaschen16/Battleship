/*
   Console-only menus and board rendering.
*/

public final class ConsoleDisplay {

   private ConsoleDisplay() {
   }

   public static void clearConsole() {
      for (int i = 0; i < 15; i++) {
         System.out.println();
      }
   }

   public static void printMenu() {
      System.out.println("""
            +=======================================+
            |         WELCOME TO BATTLESHIP         |
            |---------------------------------------|
            |  1) New Game                          |
            |  2) Load Saved Game                   |
            |  3) Show Instructions                 |
            |  4) Quit                              |
            +=======================================+""");
   }

   public static void displayShipBoard(char[][] board) {
      for (int i = 1; i <= GameConstants.SIZE; i++) {
         System.out.printf("%4s", i);
      }
      System.out.println();
      for (int i = 0; i < GameConstants.SIZE; i++) {
         System.out.printf("%2d ", i + 1);
         for (int j = 0; j < GameConstants.SIZE; j++) {
            System.out.printf("%-4s", board[i][j]);
         }
         System.out.println();
      }
      System.out.println();
   }

   public static void displayShipAndShotBoard(char[][] ship, char[][] shots) {
      System.out.println("Ship Board:                                     Shots Board:");
      for (int i = 1; i <= GameConstants.SIZE; i++) {
         System.out.printf("%4s", i);
      }
      System.out.print("\t");

      for (int i = 1; i <= GameConstants.SIZE; i++) {
         System.out.printf("%4s", i);
      }
      System.out.println();

      for (int i = 0; i < GameConstants.SIZE; i++) {
         System.out.printf("%2d ", i + 1);
         for (int j = 0; j < GameConstants.SIZE; j++) {
            System.out.printf("%-4s", ship[i][j]);
         }
         System.out.print("     ");
         System.out.printf("%2d ", i + 1);
         for (int j = 0; j < GameConstants.SIZE; j++) {
            if (shots[i][j] == GameConstants.HIT || shots[i][j] == GameConstants.MISS) {
               System.out.printf("%-4s", shots[i][j]);
            } else {
               System.out.printf("%-4s", GameConstants.EMPTY);
            }
         }
         System.out.println();
      }
   }

   public static void printInstruction() {
      System.out.println("""
            +=======================================+
            |             INSTRUCTIONS              |
            |---------------------------------------|
            |  In this game of Battleship against   |
            |  a computer, the first to sink all    |
            |  opponent ships is victorious. The    |
            |  player can choose between an easy    |
            |  and normal difficulty. In easy, the  |
            |  AI will always attack random squares |
            |  on the board. In normal, once the AI |
            |  lands a random hit, it will begin to |
            |  attack squares around the hit. To    |
            |  start the game the player will place |
            |  5 ships of varying sizes on a 10x10  |
            |  board. An aircraft carrier is 5      |
            |  squares long, a  battleship is 4     |
            |  squares long, both a submarine and a |
            |  cruiser are 3 squares long and a     |
            |  destroyer is 2 squares long. These   |
            |  can be placed vertically or          |
            |  horizontally. Once in play, the      |
            |  player will choose to attack a       |
            |  square. The game will tell whether   |
            |  it was a hit or miss. The player     |
            |  and the AI will continue to          |
            |  alternate turns until the player or  |
            |  the AI's ships have  all been sunk.  |
            +=======================================+
            """);
   }

   public static void shipSunk(int[] shipsLeft) {
      if (shipsLeft[GameConstants.DESTROYER_IDX] == 0) {
         System.out.println("You have sunk Computer's Destroyer!");
         shipsLeft[GameConstants.DESTROYER_IDX] = -1;
      } else if (shipsLeft[GameConstants.CRUISER_IDX] == 0) {
         System.out.println("You have sunk Computer's Cruiser!");
         shipsLeft[GameConstants.CRUISER_IDX] = -1;
      } else if (shipsLeft[GameConstants.SUBMARINE_IDX] == 0) {
         System.out.println("You have sunk Computer's Submarine!");
         shipsLeft[GameConstants.SUBMARINE_IDX] = -1;
      } else if (shipsLeft[GameConstants.BATTLESHIP_IDX] == 0) {
         System.out.println("You have sunk Computer's Battleship!");
         shipsLeft[GameConstants.BATTLESHIP_IDX] = -1;
      } else if (shipsLeft[GameConstants.CARRIER_IDX] == 0) {
         System.out.println("You have sunk Computer's Aircraft Carrier!");
         shipsLeft[GameConstants.CARRIER_IDX] = -1;
      }
   }

   public static boolean shipSunkByComputer(int[] shipsLeft) {
      if (shipsLeft[GameConstants.DESTROYER_IDX] == 0) {
         System.out.println("Computer has sunk your Destroyer!");
         shipsLeft[GameConstants.DESTROYER_IDX] = -1;
         return true;
      } else if (shipsLeft[GameConstants.CRUISER_IDX] == 0) {
         System.out.println("Computer has sunk your Cruiser");
         shipsLeft[GameConstants.CRUISER_IDX] = -1;
         return true;
      } else if (shipsLeft[GameConstants.SUBMARINE_IDX] == 0) {
         System.out.println("Computer has sunk your Submarine!");
         shipsLeft[GameConstants.SUBMARINE_IDX] = -1;
         return true;
      } else if (shipsLeft[GameConstants.BATTLESHIP_IDX] == 0) {
         System.out.println("Computer has sunk your Battleship!");
         shipsLeft[GameConstants.BATTLESHIP_IDX] = -1;
         return true;
      } else if (shipsLeft[GameConstants.CARRIER_IDX] == 0) {
         System.out.println("Computer has sunk your Aircraft Carrier!");
         shipsLeft[GameConstants.CARRIER_IDX] = -1;
         return true;
      }
      return false;
   }
}
