package minesweeper;
/*
Project 2: MineSweeper Program
COMP 182/L
Leslie Hoyt
2/15/2017
*/

public class Cell {
	private int row, col; // position in the grid
	private boolean covered; // covered is true, uncovered is false
	private boolean marked; // marked is true, unmarked is false
	private boolean mined; // contains a mine is true, no mine is false
	private int adjcount; // number of mines adjacent to this cell (0-8)
	private boolean uncoveredflag, minedflag, adjflag;

	public Cell(int r, int c) {
		// Cell constructor
		// Precondition: Calling code provides the row and column of the cell
		// Postcondition: Cell object is created with its data fields initialized
		
		// Set row and column to passed values
		row = r;
		col = c;
		// Cell is not marked
		marked = false;
		// Cell is covered
		covered = true;
		// Cell is not uncovered
		uncoveredflag = false;
		// Cell is not mined
		minedflag = false;
		mined = false;
		// Cell has no adjacency count
		adjflag = false;
		adjcount = 0;
	}

	public void show() {
		// This method displays the contents of the cells,
		// if a cell is covered and marked, it displays an X (letter X)
		// if a cell is covered and unmarked, it displays a ? (question mark)
		// if a cell is not covered and mined, it displays an * (asterisk)
		// if a cell is not covered and not mined, it displays the adjacency
		// count of the cell if the adjacency count is not 0
		// if the adjacency count is 0, then it displays _ (underscore)
		// Postcondition: Cell contents are displayed
		
		// If cell is covered
		if (covered) {
			// If cell is marked
			if (marked) {
				// Display X (letter X) in cell
				System.out.print(" X");
			} // end inner if
			else {
				// Cell is covered, but not marked
				// Display ? (question mark) in cell
				System.out.print(" ?");
			} // end inner else
		} // end if
		// Cell is not covered
		else {
			// If cell is mined
			if (mined) {
				// Cell is not covered and mined
				// Display * (asterisk) in cell
				System.out.print(" *");
			} // end inner if
			// Cell is not mined
			// If adjacency count is greater than 0
			else if (adjcount > 0) {
				// Cell is not mined and adjacency
				// count is greater than 0
				// Print adjacency count of cell
				System.out.print(" " + adjcount);
			} // end inner else if
			else {
				// Cell is not mined and adjacency
				// count is 0
				// Print _ (underscore) in cell
				System.out.print(" _");
			} // end inner else
		} // end else
	}

	public String toString() {
		// Formats the output of a Cell object
		return "[" + row + "," + col + ","
				+ covered + "," + marked + "," + mined + ","
				+ adjcount + "]";
	}

	public boolean getMined() {
		// Returns the boolean value of mined
		return mined;
	}

	public void setMined(boolean m) {
		// This method sets the value of mined for a cell
		// Precondition: Calling code provides a boolean value
		// Postcondition: The value of mined and the value of minedflag are set
		// (the values adjcount and adjflag may also be set if the passed value is true)
		
		// Check if both the minedflag and adjflag are false
		if (minedflag == false && adjflag == false) {
			// Set mined to the passed value
			mined = m;
			// Set minedflag to true
			minedflag = true;
			// Check if the passed value is true
			if (m == true) {
				// Cell is mined
				// Set adjcount to -1 to indicate a mine
				adjcount = -1;
				// Set adjflag to true
				adjflag = true;
			} // end inner if
		} // end outer if
	}

	public boolean getCovered() {
		// Returns the value of covered
		return covered;
	}

	public void setUncovered() {
		// This method uncovers a cell
		// Postcondition: Uncovers a cell
		
		// Check if cell is not marked
		if (marked == false) {
			// Check if cell is not already uncovered
			// (uncoveredflag is false)
			if (uncoveredflag == false) {
				// Cell is not marked
				// and uncoveredflag is false
				// So uncover the cell
				// (set covered to false)
				covered = false;
				// Set uncoveredflag to true
				uncoveredflag = true;
			} // end inner if
		} // end outer if
	}

	public boolean getMarked() {
		// Returns the value of marked
		return marked;
	}

	public void setMarked(boolean m) {
		// This method sets the Cells' value of marked
		// Precondition: Calling code provides a boolean value
		// Postcondition: The Cell's value of marked is set
		
		// Check if cell is covered
		if (covered == true) {
			// Cell is covered
			// So set the value of marked
			marked = m;
		} // end if
		// If cell is not covered, do nothing
	}

	public int getAdjCount() {
		// Returns the adjacency count of a cell
		return adjcount;
	}

	public void setAdjCount(int c) {
		// This method sets the adjacency count of a cell
		// Precondition: Calling code provides an integer value representing the
		// cell's adjacency count (number of adjacent mines)
		// Postcondition: The adjacency count of a cell is set
		
		// Check if the cell has a mine
		if (mined == false) {
			// Cell does not have a mine
			// So set the adjacency count to the passed value
			adjcount = c;
		} // end if
		else {
			// Cell has a mine
			// So set the adjacency count to -1
			adjcount = -1;
		} // end else
		// Set adjflag to true
		adjflag = true;
	}

	public int getRow() {
		// Returns the row of a cell
		return row;
	}

	public int getCol() {
		// Returns the column of a cell
		return col;
	}



}
