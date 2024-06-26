package chessgui.Frame;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chessgui.Game.Game;
import chessgui.Pieces.*;


public class Panel extends JPanel{
	private static final long serialVersionUID = 1L;
	Game game;
	int ti,tj;
	public static int xx, yy;
	JPanel panel = this;
	
	Panel(){
		this.setFocusable(true);
		this.addMouseListener(new Listener());
		this.addMouseMotionListener(new Listener());
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 37) {
					//Mouse left click will do following action
					Game.board.undoMove();
				}
			}
		});
		game = new Game();

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g, xx, yy, this);
	}
	

	class Listener extends MouseAdapter{
		@Override
		//when mouse click on a piece get its x and y
		public void mouseClicked(MouseEvent e) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				int x = e.getX()/Piece.size;
				int y = e.getY()/Piece.size;
				Game.drag = false;
				game.active = null;
				game.selectPiece(x, y);
				revalidate();
				repaint();
			}
		}
		
		//when moved a piece, get that square x and y of its destination
		@Override
		public void mouseMoved(MouseEvent e) {
			ti = e.getX()/Piece.size;
			tj = e.getY()/Piece.size;
			if(Game.board.getPiece(ti, tj) != null)  {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			revalidate();
			repaint();
		}
		//when dragged a piece, get that square x and y of its destination
		@Override
		public void mouseDragged(MouseEvent e) {
			if(!Game.drag && game.active != null) {
				game.active = null;
			}
			if(SwingUtilities.isLeftMouseButton(e)) {
				game.selectPiece(e.getX()/Piece.size, e.getY()/Piece.size);
				Game.drag = true;
				xx = e.getX();
				yy = e.getY();				
			}
			revalidate();
			repaint();
		}
		
		//When mouse is clicked on a square, get that x and y
		@Override
		public void mouseReleased(MouseEvent e) {
			int x = e.getX() / Piece.size;
			int y = e.getY() / Piece.size;
			game.move(x, y);
			revalidate();
			repaint();
		}

	}
}
