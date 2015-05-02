package sampleTests;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageReaderTest {

	@Test
	public void test() {
		System.out.println(getClass().getClassLoader().getParent());
		try {
			BufferedImage im = ImageIO.read(getClass().getResourceAsStream("/base_Images/Scenario_Directory_V1.0/open_browser/Google_Home.jpg"));
			System.out.println(im.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getClass().getResourceAsStream("/base_Images/Scenario_Directory_V1.0/open_browser/Google_Home.jpg"));
	}

}
