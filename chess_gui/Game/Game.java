package chessgui.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chessgui.Board.Board;
import chessgui.Board.Move;
import chessgui.Pieces.*;

public class Game {
	/* In this section, to use both black and white pieces, and their kings as indicators to set up functions.*/
	public static Board board = new Board();
	static King wk;
	static King bk;
	static ArrayList<Piece> wPieces = new ArrayList<Piece>();
	static ArrayList<Piece> bPieces = new ArrayList<Piece>();
	static boolean player = true;
	public Piece active = null;
	public static boolean drag = false;
	public static ArrayList<Piece> AllPieces = new ArrayList<Piece>();
	static List<Move> allPlayersMove = new ArrayList<Move>();
	public static List<Move> allEnemysMove = new ArrayList<Move>();
	public static boolean gameOver = false;
	//PieceImage will print each piece's image based accordingly to their matching piece.
	//FEN position is used here, to set up the pieces' initial line-up. Such 'knbr' = King, Knight, Bishop, Rook in first row, '/' will continue on following rows 
	public Game() {
		new PieceImages();
		loadFenPosition("knbr/p3/4/3P/RBNK w - 0 1"); 
		start();
	}
	
	public void start() {
		fillPieces();
		generatePlayer1Moves(board);
		generatePlayer2Moves(board);
		checkPlayersLegalMoves();
	}
	// Run all draw Board and Piece functions
	public void draw(Graphics g, int x, int y, JPanel panel) {
		drawBoard(g);
		drawPiece(g, panel);
		drawPossibleMoves(g, panel);
		drag(active, x, y, g, panel);
		drawKingInCheck(player, g, panel);
	}
	
	//Setting up and collecting all moves for player 1
	public static void generatePlayer1Moves(Board board) {
		allPlayersMove = new ArrayList<Move>();
		for (Piece p : AllPieces) {
			if (p.isWhite() == player) {
				p.fillLegalMoves(board);
				allPlayersMove.addAll(p.getMoves());
				
			}
		}
	}

	//Setting and collecting all moves for player 2
	public static void generatePlayer2Moves(Board board) {
		allEnemysMove = new ArrayList<Move>();
		
		for (Piece p : AllPieces) {
			if (p.isWhite() != player) {
				p.fillLegalMoves(board);
				allEnemysMove.addAll(p.getMoves());
			}	
		}
		
	}

	// Switch turn after each player took their turn
	public static void Swap() {
		
		player = !player;
		generatePlayer2Moves(board);
		generatePlayer1Moves(board);
		checkPlayersLegalMoves();
		checkMate();
	}

	
	//FEN(Forsyth¡VEdwards Notation) is standard notation for describing a particular board position of a chess game.
	//Deploying each piece base on their letters (case-senstivity), and numbers represent empty sqares
	public void loadFenPosition(String fenString) {
		String[] parts = fenString.split(" ");
		String position = parts[0];
		int row = 0, col = 0;
		for (char c : position.toCharArray()) {
			if (c == '/') {
				row++;
				col = 0;
			}
			if (Character.isLetter(c)) {
				if (Character.isLowerCase(c)) {
					addToBoard(col, row, c, false);
				} else {
					addToBoard(col, row, c, true);
				}
				col++;
			}
			if (Character.isDigit(c)) {
				col += Integer.parseInt(String.valueOf(c));
			}
		}

		if (parts[1].equals("w")) {
			player = true;
		} else {
			player = false;
		}
	}
	
	
	//Piece at their coordinate (x, y), check if the Piece is avaliable to be use at that position
	public void selectPiece(int x, int y) {
				if (active == null && board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == player) {
					active = board.getPiece(x, y);
				}
			}
			public void move(int x, int y) {
				if (active != null) {
					if (active.makeMove(x, y, board)) {
						Swap();
						active = null;
					}
					drag = false;
				}
			}
			
	// Run over every pieces, and collect necessary piece on the board
	public static void fillPieces() {
			wPieces = new ArrayList<Piece>();
			bPieces = new ArrayList<Piece>();
				for (Piece p : AllPieces) {
					if (p.isWhite()) {
						wPieces.add(p);
					} else {
						bPieces.add(p);
					}
			}
	}
	
	// Add every piece base on each single case, adding each piece. Using the values at the end,
	// to indicator if it is a white or black piece
	public void addToBoard(int x, int y, char c, boolean isWhite) {
	switch (String.valueOf(c).toUpperCase()) {
	case "R":
		AllPieces.add(new Rook(x, y, isWhite, board, isWhite ? 1 : -1));
		break;
	case "N":
		AllPieces.add(new Knight(x, y, isWhite, board, isWhite ? 1 : -1));
		break;
	case "B":
		AllPieces.add(new Bishop(x, y, isWhite, board, isWhite ? 1 : -1));
		break;
	case "P":
		AllPieces.add(new Pawn(x, y, isWhite, board, isWhite ? 1 : -1));
		break;
	case "K":
		King king = new King(x, y, isWhite, board, isWhite ? 1 : -1);
		AllPieces.add(king);
		if (isWhite) {
			wk = king;
		} else {
			bk = king;
		}
		break;
		}
	}

