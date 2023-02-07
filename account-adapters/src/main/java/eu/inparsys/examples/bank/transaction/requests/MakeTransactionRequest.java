package eu.inparsys.examples.bank.transaction.requests;

import eu.inparsys.examples.bank.account.Recipient;
import lombok.Data;
import org.javamoney.moneta.Money;

@Data
public class MakeTransactionRequest {
    private String accountId;
    private Recipient recipient;
    private Money amount;
    private String transferTitle;
}
