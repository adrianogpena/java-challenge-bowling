package com.board;

import java.util.ArrayList;
import java.util.List;

import com.exceptions.BowlingException;

import org.apache.commons.lang3.StringUtils;

public class Player {
  private String name;
  private List<Frame> frames;
  private int frameCounter;
  public static final int MAX_FRAMES = 10;
  private static final int MAX_PINS = 10;

  public Player(String name) {
    this.name = name;
    setFrameCounter(0);
    frames = new ArrayList<Frame>(MAX_FRAMES);

    for (int i = 0; i < MAX_FRAMES; i++) {
      frames.add(new Frame(i));
    }
  }

  public String getName() {
    return this.name;
  }

  public List<Frame> getFrames() {
    return this.frames;
  }

  public void setFrames(List<Frame> frames) {
    this.frames = frames;
  }

  public void addFrame(Frame frame) {
    this.frames.add(frame);
  }

  private int getFrameCounter() {
    return this.frameCounter;
  }

  private void setFrameCounter(int frameCounter) {
    this.frameCounter = frameCounter;
  }

  private void nextFrame() {
    setFrameCounter(getFrameCounter() + 1);
  }
  
  public void roll(int pinsKnockedDown, boolean foul) {

    // Check if the numbers of pins knocked down is between 0 and 10
    if (pinsKnockedDown > MAX_PINS || pinsKnockedDown < 0) {
      throw new BowlingException("Player " + getName() + " scored an invalid number of pins on frame " +
                                 (getFrameCounter() + 1) + ": " + pinsKnockedDown);
    }

    Frame frame = getCurrentFrame();

    // If the frame is null, than the player tried to play more than 10 frames
    if (frame == null) {
      throw new BowlingException("Player " + getName() + " tried to play an invalid frame: " + (getFrameCounter() + 1));
    }

    // if there is no more attempts and has a ball to score, something is wrong
    if (frame.noMoreAttempts() && !isLastFrame()) {
      throw new BowlingException("Player " + getName() + " tried to play more attempts on frame " +
                                 (getFrameCounter() + 1) + " than is permited");
    }

    // If is the last frame and the player made an Strike or an Spare, he is allowed one more shot
    if (frame.noMoreAttempts() && isLastFrame() && !frame.getIsSpare() && !frame.getIsStrike() ) {
      throw new BowlingException("Player " + getName() + " not allowed to play the bonus ball on the last frame");
    }

    // Save the numbers of pin knocked down on the appropriate roll 
    frame.setRoll(pinsKnockedDown, foul);

    // If is a strike and not the last round, next roll is 0
    if (frame.getIsStrike() && !isLastFrame()) {
      frame.setRoll(0, false);
    }

    // if there is no more attempts but it just score a ball, selext the next frame
    if (frame.noMoreAttempts() && !isLastFrame()) {
      nextFrame();
    }
  }

  public Frame getFrame(int frameNumber) {
    return frames.get(frameNumber);
  }

  private Frame getCurrentFrame () {
    return frames.get(getFrameCounter());
  }

  private boolean isLastFrame() {
    return getFrameCounter() == MAX_FRAMES - 1;
  }

  @Override
  public String toString() {
    StringBuilder scoreBoard = new StringBuilder();
    StringBuilder score = new StringBuilder();
    List<Frame> frames = getFrames();

    scoreBoard.append(getName()).append('\n');
    scoreBoard.append(StringUtils.rightPad("Pinfalls", 10));

    score.append(StringUtils.rightPad("Score", 10));

    for (Frame frame : frames) {
      score.append(StringUtils.rightPad(String.valueOf(frame.getScore()), 6));
      
      // Set First Ball and Second Ball
      String firstBall  = String.valueOf(frame.getRoll(0, true));
      String secondBall = String.valueOf(frame.getRoll(1, true));
      String thirdBall  = "";

      // Check if it was a foul play
      firstBall  = (firstBall.equals("-1")) ? "F" : firstBall;
      secondBall = (secondBall.equals("-1")) ? "F" : secondBall;

      // Check if is a Strike
      if (frame.getIsStrike()) {
        firstBall = "";
        secondBall = "X";
      }

      // Check if is a Spare
      if (frame.getIsSpare()) {
        secondBall = "/";
      }

      // Check if is the last frame
      if (frame == frames.get(frames.size() - 1)) {
        // If is a Strike change the value for X
        firstBall  = (String.valueOf(frame.getRoll(0)).equals("10")) ? "X" : String.valueOf(frame.getRoll(0, true));
        secondBall = (String.valueOf(frame.getRoll(1)).equals("10")) ? "X" : String.valueOf(frame.getRoll(1, true));
        thirdBall  = (String.valueOf(frame.getRoll(2)).equals("10")) ? "X" : String.valueOf(frame.getRoll(2, true));

        // Check if it was a foul play
        firstBall  = (firstBall.equals("-1")) ? "F" : firstBall;
        secondBall = (secondBall.equals("-1")) ? "F" : secondBall;
        thirdBall  = (thirdBall.equals("-1")) ? "F" : thirdBall;

        if (frame.getIsSpare()) {
          secondBall = "/";
        }
      }

      scoreBoard.append(StringUtils.rightPad(firstBall, 3))
                .append(StringUtils.rightPad(secondBall, 3))
                .append(thirdBall);
  
    }

    scoreBoard.append('\n').append(score.toString()).append('\n');

    return scoreBoard.toString();
  }

}
