package cn.bjsxt.util;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class MyFrame extends Frame{
	
	/**
	  * ���ش���
	  */
	public void LaunchFrame(){
		setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		setLocation(100, 100);
		setVisible(true);
		
		new PaintThread().start();//�����ػ��߳�
		
		addWindowListener(new WindowAdapter() {
            //�����ڲ���
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//������ǰ���е�����������������ʹ�0���쳣�����ʹ�����
			}
			
		});
	}
	
	//��������ķ�����˫���壩
	private Image offScreenImage = null;
	public void update(Graphics g) {
		//System.out.println("����û�б�ִ��");
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	
	/**
	 *����һ���ػ����ڵ��߳��࣬��һ���ڲ��࣬��������ⲿ������� 
	 * @author YJL
	 */
	class PaintThread extends Thread{
		
		public void run() {
			//System.out.println("˭��");
			while(true) {
				//System.out.println("����");
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
