package eu.inparsys.examples.bank.account;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class CreditLine {
    Money amount;
}
