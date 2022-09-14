package lesson4.logic;

import java.util.Objects;

import lesson4.graphic.CellGameField;

public class UtilField {
	
	
	private String direct;
	
	private int weight;
	
	private CellGameField targetField;
	
	private String type;
	
	
	
	public UtilField(CellGameField targetField) {
		
		this.targetField=targetField;
	}
	
	

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public CellGameField getTargetField() {
		return targetField;
	}

	public void setTargetField(CellGameField targetField) {
		this.targetField = targetField;
	}
	
	

	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	@Override
	public int hashCode() {
		return Objects.hash(direct, targetField, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UtilField other = (UtilField) obj;
		return Objects.equals(direct, other.direct) && Objects.equals(targetField, other.targetField)
				&& weight == other.weight;
	}
	
	
	
	

}
