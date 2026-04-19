package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name ="Data")
	public String[][] getTestData() throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount("Sheet1");
		int colCnt = utl.getCellCount("Sheet1", 1);
		String apiData[][] = new String[rowNum][colCnt];
		
		for(int i=1; i <= rowNum; i++) {
			for(int j=0; j < colCnt; j++) {
				apiData[i-1][j] = utl.getCellData("Sheet1", i, j);
			}
		}
		
		return apiData;	
	}
	
	@DataProvider(name ="UserName")
	public String[] getUserName() throws IOException {
		
		String path = System.getProperty("user.dir")+"//TestData//UserDetails.xlsx";
		ExcelUtility utl = new ExcelUtility(path);
		int rowNum = utl.getRowCount("Sheet1");
		String apiData[] = new String[rowNum];
		
		for(int i=1; i <= rowNum; i++) {
			apiData[i-1] = utl.getCellData("Sheet1", i, 1);	
		}
		
		return apiData;	
	}
}
