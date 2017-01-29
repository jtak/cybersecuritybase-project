
package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showForm(){
        return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newAccount(@RequestParam String username, @RequestParam String password){
        Account account = new Account();
        account.setPassword(password);
        account.setUsername(username);
        account.setAuthority("USER");
        accountRepository.save(account);
        return "redirect:/login";
    }
    
    
}
