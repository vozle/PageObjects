package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldTransferMoneyBetweenCard001toCard002() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCardNumber();
        var secondCard = DataHelper.getSecondCardNumber();
        int amount = 1917;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCard) - amount;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCard) + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCard);
        dashboardPage = transferPage.doTransfer(String.valueOf(amount), firstCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyBetweenCard0002toCard0001() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCardNumber();
        var secondCard = DataHelper.getSecondCardNumber();
        int amount = 2740;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCard) + amount;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCard) - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCard);
        dashboardPage = transferPage.doTransfer(String.valueOf(amount), secondCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

}