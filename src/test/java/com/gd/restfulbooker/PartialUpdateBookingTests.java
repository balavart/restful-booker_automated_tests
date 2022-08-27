package com.gd.restfulbooker;

import com.gd.restfulboker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * .
 *
 * @author vbalaian
 * @version 8/27/22
 */
public class PartialUpdateBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get booking id of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");

        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Sai");
        body.put("bookingdates.checkin", "2019-01-07");

        // Partial Update booking
        Response responseUpdate = RestAssured.
                given(specification).
                auth().
                preemptive().
                basic("admin", "password123").
                contentType(ContentType.JSON).
                body(body.toString()).
                patch("/booking/" + bookingId);
        responseUpdate.print();

        // Verifications
        // Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Sai", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Balaian", "lastname in response is not expected");

        int actualTotalPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice, 200, "totalprice in response is not expected");

        boolean actualDepositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2019-01-07", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-02-24", "checkout in response is not expected");

        String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby Bush", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}
