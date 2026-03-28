/*
   Terminal menus and game loop for Battleship.
   Core rules live in GameRules; AI in ComputerPlayer; saves in GamePersistence.
*/

import java.util.*;

public final class BattleshipConsole {

   private BattleshipConsole() {
   }

   private static int readInt(Scanner sc) {
      while (true) {
         String input = sc.nextLine().trim();
         try {
            return Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a whole number.");
         }
      }
   }

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      boolean quit = false;
      while (!quit) {

         char[][] playerBoard = GameRules.emptyBoard();
         char[][] computerBoard = GameRules.emptyBoard();
         int[] playerShipPartsLeft = { GameConstants.DESTROYER_SIZE, GameConstants.CRUISER_SIZE,
               GameConstants.SUBMARINE_SIZE, GameConstants.BATTLESHIP_SIZE, GameConstants.CARRIER_SIZE };
         int[] computerShipPartsLeft = { GameConstants.DESTROYER_SIZE, GameConstants.CRUISER_SIZE,
               GameConstants.SUBMARINE_SIZE, GameConstants.BATTLESHIP_SIZE, GameConstants.CARRIER_SIZE };
         boolean easyDifficulty = true;
         int[] lastHit = { -1, -1 };
         int[] firstHit = { -1, -1 };

         boolean userDeciding = true;
         boolean newGame = false;
         boolean gameOver = false;
         while (userDeciding) {
            ConsoleDisplay.printMenu();
            int userChoice = readInt(sc);
            ConsoleDisplay.clearConsole();
            switch (userChoice) {
               case 1:
                  ConsoleDisplay.clearConsole();
                  newGame = true;
                  userDeciding = false;
                  break;
               case 2:
                  ConsoleDisplay.clearConsole();
                  boolean boardLoaded = false;
                  while (!boardLoaded) {
                     System.out.print(
                           "Enter the name of the saved game (Enter 'exit' to exit back to main menu): ");
                     String filename = sc.nextLine() + ".txt";
                     if (filename.equals("exit.txt")) {
                        boardLoaded = true;
                     } else {
                        GamePersistence.LoadResult load = GamePersistence.tryLoad(filename, playerBoard,
                              computerBoard);
                        if (load.success) {
                           easyDifficulty = load.easyDifficulty;
                           boardLoaded = true;
                           userDeciding = false;
                        } else {
                           System.out.println("Error reading file or game cannot be found. Press ENTER to try again.");
                           sc.nextLine();
                           ConsoleDisplay.clearConsole();
                        }
                     }
                  }
                  GameRules.shipPartsLeft(playerShipPartsLeft, playerBoard);
                  GameRules.shipPartsLeft(computerShipPartsLeft, computerBoard);
                  break;
               case 3:
                  ConsoleDisplay.printInstruction();
                  System.out.println("Press ENTER to return to main menu.");
                  sc.nextLine();
                  ConsoleDisplay.clearConsole();
                  userDeciding = true;
                  break;
               case 4:
                  quit = true;
                  userDeciding = false;
                  gameOver = true;
                  ConsoleDisplay.clearConsole();
                  break;
               default:
                  ConsoleDisplay.clearConsole();
                  System.out.println("Invalid Input! Please try again.");
            }
         }

         if (newGame) {
            boolean validInput = false;
            while (!validInput) {
               System.out.println("""
                     +=======================================+
                     |           CHOOSE DIFFICULTY           |
                     |---------------------------------------|
                     |  1) Easy                              |
                     |  2) Normal                            |
                     +=======================================+
                     """);
               int input = readInt(sc);
               if (input == 1) {
                  easyDifficulty = true;
                  validInput = true;
               } else if (input == 2) {
                  easyDifficulty = false;
                  validInput = true;
               } else {
                  System.out.println("Invalid input. Press ENTER to try again.");
                  sc.nextLine();
                  ConsoleDisplay.clearConsole();
               }
            }
            for (int i = 0; i < playerShipPartsLeft.length; i++) {
               validInput = false;
               while (!validInput) {
                  ConsoleDisplay.clearConsole();
                  System.out.println("Player Board:");
                  ConsoleDisplay.displayShipBoard(playerBoard);
                  System.out.printf("""
                        +=======================================+
                        |    %-30s     |
                        |---------------------------------------|
                        |  1) Vertically                        |
                        |  2) Horizontally                      |
                        +=======================================+
                            """, "PLACING " + GameConstants.SHIP_NAMES[i] + " (" + playerShipPartsLeft[i] + ")");
                  int orientation = readInt(sc);
                  ConsoleDisplay.clearConsole();
                  ConsoleDisplay.displayShipBoard(playerBoard);
                  System.out.printf("""
                        +=======================================+
                        |    %-30s     |
                        |---------------------------------------|
                        |  Enter the column number (left most   |
                        |  square if horizontal)                |
                        +=======================================+
                        """, "PLACING " + GameConstants.SHIP_NAMES[i] + " (" + playerShipPartsLeft[i] + ")");
                  int column = readInt(sc);
                  ConsoleDisplay.displayShipBoard(playerBoard);
                  System.out.printf("""
                        +=======================================+
                        |    %-30s     |
                        |---------------------------------------|
                        |  Enter the row number (top square if  |
                        |  vertical)                            |
                        +=======================================+
                        """, "PLACING " + GameConstants.SHIP_NAMES[i] + " (" + playerShipPartsLeft[i] + ")");
                  int row = readInt(sc);
                  if (GameRules.validShipPlacement(row, column, orientation, playerShipPartsLeft[i], playerBoard)) {
                     validInput = true;
                     GameRules.placeShips(row, column, orientation, playerShipPartsLeft[i],
                           GameConstants.SHIP_SYMBOLS[i], playerBoard);
                  } else {
                     System.out.println("Invalid ship placement. Press ENTER to try again.");
                     sc.nextLine();
                  }
               }

            }
            GameRules.placeComputerShips(computerBoard, computerShipPartsLeft);
            ConsoleDisplay.displayShipBoard(computerBoard);
         }

         while (!gameOver) {
            ConsoleDisplay.displayShipAndShotBoard(playerBoard, computerBoard);
            ConsoleDisplay.displayShipBoard(computerBoard);
            boolean validInput = false;
            int input = 0;
            while (!validInput) {
               System.out.println("""
                     +=======================================+
                     |  1) Attack                            |
                     |  2) Save Game                         |
                     |  3) Surrender                         |
                     +=======================================+
                     """);
               input = readInt(sc);
               if (input != 1 && input != 2 && input != 3) {
                  System.out.println("Invalid Option. Press ENTER to try again.");
                  sc.nextLine();
               } else {
                  validInput = true;
               }
            }
            if (input == 1) {
               boolean validAttack = false;
               while (!validAttack) {
                  ConsoleDisplay.displayShipAndShotBoard(playerBoard, computerBoard);
                  ConsoleDisplay.displayShipBoard(computerBoard);
                  System.out.print("Enter the column number: ");
                  int col = readInt(sc);
                  System.out.print("Enter the row number: ");
                  int row = readInt(sc);

                  validAttack = GameRules.isValidAttack(row, col, computerBoard);
                  if (validAttack) {
                     System.out.println("Attacking (" + col + ", " + row + ")...\n");
                     GameRules.hitOrMiss(row, col, computerBoard, computerShipPartsLeft);
                     ConsoleDisplay.shipSunk(computerShipPartsLeft);
                     System.out.println("Player: ");
                     ConsoleDisplay.displayShipAndShotBoard(playerBoard, computerBoard);
                     System.out.println("Computer: ");
                     ConsoleDisplay.displayShipBoard(computerBoard);
                  } else {
                     System.out.println("Invalid attack. Press ENTER to try again.");
                     sc.nextLine();
                     ConsoleDisplay.clearConsole();
                  }
               }
               if (GameRules.isGameOver(computerShipPartsLeft)) {
                  gameOver = true;
                  System.out.println("You WIN! You have sunk all Computer ships!");
               } else {
                  System.out.println("Press ENTER to continue...");
                  sc.nextLine();
                  ConsoleDisplay.clearConsole();

                  if (easyDifficulty) {
                     ComputerPlayer.computerEasyAttack(playerBoard, playerShipPartsLeft);
                  } else {
                     ComputerPlayer.computerNormalAttack(playerBoard, playerShipPartsLeft, lastHit, firstHit);
                  }
                  gameOver = GameRules.isGameOver(playerShipPartsLeft);
                  if (gameOver) {
                     System.out.println("You LOSE! Computer has sunk all your ships! Here was the Computer's board:");
                  }
               }
            } else if (input == 2) {
               boolean gameSaved = false;
               while (!gameSaved) {
                  System.out.print("Enter a save name: ");
                  String filename = sc.nextLine();
                  gameSaved = GamePersistence.saveGame(filename, playerBoard, computerBoard, easyDifficulty);
               }
               gameOver = true;
               System.out.println("Game saved! Press ENTER to return to main menu.");
               sc.nextLine();
            } else {
               System.out.println("You have surrendered! Computer Wins! Here was the Computer's board: ");
               ConsoleDisplay.displayShipBoard(computerBoard);
               System.out.println("Press ENTER to return to main menu.");
               sc.nextLine();
               ConsoleDisplay.clearConsole();
               gameOver = true;
            }
         }
      }

      System.out.println("""
            +=======================================+
            |         THANK YOU FOR PLAYING!        |
            +=======================================+""");
   }
}
