package com.gd.restfulbooker;

import com.gd.restfulboker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * .
 *
 * @author vbalaian
 * @version 8/26/22
 */
public class GetBookingTests extends BaseTest {

    @Test(enabled = true)
    public void fieldsCheckJSONTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Set path parameter with booking id of new booking
        specification.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        // Get response with booking
//        Response response =
//                given().
//                        when().
//                        get("https://restful-booker.herokuapp.com/booking/{bookingId}");

        Response response = RestAssured.given(specification).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Vardan", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Balaian", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice, 200, "totalprice in response is not expected");

        boolean actualDepositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-02-21", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-02-24", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby Bush", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void fieldsCheckXMLTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Set path parameter with booking id of new booking
        specification.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        // Add xml request option
        Header xmlHeader = new Header("Accept", "application/xml");
        specification.header(xmlHeader);

        Response response = RestAssured.given(specification).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Vardan", "firstname in response is not expected");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Balaian", "lastname in response is not expected");

        int actualTotalPrice = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 200, "totalprice in response is not expected");

        boolean actualDepositpaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-02-21", "checkin in response is not expected");

        String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-02-24", "checkout in response is not expected");

        String actualAdditionalneeds = response.xmlPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby Bush", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}
