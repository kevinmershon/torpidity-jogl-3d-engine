package org.torpidity.jogl.objects;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

public class objSphere extends gl3DObject {
	protected float radius;
	protected int detail;

	public objSphere(float radius) {
		this(radius, 20);
	}

	public objSphere(float radius, int detail) {
		super();
		this.radius = radius;
		this.detail = detail;
	}

	public void setDetail(int level) {
		detail = level;
	}

	public void draw() {
		gl.glPushMatrix();
		super.draw();
		int slices = detail;
		int rings = detail;

		GLUquadric gQuad = glu.gluNewQuadric();
		glu.gluSphere(gQuad, radius, slices, rings);
		glu.gluDeleteQuadric(gQuad);
		gl.glPopMatrix();
	}

	public void drawTextured() {
		gl.glPushMatrix();
		super.drawTextured();
		int slices = detail;
		int rings = detail;

		gl.glEnable(GL.GL_TEXTURE_2D);
		GLUquadric gQuad = glu.gluNewQuadric();
		glu.gluQuadricTexture(gQuad, true);
		glu.gluSphere(gQuad, radius, slices, rings);
		glu.gluDeleteQuadric(gQuad);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}
}