package org.torpidity.jogl.environment;

import javax.media.opengl.*;
import java.nio.FloatBuffer;

/**
 * <p>
 * This is a static Light-controlling class. Once the engine is implemented for
 * a game or something, this code really should be worked directly into the
 * game's core directly.
 * </p>
 * <p>
 * Almost every function requires a lightNumber value. The options for this
 * value range from GL.GL_LIGHT0 to GL.GL_LIGHT8.
 * </p>
 * 
 * <p>
 * <b>Sample Light calls:</b>
 * <ul>
 * <li>Light.init(drawable);</li>
 * <li>Light.enable(Light.L0);</li>
 * <li>Light.setHighlight(Light.L0,1f,1f,1f);</li>
 * <li>Light.setShadow(Light.L0,0f,0f,0f);</li>
 * <li>Light.setLocation(Light.L0,0f,1f,-15f,Light.POSITIONAL);</li>
 * </ul>
 * 
 * @author Kevin
 * @version 1.1
 */
public class Light {
	private static GL gl;
	private static float[] diffuse; // Shadow
	private static float[] ambient; // Highlights
	private static float[] position; // Source position of light
	private static float[] global_ambient; // Global lighting
	
	public static final int L0 = GL.GL_LIGHT0;
	public static final int L1 = GL.GL_LIGHT1;
	public static final int L2 = GL.GL_LIGHT2;
	public static final int L3 = GL.GL_LIGHT3;
	public static final int L4 = GL.GL_LIGHT4;
	public static final int L5 = GL.GL_LIGHT5;
	public static final int L6 = GL.GL_LIGHT6;
	public static final int L7 = GL.GL_LIGHT7;

	public static final float DIRECTIONAL = 0f;
	public static final float POSITIONAL = 1f;

	/**
	 * <p>
	 * Initialize the static Light class. Required before any function calls.
	 * </p>
	 * 
	 * @param drawable
	 */
	public static void init(GLAutoDrawable drawable) {
		gl = drawable.getGL();
		gl.glEnable(GL.GL_LIGHTING);
	}

	/**
	 * <p>
	 * Turn on a light
	 * </p>
	 * 
	 * @param lightNumber
	 */
	public static void enable(int lightNumber) {
		gl.glEnable(lightNumber);
	}

	/**
	 * <p>
	 * Turn off a light
	 * </p>
	 * 
	 * @param lightNumber
	 */
	public static void disable(int lightNumber) {
		gl.glDisable(lightNumber);
	}

	/**
	 * <p>
	 * Set the shadow color for a light using 0-255 values for RGB.
	 * </p>
	 * 
	 * @param lightNumber
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setShadow(int lightNumber, int red, int green, int blue) {
		setShadow(lightNumber, (float) red / 255f, (float) green / 255f,
				(float) blue / 255f);
	}

	/**
	 * <p>
	 * Set the shadow color for a light using 0f-1f values for RGB.
	 * </p>
	 * 
	 * @param lightNumber
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setShadow(int lightNumber, float red, float green,
			float blue) {
		ambient = new float[] { red, green, blue, 1f };
		gl.glLightfv(lightNumber, GL.GL_AMBIENT, FloatBuffer.wrap(ambient));
	}

	/**
	 * <p>
	 * Set the highlight color for a light using 0-255 values for RGB.
	 * </p>
	 * 
	 * @param lightNumber
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setHighlight(int lightNumber, int red, int green,
			int blue) {
		setHighlight(lightNumber, (float) red / 255f, (float) green / 255f,
				(float) blue / 255f);
	}

	/**
	 * <p>
	 * Set the highlight color for a light using 0f-1f values for RGB.
	 * </p>
	 * 
	 * @param lightNumber
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setHighlight(int lightNumber, float red, float green,
			float blue) {
		diffuse = new float[] { red, green, blue, 1f };
		gl.glLightfv(lightNumber, GL.GL_DIFFUSE, FloatBuffer.wrap(diffuse));
	}

	/**
	 * <p>
	 * Set the (x,y,z) location and light type for a light. Locations must be
	 * float values. The light type should be either Light.DIRECTIONAL or
	 * Light.POSITIONAL.
	 * </p>
	 * 
	 * @param lightNumber
	 * @param x
	 * @param y
	 * @param z
	 * @param type
	 */
	public static void setLocation(int lightNumber, float x, float y, float z,
			float type) {
		position = new float[] { x, y, z, type };
		gl.glLightfv(lightNumber, GL.GL_POSITION, FloatBuffer.wrap(position));
	}

	/**
	 * <p>
	 * Set the global lighting level using 0-255 values for RGB
	 * </p>
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setGlobalLighting(int red, int green, int blue) {
		setGlobalLighting((float) red / 255f, (float) green / 255f,
				(float) blue / 255f);
	}

	/**
	 * <p>
	 * Set the global lighting level using 0f-1f values for RGB
	 * </p>
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setGlobalLighting(float red, float green, float blue) {
		global_ambient = new float[] { red, green, blue };
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(global_ambient));
	}

}