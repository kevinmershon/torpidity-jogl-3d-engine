package org.torpidity.jogl.objects;

import javax.media.opengl.*;
import java.util.Random;

public class objWater extends gl3DObject {
	private int size;
	private int detail;
	private float[][] pointHeights;
	private float[][] oldPointHeights;
	private float waveAngle = 0;
	private float waveHeight = 1f;
	private boolean waves = true;
	private float flowRate = .001f;
	private float flowSpeed = 0f;
	private float flowPoint = 0f;

	public objWater(int size, int detail) {
		super();
		this.size = size;
		this.detail = detail;
		pointHeights = new float[detail][detail];
	}

	public void drawTextured() {
		gl.glPushMatrix();
		super.drawTextured();

		float startx = -(size / 2);
		float startz = -(size / 2);

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_ONE_MINUS_DST_COLOR, GL.GL_SRC_ALPHA);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBegin(GL.GL_QUADS);

		if (waves)
			makeWaves();
		if (waves)
			flow();

		for (int x = 0; x < detail - 1; x++) {
			for (int z = 0; z < detail - 1; z++) {
				float height1 = pointHeights[x][z];
				float height2 = pointHeights[x][z + 1];
				float height3 = pointHeights[x + 1][z + 1];
				float height4 = pointHeights[x + 1][z];

				float x1 = startx + (float) x / detail * size;
				float x2 = startx + (float) (x + 1) / detail * size;
				float z1 = startz + (float) z / detail * size;
				float z2 = startz + (float) (z + 1) / detail * size;

				// Unlike Terrain, water can be quads since it requires less
				// detail
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord2f(0f + flowPoint, 4f + flowPoint);
				gl.glVertex3f(x1, height1, z1);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord2f(0f + flowPoint, 0f + flowPoint);
				gl.glVertex3f(x1, height2, z2);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord2f(4f + flowPoint, 0f + flowPoint);
				gl.glVertex3f(x2, height3, z2);
				gl.glNormal3f(0f, 1f, 0f);
				gl.glTexCoord2f(4f + flowPoint, 4f + flowPoint);
				gl.glVertex3f(x2, height4, z1);
			}
		}
		gl.glEnd();
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}

	public void makeWaves() {
		oldPointHeights = pointHeights;
		for (int x = 1; x < detail - 1; x++) {
			for (int y = 1; y < detail - 1; y++) {
				pointHeights[x][y] = ((oldPointHeights[x][y - 1]
						+ oldPointHeights[x][y + 1] + oldPointHeights[x - 1][y] + oldPointHeights[x + 1][y]) / 2)
						- pointHeights[x][y];
				pointHeights[x][y] -= (pointHeights[x][y] / (size * 5));
			}
		}

		waveAngle += (float) Math.PI / 200;
		float tempHeight = waveHeight * (float) Math.sin(waveAngle);
		flowSpeed = (float) Math.cos(waveAngle) * flowRate; // Cosine, so that
															// it's slightly
															// before/after

		Random r = new Random();
		pointHeights[r.nextInt(detail - 1)][r.nextInt(detail - 1)] = tempHeight * 1.02f;
		for (int i = 0; i <= detail - 1; i++) {
			pointHeights[0][i] = tempHeight;
			pointHeights[i][0] = tempHeight;
			pointHeights[detail - 1][i] = tempHeight;
			pointHeights[i][detail - 1] = tempHeight;
		}

	}

	public void setWaves(boolean waves) {
		this.waves = waves;
	}

	public void setWaveHeight(float waveHeight) {
		this.waveHeight = waveHeight;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}

	public void flow() {
		flowPoint += flowSpeed + flowRate / 2;
		if (flowPoint >= 1f)
			flowPoint -= 1f;
		if (flowPoint <= -1f)
			flowPoint += 1f;
	}

}