package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.PetEndPoint;
import api.payload.Pet;
import api.utilities.DataProviders;
import api.utilities.SheetName;
import io.restassured.response.Response;

public class PetTest {
	
	public Logger logger = LogManager.getLogger(this.getClass());
	Faker faker;
	String apiKey="";
	
	@BeforeClass
	public void setUpData() {
		
		faker = new Faker();
		apiKey = faker.internet().uuid();
		
	}
	
	@Test(priority=1, dataProvider="PetData", dataProviderClass=DataProviders.class, groups = {"PetAPI-CreatePet"})
	@SheetName("PetDetails")
	public void testCreatePet(Pet petDtls) {
		
		logger.info("Creating Pet");
		logger.info("Pet Details: "+petDtls.toString());
		Response response = PetEndPoint.createPet(petDtls);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="PetId", dataProviderClass=DataProviders.class, groups = {"PetAPI-GetPet"})
	@SheetName("PetDetails")
	public void testGetPet(int petId) {
		
		logger.info("PetId: "+petId);
		Response response = PetEndPoint.getPet(petId);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3, dataProvider="PetData", dataProviderClass=DataProviders.class, groups = {"PetAPI-UpdatePet"})
	@SheetName("PetDetails")
	public void testUpdatePet(Pet petDtls) {
		
		petDtls.setName(faker.name().name());
		Response response = PetEndPoint.updatePet(petDtls);
		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response updateResp = PetEndPoint.getPet(petDtls.getId());
		String petName = updateResp.jsonPath().getString("name");
		Assert.assertEquals(petName, petDtls.getName());
		Assert.assertEquals(updateResp.getStatusCode(), 200);
		
		
	}
	
	@Test(priority=4, dataProvider="PetId", dataProviderClass=DataProviders.class, groups = {"PetAPI-DeletePet"})
	@SheetName("PetDetails")
	public void testDeletePet(int petId) {
		
		Response response = PetEndPoint.deletePet(petId,apiKey);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = PetEndPoint.getPet(petId);
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "Pet not found");
		
	}

}
