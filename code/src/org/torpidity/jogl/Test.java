package org.torpidity.jogl;

import java.awt.Toolkit;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This is a tester class which demonstrates the proper way to load a
 * GLEventListener-implementing class and begin drawing things.
 * 
 * @author Kevin
 */
public class Test {

	public static void main(String[] args) {
		int width = (int) (Toolkit.getDefaultToolkit()).getScreenSize()
				.getWidth();
		int height = (int) (Toolkit.getDefaultToolkit()).getScreenSize()
				.getHeight();
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Torpidity JOGL Engine");
		shell.setBounds((width - 800) / 2, (height - 600) / 2, 800, 600);
		shell.setLayout(new FillLayout());
		
		final Screen screen = new Screen();
		 new Control(screen);
		 
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();

		/*
		screen.startAnimation();
		frame.getContentPane().add(screen.getCanvas());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				screen.stopAnimation();
				System.exit(0);
			}
		});*/
	}

}