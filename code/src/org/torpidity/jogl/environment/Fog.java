package org.torpidity.jogl.environment;

import javax.media.opengl.*;
import java.nio.FloatBuffer;

/**
 * <p>
 * This is a static Fog-controlling class. Once the engine is implemented for a
 * game or something, this code really should be worked directly into the game's
 * core directly.
 * </p>
 * 
 * <p>
 * <b>Sample Fog calls:</b>
 * <ul>
 * <li>Fog.init(drawable);</li>
 * <li>Fog.enable();</li>
 * <li>Fog.setColor(.8f, .8f, .9f, 1f);</li>
 * <li>Fog.setStartDistance(100f);</li>
 * <li>Fog.setEndDistance(250f);</li>
 * </ul>
 * 
 * @author Kevin
 * @version 1.1
 */
public class Fog {
	private static GL gl;
	private static float[] color = new float[] { .5f, .5f, .5f, 0f };
	private static float startDistance = 10f;
	private static float endDistance = 100f;
	private static int mode = GL.GL_LINEAR;
	private static float density = .05f;
	private static int precision = GL.GL_DONT_CARE;

	/**
	 * <p>
	 * Initialize the static Fog class.
	 * </p>
	 * 
	 * @param drawable
	 */
	public static void init(GLAutoDrawable drawable) {
		gl = drawable.getGL();
	}

	public static void setColor(int red, int green, int blue, int alpha) {
		setColor((float) red / 255f, (float) green / 255f, (float) blue / 255f,
				(float) alpha / 255f);
	}

	public static void setColor(float red, float green, float blue, float alpha) {
		color = new float[] { red, green, blue, alpha };
		gl.glFogfv(GL.GL_FOG_COLOR, FloatBuffer.wrap(color));
		gl.glClearColor(red / 2, green / 2, blue / 2, 0f);
	}

	public static void enable(float sDi, float eDi, int m, float de, int p) {
		startDistance = sDi;
		endDistance = eDi;
		mode = m;
		density = de;
		precision = p;
		enable();
	}

	public static void enable() {
		setColor(color[0], color[1], color[2], 1f);
		setStartDistance(startDistance);
		setEndDistance(endDistance);
		setMode(mode);
		setDensity(density);
		setPrecision(precision);
		gl.glEnable(GL.GL_FOG);
	}

	public static void disable() {
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glDisable(GL.GL_FOG);
	}

	public static void setStartDistance(float distance) {
		startDistance = distance;
		gl.glFogf(GL.GL_FOG_START, distance);
	}

	public static void setEndDistance(float distance) {
		endDistance = distance;
		gl.glFogf(GL.GL_FOG_END, distance);
	}

	/**
	 * <p>
	 * Set the fog mode
	 * </p>
	 * 
	 * <p>
	 * <b>Options are:</b>
	 * <ul>
	 * <li>GL.GL_EXP</li>
	 * <li>GL.GL_EXP2</li>
	 * <li>GL.GL_LINEAR</li>
	 * </ul>
	 * 
	 * @param modeVal
	 */
	public static void setMode(int modeVal) {
		mode = modeVal;
		gl.glFogi(GL.GL_FOG_MODE, mode);
	}

	public static void setDensity(float densityVal) {
		// Only matters for EXP and EXP2 modes
		density = densityVal;
		gl.glFogf(GL.GL_FOG_DENSITY, density);
	}

	public static void setPrecision(int precisionVal) {
		/*
		 * Possible: GL.GL_DONT_CARE GL.GL_NICEST GL.GL_LINEAR
		 */
		precision = precisionVal;
		gl.glHint(GL.GL_FOG_HINT, precision);
	}
}