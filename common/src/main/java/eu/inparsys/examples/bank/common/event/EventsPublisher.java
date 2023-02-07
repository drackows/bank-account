package eu.inparsys.examples.bank.common.event;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;

public interface EventsPublisher {
    void publish(final DomainOutgoingEvent<?> event);
}
