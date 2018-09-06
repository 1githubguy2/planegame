package cn.bjsxt.plane;

import java.awt.Graphics;
import java.awt.Image;

import cn.bjsxt.util.GameUtil;

/**
 * ��ը��
 * @author YJL
 *
 */

public class Explode {
	double x, y;             //����
	static Image[] imgs = new Image[16];     //��ը��Ҫһ��ͼƬ,����һ�ξ��У���static��ʵ�ֹ���
	//��̬���Եĳ�ʼ��
	static {
		for(int i=0; i<16; i++) {
			imgs[i] = GameUtil.getImage("images/explode/e" + (i+1) + ".gif");
			//����������(��һ�μ���ͼƬ����ز�����������Ҫ������һ�β���)
			imgs[i].getWidth(null);
		}
	}
	int count;               //ͼƬ��Ҫָ��
	
	public void draw(Graphics g) {
		//ʵ��ֻ��ըһ��
		if(count <= 15) {
			g.drawImage(imgs[count], (int)x, (int)y, null);
			count++;
		}		
	}
	//����һ����ը����,д������
	public Explode(double x, double y){
		this.x = x;
		this.y = y;
	}

}
