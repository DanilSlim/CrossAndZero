package lesson4.graphic;





import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	

	private static final long serialVersionUID = -8180845160632468400L;

	private JButton startButton=new JButton("Start");
	
	private JButton endButton=new JButton("End");
	
	private JDialog settingsFrame;
	
     
	public ButtonPanel(MyFrame parent) {
		
		this.settingsFrame=new SettingFrame(parent);
		
		
		endButton.addActionListener(e->{System.exit(0);});
		
		
		startButton.addActionListener(e->{
			
				if(!parent.isStartGameFlag()) {
					
					this.settingsFrame.setVisible(true);
					
					//parent.setStartGameFlag(true);
				}
			
		  });
		
		
		
		this.add(startButton);
		this.add(endButton);
		
		
		
		
	}

	
	
	
	


	
}
