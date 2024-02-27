package ru.netology;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new locale("en"));

    private DataGenerator() {
    }

    @BeforeAll
    static void setUpAll() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getRandomLogin() {
        return Faker.name().username();
    }

    public static String getRandomPassword() {
        return Faker.internet().password();
    }
    public static class Registration {
        private Registration(){
        }
    public static RegistrationDto getUser(String status) {
            return  new  RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    }
    public static RegistrationDto getRegisteredUser(String status) {
            return  sendRequest(getUser(status));
    }
    }
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String  status;
    }
}