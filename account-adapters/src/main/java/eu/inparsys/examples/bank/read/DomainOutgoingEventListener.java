package eu.inparsys.examples.bank.read;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;

public interface DomainOutgoingEventListener {
    void dispatchEvent(DomainOutgoingEvent<?> event);
}
