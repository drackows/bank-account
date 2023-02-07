package eu.inparsys.examples.bank.account.events.outgoing;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.Recipient;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.transaction.TransactionId;
import lombok.Value;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Value
public class TransactionOrdered implements DomainOutgoingEvent<AccountId> {
    UUID eventId = UUID.randomUUID();
    AccountId agregatId;
    TransactionId transactionId;
    Recipient recipient;
    Money amount;
    String transferTitle;
}
