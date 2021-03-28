package com.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Game {
  private List<Player> players;
  private LinkedHashMap<String, Integer> playerMap;

  public Game() {
    players = new ArrayList<Player>();
    playerMap = new LinkedHashMap<String, Integer>();
  }

  public void readFile(Scanner sc) {
    System.out.print("Input file path: ");
    String path = sc.nextLine();

    try (BufferedReader br = new BufferedReader(new FileReader(path))){
      String line = br.readLine();
      int maxPlayerId = 0;

      while (line != null) {
        System.out.println("=============================================");
        String playerName = line.split(" ")[0];
        int pinsKnockedDown;
        boolean foul = false;
        
        // if its a foul play, it will be saved as -1, but the score will count as 0
        if (line.split(" ")[1].toUpperCase().equals("F")) {
          pinsKnockedDown = 0;
          foul = true;

        } else {
          pinsKnockedDown = Integer.parseInt(line.split(" ")[1]);
        }

        System.out.println(playerName + " Turn: Pins " + pinsKnockedDown);

        if (!playerMap.containsKey(playerName)) {
          System.out.println("New player on the board!");
          playerMap.put(playerName, maxPlayerId);
          players.add(new Player(playerName));
          maxPlayerId++;
        }

        Player player = getCurrentPlayer(playerName);
        System.out.println("Player " + player.getName() + " selected!");
        
        player.roll(pinsKnockedDown, foul);

        line = br.readLine();
      }
    } catch (IOException e) {
      System.out.println("Message: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void printPlayers() {
    for (Player player : players) {
      System.out.println(player);
    }
  }

  private Player getCurrentPlayer(String name) {
    return players.get(playerMap.get(name));
  }
  
}
