package cn.bjsxt.util;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class MyFrame extends Frame{
	
	/**
	  * 加载窗口
	  */
	public void LaunchFrame(){
		setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		setLocation(100, 100);
		setVisible(true);
		
		new PaintThread().start();//启动重画线程
		
		addWindowListener(new WindowAdapter() {
            //匿名内部类
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//结束当前运行的虚拟机，正常结束就传0，异常结束就传负数
			}
			
		});
	}
	
	//解决闪屏的方法（双缓冲）
	private Image offScreenImage = null;
	public void update(Graphics g) {
		//System.out.println("我有没有被执行");
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	
	/**
	 *定义一个重画窗口的线程类，是一个内部类，方便访问外部类的属性 
	 * @author YJL
	 */
	class PaintThread extends Thread{
		
		public void run() {
			//System.out.println("谁先");
			while(true) {
				//System.out.println("我吗");
				repaint();
				try {
					Thread.sleep(40);   //1s=1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
			}
		}
	}


}
