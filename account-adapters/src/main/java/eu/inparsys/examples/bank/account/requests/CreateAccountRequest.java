package eu.inparsys.examples.bank.account.requests;

import eu.inparsys.examples.bank.customer.CustomerId;
import lombok.Data;
import org.javamoney.moneta.Money;

import java.util.Currency;

@Data
public class CreateAccountRequest {
    private String ownerId;
    private Currency currency;
    private Money initialDeposit;
}
