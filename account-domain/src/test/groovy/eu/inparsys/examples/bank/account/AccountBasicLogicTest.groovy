package eu.inparsys.examples.bank.account

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand
import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer
import eu.inparsys.examples.bank.common.Result
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent
import eu.inparsys.examples.bank.customer.CustomerId
import org.iban4j.Iban
import org.javamoney.moneta.Money
import spock.lang.Specification

import javax.money.CurrencyUnit
import javax.money.Monetary

class AccountBasicLogicTest extends Specification {
    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR")

    def 'new account for customer is registered'() {
        given: "a bank employee registered customer details"
        CustomerId customerId = new CustomerId();
        Account account = new Account()
        and: "account is created"
        account.dispatchEvent(new AccountRegisteredForCustomer(account.id, customerId, Money.of(1000, EUR)));
        and: "first transactions is made"
        account.dispatchEvent(new TransactionOrdered(account.id, new TransactionId(), new Recipient(Iban.random(), new NameAddress("addr1", [])), Money.of(500, EUR), "Title1"));
        and: "second transaction is made"
        account.dispatchEvent(new TransactionOrdered(account.id, new TransactionId(), new Recipient(Iban.random(), new NameAddress("addr2", [])), Money.of(350, EUR), "Title2"));
        expect: "details of account is set"
        account.ownerId == customerId
        account.balance == Money.of(150, EUR)
        when: "try to transfer more money than balance"
        def result = account.makeTransaction(new MakeTransactionCommand(new Recipient(Iban.random(), new NameAddress("addr3", [])), Money.of(200, EUR), "Title3"))
        then: "expect error"
        result.isFailure()
        result.reason().contains "insufficient"

    }

    private void given(DomainOutgoingEvent event) {
        account = account.dispatchEvent(event);
    }
}
