/**
 * 
 */
package org.ivan.project;


/**
 * @author ivantsui
 *
 */
public class Othello {

	private final String CSV_RECORD_DELIMITER = ",";
	private final String INPUT_PASS = "PASS";

	private final short NUMBER_OF_ROW = 8;
	private final short NUMBER_OF_COLUMN = 8;
	private final char PIECE_DARK = 'X';
	private final char PIECE_LIGHT = 'O';
	private final char PIECE_NIL = '-';
	
	private char[][] board = new char[NUMBER_OF_ROW][NUMBER_OF_COLUMN];
	private char currentPiece = PIECE_DARK;
	
	/**
	 * Default constructor
	 */
	public Othello() {
		// initialize the board
		for (short x = 0; x < NUMBER_OF_COLUMN; x++) {
			for (short y = 0; y < NUMBER_OF_ROW; y++) {
				if ((x == 3 && y == 3) || (x == 4 && y == 4)) {
					board[x][y] = PIECE_LIGHT;
				} else if ((x == 3 && y == 4) || (x == 4 && y == 3)) {
					board[x][y] = PIECE_DARK;
				} else {
					board[x][y] = PIECE_NIL;
				}
			}
		}
	}
	
	/**
	 * Get the status of the game now
	 * @return String of the board at the moment
	 */
	public String displayBoard() {
		String display = "";
		for (short y = 0; y < NUMBER_OF_ROW; y++) {
			display += (y + 1) + " ";
			for (short x = 0; x < NUMBER_OF_COLUMN; x++) {
				display += board[x][y];
			}
			display += "\n";
		}
		display += "  abcdefgh\n";
		return display;				
	}
	
	/**
	 * Start the game play
	 * @param listOfMoves
	 * @return
	 */
	public String playGame(final String moves) {
		if (moves != null && this.INPUT_PASS.equalsIgnoreCase(moves.trim())) {
			// skip this turn and switch player to place piece
			switchPlayer();
		} else {
			String[] moveArray = moves.split(CSV_RECORD_DELIMITER);
			for (String move : moveArray) {
				String move_ = move.trim();
				if (isValidInput(move_)) {
					int x = getXIndex(move_);
					int y = getYIndex(move_);
					if (isValidMove(x, y)) {
						makeTheMove(x, y);
					} else {
						System.out.println("Invalid move. Please try again.\n");
					}
				} else {
					System.out.println("Invalid input. Please try again.\n");
				}
			}
		}
		return this.displayBoard();
	}
	
