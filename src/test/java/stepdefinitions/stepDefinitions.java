package stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefinitions extends Utils{

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("add place Payload with {string}{string}{string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	   
			res = given().spec(requestSpecification())
				.body(data.addPlacePayLoad(name, language, address)); 
	}

	@When("user calls {string}  with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		//Constructor will be called with value of which we passed
		APIResources resourceAPI=APIResources.valueOf(resource);
				System.out.println(resourceAPI.getResource());
				
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST")) {
			
		response = res.when().post(resourceAPI.getResource());
		
		}
		else if(method.equalsIgnoreCase("GET")) {
			
			response = res.when().get(resourceAPI.getResource());
			
		}

	}

	@Then("the Api calls is success status code {int}")
	public void the_api_calls_is_success_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_resonse_body_is(String keyValue, String Expectedvalue) {
		assertEquals(getJsonPath(response, keyValue), Expectedvalue);
	}
	
	@Then("verfity place_ID created maps to {string} using {string}")
	public void verfity_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id=getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName=getJsonPath(response, "name");
		assertEquals(actualName,expectedName);
	}
	
	@Given("delecte payLoad")
	public void delecte_pay_load() throws IOException {
		
		res = given().spec(requestSpecification())
				.body(data.deletePlacePayload(place_id)); 
		
	}
		
}
