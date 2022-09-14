package lesson4.graphic;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class SettingFrame extends JDialog {

	private static final long serialVersionUID = -3972378995584630108L;
	
	private JPanel radiobuttonPanel;
	
	private JPanel slidersPanel;
	
	private JButton okButton;
	
	private int dimensionField;
	
	private int winCombination;
	
	private boolean aiGameFlag;
	
	private MyFrame parent;
	
	
	
	
	public SettingFrame(MyFrame parent) {
		
		
		
		super(parent,"Settings window",true);
		
		this.parent=parent;
		
	
		
		Point point=parent.getLocation();
		
		int x=point.x;
		
		int y=point.y;
		
		int hight=parent.getHeight();
		
		int childY=y+hight/4;
		
		this.setLocation(x, childY);
		
		this.setSize(parent.getWidth(),parent.getHeight()/2);
		
		
		
		this.radiobuttonPanel=new RadiobuttonPanel();
		
		add(radiobuttonPanel,BorderLayout.NORTH);
		
		
		
		this.slidersPanel=new SlidersPanel(3, 6);
		
		this.dimensionField=((SlidersPanel) this.slidersPanel).getFieldDimension();
		
		
		
		add(slidersPanel, BorderLayout.CENTER);
		
		
		this.okButton=new JButton("OK");
		
		okButton.addActionListener(e->{
			
			this.dimensionField=((SlidersPanel) this.slidersPanel).getFieldDimension();
			
			this.winCombination=((SlidersPanel) this.slidersPanel).getWinCombination();
			
			this.aiGameFlag=((RadiobuttonPanel) this.radiobuttonPanel).isAiGameFlag();
			
			this.parent.setDimensionField(this.dimensionField);
			
			this.parent.setNumberSimbolForWin(this.winCombination);
			
			this.parent.setAiGameFlag(this.aiGameFlag);
			
			this.parent.addGameField();
			
			this.parent.setStartGameFlag(true);
			
			
			
			this.dispose();
		});
		
		add(okButton,BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				parent.setStartGameFlag(false);
			}
		});
		
		this.setResizable(false);
	}
	
	

}
