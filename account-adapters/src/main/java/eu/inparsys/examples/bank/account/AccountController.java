package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.requests.AccountsMapper;
import eu.inparsys.examples.bank.account.requests.CreateAccountRequest;
import eu.inparsys.examples.bank.customer.CustomerId;
import eu.inparsys.examples.bank.read.AccountView;
import eu.inparsys.examples.bank.read.AccountViewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static eu.inparsys.examples.bank.account.requests.AccountsMapper.setupCreditLineCommand;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountController {

    private final CreateNewAccountCommandHandler createNewAccountCommandHandler;
    private final SetupCreditLineCommandHandler setupCreditLineCommandHandler;
    private final AccountViewService accountViewService;

    @GetMapping
    ResponseEntity<List<AccountView>> getAccounts(@RequestHeader("X-CUSTOMER-ID") CustomerId customerId) {
        return ResponseEntity.ok(accountViewService.findByCustomerId(customerId));
    }

    @GetMapping("/{accountId}")
    ResponseEntity<AccountView> getDetails(@PathVariable final AccountId accountId) {
        return ResponseEntity.ok(accountViewService.findById(accountId));
    }

    @PostMapping
    ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        createNewAccountCommandHandler.handle(AccountsMapper.registerNewAccountCommand(request));
        return ResponseEntity.created(URI.create("http://localhost")).build();
    }

    @PutMapping("/{accountId}/credit-line")
    ResponseEntity<Void> setupCreditLine(@PathVariable final AccountId accountId,
                                         @RequestBody CreditLine creditLine) {
        setupCreditLineCommandHandler.handle(setupCreditLineCommand(accountId, creditLine));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/red-accounts")
    ResponseEntity<List<AccountView>> findRedAccounts() {
        return ResponseEntity.ok(accountViewService.findRedAccounts());
    }
}
