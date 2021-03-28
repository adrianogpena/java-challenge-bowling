package com.board;

import java.util.ArrayList;
import java.util.List;

import com.exceptions.BowlingException;

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
    // System.out.println("Roll action");
    if (pinsKnockedDown > MAX_PINS || pinsKnockedDown < 0) {
      throw new BowlingException("Invalid number of pins: " + pinsKnockedDown);
    }

    Frame frame = getCurrentFrame();

    if (frame == null) {
      throw new BowlingException("Invalid number of frames: " + (getFrameCounter() + 1));
    }

    // if there is no more attempts and has a ball to score, something is wrong
    if (frame.noMoreAttempts() && !isLastFrame()) {
      throw new BowlingException("Invalid number of attempts on frame " + (getFrameCounter() + 1));
    }

    frame.setRoll(pinsKnockedDown, foul);

    // If is a strike and not the last round, next roll is 0
    if (frame.getIsStrike() && !isLastFrame()) {
      // System.out.println("Setting the score for the second roll");
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
    StringBuilder sb = new StringBuilder();
    sb.append(getName()).append('\n');
    sb.append("Frame  Scores\n");

    for (int i = 0; i < MAX_FRAMES; i++) {
      setFrameCounter(i);
      Frame frame = getFrame(i);
      sb.append(i + 1).append("      ").append(frame.getRoll(0)).append(" ").append(frame.getRoll(1));

      if (isLastFrame()) {
        sb.append(" ").append(frame.getRoll(2));
      }

      sb.append(" = ").append(frame.getScore()).append('\n');
    }

    return sb.toString();
  }

}
