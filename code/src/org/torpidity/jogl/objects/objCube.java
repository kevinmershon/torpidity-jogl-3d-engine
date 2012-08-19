package org.torpidity.jogl.objects;

import javax.media.opengl.*;

public class objCube extends gl3DObject {
	protected float size;

	public objCube(float size) {
		super();
		this.size = size;
	}

	public void drawTextured() {
		gl.glPushMatrix();
		super.drawTextured();

		float s = size / 2f;

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
		// Right side
		gl.glNormal3f(1f, 1f, 1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(s, s, s); // Right Top Front
		gl.glNormal3f(1f, -1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glNormal3f(1f, -1f, -1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(1f, 1f, -1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(s, s, -s); // Right Top Back

		// Back side
		gl.glNormal3f(1f, 1f, -1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(s, s, -s); // Right Top Back
		gl.glNormal3f(1f, -1f, -1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back

		// Left side
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(-s, s, s); // Left Top Front

		// Front side
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(1f, -1f, 1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glNormal3f(1f, 1f, 1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(s, s, s); // Right Top Front

		// Top side
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glNormal3f(1f, 1f, 1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(s, s, s); // Right Top Front
		gl.glNormal3f(1f, 1f, -1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(s, s, -s); // Right Top Back

		// Bottom side
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(1f, -1f, -1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(1f, -1f, 1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}

	public void draw() {
		gl.glPushMatrix();
		super.draw();

		float s = size / 2f;

		gl.glBegin(GL.GL_QUADS);
		gl.glColor3f(red, green, blue);
		// Right side
		gl.glNormal3f(1f, 1f, 1f);
		gl.glVertex3f(s, s, s); // Right Top Front
		gl.glNormal3f(1f, -1f, 1f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glNormal3f(1f, -1f, -1f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(1f, 1f, -1f);
		gl.glVertex3f(s, s, -s); // Right Top Back

		// Back side
		gl.glNormal3f(1f, 1f, -1f);
		gl.glVertex3f(s, s, -s); // Right Top Back
		gl.glNormal3f(1f, -1f, -1f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back

		// Left side
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glVertex3f(-s, s, s); // Left Top Front

		// Front side
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(1f, -1f, 1f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glNormal3f(1f, 1f, 1f);
		gl.glVertex3f(s, s, s); // Right Top Front

		// Top side
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glVertex3f(-s, s, -s); // Left Top Back
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glVertex3f(-s, s, s); // Left Top Front
		gl.glNormal3f(1f, 1f, 1f);
		gl.glVertex3f(s, s, s); // Right Top Front
		gl.glNormal3f(1f, 1f, -1f);
		gl.glVertex3f(s, s, -s); // Right Top Back

		// Bottom side
		gl.glNormal3f(-1f, -1f, 1f);
		gl.glVertex3f(-s, -s, s); // Left Bottom Front
		gl.glNormal3f(-1f, -1f, -1f);
		gl.glVertex3f(-s, -s, -s); // Left Bottom Back
		gl.glNormal3f(1f, -1f, -1f);
		gl.glVertex3f(s, -s, -s); // Right Bottom Back
		gl.glNormal3f(1f, -1f, 1f);
		gl.glVertex3f(s, -s, s); // Right Bottom Front
		gl.glEnd();
		gl.glColor3f(1f, 1f, 1f);
		gl.glPopMatrix();
	}

}