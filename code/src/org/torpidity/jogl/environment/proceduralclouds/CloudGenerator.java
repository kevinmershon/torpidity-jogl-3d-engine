package org.torpidity.jogl.environment.proceduralclouds;

/**
 * <p>
 * A procedural cloud texture, following this guide:
 * http://www.intel.com/cd/ids/developer/asmo-na/eng/dc/games/graphics/20534.htm?page=5
 * </p>
 * <p>
 * <b>Note:</b> Do <b>NOT</b> implement anything using this class directly!
 * Instead, implement a ProceduralClouds object, unless you know <b>EXACTLY</b>
 * what you are doing!
 * </p>
 * 
 * @author Kevin
 * @version 1.1
 */
public class CloudGenerator {
	private static final int MAX_SIZE = 256;
	private static float DENSITY = .5f;

	private int size;
	private float[][] texture;
	private float[][] upSampled;

	// private float[][] upSampledOld;

	public CloudGenerator(int size) {
		this.size = size;
		texture = new float[size][size];
		//random();
	}

	/**
	 * Regen the texture with new values.
	 * 
	 * @param texture
	 * @param size
	 * @return
	 */
	public void regen() {
		for (int i = 0; i < size; i++) {
			for (int j = 1; j < size-1; j++) {
				texture[i][j] = texture[i][j+1];
			}
			texture[i][size-1] = (float) Math.round(Math.random());
		}
	}

	public void random() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				texture[i][j] = (float) Math.round(Math.random());
			}
		}
	}

	/**
	 * Up-sample the image to MAX_SIZE squared. Never try and use a value
	 * greater than MAX_SIZE for procedural textures.
	 */
	public float[][] upSample() {
		/*
		 * if (upSampled != null) upSampledOld = upSampled;
		 */

		float[][] blended = interpolate(texture, size, size);
		int scale = MAX_SIZE / size;
		if (scale == 1) {
			// Don't bother upsampling the 256x256 texture
			upSampled = texture;
			return upSampled = interpolate(upSampled, MAX_SIZE, MAX_SIZE);
		}
		upSampled = new float[MAX_SIZE][MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++) {
			for (int j = 0; j < MAX_SIZE; j++) {
				upSampled[i][j] = blended[i / scale][j / scale];
			}
		}
		return (upSampled = interpolate(upSampled, MAX_SIZE, MAX_SIZE));
	}

	/**
	 * Interpolate each pixel with each other pixel. Pixels which are on edges
	 * get interpolated by wrapping.
	 */
	private static float[][] interpolate(float[][] texture, int w, int h) {
		float[][] newTexture = new float[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				float sum = 0f;
				int count = 0;
				if (j - 1 > 0) {
					// Top Left
					if (i - 1 > 0) {
						sum += texture[i - 1][j - 1];
						count++;
					}
					// Top Center
					sum += texture[i][j - 1];
					count++;
					// Top Right
					if (i + 1 < w - 1) {
						sum += texture[i + 1][j - 1];
						count++;
					}
				}
				// Left
				if (i - 1 > 0) {
					sum += texture[i - 1][j];
					count++;
				}
				// Center
				sum += texture[i][j];
				count++;
				// Right
				if (i + 1 < j - 1) {
					sum += texture[i + 1][j];
					count++;
				}
				if (j + 1 < h - 1) {
					// Bottom Left
					if (i - 1 > 0) {
						sum += texture[i - 1][j + 1];
						count++;
					}
					// Bottom
					sum += texture[i][j + 1];
					count++;
					if (i + 1 < w - 1) {
						sum += texture[i + 1][j + 1];
						count++;
					}
				}
				newTexture[i][j] = sum / count;
			}
		}
		return newTexture;
	}

	public static void setDensity(float density) {
		CloudGenerator.DENSITY = 1f - density;
	}

}
