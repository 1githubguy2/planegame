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
	
	//取得开始背景
	Image beginBg = GameUtil.getImage("images/startbg1.jpg");
	//取得游戏背景
	public static Image gameBg = GameUtil.getImage("images/background1.bmp");
	//新增变量begin，标志这是刚刚开始游戏
	static boolean begin = true;
	
	//使用两个变量记录位置：
	static int yPos = -1*(gameBg.getHeight(null)-Constant.GAME_HEIGHT)+1;
	static int yPos2 = yPos-gameBg.getHeight(null);
	
	
	//Image bg = GameUtil.getImage("images/bg.jpg");
	Plane p = new Plane("images/plane.png", 50, 50);
	
	ArrayList bulletList = new ArrayList();//泛型暂时未学，暂时不加，以后学了，强烈建议加上
	
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
		//在第一个图片后面紧接着画一张图片
		g.drawImage(gameBg, 0, yPos, null);
		g.drawImage(gameBg, 0, yPos2, null);	//两张图片交替
		
		
		//g.drawImage(bg, 0, 0, null);
		p.draw(g);
		
		for(int i=0; i<bulletList.size(); i++) {
			Bullet b = (Bullet) bulletList.get(i);
			b.draw(g);
			
			//检测飞机的碰撞
			boolean peng = b.getRect().intersects(p.getRect());
			if(peng) {
				p.setLive(false);//飞机死掉
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
			printInfo(g, "时间:"+period+"秒", 20, 120, 260, Color.white);
			
			switch (period/10) {
			case 0 :
			case 1 :
				printInfo(g, "菜鸟", 50, 100, 200, Color.white);
				break;
			case 2 :
				printInfo(g, "小鸟", 50, 100, 200, Color.white);
				break;
			case 3 :
				printInfo(g, "大鸟", 50, 100, 200, Color.yellow);
				break;
			case 4 :
				printInfo(g, "鸟王子", 50, 100, 200, Color.yellow);
				break;
			case 5 :
				printInfo(g, "鸟人", 50, 100, 200, Color.yellow);
				break;
			default :
				break;
			}
		}
		
		
		//printInfo(g, "分数：100", 10, 50, 50, Color.yellow);
	}
	/**
	 * 在窗口上打印信息
	 * @param g
	 * @param str
	 * @param size
	 */
	public void printInfo(Graphics g, String str, int size, int x, int y, Color color) {
		Color c = g.getColor();
		g.setColor(color);
		Font f = new Font("宋体", Font.BOLD, size);
		g.setFont(f);
		g.drawString(str, x, y);
		g.setColor(c);
	}
	

	
	public void LaunchFrame() {
		super.LaunchFrame();//想用父类的方法，直接调用就行
		//增加键盘监听
		addKeyListener(new KeyMonitor());
		
		//生成一堆子弹
		for (int i = 0; i < 5; i++) {
			Bullet b = new Bullet();
			bulletList.add(b);
		}
		
		startTime = new Date();
	}
	
	// 定义为内部类，可以方便使用外部类的普通属性
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println("按下:" + e.getKeyCode());
			p.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("释放：" + e.getKeyCode());
			p.minusDirection(e);
		}
		
	}
	
	
	//创建BgThread类(内部类)，专门用于改名yPos使背景图片滚动
	static class BgThread extends Thread{
		@Override
		public void run() {
			while(true) {
				if(yPos >= Constant.GAME_HEIGHT) {//交替
					yPos = yPos2-gameBg.getHeight(null);
				}else {
					if(yPos2 >= Constant.GAME_HEIGHT) {//交替
						yPos2 = yPos-gameBg.getHeight(null);
					}else {
						if(begin == false) {//真正进入游戏才开始滚动
							yPos += 2;
						    yPos2 += 2;
						}
					}
				}
				try {
					Thread.sleep(50);//滚动速度的设定
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	} 
	
	public static void main(String[] args) {
		new PlaneGameFrame().LaunchFrame();
		new BgThread().start();//启动线程
	}

}
