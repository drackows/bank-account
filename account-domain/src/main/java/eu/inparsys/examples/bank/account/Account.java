package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.commands.CreateNewAccountCommand;
import eu.inparsys.examples.bank.account.commands.PlanTransactionCommand;
import eu.inparsys.examples.bank.account.commands.PayInCacheCommand;
import eu.inparsys.examples.bank.account.events.outgoing.AccountCreatedEvent;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.customer.CustomerId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
class Account {
    private UUID id;
    CustomerId ownerId;
    Money balance;

    List<DomainOutgoingEvent> outgoingEvents = new ArrayList<>();

    public static Account of(CreateNewAccountCommand command) {
        final Account account = Account.builder()
                .id(UUID.randomUUID())
                .ownerId(command.ownerId())
                .balance(command.initialDeposit())
                .build();
        account.outgoingEvents.add(new AccountCreatedEvent());
        return account;
    }

    public void payInCache(PayInCacheCommand command) {

    }

    public void planTransaction(PlanTransactionCommand command) {

    }

    public void makeTransaction(PlanTransactionCommand command) {

    }



    public boolean isBalanceSufficientFor(Money amount) {
        return balance.isGreaterThanOrEqualTo(amount);
    }
}
