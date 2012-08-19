package org.torpidity.jogl.objects;

import javax.media.opengl.*;
import com.sun.opengl.util.GLUT;

public class objText extends gl3DObject {
	private String string;

	public objText(String string, int xPos, int yPos) {
		super();
		this.string = string;
		setLocation(xPos, yPos, 0);
	}

	public void draw() {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, 800, 600, 0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glColor3f(red, green, blue);

		GLUT glut = new GLUT();
		int font = GLUT.BITMAP_HELVETICA_18;
		gl.glRasterPos3f(xPos, yPos, 0);
		glut.glutBitmapString(font, string);

		gl.glColor3f(1f, 1f, 1f);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	public void setText(String string) {
		this.string = string;
	}
}