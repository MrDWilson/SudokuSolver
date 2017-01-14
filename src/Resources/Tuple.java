package Resources;

public class Tuple<T,J> {
	
	private T boxNumber;
	private J value;
	
	public Tuple() {
	}
	
	public Tuple(T boxNumber) {
		this.boxNumber = boxNumber;
	}
	
	public Tuple(T boxNumber, J value) {
		this.boxNumber = boxNumber;
		this.value = value;
	}
	
	public T getBoxNumber() {
		return boxNumber;
	}
	
	public J getValue() {
		return value;
	}
	
	public void setValue(J corrected) {
		value = corrected;
	}
	
	
//	public String getAsOne() {
//		//return boxNumber + ":" + T.toString(value);
//	}
//	
	public String toString() {
		return "Box: " + boxNumber.toString() + ", value: " + value.toString();
	}

}
