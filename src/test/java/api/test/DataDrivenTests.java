package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.UserEndPoint;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testCreateUser(String id, String uName, String fName, String lName, String email, String password, String phone) {
		
		User dtls = new User();
		
		dtls.setId(Integer.parseInt(id));
		dtls.setUsername(uName);
		dtls.setFirstName(fName);
		dtls.setLastName(lName);
		dtls.setEmail(email);
		dtls.setPassword(password);
		dtls.setPhone(phone);
		
		Response response = UserEndPoint.createUser(dtls);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserName", dataProviderClass=DataProviders.class)
	public void testDeleteUser(String uName) {
		
		Response response = UserEndPoint.deleteUser(uName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = UserEndPoint.getUser(uName);
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "User not found");
		
	}

}
