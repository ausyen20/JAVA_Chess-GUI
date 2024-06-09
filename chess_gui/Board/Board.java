package chessgui.Board;
import java.util.*;

import chessgui.Game.Game;
import chessgui.Pieces.*;

public class Board {
	public static int rows = 5;
	public static int cols = 5;
	
	public int[][] matrice;
	public Piece[][] pieces;
	public Piece lastPieceMoved;
	public Move lastMove;
	public Piece killed;
	
	public Stack<Move> lastMoves = new Stack<>();
	public Stack<Piece> deadPieces = new Stack<>();
	public List<Piece> piecesList = new ArrayList<Piece>();
	
	public Board() {
		//Setting pieces on a specific square on the grid
		matrice = new int[rows][cols];
		pieces = new Piece[rows][cols];
	}
	
	//Setting each piece base on their coordinates (x and y)
	public void setPieceOnBoard(int x,int y,Piece piece) {
		if(piece != null) {
			matrice[x][y] = piece.getValueInTheboard();
			pieces[x][y] = piece;
			piecesList.add(piece);			
		}else {
			matrice[x][y] = 0;
			pieces[x][y] = null;
		}
	}
	//Update a piece and recording it's lastMoves, killing piece on the same square
	public void updatePieces(int beginX,int beginY,int endX,int endY,Piece piece) {
		lastMove = new Move(beginX, beginY, endX, endY, piece);
		lastMoves.add(lastMove);
		if(pieces[endX][endY] != null) {
			killed = pieces[endX][endY];
			deadPieces.add(killed);
			piecesList.remove(killed);
			Game.AllPieces.remove(killed);
			Game.fillPieces();
		}else {
			deadPieces.add(null);
		}
		matrice[beginX][beginY] = 0;
		matrice[endX][endY] =  piece.getValueInTheboard();
		pieces[beginX][beginY] = null;
		pieces[endX][endY] = piece;
	}
	
	//When a piece is no longer available on board, then pop() the piece out of the board, Remove piece's available movement
	//After all actions are done, then changeside 
	public void undoMove() {
		//Ensure the piece selected and moved contain a last moved.
		//Update their move to their current x and y instead of last moved x and y
		//If there is a dead piece, record it to deadpieces
		if(!lastMoves.isEmpty()) {
			Move move = lastMoves.pop();
			Piece dead = deadPieces.pop();
			matrice[move.beginX][move.beginY] = move.getPiece().getValueInTheboard();
			pieces[move.beginX][move.beginY] = move.getPiece();
			
			move.getPiece().setXcord(move.beginX);
			move.getPiece().setYcord(move.beginY);
			
			//If there is a dead piece, set their their piece to last moved position. Set to dead
			if(dead != null) {
				Game.AllPieces.add(dead);
				Game.fillPieces();
				matrice[move.endX][move.endY] = dead.getValueInTheboard();
				pieces[move.endX][move.endY] = dead;
				dead.setXcord(move.getEndX());
				dead.setYcord(move.getEndY());
			}
			else {
				matrice[move.endX][move.endY] = 0;
				pieces[move.endX][move.endY] = dead;
			}
			Game.Swap();
		}
		return;
	}
	
	public Piece getPiece(int x,int y) {
		return pieces[x][y];
	}


	public int[][] getGrid() {
		return matrice;
	}
	

	public void setGrid(int[][] grid) {
		this.matrice = grid;
	}
	
	public int getXY(int x,int y) {
		return matrice[x][y];
	}
	
	public void setXY(int x,int y,int value) {
		matrice[x][y] = value ;
	}
	
	// Set new board base on x and y lengths
	public Board NewBoard() {
		Board b = new Board();
		for(int i=0; i<5; i++) {
			for(int j=0; j<4; j++) {
				if(this.getPiece(i, j) != null) {
					b.setPieceOnBoard(i, j, this.getPiece(i, j).getClone());
				}
			}
		}
		return b;
	}
	
	// Print board base on x and y lengths
	public void printBoard() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<4; j++) {
				System.out.print(matrice[j][i] +  "  ");
			}
			System.out.println();
		}
	}
	
	
}
	
	

