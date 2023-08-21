// Emin Salih AÇIKGÖZ 22050111032
// Ferit Akyıldız 22050111036
public class NPuzzleRecursiveSolver {
	protected Board solutionBoard;
	
	Board solve(Board board) {
		initializeSearch();
		
		NPuzzleRecursiveSolver.this.searchSolution(new Board(board));
		
		return solutionBoard;
	}
	
	void initializeSearch() {
		solutionBoard = null;
	}
	
    private void searchSolution(Board board_config) {
        search(board_config);
    }

    private boolean search(Board board_config) {
        if (board_config.isFull()) {
            //stop searching when all queens are placed
            solutionBoard = board_config;
            return true;
        }
        //current column is the column that has not placed its queen yet
        int column = board_config.getFirstEmptyColumn();

        for (int i = 0; i < board_config.height; i++) {
            //Attempt to place the queen by checking for availible positions
            if (board_config.isInsideBoard(column, i)
                    && !board_config.canAttackOtherQueens(column, i)) {
                board_config.addQueen(column, i);
                //return true if a solution is found upon placing the queen
                if (search(board_config)) {
                    return true;
                }
                //remove the queen from the current position and try the next row
                board_config.removeQueen(column);

            }
        }
        //If there is no solution return false
        return false;
    }



}
