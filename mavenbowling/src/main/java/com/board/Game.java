package com.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.exceptions.BowlingException;
import com.util.BowlingUtils;

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

      if (line == null) {
        throw new BowlingException("Empty File!");
      }

      while (line != null) {

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

        // New player on the board
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
    } catch (BowlingException e) {
      System.out.println("Message: " + e.getMessage());
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

        // Check if a Spare happen on the frame
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

        // Check if a Strike happen on the frame
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

    // Create the header
    scoreBoard.append(BowlingUtils.padRight("Frame", 10));
    IntStream.range(1, 11).forEach(frameNumber -> scoreBoard.append(BowlingUtils.padRight(String.valueOf(frameNumber), 6)));
    scoreBoard.append('\n');

    // For each player, add the pinfalls and the score of each frame
    players.stream().forEach(player -> scoreBoard.append(player));

    System.out.println(scoreBoard.toString());
  }

  private Player getPlayer(String name) {
    return players.get(playerMap.get(name));
  } 
}