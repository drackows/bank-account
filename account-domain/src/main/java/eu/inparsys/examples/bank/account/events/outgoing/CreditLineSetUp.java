package eu.inparsys.examples.bank.account.events.outgoing;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.CreditLine;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.customer.CustomerId;
import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Value
public class CreditLineSetUp implements DomainOutgoingEvent {
    UUID eventId = UUID.randomUUID();
    AccountId agregatId;
    CreditLine creditLine;
}
