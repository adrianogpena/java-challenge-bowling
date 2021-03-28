package com.board;

public class Frame {
  private int[] rolls;
  private int score;
  private int pinsUp;
  private int numAttempts;
  private boolean isSpare;
  private boolean isStrike;
  private static final int MAX_ATTEMPTS = 2;

  public Frame(int frameNumber) {
    rolls = (frameNumber == 9) ? new int[MAX_ATTEMPTS + 1] : new int[MAX_ATTEMPTS];
    pinsUp = 10;
    numAttempts = 0;
    isSpare = false;
    isStrike = false;
  }

  public int getRoll(int rollNumber) {
    return (rolls[rollNumber] == -1) ? 0 : rolls[rollNumber];
  }

  public void setRoll(int pinsKnockedDown, boolean foul) {
    if (foul) {
      rolls[numAttempts] = -1;
    } else {
      rolls[numAttempts] = pinsKnockedDown;
      pinsUp -= pinsKnockedDown;
    }

    // System.out.println("Score " + rolls[numAttempts] + " saved! There are still " + pinsUp + " pins up.");

    // First ball and no pins up is a STRIKE
    if (numAttempts == 0 && pinsUp == 0) {
      // System.out.println("STRIKE!");
      isStrike = true;
    }

    // Second ball and no pins up is a SPARE
    if (numAttempts == 1 && pinsUp == 0 && !isStrike) {
      // System.out.println("Spare");
      isSpare = true;
    }

    numAttempts++;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean getIsSpare() {
    return this.isSpare;
  }

  public boolean getIsStrike() {
    return this.isStrike;
  }

  public boolean noMoreAttempts() {
    return numAttempts == MAX_ATTEMPTS;
  }
}
