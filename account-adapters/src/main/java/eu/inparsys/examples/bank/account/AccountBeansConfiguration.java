package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.events.InMemoryAggregateEventsStorage;
import eu.inparsys.examples.bank.account.ports.incoming.AccountReaderPort;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountBeansConfiguration {

    @Bean
    InMemoryAggregateEventsStorage<AccountId> eventsStorage() {
        return new InMemoryAggregateEventsStorage<>();
    }

    @Bean
    AccountReaderPort accountReaderPort(InMemoryAggregateEventsStorage<AccountId> accountEventsStorage) {
        return accountId -> Account.recreate(accountEventsStorage.findAllEvents(accountId), new Account(accountId));
    }

    @Bean
    CreateNewAccountCommandHandler createNewAccountCommandHandler(final EventsPublisher eventsPublisher) {
        return new CreateNewAccountCommandHandler(eventsPublisher);
    }

    @Bean
    SetupCreditLineCommandHandler setupCreditLineCommandHandler(final AccountReaderPort accountReaderPort, final EventsPublisher eventsPublisher) {
        return new SetupCreditLineCommandHandler(accountReaderPort, eventsPublisher);
    }

    @Bean
    MakeTransactionCommandHandler makeTransactionCommandHandler(final AccountReaderPort accountReaderPort,
                                                                final EventsPublisher eventsPublisher) {
        return new MakeTransactionCommandHandler(accountReaderPort, eventsPublisher);
    }
}
