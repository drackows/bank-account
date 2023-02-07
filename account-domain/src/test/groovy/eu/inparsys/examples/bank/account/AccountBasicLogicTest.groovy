package eu.inparsys.examples.bank.account

import eu.inparsys.examples.bank.account.commands.MakeTransactionCommand
import eu.inparsys.examples.bank.account.commands.SetupCreditLineCommand
import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer
import eu.inparsys.examples.bank.account.events.outgoing.TransactionOrdered
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent
import eu.inparsys.examples.bank.customer.CustomerId
import eu.inparsys.examples.bank.transaction.TransactionId
import org.iban4j.Iban
import org.javamoney.moneta.Money
import spock.lang.Specification

import javax.money.CurrencyUnit
import javax.money.Monetary

class AccountBasicLogicTest extends Specification {
    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR")

    def 'new account for customer is registered'() {
        given: "customer and base for bank account"
        CustomerId customerId = new CustomerId();
        Account account = new Account()

        and: "account is registered for customer"
        account.dispatchEvent(new AccountRegisteredForCustomer(account.id, customerId, Money.of(1000, EUR)));

        expect: "details of account is set"
        account.ownerId == customerId
        account.balance == Money.of(1000, EUR)

        when: "first transactions is made"
        account.dispatchEvent(new TransactionOrdered(account.id, new TransactionId(), new Recipient(Iban.random(), new NameAddress("addr1", [])), Money.of(500, EUR), "Title1"));

        and: "second transaction is made"
        account.dispatchEvent(new TransactionOrdered(account.id, new TransactionId(), new Recipient(Iban.random(), new NameAddress("addr2", [])), Money.of(350, EUR), "Title2"));

        then: "balance is decreased"
        account.balance == Money.of(150, EUR)

        when: "try to transfer more money than balance"
        def makeTransferError = account.makeTransaction(new MakeTransactionCommand(new Recipient(Iban.random(), new NameAddress("addr3", [])), Money.of(200, EUR), "Title3"))

        then: "expect error"
        makeTransferError.isFailure()
        makeTransferError.reason().contains "Insufficient"

        expect: "setup credit line successfully set up"
        account.setupCreditLine(new SetupCreditLineCommand(new CreditLine(Money.of(500, EUR)))).isSuccessful()

        when: "retry to make last transfer"
        def makeTransaction = account.makeTransaction(new MakeTransactionCommand(new Recipient(Iban.random(), new NameAddress("addr3", [])), Money.of(200, EUR), "Title3"))

        then:
        makeTransaction.isSuccessful()

        and:
        account.balance == Money.of(-50, EUR)

        when: "more than credit line still not possible"
        def makeTransactionBiggerThanCredit = account.makeTransaction(new MakeTransactionCommand(new Recipient(Iban.random(), new NameAddress("addr4", [])), Money.of(500, EUR), "Title4"))

        then: "result is fail"
        makeTransactionBiggerThanCredit.isFailure()
    }

    private void given(DomainOutgoingEvent event) {
        account = account.dispatchEvent(event);
    }
}
