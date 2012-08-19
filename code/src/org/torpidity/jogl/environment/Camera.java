package org.torpidity.jogl.environment;

import javax.media.opengl.glu.*;

/**
 * <p>
 * This is a static Camera-controlling class, which is only useful for fly-mode
 * camera behavior. Once the engine is implemented for a game or something, this
 * code really should be worked directly into the game's core directly.
 * </p>
 * 
 * <p>
 * <b>Sample Camera calls:</b>
 * <ul>
 * <li>Camera.init(drawable);</li>
 * <li>Camera.setLocation(0f, 5f, 0f);</li>
 * <li>Camera.lookAt(0f,0f,0f);</li>
 * </ul>
 * </p>
 * @author Kevin
 * @version 1.1
 */
public class Camera {
	private static GLU glu;
	private static float xPos = 0;
	private static float yPos = 1;
	private static float zPos = 0;
	private static float xTarget = 0;
	private static float yTarget = 0;
	private static float zTarget = 0;
	private static float xzAngle = 3 * (float) Math.PI / 2;
	private static float yAngle = 0;
	private static float booster = 1f;
	public static final int FLY_MODE = 0;
	public static final int FOLLOW_MODE = 1;
	
	private static float isSpinningY = 0;
	private static float isSpinningZ = 0;
	private static float isMoving = 0;
	private static float isStrafing = 0;

	/**
	 * <p>
	 * Initialize the static Camera class.
	 * </p>
	 * 
	 * @param drawable
	 */
	public static void init() {
		glu = new GLU();
	}

	/**
	 * <p>
	 * Set the (x,y,z) location of the camera
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void setLocation(float x, float y, float z) {
		xPos = x;
		yPos = y;
		zPos = z;
	}

	/**
	 * <p>
	 * Set the (x,y,z) target of the camera
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void lookAt(float x, float y, float z) {
		xTarget = x;
		yTarget = y;
		zTarget = z;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * @param distance
	 */
	public static void move(float distance) {
		xPos += (booster * distance * Math.cos(xzAngle));
		yPos += (booster * distance * Math.sin(yAngle));
		zPos += (booster * distance * Math.sin(xzAngle));
		isMoving = distance;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * @param distance
	 */
	public static void strafe(float distance) {
		xPos += (booster * distance * Math.cos(xzAngle + Math.PI / 2));
		zPos += (booster * distance * Math.sin(xzAngle + Math.PI / 2));
		isStrafing = distance;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * @param y
	 */
	public static void spinY(float y) {
		yAngle += booster * (y / (float) Math.PI);
		if (yAngle > (float) Math.PI / 2)
			yAngle = (float) Math.PI / 2;
		if (yAngle < -(float) Math.PI / 2)
			yAngle = -(float) Math.PI / 2;
		isSpinningY = y;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * @param z
	 */
	public static void spinZ(float z) {
		xzAngle += booster * (z / (float) Math.PI);
		if (xzAngle > 2f * (float) Math.PI)
			xzAngle -= 2f * (float) Math.PI;
		if (xzAngle < 0f)
			xzAngle += 2f * (float) Math.PI;
		isSpinningZ = z;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * @param b
	 */
	public static void setBooster(float b) {
		booster = b;
	}

	/**
	 * <p>
	 * Special flymode function for testing
	 * </p>
	 * 
	 * <p>
	 * This function performs the necessary calculations for the camera
	 * position/direction to be updated properly, and then calls the function
	 * which communicates with openGL.
	 * </p>
	 */
	public static void flyModeUpdate() {
		move(isMoving);
		strafe(isStrafing);
		spinY(isSpinningY);
		spinZ(isSpinningZ);
		
		xTarget = xPos + (float) Math.cos(xzAngle);
		zTarget = zPos + (float) Math.sin(xzAngle);
		yTarget = yPos + (float) Math.sin(yAngle);
		
		update();
	}

	/**
	 * <p>
	 * This function calls the normal openGL camera-update call. So, in short,
	 * 99% of this class is designed to make this one openGL call easier to
	 * understand.
	 * </p>
	 * 
	 * <p>
	 * <b>Note:</b> the last 3 parameters of gluLookAt are intended to
	 * determine which direction is vertically "up". This is *always* 0,1,0.
	 * </p>
	 */
	private static void update() {
		glu.gluLookAt(xPos, yPos, zPos, xTarget, yTarget, zTarget, 0, 1, 0);
	}
	
	/**
	 * <p>Get the X position of the camera.</p>
	 * @return the X position of the camera
	 */
	public static float getX() {
		return xPos;
	}

	/**
	 * <p>Get the Y position of the camera.</p>
	 * @return the Y position of the camera
	 */
	public static float getY() {
		return yPos;
	}

	/**
	 * <p>Get the Z position of the camera.</p>
	 * @return the Z position of the camera
	 */
	public static float getZ() {
		return zPos;
	}
}