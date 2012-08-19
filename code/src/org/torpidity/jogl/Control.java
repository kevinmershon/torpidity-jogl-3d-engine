package org.torpidity.jogl;

import org.torpidity.jogl.environment.Camera;
import net.java.games.input.*;

/**
 * <p>
 * This class handles all user input, and sends updates to the Camera.
 * </p>
 * 
 * @author Kevin
 * @version 1.1
 */
public class Control implements Runnable {
	private Screen screen;
	private Mouse mouse;
	private Controller keyboard;
	private int delta;

	public Control(Screen screen) {
		this.screen = screen;
		ControllerEnvironment ce = ControllerEnvironment
				.getDefaultEnvironment();
		Controller[] controllers = ce.getControllers();
		for (int i = 0; i < controllers.length; i++) {
			if (controllers[i].getType() == Controller.Type.MOUSE) {
				System.out.println("Mouse detected");
				mouse = (Mouse) controllers[i];
			} else if (controllers[i].getType() == Controller.Type.KEYBOARD) {
				try {
					System.out.println("Keyboard detected");
					keyboard = (Keyboard)controllers[i];
				} catch (ClassCastException cce) {
					System.out.println("Special Keyboard");
					keyboard = controllers[i];
				}
				/**
				 * Search for a match to W and set the keyboard delta.
				 * Not all keyboards are created equally.
				 */
				Component[] keyAxes = keyboard.getComponents();
				for (int j=0; j< keyAxes.length; j++) {
					if (keyAxes[i].getName().equals("W")) {
						delta = 17-j;
					}
				}
			}
		}
		new Thread(this).start();
	}

	/**
	 * Run through all possible control interactions and trigger events
	 * accordingly.
	 */
	public void run() {
		// W is key 17, up is 110
		// A is key 30, left is 112
		// S is key 31, down is 115
		// D is key 32, right is 113
		// F10 is key 83
		// F11 is key 84, F12 is 85
		// Left Shift is key 42, Right is 54
		while (true) {
			keyboard.poll();
			Component[] keyAxes = keyboard.getComponents();

			boolean FORWARD = (keyAxes[17-delta].getPollData() == 1f || keyAxes[110-delta]
					.getPollData() == 1f);
			boolean BACKWARD = (keyAxes[31-delta].getPollData() == 1f || keyAxes[115-delta]
					.getPollData() == 1f);
			boolean LEFT = (keyAxes[30-delta].getPollData() == 1f || keyAxes[112-delta]
					.getPollData() == 1f);
			boolean RIGHT = (keyAxes[32-delta].getPollData() == 1f || keyAxes[113-delta]
					.getPollData() == 1f);
			boolean SHIFT = (keyAxes[42-delta].getPollData() == 1f || keyAxes[54-delta]
					.getPollData() == 1f);
			boolean F11 = (keyAxes[84-delta].getPollData() == 1f);
			boolean F12 = (keyAxes[85-delta].getPollData() == 1f);

			if (FORWARD)
				Camera.move(.3f);
			else if (BACKWARD)
				Camera.move(-.3f);
			else
				Camera.move(0f);

			if (RIGHT)
				Camera.strafe(.3f);
			else if (LEFT)
				Camera.strafe(-.3f);
			else
				Camera.strafe(0f);

			if (SHIFT)
				Camera.setBooster(3.5f);
			else
				Camera.setBooster(1f);

			if (F12)
				screen.toggleWireframe(true);
			else if (F11)
				screen.toggleWireframe(false);

			Mouse.Ball b = mouse.getBall();
			b.poll();
			Component[] mouseBallComponents = b.getComponents();
			Camera.spinZ(mouseBallComponents[0].getPollData() / 100f);
			Camera.spinY(-(mouseBallComponents[1].getPollData() / 100f));
			
			/*Mouse.Buttons btns = mouse.getButtons();
			btns.poll();
			Component[] mouseButtonComponents = btns.getComponents();
			for (int i = 0; i < mouseButtonComponents.length; i++) {
				if (mouseButtonComponents[i].getPollData() == 1f)
					System.out.println(mouseButtonComponents[i].getName() + " " + i);
			}*/

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

}
