package org.torpidity.jogl.environment.proceduralclouds;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.*;
import javax.swing.JFrame;
import org.torpidity.jogl.*;

public class CloudGeneratorTest extends GLAdapter {
	private ProceduralClouds clouds;
	private int texID;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Torpidity JOGL Render-to-texture Test");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		final CloudGeneratorTest test = new CloudGeneratorTest();
		
		frame.setVisible(true);
		test.startAnimation();
		frame.getContentPane().add(test.getCanvas());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				test.stopAnimation();
				System.exit(0);
			}
		});
	}
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
		clouds = new ProceduralClouds();
		int[] texture = new int[1];
		gl.glGenTextures(1, IntBuffer.wrap(texture));
		texID = texture[0];
	}

	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		clouds.combine();
		gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, 256, 256, 0, GL.GL_RGB, GL.GL_FLOAT, FloatBuffer.wrap(clouds.getImage()));
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
			gl.glTexCoord2f(0f, 1f);
			gl.glVertex3f(-.5f, .5f, 0f);
			gl.glTexCoord2f(0f, 0f);
			gl.glVertex3f(-.5f, -.5f, 0f);
			gl.glTexCoord2f(1f, 0f);
			gl.glVertex3f(.5f, -.5f, 0f);
			gl.glTexCoord2f(1f, 1f);
			gl.glVertex3f(.5f, .5f, 0f);
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}

}
