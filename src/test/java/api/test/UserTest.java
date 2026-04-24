package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoint;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.SheetName;
import io.restassured.response.Response;

public class UserTest {
	
	public Logger logger = LogManager.getLogger(this.getClass());
	Faker faker;
	
	@BeforeClass
	public void setUpData() {
		faker = new Faker();
	}
	
	@Test(priority=1, dataProvider="UserData", dataProviderClass=DataProviders.class, groups = {"UserAPI-CreateUser"})
	@SheetName("UserDetails")
	public void testCreateUser(User usrDtls) {
		
		Response response = UserEndPoint.createUser(usrDtls);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserName", dataProviderClass=DataProviders.class, groups = {"UserAPI-GetUser"})
	@SheetName("UserDetails")
	public void testGetUser(String userName) {
		
		Response response = UserEndPoint.getUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3, dataProvider="UserData", dataProviderClass=DataProviders.class, groups = {"UserAPI-UpdateUser"})
	@SheetName("UserDetails")
	public void testUpdateUser(User usrDtls) {
		
		//update certain user details
		usrDtls.setFirstName(faker.name().firstName());
		usrDtls.setLastName(faker.name().lastName());
		usrDtls.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoint.updateUser(usrDtls.getUsername(), usrDtls);
		response.then().log().all();
		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response updateResp = UserEndPoint.getUser(usrDtls.getUsername());
		
		String firstName = updateResp.jsonPath().getString("firstName");
		Assert.assertEquals(firstName, usrDtls.getFirstName());
		String lastName = updateResp.jsonPath().getString("lastName");
		Assert.assertEquals(lastName, usrDtls.getLastName());
		String email = updateResp.jsonPath().getString("email");
		Assert.assertEquals(email, usrDtls.getEmail());
		Assert.assertEquals(updateResp.getStatusCode(), 200);
		
	}
	
	@Test(priority=4, dataProvider="UserName", dataProviderClass=DataProviders.class, groups = {"UserAPI-DeleteUser"})
	@SheetName("UserDetails")
	public void testDeleteUser(String userName) {
		
		Response response = UserEndPoint.deleteUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = UserEndPoint.getUser(userName);
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "User not found");
		
	}
	
}
