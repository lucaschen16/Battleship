/*
   Board setup, move validation, hits/misses, and fleet state — no menus or save files.
*/

public final class GameRules {

   private GameRules() {
   }

   public static boolean isValidAttack(int row, int col, char[][] board) {
      if (row > 10 || row < 1) {
         System.out.println("Invalid row number! Press ENTER to try again.");
         return false;
      }
      if (col > 10 || col < 1) {
         System.out.println("Invalid col number! Press ENTER to try again.");
         return false;
      }
      if (board[row - 1][col - 1] == GameConstants.HIT || board[row - 1][col - 1] == GameConstants.MISS) {
         System.out.println("Cannot attack the same spot twice! Press ENTER to try again.");
         return false;
      }
      return true;
   }

   public static boolean isValidAttackSilent(int row, int col, char[][] board) {
      if (row > 10 || row < 1) {
         return false;
      }
      if (col > 10 || col < 1) {
         return false;
      }
      return board[row - 1][col - 1] != GameConstants.HIT && board[row - 1][col - 1] != GameConstants.MISS;
   }

   public static boolean isGameOver(int[] shipsLeft) {
      boolean gameOver = true;
      for (int i = 0; i < shipsLeft.length; i++) {
         if (shipsLeft[i] > 0) {
            gameOver = false;
            break;
         }
      }
      return gameOver;
   }

   public static void shipPartsLeft(int[] partsLeft, char[][] board) {
      for (int i = 0; i < partsLeft.length; i++) {
         partsLeft[i] = 0;
      }
      for (int i = 0; i < GameConstants.SIZE; i++) {
         for (int j = 0; j < GameConstants.SIZE; j++) {
            if (board[i][j] == GameConstants.DESTROYER) {
               partsLeft[GameConstants.DESTROYER_IDX] += 1;
            } else if (board[i][j] == GameConstants.CRUISER) {
               partsLeft[GameConstants.CRUISER_IDX] += 1;
            } else if (board[i][j] == GameConstants.SUBMARINE) {
               partsLeft[GameConstants.SUBMARINE_IDX] += 1;
            } else if (board[i][j] == GameConstants.BATTLESHIP) {
               partsLeft[GameConstants.BATTLESHIP_IDX] += 1;
            } else if (board[i][j] == GameConstants.CARRIER) {
               partsLeft[GameConstants.CARRIER_IDX] += 1;
            }
         }
      }
   }

   public static boolean hitOrMiss(int row, int col, char[][] board, int[] shipPartsLeft) {
      if (board[row - 1][col - 1] != GameConstants.EMPTY) {
         System.out.println("HIT!");
         if (board[row - 1][col - 1] == GameConstants.DESTROYER) {
            shipPartsLeft[GameConstants.DESTROYER_IDX] -= 1;
         } else if (board[row - 1][col - 1] == GameConstants.CRUISER) {
            shipPartsLeft[GameConstants.CRUISER_IDX] -= 1;
         } else if (board[row - 1][col - 1] == GameConstants.BATTLESHIP) {
            shipPartsLeft[GameConstants.BATTLESHIP_IDX] -= 1;
         } else if (board[row - 1][col - 1] == GameConstants.SUBMARINE) {
            shipPartsLeft[GameConstants.SUBMARINE_IDX] -= 1;
         } else {
            shipPartsLeft[GameConstants.CARRIER_IDX] -= 1;
         }
         board[row - 1][col - 1] = GameConstants.HIT;
         return true;
      } else {
         System.out.println("MISS!");
         board[row - 1][col - 1] = GameConstants.MISS;
         return false;
      }
   }

   public static boolean validShipPlacement(int row, int col, int orientation, int size, char[][] board) {
      if (orientation != 1 && orientation != 2) {
         System.out.println("Invalid orientation! Press ENTER to try again.");
         return false;
      }
      if (orientation == 1) {
         if (row + size - 1 > GameConstants.SIZE || row < 1) {
            System.out.println("Invalid row number! Press ENTER to try again.");
            return false;
         }
         if (col > GameConstants.SIZE || col < 1) {
            System.out.println("Invalid column number! Press ENTER to try again.");
            return false;
         }
         for (int i = row - 1; i < row - 1 + size; i++) {
            if (board[i][col - 1] != GameConstants.EMPTY) {
               System.out.println("Cannot place ship on another ship! Press ENTER to try again.");
               return false;
            }
         }
      } else if (orientation == 2) {
         if (col + size - 1 > GameConstants.SIZE || col < 1) {
            System.out.println("Invalid column number! Press ENTER to try again.");
            return false;
         }
         if (row > GameConstants.SIZE || row < 1) {
            System.out.println("Invalid row number! Press ENTER to try again.");
            return false;
         }
         for (int i = col - 1; i < col - 1 + size; i++) {
            if (board[row - 1][i] != GameConstants.EMPTY) {
               System.out.println("Cannot place ship on another ship! Press ENTER to try again.");
               return false;
            }
         }
      }
      return true;
   }

   public static void placeShips(int row, int col, int orientation, int size, char symbol, char[][] board) {
      if (orientation == 1) {
         for (int i = row - 1; i < row - 1 + size; i++) {
            board[i][col - 1] = symbol;
         }
      } else {
         for (int i = col - 1; i < col - 1 + size; i++) {
            board[row - 1][i] = symbol;
         }
      }
   }

   public static void placeComputerShips(char[][] computerBoard, int[] shipSizes) {
      int row;
      int col;
      for (int i = 0; i < 5; i++) {
         int orientation = (int) (Math.random() * 2) + 1;
         if (orientation == 1) {
            row = (int) (Math.random() * (10 - shipSizes[i])) + 1;
            col = (int) (Math.random() * 10) + 1;
         } else {
            col = (int) (Math.random() * (10 - shipSizes[i])) + 1;
            row = (int) (Math.random() * 10) + 1;
         }
         while (!validShipPlacement(row, col, orientation, shipSizes[i], computerBoard)) {
            if (orientation == 1) {
               row = (int) (Math.random() * (10 - shipSizes[i])) + 1;
               col = (int) (Math.random() * 10) + 1;
            } else {
               col = (int) (Math.random() * (10 - shipSizes[i])) + 1;
               row = (int) (Math.random() * 10) + 1;
            }
         }
         placeShips(row, col, orientation, shipSizes[i], GameConstants.SHIP_SYMBOLS[i], computerBoard);
      }
   }

   public static char[][] emptyBoard() {
      char[][] board = new char[GameConstants.SIZE][GameConstants.SIZE];
      for (int i = 0; i < GameConstants.SIZE; i++) {
         for (int j = 0; j < GameConstants.SIZE; j++) {
            board[i][j] = GameConstants.EMPTY;
         }
      }
      return board;
   }
}
