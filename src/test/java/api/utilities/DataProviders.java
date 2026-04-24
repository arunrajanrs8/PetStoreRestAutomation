package api.utilities;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import api.payload.User;

public class DataProviders {
	
	@DataProvider(name ="UserData")
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
	        User user = new User();
	        user.setId(Integer.parseInt(utl.getCellData(sheetName, i, 0)));
	        user.setUsername(utl.getCellData(sheetName, i, 1));
	        user.setFirstName(utl.getCellData(sheetName, i, 2));
	        user.setLastName(utl.getCellData(sheetName, i, 3));
	        user.setEmail(utl.getCellData(sheetName, i, 4));
	        user.setPassword(utl.getCellData(sheetName, i, 5));
	        user.setPhone(utl.getCellData(sheetName, i, 6));
	        userData[i - 1][0] = user; // store object
	    }
	    
	    return userData;
	}
	
	@DataProvider(name ="UserName")
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
	
	@DataProvider(name ="PetData")
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
	    	
	        Pet petDtls = new Pet();
	        Category category = new Category();
	        Tag tag = new Tag();
	        
	        petDtls.setId(Integer.parseInt(utl.getCellData(sheetName, i, 0)));
	        category.setId(Integer.parseInt(utl.getCellData(sheetName, i, 1)));
			category.setName(utl.getCellData(sheetName, i, 2));
			petDtls.setCategory(category);
			petDtls.setName(utl.getCellData(sheetName, i, 3));
			String photoUrls = utl.getCellData(sheetName, i, 4);
			List<String> urlList = Arrays.stream(photoUrls.split(",")).map(String::trim).collect(Collectors.toList());
			petDtls.setPhotoUrls(urlList);
			tag.setId(Integer.parseInt(utl.getCellData(sheetName, i, 5)));
			tag.setName(utl.getCellData(sheetName, i, 6));
			petDtls.setTags(Arrays.asList(tag));
			petDtls.setStatus(utl.getCellData(sheetName, i, 7));
	        userData[i - 1][0] = petDtls; // store object
	        
	    }
	    
	    return userData;
	}
	
	@DataProvider(name ="PetId")
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
