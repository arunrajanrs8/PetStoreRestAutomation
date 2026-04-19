package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User usrDtls;
	
	public Logger logger = LogManager.getLogger(this.getClass());
	
	@BeforeClass
	public void setUpData() {
		
		faker = new Faker();
		usrDtls = new User();
		
		usrDtls.setId(faker.idNumber().hashCode());
		usrDtls.setUsername(faker.name().username());
		usrDtls.setFirstName(faker.name().firstName());
		usrDtls.setLastName(faker.name().lastName());
		usrDtls.setEmail(faker.internet().safeEmailAddress());
		usrDtls.setPassword(faker.internet().password(5, 10));
		usrDtls.setPhone(faker.phoneNumber().cellPhone());
		
	}
	
	@Test(priority=1,groups = {"UserAPI"})
	public void testCreateUser() {
		
		logger.info("Creating user");
		logger.info("User Details: "+usrDtls.toString());
		Response response = UserEndPoint.createUser(usrDtls);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2,groups = {"UserAPI"})
	public void testGetUser() {
		
		Response response = UserEndPoint.getUser(this.usrDtls.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3,groups = {"UserAPI"})
	public void testUpdateUser() {
		
		//update certain user Details
		usrDtls.setFirstName(faker.name().firstName());
		usrDtls.setLastName(faker.name().lastName());
		usrDtls.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoint.updateUser(this.usrDtls.getUsername(), usrDtls);
		response.then().log().all();
		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response updateResp = UserEndPoint.getUser(this.usrDtls.getUsername());
		
		String firstName = updateResp.jsonPath().getString("firstName");
		Assert.assertEquals(firstName, usrDtls.getFirstName());
		String lastName = updateResp.jsonPath().getString("lastName");
		Assert.assertEquals(lastName, usrDtls.getLastName());
		String email = updateResp.jsonPath().getString("email");
		Assert.assertEquals(email, usrDtls.getEmail());
		Assert.assertEquals(updateResp.getStatusCode(), 200);
		
		
	}
	
	@Test(priority=4,groups = {"UserAPI"})
	public void testDeleteUser() {
		
		Response response = UserEndPoint.deleteUser(this.usrDtls.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = UserEndPoint.getUser(this.usrDtls.getUsername());
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "User not found");
		
	}
	
}
