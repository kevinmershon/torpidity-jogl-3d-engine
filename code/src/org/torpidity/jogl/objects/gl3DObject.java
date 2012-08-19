package org.torpidity.jogl.objects;

import org.torpidity.jogl.*;
import org.torpidity.jogl.textures.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

public class gl3DObject {
	protected GL gl;
	protected GLU glu;
	protected float xPos = 0f;
	protected float yPos = 0f;
	protected float zPos = 0f;
	protected float red = 1f;
	protected float green = 1f;
	protected float blue = 1f;
	protected float alpha = 1f;
	protected Texture texture;

	public gl3DObject() {
		gl = GLAdapter.getGL();
		glu = GLAdapter.getGLU();
	}

	/**
	 * <p>
	 * Set the (x,y,z) location of the object.
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setLocation(float x, float y, float z) {
		xPos = x;
		yPos = y;
		zPos = z;
	}

	/**
	 * <p>
	 * Sets the color of an object (if no texture is chosen).
	 * </p>
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public void setColor(int red, int green, int blue, int alpha) {
		this.red = (float) (red / 255f);
		this.blue = (float) (green / 255f);
		this.green = (float) (blue / 255f);
		this.alpha = alpha;
	}

	/**
	 * <p>
	 * Load a texture into memory.
	 * </p>
	 * 
	 * @param texture
	 *            the Texture for this object
	 */
	public void loadTexture(Texture texture) {
		this.texture = texture;
	}

	/**
	 * <p>
	 * Draw this object untextured
	 * </p>
	 */
	public void draw() {
		gl.glTranslatef(xPos, yPos, zPos);
	}

	/**
	 * <p>
	 * Draw this object textured
	 * </p>
	 */
	public void drawTextured() {
		gl.glTranslatef(xPos, yPos, zPos);
		if (texture != null)
			texture.bind();
	}

}