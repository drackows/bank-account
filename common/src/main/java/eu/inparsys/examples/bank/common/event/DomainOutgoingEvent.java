package eu.inparsys.examples.bank.common.event;

import java.io.Serializable;
import java.util.UUID;

public interface DomainOutgoingEvent extends Serializable {
    UUID getEventId();
}
