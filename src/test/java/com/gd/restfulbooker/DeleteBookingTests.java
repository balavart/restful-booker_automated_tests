package com.gd.restfulbooker;

import com.gd.restfulboker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;

/**
 * .
 *
 * @author vbalaian
 * @version 8/27/22
 */
public class DeleteBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get booking id of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");

        // Delete booking by id
        Response responseDelete = RestAssured.
                given().
                spec(specification).
                auth().
                preemptive().
                basic("admin", "password123").
                delete("/booking/" + bookingId);
        responseDelete.print();

        // Get booking by id
        Response responseGet =
                given().
                        spec(specification).
                        when().
                        get("/booking/" + bookingId);
        responseGet.print();

        // Verifications
        // Verify responseDelete HTTP/1.1 201 Created and Not Found
        Assert.assertEquals(responseDelete.getStatusCode(), HTTP_CREATED, "Status code should be 201, but it's not");
        Assert.assertEquals(responseDelete.getBody().asString(), "Created", "Status message should be Created, but it's not");
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Status message should be Not Found, but it's not");
    }
}
