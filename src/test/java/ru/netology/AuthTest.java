package ru.netology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;

import java.time.Duration;
import java.util.concurrent.locks.Condition;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    public void setup() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful login with active registered user")
    public void shouldTestSuccessfulRegisteredActiveUserLogin() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    public void shouldTestGetErrorMessageIfUserEnteredWrongLogin() {
        var NotRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")),
                Duration.ofSeconds(10).shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    public void shouldTestGetErrorMessageIfUserEnteredWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")),
                Duration.ofSeconds(10).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if login with wrong login")
    public void shouldTestGetErrorMessageIfUserEnteredWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getLogin());
        ;
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")),
                Duration.ofSeconds(10).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if user is blocked")
    public void shouldTestGetErrorMessageIfUserBlocked() {
        var blockedUser = getRegisteredUser("blocked");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldHave(Condition.text("Ошибка! Пользователь Заблокирован")),
                Duration.ofSeconds(10).shouldBe((Condition.visible));
    }
}
