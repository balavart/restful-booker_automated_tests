package com.gd.restfulbooker;

import com.gd.restfulboker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;

/**
 * .
 *
 * @author vbalaian
 * @version 8/26/22
 */
public class HealthCheckTests extends BaseTest {

    @Test
    public void healthCheckTest() {
        given().
                when().
                get("https://restful-booker.herokuapp.com/ping").
                then().
                assertThat().
                statusCode(HTTP_CREATED);
    }

    @Test
    public void healthCheckTestWithSpecTest() {
        given().
                spec(specification).
                when().
                get("/ping").
                then().
                assertThat().
                statusCode(HTTP_CREATED);
    }

    @Test
    public void headersAndCookiesTest() {
        Header someHeader = new Header("some name", "some value");
        specification.header(someHeader);

        Cookie someCookie = new Cookie.Builder("some cookie", "some cookie value").build();
        specification.cookie(someCookie);

        Response response = RestAssured.given(specification).
                cookie("Test Cookie name", "Test cookie value").
                header("Test Header name", "Test Header value").
                log().
                all().
                get("/ping");

        // Get headers
        Headers headers = response.getHeaders();
//        System.out.println("Headers: " + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        // Get cookies
        Cookies cookies = response.getDetailedCookies();
//        System.out.println("Cookies: " + cookies);
    }
}
