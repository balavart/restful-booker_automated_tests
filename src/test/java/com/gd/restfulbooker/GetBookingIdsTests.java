package com.gd.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * .
 *
 * @author vbalaian
 * @version 8/26/22
 */
public class GetBookingIdsTests extends BaseTest {

    @Test
    public void getBookingIdsWithoutFilterTest() {
        // Get response with booking ids
        Response response = RestAssured.
                given(specification).
                get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = RestAssured.
                given(specification).
                get("/booking").
                jsonPath().
                getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty, but it shouldn't be");
    }

    @Test
    public void getBookingIdsWithFilterTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // add Query parameter
        specification.queryParam("firstname", "Vardan");
        specification.queryParam("lastname", "Balaian");

        // Get response with booking ids
        Response response = RestAssured.
                given(specification).
                get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = RestAssured.
                given(specification).
                get("/booking").
                jsonPath().
                getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty, but it's shouldn't be");
    }
}
