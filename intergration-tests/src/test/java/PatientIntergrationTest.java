import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntergrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI= "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken(){
        String loginPayload= """   
                {
                "email":"testuser@test.com",
                "password":"password123"
                }
                """;

//        Same as auth intergration test
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");
        System.out.println("Generated Token: "+ token);

        given()
                .header("Authorization", "Bearer "+token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());


    }

    @Test
    public void shouldDeletePatientWithValidToken(){
        String loginPayload= """   
                {
                "email":"testuser@test.com",
                "password":"password123"
                }
                """;

        String patientPayload = """
                {
                  "name": "Intergation Test",
                  "email": "IntergrationTes@example.com",
                  "address": "456 Elm St, Shelbyville,INtergara",
                  "birthDate": "1990-09-23",
                  "registerDate":"2023-12-01"
                }
                """;
//        Same as auth intergration test
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");
        System.out.println("Generated Token: "+ token);

//        Get Patient ID by creating a new patient
        String patientId= given()
                .header("Authorization", "Bearer "+token)
                .contentType("application/json")
                .body(patientPayload)
                .body(patientPayload)
                .when()
                .post("/api/patients")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id");
        System.out.println("Created Patient ID: " + patientId);

        given ()
                .header("Authorization", "Bearer "+token)
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .delete("/api/patients/"+patientId)
                .then()
                .statusCode(204);


        given ()
                .header("Authorization", "Bearer "+token)
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .delete("/api/patients/"+patientId)
                .then()
                .statusCode(404);
        System.out.println("Deleted Patient ID: " + patientId);

    }



}
