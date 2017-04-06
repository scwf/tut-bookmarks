package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

/**
 * Created by w00228970 on 2017/4/5.
 */
@RestController
@RequestMapping("/v1.0/databases")
public class DatabaseController {
    @Autowired
    DatabaseController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    String createDatabase(Principal principal) {
        this.validateUser(principal);

        return "database created";
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId));
    }

    private final AccountRepository accountRepository;

}
