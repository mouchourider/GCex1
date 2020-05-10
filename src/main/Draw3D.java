//Franck Schwartz 329863237
//Raphael Abenmoha 337689103
package main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Draw3D {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame myFrame = new Frame("Exercise1");
		MyCanvas myCanvas = new MyCanvas();
		myFrame.add(myCanvas);

		WindowAdapter myWindowAdapter = new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		
		myFrame.addWindowListener(myWindowAdapter);
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
