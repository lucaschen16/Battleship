/*
   AI attack strategies for the computer opponent.
*/

public final class ComputerPlayer {

   private ComputerPlayer() {
   }

   public static void computerEasyAttack(char[][] playerBoard, int[] shipPartsLeft) {
      int row = 0;
      int col = 0;
      boolean validAttack = false;
      while (!validAttack) {
         row = (int) (Math.random() * 10) + 1;
         col = (int) (Math.random() * 10) + 1;
         validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
      }
      System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
      GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft);
      ConsoleDisplay.shipSunkByComputer(shipPartsLeft);
   }

   public static void computerNormalAttack(char[][] playerBoard, int[] shipPartsLeft, int[] lastHit,
         int[] firstHit) {
      int row = 0;
      int col = 0;
      if (lastHit[0] == -1) {
         boolean validAttack = false;
         while (!validAttack) {
            row = (int) (Math.random() * 10) + 1;
            col = (int) (Math.random() * 10) + 1;
            validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
         }
         System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
         if (GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft)) {
            lastHit[0] = row;
            lastHit[1] = col;
            firstHit[0] = col;
            firstHit[1] = row;
         }
      } else {
         boolean validAttack = false;
         while (!validAttack) {
            row = lastHit[0];
            col = lastHit[1];
            if (GameRules.isValidAttackSilent(row + 1, col, playerBoard) || GameRules.isValidAttackSilent(row - 1,
                  col, playerBoard)
                  || GameRules.isValidAttackSilent(row, col + 1, playerBoard)
                  || GameRules.isValidAttackSilent(row, col - 1, playerBoard)) {
               int computerGuess = (int) (Math.random() * 4) + 1;
               switch (computerGuess) {
                  case 1:
                     row += 1;
                     validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
                     if (validAttack) {
                        System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
                        if (GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft)) {
                           if (ConsoleDisplay.shipSunkByComputer(shipPartsLeft)) {
                              lastHit[0] = -1;
                              lastHit[1] = -1;
                           } else {
                              lastHit[0] = row;
                           }
                        }
                     }
                     break;
                  case 2:
                     row -= 1;
                     validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
                     if (validAttack) {
                        System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
                        if (GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft)) {
                           if (ConsoleDisplay.shipSunkByComputer(shipPartsLeft)) {
                              lastHit[0] = -1;
                              lastHit[1] = -1;
                           } else {
                              lastHit[0] = row;
                           }
                        }
                     }
                     break;
                  case 3:
                     col += 1;
                     validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
                     if (validAttack) {
                        System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
                        if (GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft)) {
                           if (ConsoleDisplay.shipSunkByComputer(shipPartsLeft)) {
                              lastHit[0] = -1;
                              lastHit[1] = -1;
                           } else {
                              lastHit[1] = col;
                           }
                        }
                     }
                     break;
                  case 4:
                     col -= 1;
                     validAttack = GameRules.isValidAttackSilent(row, col, playerBoard);
                     if (validAttack) {
                        System.out.println("Computer attacks (" + col + ", " + row + ")...\n");
                        if (GameRules.hitOrMiss(row, col, playerBoard, shipPartsLeft)) {
                           if (ConsoleDisplay.shipSunkByComputer(shipPartsLeft)) {
                              lastHit[0] = -1;
                              lastHit[1] = -1;
                           } else {
                              lastHit[1] = col;
                           }
                        }
                     }
                     break;
               }
            } else {
               lastHit[0] = firstHit[0];
               lastHit[1] = firstHit[1];
            }
         }
      }
   }
}
