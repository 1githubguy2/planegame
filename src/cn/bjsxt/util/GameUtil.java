package cn.bjsxt.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


/**
 *��Ϸ�����г��õĹ����ࣨ���磺����ͼƬ�ȷ�����
 *@author YJL 
 * 
 */
public class GameUtil {
	
	private GameUtil() {}//������ͨ���Ὣ���췽��˽����
	
	public static Image getImage(String path) {
		
		BufferedImage bi = null;
		try {
			URL u = GameUtil.class.getClassLoader().getResource(path);
			bi = javax.imageio.ImageIO.read(u);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
		
		/** 
		 * return Toolkit.getDefaultToolkit().getImage
				(GameUtil.class.getClassLoader().getResource(path));
		 */
	}

}
