package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.account.commands.SetupCreditLineCommand;
import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer;
import eu.inparsys.examples.bank.account.events.outgoing.CreditLineSetUp;
import eu.inparsys.examples.bank.common.Result;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.customer.CustomerId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
public
class Account {

    public static Account recreate(final List<DomainOutgoingEvent> events, Account account) {
        events.forEach(account::dispatchEvent);
        return account;
    }

    final AccountId id;
    CustomerId ownerId;
    Money balance;
    CreditLine creditLine;

    List<TransactionId> pendingTransactions = new ArrayList<>();

    Account() {
        this.id = new AccountId();
    }

    Result register(RegisterNewAccountCommand command) {
        if (isRegisteredForAccount()) {
            return Result.failure("Account already registered");
        }
        return success(new AccountRegisteredForCustomer(id, command.ownerId(), command.initialDeposit()));
    }

    Result setupCreditLine(SetupCreditLineCommand command) {
        if (isCreditLineSetup()) {
            return Result.failure("Credit line already set up");
        }
        return success(new CreditLineSetUp(id, command.creditLine()));
    }

    Result makeTransaction(MakeTransactionCommand command) {
        if (!isFundsSufficientFor(command.amount())) {
            return Result.failure("Insufficient funds to make transfer: " + command.amount());
        }
        return success(new TransactionOrdered(id, new TransactionId(), command.recipient(), command.amount(), command.transferTitle()));
    }

    /* TODO
    Result rejectTransaction(RejectTransactionCommand command) {

    }
     */

    void dispatchEvent(final DomainOutgoingEvent event) {
        if (event instanceof AccountRegisteredForCustomer accountRegisteredForCustomer) {
            handle(accountRegisteredForCustomer);
        } else if (event instanceof CreditLineSetUp creditLineSetUp) {
            handle(creditLineSetUp);
        } else if (event instanceof TransactionOrdered transactionOrdered) {
            handle(transactionOrdered);
        }
    }

    void handle(AccountRegisteredForCustomer event) {
        ownerId = event.getOwnerId();
        balance = event.getInitialDeposit();
    }

    void handle(CreditLineSetUp event) {
        creditLine = event.getCreditLine();
    }

    void handle(TransactionOrdered event) {
        balance = balance.subtract(event.getAmount());
        pendingTransactions.add(event.getTransactionId());
    }

    private boolean isCreditLineSetup() {
        return creditLine != null;
    }

    private boolean isFundsSufficientFor(final Money transferAmount) {
        //TODO implement
        return true;
    }

    private boolean isRegisteredForAccount() {
        return ownerId != null && balance != null;
    }

    private Result success(final DomainOutgoingEvent event) {
        dispatchEvent(event);
        return Result.success(event);
    }
}
