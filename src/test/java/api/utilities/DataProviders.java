package api.utilities;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import api.payload.PetLombok;
import api.payload.PetLombok.CategoryCustom;
import api.payload.PetLombok.TagCustom;
import api.payload.UserLombok;

public class DataProviders {
	
	@DataProvider(name ="UserData", parallel = true)
	public Object[][] getUserDetails(Method method) throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
        SheetName sheetAnnotation = method.getAnnotation(SheetName.class);
        if (sheetAnnotation == null) {
            throw new RuntimeException("SheetName annotation missing for: " + method.getName());
        }
        String sheetName = sheetAnnotation.value();
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount(sheetName);
		Object[][] userData = new Object[rowNum][1]; //one object per row

	    for (int i = 1; i <= rowNum; i++) {
	  
	        Integer userId = Integer.parseInt(utl.getCellData(sheetName, i, 0));
	        String userName = utl.getCellData(sheetName, i, 1);
	        String firstName = utl.getCellData(sheetName, i, 2);
	        String lastName = utl.getCellData(sheetName, i, 3);
	        String email = utl.getCellData(sheetName, i, 4);
	        String password = utl.getCellData(sheetName, i, 5);
	        String phone = utl.getCellData(sheetName, i, 6);
	        Integer userStatus = Integer.parseInt(utl.getCellData(sheetName, i, 7));
	        
	        UserLombok userDtls = new UserLombok(userId, userName, firstName, lastName, email, password, phone, userStatus);
	        userData[i - 1][0] = userDtls; // store object
	    }
	    
	    return userData;
	}
	
	@DataProvider(name ="UserName", parallel = true)
	public String[] getUserName(Method method) throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
		SheetName sheetAnnotation = method.getAnnotation(SheetName.class);
        if (sheetAnnotation == null) {
            throw new RuntimeException("SheetName annotation missing for: " + method.getName());
        }
        String sheetName = sheetAnnotation.value();
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount(sheetName);
		String[] userName = new String[rowNum]; //one object per row
		
		for(int i=1; i <= rowNum; i++) {
			userName[i-1] = utl.getCellData(sheetName, i, 1);	
		}
		
		return userName;	
	}
	
	@DataProvider(name ="PetData", parallel = true)
	public Object[][] getPetDetails(Method method) throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
        SheetName sheetAnnotation = method.getAnnotation(SheetName.class);
        if (sheetAnnotation == null) {
            throw new RuntimeException("SheetName annotation missing for: " + method.getName());
        }
        String sheetName = sheetAnnotation.value();
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount(sheetName);
		Object[][] userData = new Object[rowNum][1]; //one object per row

	    for (int i = 1; i <= rowNum; i++) {
	    	
	    	Integer petId = Integer.parseInt(utl.getCellData(sheetName, i, 0));
	    	String petName = utl.getCellData(sheetName, i, 3);
	    	Integer categoryId = Integer.parseInt(utl.getCellData(sheetName, i, 1));
	    	String categoryName = utl.getCellData(sheetName, i, 2);
	        CategoryCustom catDtls = new CategoryCustom(categoryId, categoryName);
			String photoUrls = utl.getCellData(sheetName, i, 4);
			List<String> photoUrlList = Arrays.stream(photoUrls.split(",")).map(String::trim).collect(Collectors.toList());
			String tagData = utl.getCellData(sheetName, i, 5);
			List<TagCustom> tagList = new ArrayList<>();
			for(String tagDtls : tagData.split("\\|")) {
			    String[] values = tagDtls.split(",");
			    Integer tagId = Integer.parseInt(values[0].trim());
			    String tagName = values[1].trim();
			    tagList.add(new TagCustom(tagId, tagName));
			}
			String status = utl.getCellData(sheetName, i, 6);
			
			PetLombok petDtls=new PetLombok(petId, catDtls, petName, photoUrlList, tagList, status);
	        userData[i - 1][0] = petDtls; // store object
	        
	    }
	    
	    return userData;
	}
	
	@DataProvider(name ="PetId", parallel = true)
	public Integer[] getPetId(Method method) throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
		SheetName sheetAnnotation = method.getAnnotation(SheetName.class);
        if (sheetAnnotation == null) {
            throw new RuntimeException("SheetName annotation missing for: " + method.getName());
        }
        String sheetName = sheetAnnotation.value();
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount(sheetName);
		Integer[] petId = new Integer[rowNum]; //one object per row
		
		for(int i=1; i <= rowNum; i++) {
			petId[i-1] = Integer.parseInt(utl.getCellData(sheetName, i, 0));	
		}
		
		return petId;	
	}
	
}
