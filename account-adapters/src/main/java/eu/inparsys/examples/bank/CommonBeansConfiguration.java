package eu.inparsys.examples.bank;

import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.events.InMemoryAggregateEventsStorage;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import eu.inparsys.examples.bank.read.AccountViewService;
import eu.inparsys.examples.bank.read.DomainOutgoingEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class CommonBeansConfiguration {

    @Bean
    AccountViewService accountViewUpdater() {
        return new AccountViewService();
    }

    @Bean
    EventsPublisher eventsPublisher(InMemoryAggregateEventsStorage<AccountId> eventsStorage, List<DomainOutgoingEventListener> eventListeners) {
        return event -> {
            eventsStorage.add((DomainOutgoingEvent<AccountId>) event);
            eventListeners.forEach(listener -> listener.dispatchEvent(event));
        };
    }

}
