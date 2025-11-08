package ui;

import api.generation.EntityGenerator;
import api.models.CreateAccountResponseModel;
import api.models.CreateTransactionRequestModel;
import api.models.CreateTransactionResponseModel;
import common.annotation.UserSession;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ui.elemements.TransferElementList;
import ui.pages.DepositPage;
import ui.pages.TransferPage;
import ui.pages.UserDashboardPage;
import utils.Constants;
import utils.RetryUtils;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccountUITest extends BaseUITest {

    @Test
    @UserSession
    public void userCanCreateAccount() {

        //create account
        new UserDashboardPage().open().createAccount();

        // api - account created
        CreateAccountResponseModel createAccount = SessionStorage.getSteps().getAccounts().get(0);
        assertThat(createAccount).isNotNull();

        // check alert
        new UserDashboardPage().checkAlertMessageAndAccept(UIAlerts.ACCOUNT_CREATED);
    }

    @Test
    @UserSession
    public void userCanAddFundsToAccount() {

        CreateAccountResponseModel account = SessionStorage.getSteps().createAccount();
        int amount = new Random().nextInt(1000);

        new UserDashboardPage().open().createDeposit().getPage(DepositPage.class)
                .makeDeposit(account.getId(), amount).checkAlertMessageAndAccept(UIAlerts.DEPOSIT_CREATED);

        // check transaction
        CreateTransactionResponseModel transaction = SessionStorage.getSteps().getUserAccountTransaction(account.getId()).get(0);

        assertThat(transaction).isNotNull();
    }

    @Test
    @UserSession
    public void userCanMakeTransferBetweenAccount() {

        //create account
        CreateAccountResponseModel account = SessionStorage.getSteps().createAccount();
        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setId(account.getId());

        SessionStorage.getSteps().createTransaction(transaction);
        CreateAccountResponseModel accountSecond = SessionStorage.getSteps().createAccount();

        new UserDashboardPage().open().createTransfer().getPage(TransferPage.class)
                .makeDeposit(account.getId(), transaction.getBalance(), SessionStorage.getUser().getUsername(), accountSecond.getAccountNumber())
                .checkAlertMessageAndAccept(UIAlerts.TRANSFER_CREATAD);

        // check transaction
        CreateTransactionResponseModel transactionSecond = SessionStorage.getSteps().getUserAccountTransaction(accountSecond.getId()).get(0);
        assertThat(transactionSecond).isNotNull();
        assertThat(transactionSecond.getAmount()).isEqualTo(transaction.getBalance());
    }

    @ParameterizedTest
    @ValueSource(ints = {4})
    @UserSession
    public void userCanSeeAllTransfers(int collectionTestSize) {
        for (int i = 0; i < collectionTestSize; i++) {
            CreateAccountResponseModel account = SessionStorage.getSteps().createAccount();
            CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
            transaction.setId(account.getId());
            SessionStorage.getSteps().createTransaction(transaction);
        }

        int transfersSize = RetryUtils.retry(() -> getAllTransfer().size(), result -> result > 0, Constants.MAX_ATTEMPTS, Constants.NEXT_ATTEMPT_DELAY);

        assertThat(transfersSize).isEqualTo(collectionTestSize);
    }

    public List<TransferElementList> getAllTransfer() {

        return new UserDashboardPage()
                .open()
                .createTransfer()
                .getPage(TransferPage.class)
                .repeatDeposit()
                .getAllTransfers();

    }
}
