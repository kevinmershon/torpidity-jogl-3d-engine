package org.torpidity.jogl.textures;

import org.torpidity.jogl.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

import javax.imageio.*;

public class Texture {
	private GL gl;
	private GLU glu;
	private int id;
	private int target;
	protected byte[] imgData;
	protected int width;
	protected int height;
	protected int depth;

	/**
	 * Generate an (optionally) mipmapped 2D texture.
	 * 
	 * @param filename
	 * @param mipmap
	 */
	public Texture(String filename, boolean mipmap) {
		this.gl = GLAdapter.getGL();
		this.glu = GLAdapter.getGLU();
		this.target = GL.GL_TEXTURE_2D;
		gen2DTexture(filename, mipmap);
	}

	/**
	 * Generate a non-mipmapped 3D texture.
	 * 
	 * @param filenames
	 */
	public Texture(String[] filenames) {
		this.gl = GLAdapter.getGL();
		this.target = GL.GL_TEXTURE_3D;
		gen3DTexture(filenames);
	}

	private int gen2DTexture(String filename, boolean mipmap) {
		int[] idRef = new int[1];
		try {
			gl.glGenTextures(1, IntBuffer.wrap(idRef));
			this.id = idRef[0];
			imgData = new byte[0];
			BufferedInputStream imgStream = new BufferedInputStream(this
					.getClass().getResourceAsStream(filename));
			BufferedImage img = ImageIO.read(imgStream);
			width = img.getWidth();
			height = img.getHeight();
			if ((Math.log10(width) / Math.log10(2) % 1 != 0)
					|| (Math.log10(height) / Math.log10(2) % 1 != 0)) {
				throw new UnsupportedOperationException(
						"Width and height must be a power of 2.");
			}
			imgData = ((DataBufferByte) img.getRaster().getDataBuffer())
					.getData();
			this.bind();
			if (mipmap) {
				glu.gluBuild2DMipmaps(target, GL.GL_RGB, width, height,
						GL.GL_BGR, GL.GL_UNSIGNED_BYTE, ByteBuffer
								.wrap(imgData));
			} else {
				gl.glTexImage2D(target, 0, GL.GL_RGB, width, height, 0,
						GL.GL_BGR, GL.GL_UNSIGNED_BYTE, ByteBuffer
								.wrap(imgData));
			}
			gl.glTexParameteri(target, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(target, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return idRef[0];
	}

	private int gen3DTexture(String[] filenames) {
		int[] idRef = new int[1];
		try {
			gl.glGenTextures(1, IntBuffer.wrap(idRef));
			this.id = idRef[0];
			imgData = new byte[0];
			depth = filenames.length;
			for (int i = 0; i < depth; i++) {
				byte[] temp = imgData;
				BufferedInputStream imgStream = new BufferedInputStream(this
						.getClass().getResourceAsStream(filenames[i]));

				BufferedImage img = ImageIO.read(imgStream);
				width = img.getWidth();
				height = img.getHeight();
				if ((Math.log10(this.width) / Math.log10(2) % 1 != 0)
						|| (Math.log10(height) / Math.log10(2) % 1 != 0)
						|| (Math.log10(depth) / Math.log10(2) % 1 != 0)) {
					throw new UnsupportedOperationException(
							"Width, height, & depth must be a power of 2.");
				}
				imgData = ((DataBufferByte) img.getRaster().getDataBuffer())
						.getData();
				byte[] combined = new byte[temp.length + imgData.length];
				for (int j = 0; j < temp.length; j++) {
					combined[j] = temp[j];
				}
				for (int k = temp.length; k < combined.length; k++) {
					combined[k] = imgData[k - temp.length];
				}
				imgData = combined;
			}
			this.bind();
			gl.glTexImage3D(target, 0, GL.GL_RGB, width, height, depth, 0,
					GL.GL_BGR, GL.GL_UNSIGNED_BYTE, ByteBuffer.wrap(imgData));

			gl.glTexParameteri(target, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(target, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return idRef[0];
	}

	public void bind() {
		gl.glBindTexture(target, id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		if (target == GL.GL_TEXTURE_2D)
			throw new UnsupportedOperationException(
					"2D Textures have no depth!");
		return depth;
	}
}