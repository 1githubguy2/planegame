package cn.bjsxt.plane;

import java.awt.Graphics;
import java.awt.Image;

import cn.bjsxt.util.GameUtil;

/**
 * 爆炸类
 * @author YJL
 *
 */

public class Explode {
	double x, y;             //坐标
	static Image[] imgs = new Image[16];     //爆炸需要一堆图片,加载一次就行，用static，实现共享
	//静态属性的初始化
	static {
		for(int i=0; i<16; i++) {
			imgs[i] = GameUtil.getImage("images/explode/e" + (i+1) + ".gif");
			//避免懒加载(第一次加载图片会加载不出来，所以要事先用一次才行)
			imgs[i].getWidth(null);
		}
	}
	int count;               //图片需要指向
	
	public void draw(Graphics g) {
		//实现只爆炸一次
		if(count <= 15) {
			g.drawImage(imgs[count], (int)x, (int)y, null);
			count++;
		}		
	}
	//创建一个爆炸对象,写构造器
	public Explode(double x, double y){
		this.x = x;
		this.y = y;
	}

}
