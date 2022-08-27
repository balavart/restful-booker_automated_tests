package com.gd.restfulboker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

/**
 * .
 *
 * @author vbalaian
 * @version 8/27/22
 */
public class BaseTest {

    protected RequestSpecification specification;

    @BeforeMethod
    public void setUp() {
        specification = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").
                build();
    }

    protected Response createBooking() {
        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Vardan");
        body.put("lastname", "Balaian");
        body.put("totalprice", 200);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-02-21");
        bookingdates.put("checkout", "2022-02-24");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Baby Bush");

        // Get response
        Response response = RestAssured.
                given(specification).
                contentType(ContentType.JSON).
                body(body.toString()).
                post("/booking");
        return response;
    }
}
