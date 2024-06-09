package chessgui.Board;

import chessgui.Pieces.Piece;

public class Move{
	int beginX, beginY, endX, endY;
	Piece piece;
	public Move(int beginX, int beginY, int endX, int endY, Piece piece) {
		this.beginX = beginX;
		this.beginY = beginY;
		this.endX = endX;
		this.endY = endY;
		this.piece = piece;
	}
 
	//Collecting all x and y coordinates. This includes where the piece's x and y begins, and it ends.
	public int getBeginX() {
		return beginX;
	}

	public void setBeginX(int beginX) {
		this.beginX = beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public void setBeginY(int beginY) {
		this.beginY = beginY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	//return get the piece type and return
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	//Compare x and y, ensuring that program failed, if wrong inputs are entered during Pieces' movement
	public int compareTo(Move o) {
		if(endX == o.getEndX() && endY == o.getEndY()) {
			return 0;
		}
		return -1;
	}
}
