package org.torpidity.jogl.objects;

import javax.media.opengl.*;

public class objSkyBox extends gl3DObject {
	private int size;
	private float rotateSpeed = .0085f;
	private float rotateAngle = 0f;

	public objSkyBox(int size) {
		super();
		this.size = size;
	}

	public void drawTextured() {
		gl.glPushMatrix();
		super.drawTextured();

		float s = size / 2;

		gl.glEnable(GL.GL_TEXTURE_2D);
		rotateAngle += rotateSpeed;
		gl.glRotatef(rotateAngle, 0f, 1f, 0f);

		gl.glBegin(GL.GL_QUADS);
		// Up
		gl.glTexCoord2f((2f / 3f), (1f / 3f));
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glTexCoord2f((2f / 3f), (2f / 3f));
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glTexCoord2f((1f / 3f), (2f / 3f));
		gl.glVertex3f(s, s, -s); // Right Top Back
		gl.glTexCoord2f((1f / 3f), (1f / 3f));
		gl.glVertex3f(s, s, s); // Right Top Front

		// North
		gl.glTexCoord2f((2f / 3f), (2f / 3f));
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glTexCoord2f((2f / 3f), (3f / 3f));
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glTexCoord2f((1f / 3f), (3f / 3f));
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glTexCoord2f((1f / 3f), (2f / 3f));
		gl.glVertex3f(s, s, -s); // Right Top Back

		// South
		gl.glTexCoord2f((1f / 3f), (1f / 3f));
		gl.glVertex3f(s, s, s); // Right Top Front
		gl.glTexCoord2f((1f / 3f), (0f / 3f));
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glTexCoord2f((2f / 3f), (0f / 3f));
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glTexCoord2f((2f / 3f), (1f / 3f));
		gl.glVertex3f(-s, s, s); // Left Top Front

		// East
		gl.glTexCoord2f((1f / 3f), (2f / 3f));
		gl.glVertex3f(s, s, -s); // Right Top Back
		gl.glTexCoord2f((0f / 3f), (2f / 3f));
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glTexCoord2f((0f / 3f), (1f / 3f));
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glTexCoord2f((1f / 3f), (1f / 3f));
		gl.glVertex3f(s, s, s); // Right Top Front

		// West
		gl.glTexCoord2f((2f / 3f), (1f / 3f));
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glTexCoord2f((3f / 3f), (1f / 3f));
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glTexCoord2f((3f / 3f), (2f / 3f));
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glTexCoord2f((2f / 3f), (2f / 3f));
		gl.glVertex3f(-s, s, -s); // Left Top Back

		gl.glEnd();
		// gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]); // Left
		// gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]); // Back
		// gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]); // Right
		// gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]); // Front
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}
}