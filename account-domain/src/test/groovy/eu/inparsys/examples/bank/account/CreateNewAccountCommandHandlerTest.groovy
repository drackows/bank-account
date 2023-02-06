package eu.inparsys.examples.bank.account

import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer
import eu.inparsys.examples.bank.customer.CustomerId
import org.javamoney.moneta.Money
import spock.lang.Specification
import spock.lang.Subject

import javax.money.CurrencyUnit
import javax.money.Monetary

class CreateNewAccountCommandHandlerTest extends Specification {
    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR")

    @Subject
    Account account = new Account()

    def 'new account for customer is registered'() {
        given: "a bank employee registered customer details"
        CustomerId customerId = CustomerId.newOne();
        and: "account is created"
        given new AccountRegisteredForCustomer(customerId, Money.of(1000, EUR));
        expect: "details of account is set"
        account.ownerId == customerId
        account.balance == Money.of(1000, EUR)
    }

    private void given(AccountRegisteredForCustomer event) {
        account = account.handle(event);
    }
}
