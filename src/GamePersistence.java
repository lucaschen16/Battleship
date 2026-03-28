/*
   Save / load game files (.txt format used by the console game).
*/

import java.io.*;

public final class GamePersistence {

   private GamePersistence() {
   }

   public static final class LoadResult {
      public boolean success;
      public boolean easyDifficulty;
   }

   public static LoadResult tryLoad(String filename, char[][] playerBoard, char[][] computerBoard) {
      LoadResult r = new LoadResult();
      r.success = false;
      try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
         String input;
         reader.readLine();
         for (int i = 0; i < GameConstants.SIZE; i++) {
            input = reader.readLine();
            for (int j = 0; j < GameConstants.SIZE; j++) {
               playerBoard[i][j] = input.charAt(j);
            }
         }
         reader.readLine();
         for (int i = 0; i < GameConstants.SIZE; i++) {
            input = reader.readLine();
            for (int j = 0; j < GameConstants.SIZE; j++) {
               computerBoard[i][j] = input.charAt(j);
            }
         }
         input = reader.readLine();
         r.easyDifficulty = input != null && input.equals("easy");
         r.success = true;
      } catch (IOException iox) {
         r.success = false;
      }
      return r;
   }

   public static boolean saveGame(String filename, char[][] playerBoard, char[][] computerBoard,
         boolean difficulty) {
      try {
         BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".txt"));
         writer.write("Player Board: \n");
         for (int i = 0; i < GameConstants.SIZE; i++) {
            for (int j = 0; j < GameConstants.SIZE; j++) {
               writer.write(playerBoard[i][j]);
            }
            writer.newLine();
         }
         writer.write("Computer Board: \n");
         for (int i = 0; i < GameConstants.SIZE; i++) {
            for (int j = 0; j < GameConstants.SIZE; j++) {
               writer.write(computerBoard[i][j]);
            }
            writer.newLine();
         }
         if (difficulty) {
            writer.write("easy");
         } else {
            writer.write("normal");
         }
         writer.close();
         return true;
      } catch (IOException iox) {
         System.out.println("Error saving game. Try again.");
         return false;
      }
   }
}
