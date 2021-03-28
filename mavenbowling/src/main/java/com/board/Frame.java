package com.board;

import java.util.ArrayList;
import java.util.List;

public class Frame {
  private List<String> rows = new ArrayList<String>();
  private int score;

  public Frame() {
  }

  public List<String> getRows() {
    return this.rows;
  }

  public void setRows(List<String> rows) {
    this.rows = rows;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void addBall(String pinsKnockedDown) {
    this.rows.add(pinsKnockedDown);
  }

}
