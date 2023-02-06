package eu.inparsys.examples.bank.customer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class CustomerId implements Serializable {
    private final UUID id;

    public CustomerId() {
        this(UUID.randomUUID());
    }
}
