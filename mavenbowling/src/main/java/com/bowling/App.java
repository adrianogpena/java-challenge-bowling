package com.bowling;

import java.util.Scanner;

import com.board.Game;

public class App {
    public static void main( String[] args ) {

      Game game = new Game();

      Scanner sc = new Scanner(System.in);
      game.readFile(sc);
      sc.close();

      // Calculate the game score
      game.calcScore();

      // Print the scoreboard
      game.printPlayers();
    }
}
