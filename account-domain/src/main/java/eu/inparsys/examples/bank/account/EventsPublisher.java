package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;

interface EventsPublisher {
    void publish(final DomainOutgoingEvent event);
}