	public boolean isValidInput(final String move) {
		// the input move must be in the length of two and contains one alphabet and one numeric
		if (move != null && move.length() == 2) {
			if (move.matches("^[a-h][1-8]$") || move.matches("^[1-8][a-h]$")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidMove(final int x, final int y) {
		if (board[x][y] == PIECE_DARK || board[x][y] == PIECE_LIGHT) {
			// already occupied by other piece
			return false;
		} else if (!hasPieceToCapture(x, y)){
			// the move cannot capture any opponent's piece
			return false;
		} else {
			return true;
		}
	}
	
//	private boolean hasPieceNearby(int x, int y) {
//		// if the nearby position is 'X' or 'O', the move is valid.
//		boolean result = false;
//		for (int i = -1; i <= 1; i++) {
//			int temp_x = x + i;
//			if (temp_x >= 0 && temp_x < NUMBER_OF_COLUMN) {
//				for (int j = -1; j <= 1; j++) {
//					int temp_y = y + j;
//					if (temp_y >= 0 && temp_y < NUMBER_OF_ROW) {
//						if (board[temp_x][temp_y] == PLAYER_DARK || board[temp_x][temp_y] == PLAYER_LIGHT) {
//							result = true;
//							break;
//						}
//					}
//				}
//			}
//		}
//		return result;
//	}
	
	public short getXIndex(final String move) {
		// get the char part and convert it into the x-coordinate of the board
		short index = -1;
		char alphabet = ' ';
		if (move != null && move.length() == 2) {
			if (Character.isAlphabetic(move.charAt(0))) {
				alphabet = move.charAt(0);
			} else {
				alphabet = move.charAt(1);
			}
		}
		index = (short) (Character.getNumericValue(alphabet) - 10);	//a=10,b=11,... (converting to a=0,b=1,...)
		return index;
	}
	
	public short getYIndex(final String move) {
		// get the number part and convert it into the y-coordinate of the board
		short index = -1;
		if (move != null && move.length() == 2) {
			if (Character.isDigit(move.charAt(0))) {
				index = Short.valueOf(move.substring(0, 1));
			} else {
				index = Short.valueOf(move.substring(1, 2));
			}
		}
		return --index;	//"1"=0,"2"=1,...
	}
	
	private void makeTheMove(final int x, final int y) {
		// update the board
		this.board[x][y] = this.currentPiece;
		// capture opponent's piece if exists
		capturePiece(x, y);
		// switch turn after complete one move
		switchPlayer();
	}
	
	private void switchPlayer() {
		if (this.currentPiece == this.PIECE_LIGHT) {
			this.currentPiece = this.PIECE_DARK;
		} else {
			this.currentPiece = this.PIECE_LIGHT;
		}
	}
	
	private void capturePiece(final int x, final int y) {
		// check and capture opponent's piece (if any)
		hasPieceToCaptureHorizontally(x, y, true);
		hasPieceToCaptureVertically(x, y, true);
		hasPieceToCaptureLeftDiagonally(x, y, true);
		hasPieceToCaptureRightDiagonally(x, y, true);
	}
	
	public boolean hasPieceToCaptureHorizontally(final int x, final int y, final boolean isCapturePieces) {
		// locate left-most x-coordinate
		int horizontal_leftmost_x = x;
		boolean hasOpponentPiece = false;
		for (int i = (x - 1); i >= 0; i--) {
			if (this.board[i][y] == this.PIECE_NIL) {
				break;
			} else if (this.board[i][y] == this.getOppositePlayer()) {
				hasOpponentPiece = true;
			}
			if (this.board[i][y] == this.getCurrentPlayer()) {
				if (hasOpponentPiece) {
					horizontal_leftmost_x = i;
				}
				break;
			}
		}
		// locate right-most x-coordinate
		int horizontal_rightmost_x = x;
		boolean hasOpponentPiece2 = false;
		for (int i = (x + 1); i < this.NUMBER_OF_COLUMN; i++) {
			if (this.board[i][y] == this.PIECE_NIL) {
				break;
			} else if (this.board[i][y] == this.getOppositePlayer()) {
				hasOpponentPiece2 = true;
			}
			if (this.board[i][y] == this.getCurrentPlayer()) {
				if (hasOpponentPiece2) {
					horizontal_rightmost_x = i;
				}
				break;
			}
		}
		// capture all opponent's piece(s) in between
		if (horizontal_leftmost_x != horizontal_rightmost_x) {
			if (isCapturePieces) {
				for (int i = horizontal_leftmost_x; i <= horizontal_rightmost_x; i++) {
					this.board[i][y] = this.currentPiece;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasPieceToCaptureVertically(final int x, final int y, final boolean isCapturePieces) {
		// locate up-most y-coordinate
		int vertical_upmost_y = y;
		boolean hasOpponentPiece = false;
		for (int j = (y - 1); j >= 0; j--) {
			if (this.board[x][j] == this.PIECE_NIL) {
				break;
			} else if (this.board[x][j] == this.getOppositePlayer()) {
				hasOpponentPiece = true;
			} else if (this.board[x][j] == this.getCurrentPlayer()) {
				if (hasOpponentPiece) {
					vertical_upmost_y = j;
				}
				break;
			}
		}
		// locate down-most y-coordinate
		int vertical_downmost_y = y;
		boolean hasOpponentPiece2 = false;
		for (int j = (y + 1); j < this.NUMBER_OF_ROW; j++) {
			if (this.board[x][j] == this.PIECE_NIL) {
				break;
			} else if (this.board[x][j] == this.getOppositePlayer()) {
				hasOpponentPiece2 = true;
			} else if (this.board[x][j] == this.getCurrentPlayer()) {
				if (hasOpponentPiece2) {
					vertical_downmost_y = j;
				}
				break;
			}
		}
		// capture all opponent's piece(s) in between
		if (vertical_upmost_y != vertical_downmost_y) {
			if (isCapturePieces) {
				for (int j = vertical_upmost_y; j <= vertical_downmost_y; j++) {
					this.board[x][j] = this.currentPiece;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasPieceToCaptureLeftDiagonally(final int x, final int y, final boolean isCapturePieces) {
		// locate the left up-most x,y coordinate
		int diagonal_left_upmost_x = x;
		int diagonal_left_upmost_y = y;
		boolean hasOpponentPiece = false;
		for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
			int temp_x = x - k;
			int temp_y = y - k;
			if (temp_x >= 0 && temp_y >= 0) {
				if (this.board[temp_x][temp_y] == this.PIECE_NIL) {
					break;
				} else if (this.board[temp_x][temp_y] == this.getOppositePlayer()) {
					hasOpponentPiece = true;
					continue;
				} else if (this.board[temp_x][temp_y] == this.getCurrentPlayer()) {
					if (hasOpponentPiece) {
						diagonal_left_upmost_x = temp_x;
						diagonal_left_upmost_y = temp_y;
					}
					break;
				}
			}
		}
		// locate the right down-most x,y coordinate
		int diagonal_right_downmost_x = x;
		int diagonal_right_downmost_y = y;
		boolean hasOpponentPiece2 = false;
		for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
			int temp_x = x + k;
			int temp_y = y + k;
			if (temp_x < this.NUMBER_OF_COLUMN && temp_y < this.NUMBER_OF_ROW) {
				if (this.board[temp_x][temp_y] == this.PIECE_NIL) {
					break;
				} else if (this.board[temp_x][temp_y] == this.getOppositePlayer()) {
					hasOpponentPiece2 = true;
					continue;
				} else if (this.board[temp_x][temp_y] == this.getCurrentPlayer()) {
					if (hasOpponentPiece2) {
						diagonal_right_downmost_x = temp_x;
						diagonal_right_downmost_y = temp_y;
					}
					break;
				}
			}
		}
		// capture all opponent's piece(s) in between
		if (diagonal_left_upmost_x != diagonal_right_downmost_x) {
			if (isCapturePieces) {
				for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
					// capture from left up-most to right down-most
					int temp_x = diagonal_left_upmost_x + k;
					int temp_y = diagonal_left_upmost_y + k;
					this.board[temp_x][temp_y] = this.currentPiece;
					if (temp_x == diagonal_right_downmost_x) {
						// reach the right down-most
						break;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasPieceToCaptureRightDiagonally(final int x, final int y, final boolean isCapturePieces) {
		// locate the right up-most x,y coordinate
		int diagonal_right_upmost_x = x;
		int diagonal_right_upmost_y = y;
		boolean hasOpponentPiece = false;
		for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
			int temp_x = x + k;
			int temp_y = y - k;
			if (temp_x < this.NUMBER_OF_COLUMN && temp_y >= 0) {
				if (this.board[temp_x][temp_y] == this.PIECE_NIL) {
					break;
				} else if (this.board[temp_x][temp_y] == this.getOppositePlayer()) {
					hasOpponentPiece = true;
					continue;
				} else if (this.board[temp_x][temp_y] == this.getCurrentPlayer()) {
					if (hasOpponentPiece) {
						diagonal_right_upmost_x = temp_x;
						diagonal_right_upmost_y = temp_y;
					}
					break;
				}
			}
		}
		// locate the left down-most x,y coordinate
		int diagonal_left_downmost_x = x;
		int diagonal_left_downmost_y = y;
		boolean hasOpponentPiece2 = false;
		for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
			int temp_x = x - k;
			int temp_y = y + k;
			if (temp_x >= 0 && temp_y < this.NUMBER_OF_ROW) {
				if (this.board[temp_x][temp_y] == this.PIECE_NIL) {
					break;
				} else if (this.board[temp_x][temp_y] == this.getOppositePlayer()) {
					hasOpponentPiece2 = true;
					continue;
				} else if (this.board[temp_x][temp_y] == this.getCurrentPlayer()) {
					if (hasOpponentPiece2) {
						diagonal_left_downmost_x = temp_x;
						diagonal_left_downmost_y = temp_y;
					}
					break;
				}
			}
		}
		// capture all opponent's piece(s) in between
		if (diagonal_right_upmost_x != diagonal_left_downmost_x) {
			if (isCapturePieces) {
				for (int k = 1; k < this.NUMBER_OF_COLUMN; k++) {
					// capture from right up-most to left down-most
					int temp_x = diagonal_right_upmost_x - k;
					int temp_y = diagonal_right_upmost_y + k;
					this.board[temp_x][temp_y] = this.currentPiece;
					if (temp_x == diagonal_left_downmost_x) {
						// reach the left down-most
						break;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Return the status of the game
	 * @return boolean
	 */
	public boolean isGameEnd() {
		// The game ends when either
		// 1. neither player can make a valid move
		boolean hasValidMoveToMake = false;
		for (short x = 0; x < NUMBER_OF_COLUMN; x++) {
			for (short y = 0; y < NUMBER_OF_ROW; y++) {
				// check all available move
				if (this.board[x][y] == this.PIECE_NIL) {
					if (hasPieceToCapture(x, y)) {
						hasValidMoveToMake = true;
						break;
					}
				}
			}
		}
		// 2. the board is full
		boolean isBoardFull = (getNumberOfPiece(this.PIECE_NIL) == 0);
		// return whether this game is ended or not
		return (!hasValidMoveToMake || isBoardFull);
	}
	
	private boolean hasPieceToCapture(final int x, final int y) {
		return (hasPieceToCaptureHorizontally(x, y, false) || hasPieceToCaptureVertically(x, y, false) || hasPieceToCaptureLeftDiagonally(x, y, false) || hasPieceToCaptureRightDiagonally(x, y, false));
	}
	
	/**
	 * Get total number of piece on the board for specific disc
	 * @param disc
	 * @return
	 */
	public int getNumberOfPiece(char disc) {
		int counter = 0;
		for (short x = 0; x < NUMBER_OF_COLUMN; x++) {
			for (short y = 0; y < NUMBER_OF_ROW; y++) {
				if (this.board[x][y] == disc) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	/**
	 * 
	 * @return String - Game result
	 */
	public String gameResult() {
		String message = "No further moves available\n";
		int numberOfDark = getNumberOfPiece(this.PIECE_DARK);
		int numberOfLight = getNumberOfPiece(this.PIECE_LIGHT);
		char winner = this.PIECE_NIL;
		if (numberOfDark > numberOfLight) {
			winner = this.PIECE_DARK;
		} else if (numberOfDark < numberOfLight) {
			winner = this.PIECE_LIGHT;
		}
		if (winner != this.PIECE_NIL) {
			message += "Player '" + winner + "' wins ( " + numberOfDark + " vs " + numberOfLight + " )";
		} else {
			message += "Game draw ( " + numberOfDark + " vs " + numberOfLight + " )";
		}
		return message;
	}
	
	/**
	 * Get the player who is in his/her turn in this round
	 * @return
	 */
	public char getCurrentPlayer() {
		return this.currentPiece;
	}
	
	/**
	 * Get the player who is not in his/her turn in this round
	 * @return
	 */
	public char getOppositePlayer() {
		if (this.currentPiece == this.PIECE_DARK) {
			return this.PIECE_LIGHT;
		} else if (this.currentPiece == this.PIECE_LIGHT) {
			return this.PIECE_DARK;
		} else {
			return this.PIECE_NIL;
		}
	}
	
}
