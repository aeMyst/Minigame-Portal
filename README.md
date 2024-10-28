# seng300-f24-project

## General Organization

The project has 2 main loops:
1. The game loop
2. The UI loop

The UI loop interacts with the main loop by querying IGameLogic interface
The game state takes this as input as uses it's internal state to update the game state.
At any point the UI should be able to query the GameLogic for an instance of GameState which it can use to display the board.
The customization happens in different classes that implement the IGameLogic interface.

