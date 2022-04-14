package object;

import java.util.ArrayList;

public class ParkingLot {
	private int id;
	private String name;
	private String address;
	private double area;
	private int bikeNum;
	private ArrayList<Bike> bikeList = new ArrayList<Bike>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int getBikeNum() {
		return bikeNum;
	}
	public void setBikeNum(int bikeNum) {
		this.bikeNum = bikeNum;
	}
	public ArrayList<Bike> getBikeList() {
		return bikeList;
	}
	public void setBikeList(ArrayList<Bike> bikeList) {
		this.bikeList = bikeList;
	}

	public ParkingLot() {
	}
}
