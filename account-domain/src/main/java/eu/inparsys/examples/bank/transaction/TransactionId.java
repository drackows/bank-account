package eu.inparsys.examples.bank.transaction;

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
public class TransactionId implements Serializable {
    private final UUID id;

    public TransactionId() {
        this(UUID.randomUUID());
    }
}
