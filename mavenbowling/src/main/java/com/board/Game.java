package com.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

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

        if (!playerMap.containsKey(playerName)) {
          playerMap.put(playerName, maxPlayerId);
          players.add(new Player(playerName));
          maxPlayerId++;
        }

        Player player = getPlayer(playerName);
        
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
      List<Frame> frames = player.getFrames();
      Frame frame;
      int score = 0;
      int size = frames.size();

      for (int i = 0; i < size; i++) {
        frame = player.getFrame(i);
        score += frame.getRoll(0) + frame.getRoll(1);

        if (frame.getIsSpare()) {
          switch (i) {
            case 9:
              score += frame.getRoll(2);
              break;

            default:
              score += player.getFrame(i + 1).getRoll(0);
              break;
          }
        }

        if (frame.getIsStrike()) {
          switch (i) {
            case 8:
              score += player.getFrame(i + 1).getRoll(0) + player.getFrame(i + 1).getRoll(1);
              break;

            case 9:
              score += frame.getRoll(2);
              break;

            default:
              if (player.getFrame(i + 1).getIsStrike()) {
                score += player.getFrame(i + 1).getRoll(0) + player.getFrame(i + 2).getRoll(0);
              } else {
                score += player.getFrame(i + 1).getRoll(0) + player.getFrame(i + 1).getRoll(1);
              }
              break;
          }
        }

        frame.setScore(score);
      }
    }
  }

  public void printPlayers() {
    StringBuilder scoreBoard = new StringBuilder();

    scoreBoard.append(StringUtils.rightPad("Frame", 10));

    IntStream.range(1, 11).forEach(frameNumber -> scoreBoard.append(StringUtils.rightPad(String.valueOf(frameNumber), 6)));
    scoreBoard.append('\n');

    players.stream().forEach(player -> scoreBoard.append(player));

    System.out.println(scoreBoard.toString());
  }

  private Player getPlayer(String name) {
    return players.get(playerMap.get(name));
  } 
}