package chessgui.Frame;

import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	public static int Height = 400;
	public static int Width = 320;
	
	/* Inside this section, it is setting the display of the program.
	 * I have fixed size for height and width, for fitting the chess board perfectly.
	 * Using the dimension, to setPreferredSize(Width, Height).
	 * And pack(); cropping away unnecessary parts (blank spaces)
	 * */
	public Frame() {
		this.setContentPane(new  Panel());
		this.setTitle("Chess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.getContentPane().setPreferredSize(new Dimension(Width ,Height));
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
