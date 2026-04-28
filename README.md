# Mancala Avalanche Advisor

A JavaFX desktop app that recommends the best move for a player in 
Mancala Avalanche. The user inputs the current state of the board and 
the program simulates all possible moves to recommend the one that 
scores the most points.

## About the Project

Mancala Avalanche is a variant of Mancala where a turn continues if 
the last stone lands in an occupied pit. The player picks up all 
stones in that pit and continues sowing, creating a chain reaction. 
A free turn is also granted when the last stone lands in the player's 
own store.

This app does not play the full game. Instead, it acts as an advisor:

- The user clicks on each pit to set the number of stones (left-click 
  to add, right-click to remove)
- The user selects whose turn it is
- The app simulates every possible move (with full avalanches and 
  free turns) and recommends the pit that scores the most points

## Prerequisites

- Java 17 or newer
- JavaFX 21 (handled through Gradle)
- IntelliJ IDEA (recommended)

## How to Run

Coming soon.

## Built With

- Java
- JavaFX for the user interface
- JUnit 5 for testing
- Mockito for mocking

## Author

Jose Antonio Jaga
