package eu.inparsys.examples.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.inparsys.examples.bank.account.AccountId;
import eu.inparsys.examples.bank.account.event.InMemoryAggregateEventsStorage;
import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;
import eu.inparsys.examples.bank.common.event.EventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
class CommonBeansConfiguration {

    @Bean
    EventsPublisher eventsPublisher(InMemoryAggregateEventsStorage<AccountId> eventsStorage) {
        return event -> {
            eventsStorage.add((DomainOutgoingEvent<AccountId>) event);
        };
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new MoneyModule());
    }
}
