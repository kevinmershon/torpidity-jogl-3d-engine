package org.torpidity.jogl.environment.proceduralclouds;

/**
 * <p>
 * This class represents a combination of four CloudGenerator objects with some
 * additional functionality, and is used to produce a single procedural cloud
 * texture. Any interactions with the cloud/weather system should be done
 * through this class to minimize the annoyances involved in trying to get
 * everything set up correctly.
 * </p>
 * 
 * @author Kevin
 * @version 1.1
 */
public class ProceduralClouds implements Runnable {
	private CloudGenerator cg1;
	private CloudGenerator cg2;
	private CloudGenerator cg3;
	private CloudGenerator cg4;
	private float[][] data;
	private float[] color;
	private float[] dataOut;
	private float[][] combined;

	public ProceduralClouds() {
		//cg1 = new CloudGenerator(32);
		//cg2 = new CloudGenerator(64);
		cg3 = new CloudGenerator(128);
		//cg4 = new CloudGenerator(256);
		cg3.random();

		color = new float[] { 1f, 1f, .8f };

		new Thread(this).start();
		dataOut = new float[256 * 256 * 3];
		combined = new float[256][256];
	}

	/**
	 * Blend the CloudGenerator outputs together.
	 */
	public void combine() {
		//float[][] data1 = cg1.upSample();
		//float[][] data2 = cg2.upSample();
		float[][] data3 = cg3.upSample();
		//float[][] data4 = cg4.upSample();

		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				/*combined[i][j] = (float) (Math.pow(.5 * data1[i][j],2)
						+ Math.pow(.75 * data2[i][j],2) + Math.pow(.75 * data3[i][j],2)
						+ Math.pow(.25 * data4[i][j],2));*/
				combined[i][j] = .5f - (float)Math.pow(data3[i][j],2);
			}
		}
		data = combined;
	}

	/**
	 * <p>
	 * Return the data as a one-dimensional array of floats, which OpenGL can
	 * interpret as an RGB image.
	 * </p>
	 * 
	 * @return the image
	 */
	public float[] getImage() {
		// Influences and Bases
		float rInfl = (color[0] == 1f ? 1f : 0f);
		float gInfl = (color[1] == 1f ? 1f : 0f);
		float bInfl = (color[2] == 1f ? 1f : 0f);
		float rBase = (color[0] == 1f ? 0f : color[0]);
		float gBase = (color[1] == 1f ? 0f : color[1]);
		float bBase = (color[2] == 1f ? 0f : color[2]);
		
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				dataOut[(i * 3) + (256 * j * 3)] = rInfl * data[i][j] + rBase;
				dataOut[(i * 3 + 1) + (256 * j * 3)] = gInfl * data[i][j] + gBase;
				dataOut[(i * 3 + 2) + (256 * j * 3)] = bInfl * data[i][j] + bBase;
			}
		}
		return dataOut;
	}

	public void run() {
		int val = 0;
		while (true) {
			/*if (val == 0)
				cg1.regen();
			if (val % 16 == 0)
				cg2.regen();
			if (val % 8 == 0)
				cg3.regen();
			if (val % 1 == 0)
				cg4.regen();*/
			cg3.regen();
			val++;
			if (val > 31)
				val = 0;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				return;
			}

		}
	}

	/**
	 * <p>
	 * Sets the density of the cloud cover.<br>
	 * <b>Recommended range:</b> of .3f to .9f.<br>
	 * <b>Default:</b> .5f.
	 * </p>
	 * 
	 * @param density
	 */
	public void setDensity(float density) {
		CloudGenerator.setDensity(density);
	}

	/**
	 * <p>
	 * Set the color of the sky behind the clouds.<br>
	 * <b>Default:</b> { 1f, 1f, .8f }
	 * </p>
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */

	public void setSkyColor(float red, float green, float blue) {
		color = new float[] { red, green, blue };
	}

}
