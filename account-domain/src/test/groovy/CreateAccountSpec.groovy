import eu.inparsys.examples.bank.account.AccountId
import eu.inparsys.examples.bank.customer.CustomerId
import spock.lang.*

class CreateAccountSpec extends Specification {
    def 'accounts owner can see list of his accounts'() {
        given: "a bank employee registered customer details"
            CustomerId customerId = registerCustomer.forDetails(CustomerDetails.of(...))
        when: "banks employee creates account for customer"
            AccountId accountId = createAcc.forCustomer(customerId, EUR, Money.of(1000, EUR), CreditLine(500)));
        when:
            findAccountsView.ownedBy(customerId).size() == 1
    }

}
