package lesson4;

import java.awt.EventQueue;

import javax.swing.JFrame;

import lesson4.graphic.MyFrame;

public class GraphicApp {
	
	public static final int DIMENSION_FIELD=5;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MyFrame frame=new MyFrame(DIMENSION_FIELD);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				frame.setVisible(true);
				
			}
		});
		

	}

}
