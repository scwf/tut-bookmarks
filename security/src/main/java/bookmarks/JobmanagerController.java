package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1.0/databases")
public class JobmanagerController {

    @RequestMapping(method = RequestMethod.POST, value = "/{databaseId}/jobs/sql")
    String executeSQL(Principal principal, @PathVariable Long databaseId, @RequestBody ExecutionSQL input) {
        this.validateUser(principal);
        System.out.println("execute sql");
        return "execute sql: " + databaseId + " " + input.getSql();
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId));
    }

    private final AccountRepository accountRepository;

    @Autowired
    JobmanagerController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
