package api.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PetEndPoint;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import io.restassured.response.Response;

public class PetTest {
	
	Faker faker;
	Pet petDtls;
	String apiKey="";
	
	public Logger logger = LogManager.getLogger(this.getClass());
	
	@BeforeClass
	public void setUpData() {
		
		faker = new Faker();
		petDtls = new Pet();
		
		apiKey = faker.internet().uuid();
		
		List<String> photourl = new ArrayList<String>();
		photourl.add(faker.internet().url());
		photourl.add(faker.internet().url());
		photourl.add(faker.internet().url());
		
		Category category = new Category();
		category.setId(faker.idNumber().hashCode());
		category.setName(faker.animal().name());

		Tag tag = new Tag();
		tag.setId(faker.idNumber().hashCode());
		tag.setName(faker.name().name());

		petDtls.setId(faker.idNumber().hashCode());
		petDtls.setCategory(category);
		petDtls.setName(faker.name().name());
		petDtls.setPhotoUrls(photourl);
		petDtls.setTags(Arrays.asList(tag));
		petDtls.setStatus("Available");
		
	}
	
	@Test(priority=1,groups = {"PetAPI"})
	public void testCreatePet() {
		
		logger.info("Creating Pet");
		logger.info("Pet Details: "+petDtls.toString());
		Response response = PetEndPoint.createPet(petDtls);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2,groups = {"PetAPI"})
	public void testGetPet() {
		
		Response response = PetEndPoint.getPet(this.petDtls.getId());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3,groups = {"PetAPI"})
	public void testUpdatePet() {
		
		petDtls.setName(faker.name().name());
		Response response = PetEndPoint.updatePet(petDtls);
		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response updateResp = PetEndPoint.getPet(this.petDtls.getId());
		
		String petName = updateResp.jsonPath().getString("name");
		Assert.assertEquals(petName, petDtls.getName());
		Assert.assertEquals(updateResp.getStatusCode(), 200);
		
		
	}
	
	@Test(priority=4,groups = {"PetAPI"})
	public void testDeletePet() {
		
		Response response = PetEndPoint.deletePet(this.petDtls.getId(),apiKey);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response delRes = PetEndPoint.getPet(this.petDtls.getId());
		Assert.assertEquals(delRes.getStatusCode(), 404);
		String message = delRes.jsonPath().getString("message");
		Assert.assertEquals(message, "Pet not found");
		
	}

}
