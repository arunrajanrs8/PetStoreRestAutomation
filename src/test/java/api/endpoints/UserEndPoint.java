package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import api.reporting.RestAssuredFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoint {
	
	static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}

	public static Response createUser(User userDtls) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(userDtls)
			.when()
				.post(getURL().getString("post_url"));
		
		return response;
	}

	public static Response getUser(String userName) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.pathParam("username", userName)
			.when()
				.get(getURL().getString("get_url"));

		return response;
	}

	public static Response updateUser(String userName, User userDtls) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", userName)
				.body(userDtls)
			.when()
				.put(getURL().getString("update_url"));

		return response;
	}

	public static Response deleteUser(String userName) {

		Response response = given()
				.filter(new RestAssuredFilter())
				.pathParam("username", userName)
			.when()
				.delete(getURL().getString("delete_url"));

		return response;
	}

}
