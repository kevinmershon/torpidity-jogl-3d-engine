package org.torpidity.jogl;

import javax.media.opengl.*;

import org.torpidity.jogl.environment.*;
import org.torpidity.jogl.objects.*;
import org.torpidity.jogl.textures.*;

/**
 * This class extends GLAdapter, eliminating the necessity for openGL class
 * intermixed with Java object calls.
 * 
 * @author Kevin Mershon
 */
public class Screen extends GLAdapter {
	// These are just homemade objects
	private objTerrain land;
	private objWater water;
	//private objProceduralSkyDome clouds;
	private objSkyDome clouds;
	private objText text;
	private objText coords;
	private boolean useWireframe;

	/**
	 * Initialize objects. Don't draw.
	 */
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);

		/**
		 * Set up the environment controls.
		 */
		Camera.init();
		Camera.setLocation(0f, 30f, 0f);
		
		Light.init(drawable);
		Light.enable(Light.L0);
		Light.setLocation(Light.L0, 5, 40, 5, Light.DIRECTIONAL);

		Fog.init(drawable);
		Fog.enable();
		Fog.setColor(.8f, .8f, .9f, 1f);
		Fog.setStartDistance(100f);
		Fog.setEndDistance(250f);

		/**
		 * Now we create some objects.
		 */
		land = new objTerrain(1024, 256, 45);
		String[] files = new String[] { "/subtextures/sand.jpg",
				"/subtextures/rockstone.jpg", "/subtextures/reddirt.jpg",
				"/subtextures/grass.jpg" };
		land.loadTexture(new Texture(files));
		land.loadHeightMap("/canyon_square.jpg");
		land.createList();

		clouds = new objSkyDome(600);
		clouds.loadTexture(new Texture("/clouds.jpg", true));

		water = new objWater(1024, 32);
		water.loadTexture(new Texture("/water.jpg", false));
		water.setLocation(0f, 12f, 0f);

		text = new objText("Texture Mode - Press F12 for Wireframe", 5, 20);
		text.setColor(0, 0, 0, 255);

		coords = new objText("", 5, 590);
		coords.setColor(0, 0, 0, 255);
	}

	/**
	 * Draw objects. All interaction takes place here.
	 */
	public void display(GLAutoDrawable drawable) {
		super.display(drawable);

		Camera.flyModeUpdate();
		if (useWireframe) {
			Fog.disable();
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
		} else {
			Fog.enable();
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		}

		/**
		 * Note: Land must be drawn before water, or the water's blend functions
		 * won't work right.
		 */

		land.drawTextured();
		water.drawTextured();
		text.draw();
		coords.setText("X: " + (int) Camera.getX() + ", Y: "
				+ (int) Camera.getY() + ", Z: " + (int) Camera.getZ());
		coords.draw();
		//if (!useWireframe)
			clouds.drawTextured();
		//pc.drawTextured();
	}

	/**
	 * This function is used to handle the GLCanvas being resized.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45f, ((float) width) / ((float) height), 1f, 400f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	/**
	 * <p>
	 * Enable or disable wireframe mode.
	 * </p>
	 */
	public void toggleWireframe(boolean isOn) {
		useWireframe = isOn;
		if (useWireframe) {
			text.setText("Wireframe Mode - Press F11 for Texture Mode");
			text.setColor(255, 255, 255, 255);
			coords.setColor(255, 255, 255, 255);
		}
		if (!useWireframe) {
			text.setText("Texture Mode - Press F12 for Wireframe");
			text.setColor(255, 255, 255, 255);
			coords.setColor(255, 255, 255, 255);
		}
	}

}