/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class Car {

	/**
	 * Instance variable for storing the type of the car
	 */
	private CarType type;

	/**
	 * Instance variable for storing the car plate number
	 */
	private String plateNum;

	/**
	 * @return the type of this car
	 */
	public CarType getType() {
		// WRITE YOUR CODE HERE!
		return type;
	}

	/**
	 * Sets the type of the car
	 * 
	 * @param type is the car type
	 */
	public void setType(CarType type) {
		// WRITE YOUR CODE HERE!
		this.type = type;
	}

	/**
	 * @return the plate number
	 */
	public String getPlateNum() {
		// WRITE YOUR CODE HERE!
		return plateNum;
	}

	/**
	 * Sets the car plate number
	 * 
	 * @param plateNum is the car plate number
	 */
	public void setPlateNum(String plateNum) {
		// WRITE YOUR CODE HERE!
		this.plateNum = plateNum;
	}

	/**
	 * Constructor for Car
	 * 
	 * @param type     is the type of the car
	 * @param plateNum is the car plate number
	 */
	public Car(CarType type, String plateNum) {
		// WRITE YOUR CODE HERE!
		this.type=type;
		this.plateNum=plateNum;
		
	}

	/**
	 * Returns a string representation of the car
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		return Util.getLabelByCarType(type) + '(' + plateNum + ')';
	}
}
