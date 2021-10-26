package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req, req1;
	public RequestSpecification requestSpecification() throws IOException
	{
		
		if(req==null)
		{
		PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
		 req=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
				 .addFilter(RequestLoggingFilter.logRequestTo(log))
				 .addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		 return req;
		}
		return req;
	}
	
	public RequestSpecification bookRequestSpecification() throws IOException
	{    
		if(req1 == null)
	    { 
		 PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 req1 = new RequestSpecBuilder().setBaseUri(getGlobalValue("bookUrl")).
				 addFilter(RequestLoggingFilter.logRequestTo(log)).
				 addFilter(ResponseLoggingFilter.logResponseTo(log)).
				 build();
		 return req1;
	    }
		 return req1;
	}
	
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop =new Properties();
		FileInputStream fis =new FileInputStream(System.getProperty("user.dir") + "//src//test//java//resources//global.properties");
		prop.load(fis);
		return prop.getProperty(key);	
		
	}
	
	
	public String getJsonPath(Response response,String key)
	{
		String resp=response.asString();
		JsonPath   js = new JsonPath(resp);
		return js.get(key).toString();
	}
	
	public void Validate(Response response, String key, String Param, String bookName)
	{
		boolean bookExists = false;
		if(key.equals("ID"))
			response = RestAssured.given().queryParam("ID", Param).when().get(APIResources.GetBook.getResource());
		else
			response = RestAssured.given().queryParam("AuthorName", Param).when().get(APIResources.GetBook.getResource());
		
		response.then().assertThat().statusCode(200);
		JSONArray responseArray = new JSONArray(response.body().asString());
		
		for(int i = 0; i < responseArray.length(); i++)
		   {
			   JSONObject responseObject = responseArray.getJSONObject(i);
			   Set<String> Key = responseObject.keySet();
			   Assert.assertTrue(Key.contains("book_name"));
			   Assert.assertTrue(Key.contains("isbn"));
			   Assert.assertTrue(Key.contains("aisle"));
			   if(key.equals("ID"))
			   {	   
			       Assert.assertTrue(Key.contains("author"));
			   //    Assert.assertTrue(responseObject.getString("author").equals(Param));
			   }
			   else
			   {
				   if(responseObject.getString("book_name").equals(bookName))
					   bookExists = true;
			   }
	      }
		
		  if(!key.equals("ID"))
			  Assert.assertTrue(bookExists);
	}
}