	//checkMate run over each piece on the board, when a player has checked their king. And it will checklegalmoves() and if no move avaliable announce winner.
	public static void checkMate() {
		if (player) {
			for(Piece p :wPieces) {
				if (!p.getMoves().isEmpty()) {
					return;
				}
			}
			
			if (wk.isInCheck()) {
					for (Piece Ep : bPieces) {
						for (Move Em : Ep.getMoves())
						if(wk.canMove(Ep.getXcord(), Ep.getYcord(), board) && wk.makeMove(Em.getBeginX(), Em.getBeginY(), board) 
						&& wk.makeMove(Em.getEndX(), Em.getEndY(), board)) {
							gameOver = false;
						}else {
							gameOver = true;
						}
					}
					if (gameOver == true) {
						JOptionPane.showMessageDialog(null, "check mate " + (!player ? "white" : "black") + " wins");
					}else {
						JOptionPane.showMessageDialog(null, "stalemate ");
					}
						
			} else {
				JOptionPane.showMessageDialog(null, "stalemate ");
			}
			
		} else {
			for(Piece p :bPieces) {
				if (!p.getMoves().isEmpty()) {
					return;
				}
			}
			if (bk.isInCheck()) {
				for (Piece Ep : wPieces) {
					for (Move Em : Ep.getMoves())
					if(bk.canMove(Ep.getXcord(), Ep.getYcord(), board) && bk.makeMove(Em.getBeginX(), Em.getBeginY(), board) 
					&& bk.makeMove(Em.getEndX(), Em.getEndY(), board)) {
						gameOver = false;
					}else {
						gameOver = true;
					}
				}
				if (gameOver == true) {
					JOptionPane.showMessageDialog(null, "check mate " + (!player ? "white" : "black") + " wins");
				}else {
					JOptionPane.showMessageDialog(null, "stalemate ");
				}
					
		} else {
				JOptionPane.showMessageDialog(null, "stalemate ");

			}
		}	
}
		
	//Check each player move, base on each's turn.
	public static void checkPlayersLegalMoves() {
		List<Piece> pieces = null;
		if (player) {
			pieces = wPieces;
		} else {
			pieces = bPieces;
		}
		for (Piece p : pieces) {
			checkLegalMoves(p);
		}
	}

	//The function checks for legal of moves for pieces
	public static void checkLegalMoves(Piece piece) {
		List<Move> eachmoves = new ArrayList<Move>();
		Board clonedBoard = board.NewBoard();	//Make a new Board
		Piece clonedActive = piece.getClone(); //Make the current moving piece a clone (Active)

		//At the king's position to check if the any of the enemy piece can attack it.
		for (Move move : clonedActive.getMoves()) {
			clonedBoard = board.NewBoard();
			clonedActive = piece.getClone();
			clonedActive.canMove(move.getEndX(), move.getEndY(), clonedBoard); //Make the piece move to desire x and y
			List<Piece> enemyPieces = new ArrayList<Piece>(); 
			Piece king = null;

			if (piece.isWhite()) {
				enemyPieces = bPieces;
				king = wk;
			} else {
				enemyPieces = wPieces;
				king = bk;
			}
			
			// For each enemy pieces
			for (Piece enemyP : enemyPieces) {
				Piece clonedEnemyPiece = enemyP.getClone(); //Make every enemy piece a clone piece
				clonedEnemyPiece.fillLegalMoves(clonedBoard); //Fill all avaliable moves of that piece

				for (Move lastMove : clonedEnemyPiece.getMoves()) {
					//Recording all pieces' last moves (x and y), to ensure each is enable to move for the next round and not cause an error in the program.
					//If it doesn't set their last move, other function like Board.undoMove() could overlapped and cause an error in the program.
					if (!(clonedActive instanceof King) && lastMove.getEndX() == king.getXcord()
							&& lastMove.getEndY() == king.getYcord()
							&& clonedBoard.getGrid()[enemyP.getXcord()][enemyP.getYcord()] 
							== enemyP.getValueInTheboard()) {	
						eachmoves.add(move);
						//Ensure piece is still alive on board base on their value on board. If not return false
					} else if (clonedActive instanceof King) {
						if (lastMove.getEndX() == clonedActive.getXcord() 
								&& lastMove.getEndY() == clonedActive.getYcord()
								&& clonedBoard.getGrid()[enemyP.getXcord()][enemyP.getYcord()] 
								== enemyP.getValueInTheboard()) {
						eachmoves.add(move);
						}
					}
					
				}		
			}
		}
		for (Move move : eachmoves) {
			piece.getMoves().remove(move);
		}
	}
	
	//Surround the Kings with red box, when it is in checked. Indicate that king will eliminate if not moved.
	public void drawKingInCheck(boolean isWhite, Graphics g, JPanel panel) {
		g.setColor(Color.RED);
		if (isWhite && wk.isInCheck()) {
			g.drawRect(wk.getXcord() * Piece.size, wk.getYcord() * Piece.size, Piece.size, Piece.size);
		} else if (bk.isInCheck()) {
			g.drawRect(bk.getXcord() * Piece.size, bk.getYcord() * Piece.size, Piece.size, Piece.size);
		}
		panel.revalidate();
		panel.repaint();
	}

	
	//Display all available moves based on the current piece
	public void drawPossibleMoves(Graphics g, JPanel panel) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		if (active != null) {
			active.showMoves(g2, panel);
		}

	}
	
	//Draw all pieces
	public void drawPiece(Graphics g, JPanel panel) {
		for (Piece p : AllPieces) {
			p.draw(g, false, panel);
		}

	}

	//Drag piece to a destination x and y, and it will re-draw the piece on that square
	public void drag(Piece piece, int x, int y, Graphics g, JPanel panel) {
			if (piece != null && drag == true) {
				piece.draw2(g, player, x, y, panel);
			}
	}
		// Draw the boards base on their given x and y lengths, and setColor on each square
	public void drawBoard(Graphics g) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if ((i + j) % 2 == 1) {
						g.setColor(new Color(189, 97, 64));
					} else {
						g.setColor(new Color(219, 138, 81));
					}
					g.fillRect(i * Piece.size, j * Piece.size, Piece.size, Piece.size);
				}
			}
	}
	
	
}
