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
        String playerName = line.split(" ")[0];

        // if its a foul play, it will be saved as -1, but the score will count as 0
        int pinsKnockedDown = (line.split(" ")[1].toUpperCase() == "F") ? -1 : Integer.parseInt(line.split(" ")[1]);

        if (!playerMap.containsKey(playerName)) {
          playerMap.put(playerName, maxPlayerId);
          players.add(new Player(playerName));
          maxPlayerId++;
        }

        Player player = getCurrentPlayer(playerName);
        
        player.roll(pinsKnockedDown);

        System.out.println(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      System.out.println("Message: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private Player getCurrentPlayer(String name) {
    return players.get(playerMap.get(name));
  }
  
}
