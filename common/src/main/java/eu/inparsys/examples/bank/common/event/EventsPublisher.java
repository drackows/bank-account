package eu.inparsys.examples.bank.common.event;


public interface EventsPublisher {
    void publish(final DomainOutgoingEvent<?> event);
}
