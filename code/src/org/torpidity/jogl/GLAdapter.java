package org.torpidity.jogl;

import com.sun.opengl.util.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/**
 * This class implements GLEventListener functions. It is intended to allow the
 * last layer of abstraction between OpenGL and the Torpidity engine. What that
 * means is that this class is a proxy between Java and the OpenGL layer. All
 * interaction with OpenGL must pass through this class at some point.
 * 
 * @author Kevin Mershon
 */
public class GLAdapter implements GLEventListener {
	private GLCapabilities capabilities;
	private GLCanvas canvas;
	private FPSAnimator animator;
	protected static GL gl;
	protected static GLU glu;

	public GLAdapter() {
		// OpenGL capabilities of the hardware
		capabilities = new GLCapabilities();
		capabilities.setHardwareAccelerated(true);

		// Animation and Canvas
		canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 80);
	}

	/**
	 * In this function we must set up all the objects and OpenGL settings. No
	 * actual drawing should be done in this function.
	 */
	public void init(GLAutoDrawable drawable) {
		/**
		 * Access the OpenGL architecture to permit drawing to the hardware
		 */
		GLAdapter.gl = drawable.getGL();
		GLAdapter.glu = new GLU();

		/**
		 * The following line is basically unnecessary, but unless it's turned
		 * on, no openGL-related errors will be displayed. I suggest leaving it
		 * on.
		 */
		drawable.setGL(new DebugGL(drawable.getGL()));

		/**
		 * Without these functions the geometry looks really weird
		 */
		// Objects closer are drawn "on top"
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		// Don't draw the backs of objects
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);

		/**
		 * The following function calls are less necessary, but still help the
		 * display look really nice.
		 */
		// gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		// gl.glShadeModel(GL.GL_SMOOTH);
		// gl.glEnable(GL.GL_POLYGON_SMOOTH);
		// gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
	}

	/**
	 * This is the function in which all actual drawing is performed.
	 */
	public void display(GLAutoDrawable drawable) {
		/**
		 * The following 2 lines are the only truly important OpenGL-related
		 * lines in this function. Everything else is just stuff I've written to
		 * make the function actually draw shit.
		 */
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
	}

	/**
	 * This function is used to handle the GLCanvas being resized.
	 * It must be implemented in the class extending this adapter, otherwise any
	 * objects drawn by a different implementer might not work.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	/**
	 * This is a complete mystery to me.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// TODO Auto-generated method stub

	}

	public GLCanvas getCanvas() {
		return canvas;
	}

	public void startAnimation() {
		animator.start();
	}

	public void stopAnimation() {
		animator.stop();
	}

	public static GL getGL() {
		return GLAdapter.gl;
	}
	
	public static GLU getGLU() {
		return GLAdapter.glu;
	}
}
