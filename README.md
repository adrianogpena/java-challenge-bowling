# Java Challenge Bowling

## About the project

This is a challenge for a job oportunity on Jobsity.

## Scoring points

The least points you can make in one frame is the sum of the first and second roll.

### Spare

If the frame is a spare it add up the next ball.

### Strike

If is a Strike, it add up the next two balls.

### Foul

If a foul occurs the score is ignored but the shot is counted.

## Install

1. On the src directory
2. Execute
  ```sh
  mvn clean install
  ```
3. The file mavenbowling-1.0-SNAPSHOT.jar will be created on targer directory
4. To execute the jar file
  ```sh
  java -cp target/mavenbowling-1.0-SNAPSHOT.jar com.bowling.App
  ```