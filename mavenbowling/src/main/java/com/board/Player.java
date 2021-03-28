package com.board;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private String name;
  private List<Frame> frames = new ArrayList<Frame>();

  public Player(String name) {
    this.name = name;
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

}
