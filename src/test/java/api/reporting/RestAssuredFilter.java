package api.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RestAssuredFilter implements Filter {
	
	public Logger logger = LogManager.getLogger(this.getClass());
	private ExtentTest test;
	private ExtentTest node;
		
    @Override
    public Response filter(FilterableRequestSpecification req,
                           FilterableResponseSpecification res,
                           FilterContext ctx) {

        test = ExtentManager.getTest();
        if (test == null) {
            logger.info("ERROR: ExtentTest is NULL");
            return ctx.next(req, res);
        }
        
        node = test.createNode(
                "<span style='color:#4FC3F7;'>" + req.getMethod() + "</span> " +
                "<span style='color:#AED581;'>" + req.getURI() + "</span>"
        );
        node.info("<pre>REQUEST: " + req.getURI() +"\n"+ "BODY: "+ req.getBody()+ "</pre>");

        Response response = ctx.next(req, res);
        node.info("<pre>RESPONSE CODE: " + response.getStatusCode() +"\n"+ "RESPONSE: "+ response.getBody().asPrettyString() + "</pre>");
        
        return response;
    }
    
}