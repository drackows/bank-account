package eu.inparsys.examples.bank.read;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.events.outgoing.AccountRegisteredForCustomer;
import eu.inparsys.examples.bank.account.events.outgoing.CreditLineSetUp;
import eu.inparsys.examples.bank.account.events.outgoing.TransactionOrdered;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.customer.CustomerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountViewService implements DomainOutgoingEventListener {
    private final Map<AccountId, AccountView> mem = new HashMap<>();

    public List<AccountView> findByCustomerId(final CustomerId customerId) {
        return mem.values().stream()
                .filter(accountView -> accountView.getOwnerId() != null)
                .filter(accountView -> accountView.getOwnerId().equals(customerId))
                .toList();
    }

    public AccountView findById(final AccountId accountId) {
        return mem.get(accountId);
    }

    public List<AccountView> findRedAccounts() {
        return mem.values().stream()
                .filter(account -> account.getBalance().isNegative())
                .toList();
    }

    @Override
    public void dispatchEvent(final DomainOutgoingEvent<?> event) {
        if (event instanceof AccountRegisteredForCustomer accountRegisteredForCustomer) {
            handle(accountRegisteredForCustomer);
        } else if (event instanceof CreditLineSetUp creditLineSetUp) {
            handle(creditLineSetUp);
        } else if (event instanceof TransactionOrdered transactionOrdered) {
            handle(transactionOrdered);
        }
    }

    private void handle(final AccountRegisteredForCustomer event) {
        final AccountView accountView = mem.getOrDefault(event.getAgregatId(), new AccountView());
        accountView.setAccountId(event.getAgregatId());
        accountView.setOwnerId(event.getOwnerId());
        accountView.setBalance(event.getInitialDeposit());
        mem.put(event.getAgregatId(), accountView);
    }

    private void handle(final CreditLineSetUp event) {
        final AccountView accountView = mem.get(event.getAgregatId());
        accountView.setCreditLine(event.getCreditLine());
    }

    private void handle(final TransactionOrdered event) {
        final AccountView accountView = mem.get(event.getAgregatId());
        accountView.setBalance(accountView.getBalance().subtract(event.getAmount()));
    }
}
