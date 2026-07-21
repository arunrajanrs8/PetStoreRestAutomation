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
import api.endpoints.PetEndPoint;
import api.payload.PetLombok;
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
	public void testCreatePet(PetLombok petDtls) throws JsonMappingException, JsonProcessingException {
		
		Response response = PetEndPoint.createPet(petDtls);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="PetId", dataProviderClass=DataProviders.class, groups = {"PetAPI-GetPet"})
	@SheetName("PetDetails")
	public void testGetPet(int petId) {
		
		Response response = PetEndPoint.getPet(petId);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3, dataProvider="PetData", dataProviderClass=DataProviders.class, groups = {"PetAPI-UpdatePet"})
	@SheetName("PetDetails")
	public void testUpdatePet(PetLombok petDtls) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper obj = new ObjectMapper();
		petDtls.setName(faker.name().name());
		Response updateResponse = PetEndPoint.updatePet(petDtls);
		Assert.assertEquals(updateResponse.getStatusCode(), 200);
		
		//Checking data after update
		Response getResp = PetEndPoint.getPet(petDtls.getId());
		
		PetLombok getPetResponse = obj.readValue(getResp.asString(), PetLombok.class);
		Assert.assertEquals(petDtls.getName(), getPetResponse.getName());
		Assert.assertEquals(petDtls.getId(), getPetResponse.getId());
		Assert.assertEquals(getResp.getStatusCode(), 200);	
		
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
