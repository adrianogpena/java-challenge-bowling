# Java Challenge Bowling

## About the project

This is a challenge for a job oportunity on Jobsity.

## Scoring points

The least points you can make in one frame is the sum of the first and second roll.

### Spare

If is a Spare, it add up the next ball.

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
3. The file mavenbowling-1.0-SNAPSHOT.jar will be created on target directory

## Execution

1. On the src directory
2. Execute
  ```sh
  java -cp target/mavenbowling-1.0-SNAPSHOT.jar com.bowling.App
  ```
3. The program will request the path to a file, which should have the results for several players bowling 10 frames each

  Example:
  ```sh
  Jeff 10
  John 3
  John 7
  Jeff 7
  Jeff 3
  John 6
  John 3
  Jeff 9
  Jeff 0
  John 10
  Jeff 10
  John 8
  John 1
  Jeff 0
  Jeff 8
  John 10
  Jeff 8
  Jeff 2
  John 10
  Jeff F
  Jeff 6
  John 9
  John 0
  Jeff 10
  John 7
  John 3
  Jeff 10
  John 4
  John 4
  Jeff 10
  Jeff 8
  Jeff 1
  John 10
  John 9
  John 0
  ```

4. The result for each frame will be calculate
5. The output will show the name of each player, the pins knocked down and score of each frame

  Example:
  ```sh
  Frame     1     2     3     4     5     6     7     8     9     10
  Jeff
  Pinfalls     X  7  /  9  0     X  0  8  8  /  F  6     X     X  X  8  1
  Score     20    39    48    66    74    84    90    120   148   167
  John
  Pinfalls  3  /  6  3     X  8  1     X     X  9  0  7  /  4  4  X  9  0
  Score     16    25    44    53    82    101   110   124   132   151
  ```

## Exemples files

Some exemples file are already on directory `src\main\resources\`