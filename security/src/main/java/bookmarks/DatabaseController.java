package bookmarks;

import bookmarks.model.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("create database call");
        return "database created";
    }

    @RequestMapping(method = RequestMethod.GET)
    String listDatabase(Principal principal) {
        this.validateUser(principal);
        System.out.println("list database call");
        return "database list: a, b";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{databaseId}")
    String deleteDatabase(Principal principal, @PathVariable Long databaseId) {
        this.validateUser(principal);
        System.out.println("delete database call");
        return "database delete: " + databaseId;
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
