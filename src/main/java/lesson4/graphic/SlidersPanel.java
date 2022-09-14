package lesson4.graphic;

import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class SlidersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//private int minFieldSize;
	
	//private int maxFieldSize;
	
	private int fieldDimension;
	
	private int winCombination;
	
	private JSlider fieldDimensionSlider;
	
	private JSlider winCombinationSlader;
	
	public SlidersPanel(int minFieldSize, int maxFieldSize) {
		
		//this.minFieldSize=minFieldSize;
		
		//this.maxFieldSize=maxFieldSize;
		
		this.fieldDimension=minFieldSize;
		this.winCombination=minFieldSize;
		
		
		this.fieldDimensionSlider=new JSlider(minFieldSize, maxFieldSize, minFieldSize);
		
		this.fieldDimensionSlider.setPaintTicks(true);
		
		this.fieldDimensionSlider.setMajorTickSpacing(1);
		
		this.fieldDimensionSlider.setPaintLabels(true);
		
		this.fieldDimensionSlider.setSnapToTicks(true);
		
		this.fieldDimensionSlider.addChangeListener(e->{
			
			JSlider slider=(JSlider) e.getSource();
			
			this.fieldDimension=slider.getValue();
			
			this.winCombinationSlader.setValue(minFieldSize);
			
			this.winCombination=minFieldSize;
		});
		
		
		this.add(fieldDimensionSlider);
		
		this.add(new Label("Field dimension"));
		
		
		this.winCombinationSlader=new JSlider(minFieldSize, maxFieldSize, minFieldSize);
		

		this.winCombinationSlader.setPaintTicks(true);
		
		this.winCombinationSlader.setMajorTickSpacing(1);
		
		this.winCombinationSlader.setPaintLabels(true);
		
		this.winCombinationSlader.setSnapToTicks(true);
		
		this.winCombinationSlader.addChangeListener(e->{
			
			JSlider slider=(JSlider) e.getSource();
			
			if(slider.getValue()>this.fieldDimension) {
				
				slider.setValue(this.fieldDimension);
				
				this.winCombination=slider.getValue();
			}
			
			else
				this.winCombination=slider.getValue();
		});
		
		
		
		this.add(winCombinationSlader);
		
		this.add(new Label("Win combination"));
		
	}
	
	

	public int getFieldDimension() {
		return fieldDimension;
	}



	public int getWinCombination() {
		return winCombination;
	}
	
	
	
	

}
