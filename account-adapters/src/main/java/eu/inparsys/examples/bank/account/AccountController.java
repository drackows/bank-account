package eu.inparsys.examples.bank.account;

import eu.inparsys.examples.bank.account.requests.CreateAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static eu.inparsys.examples.bank.account.requests.AccountsMapper.toCommand;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountController {

    private final CreateNewAccountCommandHandler createNewAccountCommandHandler;

    @GetMapping("/{id}")
    ResponseEntity<String> getDetails(@PathVariable final String accountId) {
        return ResponseEntity.ok("Details of: " + accountId);
    }

    @PostMapping
    ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        createNewAccountCommandHandler.handle(toCommand(request));
        return ResponseEntity.created(URI.create("http://localhost")).build();
    }
}
