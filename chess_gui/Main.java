package chessgui;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

import chessgui.Frame.Frame;

public class Main {
	//This Main runs and pass to program to start running. Pass to Frame.java to proceed creating display for this program.
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}
}