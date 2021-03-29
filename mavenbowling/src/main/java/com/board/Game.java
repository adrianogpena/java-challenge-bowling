package com.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

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
        // System.out.println("=============================================");
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

        // System.out.println(playerName + " Turn: Pins " + pinsKnockedDown);

        if (!playerMap.containsKey(playerName)) {
          // System.out.println("New player on the board!");
          playerMap.put(playerName, maxPlayerId);
          players.add(new Player(playerName));
          maxPlayerId++;
        }

        Player player = getPlayer(playerName);
        // System.out.println("Player " + player.getName() + " selected!");
        
        player.roll(pinsKnockedDown, foul);

        line = br.readLine();
      }
    } catch (IOException e) {
      System.out.println("Message: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void calcScore() {
    for (String name : playerMap.keySet()) {
      Player player = getPlayer(name);
      Frame frame;
      int score = 0;

      // Calc frames from 1 to 8
      for (int i = 0; i < Player.MAX_FRAMES - 2; i++) {
        frame = player.getFrame(i);
        score += frame.getRoll(0) + frame.getRoll(1);

        if (frame.getIsSpare()) {
          score += player.getFrame(i + 1).getRoll(0);
        }

        if (frame.getIsStrike()) {
          if (player.getFrame(i + 1).getIsStrike()) {
            score += player.getFrame(i + 1).getRoll(0) + player.getFrame(i + 2).getRoll(0);
          } else {
            score += player.getFrame(i + 1).getRoll(0) + player.getFrame(i + 1).getRoll(1);
          }
        }

        frame.setScore(score);
      }

      // Calc frame 9
      frame = player.getFrame(Player.MAX_FRAMES - 2);
      score += frame.getRoll(0) + frame.getRoll(1);

      if (frame.getIsSpare()) {
        score += player.getFrame(Player.MAX_FRAMES - 1).getRoll(0);
      }

      if (frame.getIsStrike()) {
        score += player.getFrame(Player.MAX_FRAMES - 1).getRoll(0) + player.getFrame(Player.MAX_FRAMES - 1).getRoll(1);
      }

      frame.setScore(score);

      // Calc fram 10
      frame = player.getFrame(Player.MAX_FRAMES - 1);
      score += frame.getRoll(0) + frame.getRoll(1);

      if (frame.getIsSpare() || frame.getIsStrike()) {
        score += frame.getRoll(2);
      }

      frame.setScore(score);
    }
  }

  public void printPlayers() {
    StringBuilder scoreBoard = new StringBuilder();

    scoreBoard.append(StringUtils.rightPad("Frame", 10))
              .append(StringUtils.rightPad("1", 6))
              .append(StringUtils.rightPad("2", 6))
              .append(StringUtils.rightPad("3", 6))
              .append(StringUtils.rightPad("4", 6))
              .append(StringUtils.rightPad("5", 6))
              .append(StringUtils.rightPad("6", 6))
              .append(StringUtils.rightPad("7", 6))
              .append(StringUtils.rightPad("8", 6))
              .append(StringUtils.rightPad("9", 6))
              .append("10").append('\n');

    players.stream().forEach(player -> scoreBoard.append(player));

    System.out.println(scoreBoard.toString());
  }

  private Player getPlayer(String name) {
    return players.get(playerMap.get(name));
  } 
}