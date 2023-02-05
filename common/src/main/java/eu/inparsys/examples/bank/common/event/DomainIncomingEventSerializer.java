package eu.inparsys.examples.bank.common.event;

public interface DomainIncomingEventSerializer {
    byte[] serialize(DomainIncomingEvent domainIncomingEvent);

    DomainIncomingEvent deserialize(byte[] bytes);
}
