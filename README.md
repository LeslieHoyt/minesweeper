# Minesweeper
A command line interface implentation of the Minesweeper game written in Java

## About the Game

The game is played by manipulating a regular grid of cells. To initialize the game, a subset of the cells is chosen at random to contain mines. Cells that don't contain mines contain an integer that indicates how many covered and hidden mines are adjacent to that cell. Cells that don't contain mines contain an integer that indicates how many covered and hidden mines are adjacent to that cell. Before the grid is shown to the player, all cells are covered to hide their contents (either a mine or an adjacency count). This is the grid that the player sees when the game begins.

To play the game, the player makes a series of moves: either uncover a covered cell, or mark/unmark a covered cell. Once a cell is uncovered, its contents are displayed, and it can never be covered again. If the player uncovers a cell that contains a mine, the game is over and the player loses. To win, the player must analyze the adjacency counts of the safely uncovered cells that don't contain mines, and figure out from this which covered cells contain mines and mark them without uncovering them. All other cells, the player must uncover. The player wins when all mined cells are covered and marked, and all other cells are uncovered.

## Implementation Overview

The game is implemented as a grid of cells with properties that can be manipulated by the player from the command line interface. Each element of the grid is an object of type Cell. A Cell object maintains the following properties:
- int row, col;     // position in the grid
- boolean covered;  // covered is true, uncovered is false
- boolean marked;   // marked is true, unmarked is false
- boolean mined;    // contains a mine is true, no mine is false
- int adjcount;     // number of mines adjacent to this cell (0-8)

During a game, the values of most cell properties are fixed before the game begins. How the cell is displayed in the grid depends on the value of these properties. In this program, the following rules apply:
- covered == true && marked == false:                     
      ? (question mark)
- covered == true && marked == false:                     
      X (capital X)
- coverd == false && mined == true:                       
      * (asterisk)
- covered == false && mined == false && adjcount == 0:    
      _ (underscore)
- covered == false && mined == false && adjcount > 0:     
      n (integer value from 1 to 8)

Note some restrictions on how the Cell state is manipulated:
- The basic properties are: row, col, mined
- The adjcount property is used only if mined is false.
- The covered and marked properties interact as follows:
  - To mark a cell, it must be covered. Marking an uncovered cell is an excluded operation.
  - To uncover a cell, it must be unmarked. Uncovering a marked cell is an excluded operation.
  - Once a cell is uncovered, it cannot be recovered.

When initializing a Cell object, the sequence of operations will normally be one of the following
- For a Cell containing a mine:
  - Intantiate the Cell with a row and column id.
  - Set covered to true.
  - Set mined to true.
  - Set adjcount to -1 (this property is not used for a Cell with mined = true)
  - During the game the row, column, mined, and adjcount properties cannot be changed. The cell can be repeatedly marked and unmarked.
  - If the Cell is uncovered, the game ends.
- For a Cell not containing a mine:
  - Intantiate the Cell with a row and column id.
  - Set covered to true.
  - Set mined to false.
  - Set the adjcount value based on the number of mined cells in adjacent positions.
  - During the game, the row, column, mined, and adjcount properties cannot be changed. The cell can be repeatedly marked and unmarked.
  - If the Cell is uncovered, the adjcount value is displayed.
  
 ## How To Play
 
 The command line interface is implemented by a method that accepts inputs from the keyboard that represent the player's next command. The possible commands are:
 - show: show the grid by printing it to the display
 - q: quit the game
 - u x y: uncover the cell at row x and column y
 - m x y: mark or unmark the covered cell at row x and column y
 
 The command line interface is implemented as a sentinel-controlled loop. The current input is obtained from the keyboard and processed repeatedly until the input represents the "quit" command.
