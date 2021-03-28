package com.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Game {
  private List<Player> players = new ArrayList<Player>();

  public void readFile(Scanner sc) {
    System.out.print("Input file path: ");
    String path = sc.nextLine();

    try (BufferedReader br = new BufferedReader(new FileReader(path))){
      String line = br.readLine();
      LinkedHashMap<String, Integer> playerMap = new LinkedHashMap<String, Integer>();
      int maxPlayerId = 1;

      while (line != null) {
        String playerName = line.split(" ")[0];
        String pinsKnockedDown = line.split(" ")[1];

        if (!playerMap.containsKey(playerName)) {
          playerMap.put(playerName, maxPlayerId);
          players.add(new Player(playerName));
          maxPlayerId++;
        }

        System.out.println(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      System.out.println("Message: " + e.getMessage());
      e.printStackTrace();
    }
  }
  
}
