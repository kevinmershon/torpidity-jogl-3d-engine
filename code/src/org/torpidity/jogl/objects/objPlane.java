package org.torpidity.jogl.objects;

import javax.media.opengl.*;

public class objPlane extends gl3DObject {
	protected float size;

	public objPlane(float size) {
		super();
		this.size = size;
	}

	public void drawTextured() {
		gl.glPushMatrix();
		super.drawTextured();

		float s = size / 2f;

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
		// Top side
		gl.glNormal3f(-1f, 1f, -1f);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(-s, yPos, -s); // Left Top Back
		gl.glNormal3f(-1f, 1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(-s, yPos, s); // Left Top Front
		gl.glNormal3f(1f, 1f, 1f);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(s, yPos, s); // Right Top Front
		gl.glNormal3f(1f, 1f, -1f);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(s, yPos, -s); // Right Top Back
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}

	public void draw() {
		gl.glPushMatrix();
		super.draw();

		float s = size / 2f;

		gl.glBegin(GL.GL_QUADS);
		gl.glColor4f(red, green, blue, alpha);
		// Top side
		gl.glNormal3f(0f, 1f, 0f);
		gl.glVertex3f(-s, yPos, -s); // Left Top Back
		gl.glNormal3f(0f, 1f, 0f);
		gl.glVertex3f(-s, yPos, s); // Left Top Front
		gl.glNormal3f(0f, 1f, 0f);
		gl.glVertex3f(s, yPos, s); // Right Top Front
		gl.glNormal3f(0f, 1f, 0f);
		gl.glVertex3f(s, yPos, -s); // Right Top Back
		gl.glEnd();
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glPopMatrix();
	}

}