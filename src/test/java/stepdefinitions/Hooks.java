package stepdefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//execute this code when place id is null
		//write a code that will give you place id
		
		stepDefinitions s = new stepDefinitions();
		
		if(stepDefinitions.place_id==null)
		s.add_place_payload_with("kora", "Africans", "243 Silver stream");
		s.user_calls_with_http_request("addPlaceAPI", "POST"); 
		s.verfity_place_id_created_maps_to_using("kora", "getPlaceAPI");
	}
}
