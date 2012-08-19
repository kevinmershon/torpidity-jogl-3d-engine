package org.torpidity.jogl.objects;

import javax.media.opengl.*;

import org.torpidity.jogl.heightmap.HeightMap;
import org.torpidity.jogl.util.*;

public class objTerrain extends gl3DObject {
	private float[][] heights;
	private int size = 128;
	private int detail;
	private float heightScale = 6.5f;

	private int list;

	public objTerrain(int size, int detail) {
		super();
		this.size = size;
		this.detail = detail;
		heights = new float[detail + 1][detail + 1];
	}

	/**
	 * 
	 * @param drawable
	 *            GLDrawable interface to the hardware
	 * @param size
	 *            the openGL unit size (on one side) of the terrain
	 * @param detail
	 *            the openGL unit size (on one side) of each grid spot
	 * @param heightScale
	 *            the openGL unit height of the terrain
	 */
	public objTerrain(int size, int detail, float heightScale) {
		this(size, detail);
		this.heightScale = heightScale;
	}

	public void draw() {
		throw new UnsupportedOperationException("Can't draw without texture.");
	}

	public void drawTextured() {
		gl.glPushMatrix();
		gl.glTranslatef(xPos, yPos, zPos);
		gl.glCallList(list);
		gl.glPopMatrix();
	}

	public void createList() {
		list = gl.glGenLists(1);
		gl.glNewList(list, GL.GL_COMPILE);
		texture.bind();
		gl.glEnable(GL.GL_TEXTURE_3D);
		gl.glBegin(GL.GL_TRIANGLES);
		// Calc offset to center mesh
		float startx = -(size / 2);
		float starty = -(size / 2);
		for (int y = 0; y < detail; y++) {
			for (int x = 0; x < detail; x++) {
				// 4 HeightMapped points
				float height1 = heightScale * heights[x][y];
				float height2 = heightScale * heights[x][y + 1];
				float height3 = heightScale * heights[x + 1][y + 1];
				float height4 = heightScale * heights[x + 1][y];

				// Vertex Positions
				float vX1 = startx + (float) x / (float) detail * (float) size;
				float vZ1 = starty + (float) y / (float) detail * (float) size;
				float vX2 = startx + (float) (x + 1) / (float) detail
						* (float) size;
				float vZ2 = starty + (float) (y + 1) / (float) detail
						* (float) size;

				// Bottom-left triangle
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord3f(0f, 0f, calcTx(height1 + yPos));
				gl.glVertex3f(vX1, height1, vZ1);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord3f(0f, 1f, calcTx(height2 + yPos));
				gl.glVertex3f(vX1, height2, vZ2);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord3f(1f, 1f, calcTx(height3 + yPos));
				gl.glVertex3f(vX2, height3, vZ2);
				gl.glNormal3f(0f, 1f, 0f);
				
				// Top-right triangle
				gl.glTexCoord3f(0f, 0f, calcTx(height1 + yPos));
				gl.glVertex3f(vX1, height1, vZ1);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord3f(1f, 1f, calcTx(height3 + yPos));
				gl.glVertex3f(vX2, height3, vZ2);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord3f(1f, 0f, calcTx(height4 + yPos));
				gl.glVertex3f(vX2, height4, vZ1);
			}
		}
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_3D);
		gl.glEndList();
	}

	public void loadHeightMap(String filename) {
		HeightMap h = new HeightMap(filename);
		int mapWidth = h.getWidth();
		int mapHeight = h.getHeight();

		for (int x = 0; x <= detail; x++) {
			for (int y = 0; y <= detail; y++) {
				heights[x][y] = h.getElevation((mapWidth / detail) * x,
						(mapHeight / detail) * y) / 255f;
			}
		}
	}

	/**
	 * Calculate the texture blend to be displayed given a certain elevation.
	 * 
	 * @param height
	 *            the elevation of the current point
	 * @return a texture value between 0 and 1
	 */
	public float calcTx(float height) {
		float trueHeight = (height - this.yPos) / this.heightScale;
		return trueHeight;
	}
	
}