package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Pet;
import api.reporting.RestAssuredFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoint {
	
	static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}
	
	public static Response createPet(Pet petDtls) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(petDtls)
			.when()
				.post(getURL().getString("post_peturl"));
		
		return response;
	}

	public static Response getPet(int petId) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.pathParam("petId", petId)
			.when()
				.get(getURL().getString("get_peturl"));

		return response;
	}

	public static Response updatePet(Pet petDtls) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(petDtls)
			.when()
				.put(getURL().getString("update_peturl"));

		return response;
	}

	public static Response deletePet(int petId, String apiKey) {

		Response response = given()
				.filter(new RestAssuredFilter())
	            .header("api_key", apiKey)
				.pathParam("petId", petId)
			.when()
				.delete(getURL().getString("delete_peturl"));

		return response;
	}

}
