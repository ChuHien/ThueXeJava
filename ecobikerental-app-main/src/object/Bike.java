package object;

public class Bike {
	private int id;
	private String name;
	private int plID;
	private String plName;
	private String type;
	private String battery;
	private boolean status;
	private double price;

	public Bike() {
	}

	public Bike(int id, String name, int plID, String plName, String type, String battery, boolean status, double price) {
		this.id = id;
		this.name = name;
		this.plID = plID;
		this.plName = plName;
		this.type = type;
		this.battery = battery;
		this.status = status;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlID() {
		return plID;
	}

	public void setPlID(int plID) {
		this.plID = plID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlName() {
		return plName;
	}

	public void setPlName(String plName) {
		this.plName = plName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
