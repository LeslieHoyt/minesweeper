package minesweeper;
/*
Project 2: MineSweeper Program
COMP 182/L
Leslie Hoyt
2/15/2017
*/

import java.util.*;

public class MineSweeper {

	private int numrows; // number of rows in the grid
	private int numcols; // number of columns in the grid
	private int numcells; // number of cells in the grid
	private int nummines; // number of mines in the grid

	private Cell[][] cells; // array of Cell objects

	public MineSweeper() {
		// Constructor creates the grid of cells and initializes
		// their properties. Each Cell is created for each position
		// in the grid. Then a subset of Cells are picked at random
		// to contain mines. Then the adjacency counts of all non-mined
		// cells are calculated.
		
		// Initialize number of rows
		numrows = 4;
		// Initialize number of columns
		numcols = 4;
		// Initialize number of cells
		numcells = numrows * numcols;
		// Create an array of Cells for the grid
		cells = new Cell[numrows][numcols];
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				cells[i][j] = new Cell(i,j);
			} // end inner for
		} // end outer for
		
		// Random loop to assign mines to cells
		Random randCells = new Random(); // holds the random number of cells to have mines
		Random randRow = new Random(); // holds a random row
		Random randCol = new Random(); // holds a random column
		// Set number of mines to a random number, based on the number of cells in the grid
		nummines = randCells.nextInt(numcells + 1);
		// Assign mines randomly throughout the grid
		for (int i = 0; i < nummines; i++) {
			// Get a random row
			int row = randRow.nextInt(numrows);
			// Get a random column
			int col = randCol.nextInt(numcols);
			// Check is cell already has a mine
			if (cells[row][col].getMined()) {
				// Do not repeat any already mined cells
				// Back step loop by 1
				i--;
			} // end if
			else {
				// Cell does not already have a mine
				// So assign mine to cell
				cells[row][col].setMined(true);
			} // end else
		} // end for

		// Set adjacency count for all other cells
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				// Check to see if cell has a mine
				if (!cells[i][j].getMined()) {
					// If not, set adjacency count
					cells[i][j].setAdjCount(getAdjMineCount(i,j));
				} // end if
			} // end inner for
		} // end outer for
	}

	public int getAdjMineCount(int i, int j) {
		// This method is called only for a cell that is not mined.  
		// It counts the number of mined cells that are adjacent to the 
		// cell at position row=i, col=j.  This count will be a number between 0 and 8.
		// Precondition: Calling code provides the row and column of a cell
		// Postcondition: Returns the number of mines adjacent to the cell
	
		int minecount = 0; // reset minecount
		// Check up to 8 adjacent cells
		for (int d = 0; d < 8; d++) {
			// If an adjacent cell exists and it contains a mine
			if (getAdjCell(i,j,d) != null && getAdjCell(i,j,d).getMined()) {
				// Increment minecount
				minecount++;
			} // end if
		} // end for
		// Return the number of mines (adjacency count)
		return minecount;

	}

	public int getMarkCount() {
		// This method checks all cells in the grid and counts how many cells are marked.  
		// Although the player is supposed to mark only cells that contain mines, 
		// this method does not check to see if the marks are correct or not.
		// Postcondition: Returns the number of marked cells
	
		int markcount = 0; // initialize markcount
		// Check all cells in the grid
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				// If cell is marked
				if (cells[i][j].getMarked()) {
					markcount++; // increment markcount
				} // end if
			} // end inner for
		} // end outer for
		
		// Return the number of marked cells
		return markcount;

	}

	public void show() { 
		// This method prints out the current picture of the grid of cells.  
		// The show() method of the Cell class will handle the display of each 
		// cell based on the values of that cell's properties.
		// Postcondition: Prints the grid, showing the column and row numbers and
		// contents of each cell
		
		// Print the number of mines marked and the number of total mines
		System.out.printf("Status:  %02d/%02d\n\n", getMarkCount(), nummines);
		
		// Print the grid
		// Print column numbers
		System.out.print("   ");
		for (int i = 0; i < numcols; i++) {
			System.out.printf("%3d",i);
		} // end for
		System.out.println(" ");
		// Print horizontal dividing line
		System.out.print("   ");
		for (int i = 0; i < numcols; i++) {
			System.out.print("___");
		} // end for
		System.out.println("_");
		// Print row numbers
		for (int i = 0; i < numrows; i++) {
			// Print vertical dividing line
			System.out.printf("%2d|",i);
			System.out.print(" ");
			// Print contents of cells
			for (int j = 0; j < numcols; j++) {
				cells[i][j].show();
				if (j < numcols - 1) {
					System.out.print(" ");
				} // end if
			} // end inner for
			
			// Print vertical dividing line
			// And print row numbers
			System.out.printf(" |%2d",i);
			System.out.println("");
			if (i < numrows - 1) {
				System.out.print("  |");
				for (int j = 0; j < numcols; j++) {
					System.out.print("   ");
				} // end inner for
				System.out.println(" |");
			} // end if
		} // end outer for
		
		// Print bottom horizontal dividing line
		System.out.print("   ");
		for (int i = 0; i < numcols; i++) {
			System.out.print("___");
		} // end for
		System.out.println("_");

		System.out.print("   ");
		// Print column numbers
		for (int i = 0; i < numcols; i++) {
			System.out.printf("%3d",i);
		} // end for
		System.out.println(" ");
	}

	public void uncoverAll() {
		// This method is called if the player loses the game to show the contents of all grid cells. 
		// It loops over all cells in the board and sets their covered values to false.
		// Postcondition: Sets all cells' covered values to false
		
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				// Set each cells' covered values to false
				cells[i][j].setUncovered();
			} // end inner for
		} // end outer for

	}

	public Cell getAdjCell(int r, int c, int direction) {
		// This method looks at the cell in the grid at position row=r, col=c.  
		// It finds and returns the neighbor of the cell in the direction indicated 
		// by the direction input.  The direction is a number from 0 to 7, representing 
		// the 8 possible directions on the grid from one cell to an adjacent cell.
		// Precondition: Calling code provides the row and column of the Cell and direction
		// of the adjacent cell (an integer with a value from 0 to 7).
		// Postcondition: Returns the adjacent Cell


		switch (direction) {
		case 0: // if direction is 0, adjacent cell is directly to the right of the cell
			// Check if adjacent cell is out of bounds
			if (c+1 >= numcols) {
				// Adjacent cell is out of bounds, 
				// (it is greater than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r][c+1];
			} // end else
		case 1: // if direction is 1, adjacent cell is to the top diagonal right of the cell
			// Check if adjacent cell is out of bounds
			if (r-1 < 0 || c+1 >= numcols) {
				// Adjacent cell is out of bounds,
				// (it is either less than the number of rows in the grid
				// or greater than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r-1][c+1];
			} // end else	
		case 2: // if direction is 2, adjacent cell is directly above the cell
			// Check if adjacent cell is out of bounds
			if (r-1 < 0) {
				// Adjacent cell is out of bounds, 
				// (it is less than the number of rows in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r-1][c];
			} // end else
		case 3: // if direction is 3, adjacent cell is to the top diagonal left of the cell
			// Check if adjacent cell is out of bounds
			if (r-1 < 0 || c-1 < 0) {
				// Adjacent cell is out of bounds, 
				// (it is either less than the number of rows in the grid
				// or less than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r-1][c-1];
			} // end else
		case 4: // if direction is 4, adjacent cell is directly to the left of the cell
			// Check if adjacent cell is out of bounds
			if (c-1 < 0) {
				// Adjacent cell is out of bounds, 
				// (it is less than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r][c-1];
			} // end else
		case 5: // if direction is 5, adjacent cell is to the bottom diagonal left of the cell
			// Check if adjacent cell is out of bounds
			if (r+1 >= numrows || c-1 < 0) {
				// Adjacent cell is out of bounds, 
				// (it is either greater than the number of rows in the grid
				// or less than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r+1][c-1];
			} // end else
		case 6: // if direction is 6, adjacent cell is directly below the cell
			// Check if adjacent cell is out of bounds
			if (r+1 >= numrows) {
				// Adjacent cell is out of bounds, 
				// (it is greater than the number of rows in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r+1][c];
			} // end else
		case 7: // if direction is 7, adjacent cell is to the bottom diagonal right of the cell
			// Check if adjacent cell is out of bounds
			if (r+1 >= numrows || c+1 >= numcols) {
				// Adjacent cell is out of bounds, 
				// (it is either greater than the number of rows in the grid
				// or it is greater than the number of columns in the grid)
				// so return null
				return null;
			} // end if
			else {
				// Return the adjacent cell
				return cells[r+1][c+1];
			} // end else
		default:
			// This part activates if the direction provided was not
			// within the range of 0 to 7, so return null
			return null;
		} // end switch

	}

	public boolean allNonMinesUncovered() {
		// This method also helps detect if the player has won the game.  
		// It loops over all cells in the grid and checks if each cell that 
		// is not a mine has been uncovered.  It returns true if all non-mines 
		// are uncovered, false otherwise.
		// Postcondition: Returns true if all non-mines are uncovered, or
		// returns false if all non-mines are not uncovered

		// initialize non-mines uncovered flag to false
		boolean nonMinesUncovered = false;
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				// check if cell is not mined
				if (cells[i][j].getMined() == false) {
					// check if cell is not covered
					if (cells[i][j].getCovered() == false) {
						// cell is not mined nor covered
						// (at least this non-mined cell is uncovered)
						// so set flag to true
						nonMinesUncovered = true;
					} // end inner if
					else {
						// cell is not mined, but is covered
						// (not all non-mined cells are uncovered)
						// so return false
						return false;
					} // end else
				} // end outer if
			} // end inner for
		} // end outer for
		// return the value of the non-mines uncovered flag
		// (this will return only if all non-mined cells are uncovered)
		return nonMinesUncovered;

	}

	public boolean allMinesMarked() {
		// This method is used to help detect if the player has won the game.  
		// It loops over all cells in the grid and checks if each cell with a 
		// mine has been marked.  It returns true if all mines are marked, false otherwise.
		// Postcondition: If all mines are marked, a value of true is returned.
		// If all mines are not marked, a value of false is returned.

		// initialize mines marked flag to false
		boolean minesMarked = false;
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				// check if cell is mined
				if (cells[i][j].getMined()) {
					// check if cell is marked
					if (cells[i][j].getMarked()) {
						// cell is both mined and marked
						// (at least this cell is mined and marked)
						// so set flag to true
						minesMarked = true;
					} // end inner if
					else {
						// cell is mined, but not marked
						// (not all mines are marked)
						// so return false
						return false;
					} // end else
				} // end outer if
			} // end inner for
		} // end outer for
		// return the value of the mines marked flag
		// (this will return only if all mines are marked)
		return minesMarked;

	}

	public void game() {
		// This method implements the CLI (command line interface)
		
		// Create a Scanner to represent the keyboard
		Scanner in = new Scanner(System.in);
		// Print the game command prompt ">"
		System.out.print("> ");
		// Read one line of input
		String line = in.nextLine();
		// Initialize quit flag to false
		boolean quit = false;
		// While input is not empty and player has not quit the game
		while (!line.equals("") && !(quit)) {
			// Tokenize the string input
			String[] tk  = line.split("\\ +");
			String cmd = tk[0];
			
			// If command is show
			if (cmd.equals("show")) {
				// Show the grid
				show();
			} // end if
			// Else if command is uncover
			else if (cmd.equals("u")) {
				// Initialize row and column coordinates (x,y) to 0
				int x = 0;
				int y = 0;
				// Assign row input to x
				x = Integer.parseInt(tk[1]);
				// Assign column input to y
				y = Integer.parseInt(tk[2]);

				// If the cell is not marked, uncover it
				if (!cells[x][y].getMarked()) {
					cells[x][y].setUncovered();
					// Check if the uncovered cell is mined
					if (cells[x][y].getMined()) {
						// game over -- player loses
						// Uncover all cells
						uncoverAll();
						// Display the grid
						show();
						// Print game over message
						System.out.println("You LOSE!");
						// Exit the game
						quit = true;
					} // end inner if
					// Check to see if the player has won
					if (allMinesMarked() && allNonMinesUncovered()) {
						// game over -- player wins
						// Display the grid
						show();
						// Print game over message
						System.out.println("You WIN!");
						// Exit the game
						quit = true;
					} // end inner if

				} // end outer if
			} // end else if
			// Else if command is mark
			else if (cmd.equals("m")) {
				// Initialize row and column coordinates (x,y) to 0
				int x = 0;
				int y = 0;
				// Assign row input to x
				x = Integer.parseInt(tk[1]);
				// Assign column input to y
				y = Integer.parseInt(tk[2]);
				
				// Check if cell is covered
				if (cells[x][y].getCovered()) {
					// Check if cell is marked
					if (cells[x][y].getMarked()) {
						// Cell is already marked, so unmark it
						cells[x][y].setMarked(false);
					} // end inner if
					else {
						// Mark the cell
						cells[x][y].setMarked(true);
						// Check to see if the player has won
						if (allMinesMarked() && allNonMinesUncovered()) {
							// game over -- player wins
							// Display grid
							show();
							// Print game over message
							System.out.println("You WIN!");
							// Exit the game
							quit = true;
						} // end inner if
					} // end else
				} // end outer if


			} // end else if
			// Else if command is quit
			else if (cmd.equals("q")) {
				// Print goodbye message
				System.out.println("Goodbye");
				// Exit the game
				quit = true;
			} // end else if
			else {
				// Print out bad command message
				System.out.println("Bad command");
			} // end else
			// Print the command prompt ">"
			System.out.print("> ");
			// Read next input
			line = in.nextLine();
		} // end while
		
		// Close the scanner
		in.close();
	}

	public static void play() {
		// Create a MineSweeper object
		MineSweeper mine = new MineSweeper();
		// Start the game
		mine.game();
	}
}
