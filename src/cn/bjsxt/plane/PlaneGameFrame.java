package cn.bjsxt.plane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import cn.bjsxt.util.Constant;
import cn.bjsxt.util.GameUtil;
import cn.bjsxt.util.MyFrame;



public class PlaneGameFrame extends MyFrame {
	
	//ȡ�ÿ�ʼ����
	Image beginBg = GameUtil.getImage("images/startbg1.jpg");
	//ȡ����Ϸ����
	public static Image gameBg = GameUtil.getImage("images/background1.bmp");
	//��������begin����־���Ǹոտ�ʼ��Ϸ
	static boolean begin = true;
	
	//ʹ������������¼λ�ã�
	static int yPos = -1*(gameBg.getHeight(null)-Constant.GAME_HEIGHT)+1;
	static int yPos2 = yPos-gameBg.getHeight(null);
	
	
	//Image bg = GameUtil.getImage("images/bg.jpg");
	Plane p = new Plane("images/plane.png", 50, 50);
	
	ArrayList bulletList = new ArrayList();//������ʱδѧ����ʱ���ӣ��Ժ�ѧ�ˣ�ǿ�ҽ������
	
	Date startTime;
	Date endTime;
	
	Explode bao;
	
	public void paint(Graphics g) {
		
		if(begin) {
			g.drawImage(beginBg, 0, 0, null);
			try {
				Thread.sleep(3000);
				begin = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//�ڵ�һ��ͼƬ��������Ż�һ��ͼƬ
		g.drawImage(gameBg, 0, yPos, null);
		g.drawImage(gameBg, 0, yPos2, null);	//����ͼƬ����
		
		
		//g.drawImage(bg, 0, 0, null);
		p.draw(g);
		
		for(int i=0; i<bulletList.size(); i++) {
			Bullet b = (Bullet) bulletList.get(i);
			b.draw(g);
			
			//���ɻ�����ײ
			boolean peng = b.getRect().intersects(p.getRect());
			if(peng) {
				p.setLive(false);//�ɻ�����
				if(bao == null) {
					endTime = new Date();
					bao = new Explode(p.x, p.y);
				}
				bao.draw(g);
				break;
				//System.out.println("###########peng");
			}
		}
		if(!p.isLive()) {
			int period = (int)(endTime.getTime() - startTime.getTime())/1000;
			printInfo(g, "ʱ��:"+period+"��", 20, 120, 260, Color.white);
			
			switch (period/10) {
			case 0 :
			case 1 :
				printInfo(g, "����", 50, 100, 200, Color.white);
				break;
			case 2 :
				printInfo(g, "С��", 50, 100, 200, Color.white);
				break;
			case 3 :
				printInfo(g, "����", 50, 100, 200, Color.yellow);
				break;
			case 4 :
				printInfo(g, "������", 50, 100, 200, Color.yellow);
				break;
			case 5 :
				printInfo(g, "����", 50, 100, 200, Color.yellow);
				break;
			default :
				break;
			}
		}
		
		
		//printInfo(g, "������100", 10, 50, 50, Color.yellow);
	}
	/**
	 * �ڴ����ϴ�ӡ��Ϣ
	 * @param g
	 * @param str
	 * @param size
	 */
	public void printInfo(Graphics g, String str, int size, int x, int y, Color color) {
		Color c = g.getColor();
		g.setColor(color);
		Font f = new Font("����", Font.BOLD, size);
		g.setFont(f);
		g.drawString(str, x, y);
		g.setColor(c);
	}
	

	
	public void LaunchFrame() {
		super.LaunchFrame();//���ø���ķ�����ֱ�ӵ��þ���
		//���Ӽ��̼���
		addKeyListener(new KeyMonitor());
		
		//����һ���ӵ�
		for (int i = 0; i < 5; i++) {
			Bullet b = new Bullet();
			bulletList.add(b);
		}
		
		startTime = new Date();
	}
	
	// ����Ϊ�ڲ��࣬���Է���ʹ���ⲿ�����ͨ����
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println("����:" + e.getKeyCode());
			p.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("�ͷţ�" + e.getKeyCode());
			p.minusDirection(e);
		}
		
	}
	
	
	//����BgThread��(�ڲ���)��ר�����ڸ���yPosʹ����ͼƬ����
	static class BgThread extends Thread{
		@Override
		public void run() {
			while(true) {
				if(yPos >= Constant.GAME_HEIGHT) {//����
					yPos = yPos2-gameBg.getHeight(null);
				}else {
					if(yPos2 >= Constant.GAME_HEIGHT) {//����
						yPos2 = yPos-gameBg.getHeight(null);
					}else {
						if(begin == false) {//����������Ϸ�ſ�ʼ����
							yPos += 2;
						    yPos2 += 2;
						}
					}
				}
				try {
					Thread.sleep(50);//�����ٶȵ��趨
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	} 
	
	public static void main(String[] args) {
		new PlaneGameFrame().LaunchFrame();
		new BgThread().start();//�����߳�
	}

}
