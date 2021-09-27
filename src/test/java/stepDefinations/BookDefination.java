package stepDefinations;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import org.junit.*;

public class BookDefination {
	
	static Response response;
	static String bookId, authorName, bookName;
	
	@Given("The Base Url is assigned")
	public void the_Base_Url_is_assigned() throws IOException {
		
	    RestAssured.baseURI = Utils.getGlobalValue("bookUrl");
	}
	
	@Given("The user adds a book with name {string} isbn {string} aisle {string} author {string} as {string}")
	public void the_user_adds_a_book_with_name_isbn_aisle_author_as(String string, String string2, String string3, String string4, String string5) throws IOException {
	    
		//Constructing the Book Object
		if(string5.equals("Object"))
		{
		
		  //Building the request
		  response =     RestAssured.given().spec(new Utils().bookRequestSpecification()).body(new TestDataBuild().
				         requestBodyBuilder(string, string2, string3, string4).toString()).
		                 when().post(APIResources.AddBook.getResource()); 
		}
		
		//Validating the request
		JSONObject jo = new JSONObject(response.body().asString());
		
		//Validating the Status Code
		if (response.getStatusCode() == 200)
		{
		
		   //Getting the bookId
		   bookId = jo.getString("ID");
		
		   //Assigning the author name also to a static variable
		   authorName = string4;
		
		   //Assigning the book Name to a string
		   bookName = string;
		   
		   Set<String> Key = jo.keySet();
		   
		   Assert.assertTrue(Key.contains("Msg"));
		   Assert.assertTrue(Key.contains("ID"));
		}
		
		else
		{
			//Getting the bookId
			bookId = string2.concat(string3);
			
			//Assigning the author name also to a static variable
			authorName = string4;
			
			//Assigning the book Name to a string
			bookName = string;
			
			//Print message if the book has already been added
		    System.out.println("The book has already been added");
		    Set<String> Key = jo.keySet();
		    Assert.assertTrue(Key.contains("msg"));
		    Assert.assertTrue(jo.getString("msg").equals("Add Book operation failed, looks like the book already exists"));
		}
		
			    
	}

	@Then("The user gets the book added with the {string}")
	public void the_user_gets_the_book_added_with_the(String string) {
		
	   //Constructing the request
	   if(string.equals("ID"))
	   {
		   //Getting the request based on response
		   response = RestAssured.given().queryParam("ID", bookId).when().get(APIResources.GetBook.getResource());
		   response.then().assertThat().statusCode(200);
		   
		   JSONArray responseArray = new JSONArray(response.body().asString());
		   for(int i = 0; i < responseArray.length(); i++)
		   {
			   JSONObject responseObject = responseArray.getJSONObject(i);
			   Set<String> Key = responseObject.keySet();
			   Assert.assertTrue(Key.contains("book_name"));
			   Assert.assertTrue(Key.contains("isbn"));
			   Assert.assertTrue(Key.contains("aisle"));
			   Assert.assertTrue(Key.contains("author"));
			   Assert.assertTrue(responseObject.getString("book_name").equals(bookName));
			   Assert.assertTrue(responseObject.getString("author").equals(authorName));			   
		   }
		   
	   }
	   else
	   {
		   //Getting the request based on response
		   response = RestAssured.given().queryParam("AuthorName", authorName).when().get(APIResources.GetBook.getResource());
		   response.then().assertThat().statusCode(200);
		   JSONArray responseArray = new JSONArray(response.body().asString());
		   boolean bookExists = false;
		   for(int i = 0; i < responseArray.length(); i++)
		   {
			   JSONObject responseObject = responseArray.getJSONObject(i);
			   Set<String> Key = responseObject.keySet();
			   Assert.assertTrue(Key.contains("book_name"));
			   Assert.assertTrue(Key.contains("isbn"));
			   Assert.assertTrue(Key.contains("aisle"));
			   if(responseObject.getString("book_name").equals(bookName))
				   bookExists = true;
		   }
		       Assert.assertTrue(bookExists);
			   
	   }       
	}

	@Then("The user deletes the book")
	public void the_user_deletes_the_book() {
		
		   //Constructing the request
		   JSONObject jo = new JSONObject();
		   jo.put("ID", bookId);
	       response = RestAssured.given().body(jo.toString()).
	    		      when().post(APIResources.DeleteBook.getResource());
	       
	       //Validating the response
	       response.then().assertThat().statusCode(200);
	       
	}

	@Then("Verifies whether the book has been deleted")
	public void verifies_whether_the_book_has_been_deleted() {
           
		   //Constructing the request
		   JSONObject jo = new JSONObject();
		   jo.put("ID", bookId);
	       response = RestAssured.given().contentType(ContentType.JSON).body(jo.toString()).  
	    		      when().post(APIResources.DeleteBook.getResource());
	      
	       //Validating the response
	       response.then().assertThat().statusCode(404);
	}

}
