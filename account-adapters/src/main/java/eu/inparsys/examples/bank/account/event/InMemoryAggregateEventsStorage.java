package eu.inparsys.examples.bank.account.event;

import eu.inparsys.examples.bank.common.event.DomainOutgoingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAggregateEventsStorage<T> {
    private final Map<T, List<DomainOutgoingEvent<T>>> mem = new HashMap<>();

    public void add(DomainOutgoingEvent<T> event) {
        final List<DomainOutgoingEvent<T>> events = mem.getOrDefault(event.getAgregatId(), new ArrayList<>());
        events.add(event);
        mem.put(event.getAgregatId(), events);
    }

    public List<DomainOutgoingEvent<T>> findAll(T id) {
        return mem.getOrDefault(id, Collections.emptyList());
    }
}
