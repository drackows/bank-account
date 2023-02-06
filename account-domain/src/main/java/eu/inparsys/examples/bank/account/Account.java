package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand;
import eu.inparsys.examples.bank.account.commands.RegisterNewAccountCommand;
import eu.inparsys.examples.bank.account.commands.SetupCreditLineCommand;
import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer;
import eu.inparsys.examples.bank.account.events.outgoing.CreditLineSetUp;
import eu.inparsys.examples.bank.common.Result;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.customer.CustomerId;
import io.vavr.API;
import io.vavr.Predicates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.$;
import static io.vavr.API.Case;

@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
public
class Account {

    public static Account recreate(final List<DomainOutgoingEvent> events, Account account) {
        return io.vavr.collection.List.ofAll(events)
                .foldLeft(account, Account::dispatchEvent);
    }

    private final AccountId id;
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

    Account handle(AccountRegisteredForCustomer event) {
        return toBuilder()
                .ownerId(event.getOwnerId())
                .balance(event.getInitialDeposit())
                .build();
    }

    Account handle(CreditLineSetUp event) {
        return toBuilder()
                .creditLine(event.getCreditLine())
                .build();
    }

    Account handle(TransactionOrdered event) {
        final var pendingTransactions = new ArrayList<>(this.pendingTransactions);
        pendingTransactions.add(event.getTransactionId());
        return toBuilder()
                .balance(balance.subtract(event.getAmount()))
                .pendingTransactions(pendingTransactions)
                .build();
    }

    private boolean isCreditLineSetup() {
        return creditLine != null;
    }

    private boolean isFundsSufficientFor(final Money command) {
        return false;
    }

    private boolean isRegisteredForAccount() {
        return ownerId != null && balance != null;
    }

    private Result success(final DomainOutgoingEvent event) {
        dispatchEvent(event);
        return Result.success(event);
    }

    private Account dispatchEvent(final DomainOutgoingEvent event) {
        return API.Match(event).of(
                Case($(Predicates.instanceOf(AccountRegisteredForCustomer.class)), this::handle),
                Case($(Predicates.instanceOf(CreditLineSetUp.class)), this::handle)
                Case($(Predicates.instanceOf(TransactionOrdered.class)), this::handle)
        );
    }
}
