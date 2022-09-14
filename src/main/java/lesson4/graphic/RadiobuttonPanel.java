package lesson4.graphic;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadiobuttonPanel extends JPanel {

	private static final long serialVersionUID = -3411723870085434128L;
	
	private ButtonGroup radioButtonGroup= new ButtonGroup();
	
	private JRadioButton aiGameButton;
	
	private JRadioButton humanGameButton;
	
	private boolean aiGameFlag=true;
	
	public RadiobuttonPanel() {
		
		this.aiGameButton=new JRadioButton("AI vs human game", true);
		this.humanGameButton=new JRadioButton("Human vs human game", false);
		
		aiGameButton.addActionListener(e->{aiGameFlag=true;});
		
		this.radioButtonGroup.add(aiGameButton);
		
		humanGameButton.addActionListener(e->{aiGameFlag=false;
		
		System.out.println("aiGameFlag= "+aiGameFlag);
		});
		
		this.radioButtonGroup.add(humanGameButton);
		
		this.add(aiGameButton);
		this.add(humanGameButton);
	}

	public boolean isAiGameFlag() {
		return aiGameFlag;
	}

	
	
	
	

}
