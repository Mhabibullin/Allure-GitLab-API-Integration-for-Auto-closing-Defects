package mkhabibullin.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static mkhabibullin.data.StaticData.ALLURE_ENDPOINT;
import static mkhabibullin.data.StaticData.ALLURE_TOKEN;
import static org.hamcrest.CoreMatchers.endsWith;

public class AllureSpecs {

    public RequestSpecification initialRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ALLURE_ENDPOINT)
                .build()
                .header("Accept", "application/json")
                .header("Authorization", ALLURE_TOKEN);
    }

    public ResponseSpecification okResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectStatusLine(endsWith("OK"))
                .build();
    }
}
