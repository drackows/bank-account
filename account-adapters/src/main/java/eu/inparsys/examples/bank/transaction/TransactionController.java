package eu.inparsys.examples.bank.transaction;

import eu.inparsys.examples.bank.account.MakeTransactionCommandHandler;
import eu.inparsys.examples.bank.customer.CustomerId;
import eu.inparsys.examples.bank.transaction.requests.MakeTransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static eu.inparsys.examples.bank.transaction.requests.TransactionsMapper.toDomain;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
class TransactionController {

    private final MakeTransactionCommandHandler makeTransactionCommandHandler;

    @PostMapping
    ResponseEntity<Void> makeTransaction(@RequestHeader("X-CUSTOMER-ID") CustomerId customerId, @RequestBody MakeTransactionRequest request) {
        makeTransactionCommandHandler.handle(toDomain(customerId, request));
        return ResponseEntity.created(URI.create("http://localhost")).build();
    }
}
