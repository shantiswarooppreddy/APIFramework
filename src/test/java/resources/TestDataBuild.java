package resources;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pojo.AddPlace;
import pojo.Location;
import pojo.UpdatePlace;

public class TestDataBuild {

	
	
	public AddPlace addPlacePayLoad(String name, String language, String address)
	{
		AddPlace p =new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName(name);
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		p.setTypes(myList);
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		return p;
	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
	
	public UpdatePlace updatePlacePayload(String placeId, String Address)
	{
		UpdatePlace p = new UpdatePlace();
		p.setPlace_id(placeId);
		p.setAddress(Address);
		return p;
	}
	
	public JSONObject requestBodyBuilder(String book, String isbn, String aisle, String author)
	{
		JSONObject jo = new JSONObject();
		jo.put("name",book);
		jo.put("isbn", isbn);
		jo.put("aisle", aisle);
		jo.put("author", author);
		
		return jo;
	}
	
}
