package eu.inparsys.examples.bank.common.event;

import java.io.Serializable;
import java.time.Instant;

public interface DomainIncomingEvent extends Serializable {
    Instant getWhen();
    Integer getSequenceNumber();
}
