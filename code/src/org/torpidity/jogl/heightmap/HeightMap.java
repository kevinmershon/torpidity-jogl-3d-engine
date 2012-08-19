package org.torpidity.jogl.heightmap;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import javax.imageio.*;

public class HeightMap {
	private int width;
	private int height;
	private byte[] heightMap;
	
	public HeightMap(String filename) {
		try {
			BufferedInputStream imgStream = new BufferedInputStream(this.getClass()
					.getResourceAsStream(filename));
			BufferedImage img = ImageIO.read(imgStream);
			width = img.getWidth();
			height = img.getHeight();
			heightMap = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int getElevation(int x, int y) {
		int fx = x % width;
		int fy = y % height;
		return (heightMap[fx + (fy * width)] & 0xFF);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}