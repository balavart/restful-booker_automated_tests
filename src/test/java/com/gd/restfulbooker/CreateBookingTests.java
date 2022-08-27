package com.gd.restfulbooker;

import com.gd.restfulboker.BaseTest;
import com.gd.restfulboker.Booking;
import com.gd.restfulboker.BookingDates;
import com.gd.restfulboker.BookingId;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * .
 *
 * @author vbalaian
 * @version 8/27/22
 */
public class CreateBookingTests extends BaseTest {

    @Test
    public void createBooingTest() {
        Response response = createBooking();
        response.print();

        // Verifications
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Vardan", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Balaian", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 200, "totalprice in response is not expected");

        boolean actualDepositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-02-21", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-02-24", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby Bush", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void createBooingWithPOJOTest() {
        // Create body using POJOs
        BookingDates bookingDates = new BookingDates("2022-02-21", "2022-02-24");
        Booking bookingBody = new Booking("Vardan", "Balaian", 200, false, bookingDates, "Baby Bush");

        // Get response
        Response response = RestAssured.
                given(specification).
                contentType(ContentType.JSON).
                body(bookingBody).
                post("/booking");
        response.print();
        BookingId bookingId = response.as(BookingId.class);

        // Verifications
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), HTTP_OK, "Status code should be 200, but it's not");

        // Verify fields with classic option
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Vardan", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Balaian", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 200, "totalprice in response is not expected");

        boolean actualDepositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-02-21", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-02-24", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby Bush", "additionalneeds in response is not expected");

        softAssert.assertAll();

        // Verify fields with deserialization option
        System.out.println("Request booking: " + bookingBody);
        System.out.println("Response booking: " + bookingId.getBooking().toString());
        Assert.assertEquals(bookingId.getBooking().toString(), bookingBody.toString());
    }
}
