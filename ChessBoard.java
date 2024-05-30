import java.util.Random;

public class ChessBoard
{
	private int[][] board;
	private int h;
	private int x;     
	public int stateChanges;                        //width of board
	Random rand = new Random();

	/**
	 * Initializes a ChessBoard of size x.
	 **/
	public ChessBoard(int x)
	{
		this.x = x;
		board = new int[x][x];
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < x; j++)
			{
				board[i][j] = 0;
			}
		}
	}

	/**
	 * Places queens(1's) in each column at a random row on board.
	 **/
	public void start()
	{
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < x; j++)
			{
				board[i][j] = 0;
			}
		}
		int rando;
		for (int i = 0; i < x; i++)
		{
			rando = rand.nextInt(8);
			board[i][rando] = 1;
		}
		h = heuristicScore();
	}

	public void printBoard()
	{
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < x; j++)
			{
				System.out.print(board[i][j] + ", ");
			}
			System.out.println("");
		}		
	}
	/**
	 * This method prints the ChessBoard passed in to stdout.
	 * @param the ChessBoard that will be printed
	 **/
	public void printBoard(ChessBoard state)
	{
		int[][] temp = state.getBoard();
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < x; j++)
			{
				System.out.print(temp[i][j] + ", ");
			}
			System.out.println("");
		}
	}

	/**
	 * This method saves the current state of the board and returns a ChessBoard object of this state,
	 * @return the ChessBoard object representing the current state
	 **/
	public ChessBoard saveState()
	{
		int[][] state = new int[x][x];
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < x; j++)
			{
				state[i][j] = board[i][j];
			}
		}
		ChessBoard newBoard = new ChessBoard(8);
		newBoard.setBoard(state);
		return newBoard;
	}

	/**
	 * Returns the heuristic score(h).
	 **/
	public int getH()
	{
		return this.h;
	}

	/**
	 * Returns the board.
	 **/
	public int[][] getBoard()
	{
		return this.board;
	}

	/**
	 * This replaces board with a new board.
	 * @param temp is the chessboard represented as an array to replace the board
	 **/
	public void setBoard(int[][] temp)
	{
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; i < x; j++)
			{
				board[i][j] = temp[i][j];
			}
		}
	}

	/**
	 * Calculates the current boards total heuristics score.
	 * @return
	 */
	public int heuristicScore()
	{
		return checkRows() + checkColumns() + checkDiagonals();
	}

	/**
	 * returns the number of conflicts in columns.
	 * @return number of conflicts
	 */
	public int checkColumns()
	{
		int count = 0;
		int tempH = 0;
		for (int i = 0; i < x; i++)
		{
			count = 0;
			for (int j = 0; j < x; j++)
			{
				if (board[j][i] == 1)
				{
					count++;
				}

			}
			if (count >= 2)
			{
				tempH += count - 1;
			}
		}
		return tempH;
	}

	/** 
	 * Returns the number of conflicts in rows
	 * @return number of conflicts
	*/
	public int checkRows()
	{
		int count = 0;
		int tempH = 0;
		for (int i = 0; i < x; i++)
		{
			count = 0;
			for (int j = 0; j < x; j++)
			{
				if (board[i][j] == 1)
				{
					count++;
				}
			}
			if (count >= 2)
			{
				tempH += count - 1;
			}
		}
		return tempH;
	}

	/**
	 * Checks diagonals of coordinate and returns number of conflicts.
	 * @param row the row of coordiante
	 * @param column the column of coordinate
	 * @return the number of conflicts
	 */
	public int checkDiagonals()
	{
		int conflicts = 0;

		// Check right(\) diagonals
		for (int i = 0; i < x; i++) 
		{
			for (int j = 0; j < x; j++) 
			{
				if (board[i][j] == 1) 
				{
					for (int k = 1; i+k < x && j+k < x; k++) 
					{
						if (board[i+k][j+k] == 1) conflicts++;
					}
				}
			}
		}
	
		// Check left(/) diagonals
		for (int i = 0; i < x; i++) 
		{
			for (int j = 0; j < x; j++) 
			{
				if (board[i][j] == 1)
				 {
					for (int k = 1; i+k < x && j-k >= 0; k++)
					 {
						if (board[i+k][j-k] == 1) conflicts++;
					}
				}
			}
		}
	
		return conflicts;
	}

	/**
	 * Checks all possible state of the Queens and returns the best state while also printing info to stdout.
	 */
	public void transitionModel() 
	{
        int temp;
        stateChanges = 0;
        int[][] bestBoard = new int[x][x];
        int bestH = h;
        int lowerH = 0;

        for (int i = 0; i < x; i++)
		 {
            int currentQueenRow = -1;
            for (int j = 0; j < x; j++) 
			{
                if (board[i][j] == 1)
				 {
                    currentQueenRow = j;
                    board[i][j] = 0;
                    break;
                }
            }

            for (int j = 0; j < x; j++) {
                if (j == currentQueenRow) continue;
                board[i][j] = 1;
                temp = heuristicScore();
                stateChanges++;

                if (temp < bestH) 
				{
                    lowerH++;
                    bestH = temp;
                    for (int a = 0; a < x; a++)
					{
                        System.arraycopy(board[a], 0, bestBoard[a], 0, x);
                    }
                }
                board[i][j] = 0;
            }
            board[i][currentQueenRow] = 1;
        }

        if (lowerH == 0) 
		{
            System.out.println("RESTART\n");
            start();
        } else 
		{
            System.out.println("Neighbors found with lower h: " + lowerH);
            System.out.println("Setting new current state\n");
            board = bestBoard;
			System.out.println("Current h: " + heuristicScore());
			System.out.println("Current state: ");
            h = bestH;
            printBoard();
			System.out.println("");
        }
    }
}