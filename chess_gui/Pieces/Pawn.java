package chessgui.Pieces;
import chessgui.Board.Board;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, boolean iswhite, Board board, int value) {
		super(x, y, iswhite, board, value);
		//firstMove = true;
		this.pieceImage = PieceImages.PAWN;
	}
	
	public void intializeSide(int value){
		super.intializeSide(value);
		if(isWhite()) {
			image = PieceImages.wp;
		}
		else {
			image = PieceImages.bp;
		}
	}

	public boolean canMove(int x, int y, Board board) {			

		//can't move diagonal if it isn't  for capture
		if (xCord != x && board.getPiece(x, y) == null) {
			return false;
		}

		if (isWhite) {
			//move forward
			if (x == xCord && y == yCord - 1 && board.getPiece(x, y) == null) {
				return true;
				
			}
			
			return capture(x, y, board);
		}
		if (!isWhite) {
			if (x == xCord && y == yCord + 1 && board.getPiece(x, y) == null) {
				return true;
			}
		
			
			return capture(x, y, board);
			
		}
		
		return false;
	}

	//Capture if when a piece is diagonally lining with the pawn
	public boolean capture(int x, int y, Board board) {
		if(isWhite()) {
			//capture diagonally to right
			if (y == yCord - 1 && x == xCord + 1) {
				return true;
			}
			//capture diagonally to left
			if (y == yCord - 1 && x == xCord - 1) {
				return true;
			}
		}
		else {
			// same rule apply for black pawn
			if (y == yCord + 1 && x == xCord + 1) {
				return true;
			}
			if (y == yCord + 1 && x == xCord - 1) {
				return true;
			}
		}
		return false;
	}
	
	
}