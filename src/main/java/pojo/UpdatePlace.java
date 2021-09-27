package pojo;

public class UpdatePlace {
	
	private String place_id;
	private String key = "qaclick123";
	private String address;
	
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getPlace_id() {
		return place_id;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getKey() {
		return key;
	}

}
