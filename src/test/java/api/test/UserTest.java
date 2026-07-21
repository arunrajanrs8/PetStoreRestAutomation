package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoint;
import api.payload.UserLombok;
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
	public void testCreateUser(UserLombok usrDtls) {
		
		Response response = UserEndPoint.createUser(usrDtls);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserName", dataProviderClass=DataProviders.class, groups = {"UserAPI-GetUser"})
	@SheetName("UserDetails")
	public void testGetUser(String userName) {
		
		Response response = UserEndPoint.getUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3, dataProvider="UserData", dataProviderClass=DataProviders.class, groups = {"UserAPI-UpdateUser"})
	@SheetName("UserDetails")
	public void testUpdateUser(UserLombok usrDtls) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper obj = new ObjectMapper();
		//update certain user details
		usrDtls.setFirstName(faker.name().firstName());
		usrDtls.setLastName(faker.name().lastName());
		usrDtls.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoint.updateUser(usrDtls.getUsername(), usrDtls);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response getResp = UserEndPoint.getUser(usrDtls.getUsername());
		
		UserLombok getResponse = obj.readValue(getResp.asString(), UserLombok.class);
		Assert.assertEquals(usrDtls.getFirstName(), getResponse.getFirstName());
		Assert.assertEquals(usrDtls.getLastName(), getResponse.getLastName());
		Assert.assertEquals(usrDtls.getEmail(), getResponse.getEmail());
		Assert.assertEquals(getResp.getStatusCode(), 200);
		
	}
	
	@Test(priority=4, dataProvider="UserName", dataProviderClass=DataProviders.class, groups = {"UserAPI-DeleteUser"})
	@SheetName("UserDetails")
	public void testDeleteUser(String userName) {
		
		Response response = UserEndPoint.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = UserEndPoint.getUser(userName);
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "User not found");
	
	}
	
}
