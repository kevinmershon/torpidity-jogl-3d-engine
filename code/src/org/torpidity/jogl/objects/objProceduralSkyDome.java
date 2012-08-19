package org.torpidity.jogl.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.torpidity.jogl.environment.Camera;
import org.torpidity.jogl.environment.proceduralclouds.ProceduralClouds;

import javax.media.opengl.*;

public class objProceduralSkyDome extends gl3DObject {
	private float[][] pointHeights;
	private float[][] oldPointHeights;
	private int size;
	private int detail = 50;
	
	private ProceduralClouds clouds;
	private int texID;

	public objProceduralSkyDome(int size) {
		super();
		this.size = size;

		pointHeights = new float[detail][detail];
		for (int i = 0; i < detail; i++) {
			for (int j = 0; j < detail; j++) {
				pointHeights[i][j] = yPos;
			}
		}
		for (int p = 0; p < 10000; p++) {
			bendGrid();
		}

		clouds = new ProceduralClouds();

		int[] texture = new int[1];
		gl.glGenTextures(1, IntBuffer.wrap(texture));
		texID = texture[0];
	}

	public void drawTextured() {
		gl.glPushMatrix();
		setLocation(Camera.getX(), Camera.getY() + 60, Camera.getZ());
		gl.glTranslatef(xPos, yPos, zPos);
		setupTexture();
		
		gl.glEnable(GL.GL_TEXTURE_2D);

		float x1, z1, x2, z2, tx1, tx2, tx3, tx4;
		float startx = -(size / 2);
		float startz = -(size / 2);

		for (int i = 0; i < detail - 1; i++) {
			gl.glBegin(GL.GL_QUAD_STRIP);
			for (int j = 0; j < detail; j++) {
				x1 = startx + j * (size / detail);
				z1 = startz + (i + 1) * (size / detail);

				x2 = startx + j * (size / detail);
				z2 = startz + i * (size / detail);

				tx1 = (float) j / (float) detail;
				tx2 = (float) (i + 1) / (float) detail;

				tx3 = (float) j / (float) detail;
				tx4 = (float) i / (float) detail;

				gl.glTexCoord2f(tx1, tx2);
				gl.glVertex3f(x1, pointHeights[j][i + 1], z1);
				gl.glTexCoord2f(tx3, tx4);
				gl.glVertex3f(x2, pointHeights[j][i], z2);
			}
			gl.glEnd();
		}

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}

	public void bendGrid() {
		oldPointHeights = pointHeights;
		for (int x = 1; x < detail - 1; x++) {
			for (int y = 1; y < detail - 1; y++) {
				pointHeights[x][y] = ((oldPointHeights[x][y - 1]
						+ oldPointHeights[x][y + 1] + oldPointHeights[x - 1][y] + oldPointHeights[x + 1][y]) / 2)
						- pointHeights[x][y];
				pointHeights[x][y] -= (pointHeights[x][y] / 100);
			}
		}

		for (int i = 0; i < detail; i++) {
			pointHeights[0][i] = -100;
			pointHeights[i][0] = -100;
			pointHeights[i][detail - 1] = -100;
			pointHeights[detail - 1][i] = -100;
		}
		pointHeights[detail/2][detail/2] = yPos;
	}
	
	private void setupTexture() {
		clouds.combine();
		gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, 256, 256, 0, GL.GL_RGB, GL.GL_FLOAT, FloatBuffer.wrap(clouds.getImage()));
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	}

}