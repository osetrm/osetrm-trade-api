package com.osetrm.api.trade;

import com.osetrm.api.TestData;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class TradeResourceV1Test {

    @Test
    public void getByUniqueTransactionIdentifier() {
        TradeV1 trade = new TradeV1(TestData.randomUniqueTransactionIdentifier());
        TradeV1 saved = given()
                .contentType(ContentType.JSON)
                .body(trade)
                .post("/api/v1/trades")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(TradeV1.class);
        TradeV1 got = given()
                .when()
                .get("/api/v1/trades/{uniqueTransactionIdentifier}", saved.uniqueTransactionIdentifier().value())
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(TradeV1.class);
        assertThat(saved).isEqualTo(got);
    }

}
