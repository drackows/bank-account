package eu.inparsys.examples.bank.account;

import org.iban4j.Iban;

public record Recipient(
        Iban accountNumber, //MAX 34
        NameAddress nameAndAddress
) {
}
