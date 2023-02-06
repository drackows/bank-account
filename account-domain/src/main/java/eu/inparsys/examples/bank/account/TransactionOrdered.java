package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import lombok.Value;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Value
class TransactionOrdered implements DomainOutgoingEvent {
    UUID eventId = UUID.randomUUID();
    AccountId agregatId;
    TransactionId transactionId;
    Recipient recipient;
    Money amount;
    String transferTitle;
}
