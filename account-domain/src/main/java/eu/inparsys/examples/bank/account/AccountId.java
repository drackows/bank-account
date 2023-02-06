package eu.inparsys.examples.bank.account;

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
public class AccountId implements Serializable {
    private final UUID id;

    public AccountId() {
        this(UUID.randomUUID());
    }
}
