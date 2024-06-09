package chessgui.Pieces;
import chessgui.Board.Board;
import chessgui.Board.Move;
import chessgui.Game.Game;

public class King extends Piece {
	
	
	public King(int x, int y, boolean iswhite, Board board, int value) {
		super(x, y, iswhite, board, value);
		this.pieceImage = PieceImages.KING;
	}
	public void intializeSide(int value){
		super.intializeSide(value);
		if(isWhite()) {
			image = PieceImages.wk;
		}
		else {
			image = PieceImages.bk;
		}
	}
	
	//King can only move all 1 square around the king, including diagonally 1 square from king's original square
	@Override
	public boolean canMove(int x, int y, Board board) {
		
		int i = Math.abs(xCord - x);
		int j = Math.abs(yCord - y);
		
		if( j == 1 && i == 1 || (i+j) == 1) {
			
			if(board.getPiece(x, y) == null) {
				return true;
			}
			else {
				return board.getPiece(x, y).isWhite() != isWhite();				
			}
		}
		
		return false;
	
	}
	
	
	//Check if a king is in Check, using all EnemysMove to see if there any of their moves threaten the king
	public boolean isInCheck() {
		for(Move m: Game.allEnemysMove) {
			if(m.getEndX() == xCord && m.getEndY() == yCord) {
				return true;
			}
		}
		return false;
	}
	
	public boolean Dead() {
		if (!alive()) {
			return true;
		}
	return false;
	}
	
	
}