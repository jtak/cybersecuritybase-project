
package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.domain.Account;
import sec.project.domain.SecretNote;
import sec.project.repository.SecretNoteRepository;
import sec.project.service.LoggedInAccountService;

@Controller
public class SecretNoteController {
    
    @Autowired
    private SecretNoteRepository secretNoteRepository;
    
    @Autowired
    private LoggedInAccountService loggedInAccountService;
    
    
    @RequestMapping(value = "/secrets", method = RequestMethod.GET)
    public String list(Model model){
        Account account = loggedInAccountService.getAuthenticatedAccount();
        List<SecretNote> secrets;
        if(account.getAuthority().equals("ADMIN")){
            secrets = secretNoteRepository.findAll();
        } else {
            secrets = secretNoteRepository.findByOwner(account.getUsername());
        }
        model.addAttribute("account", account);
        model.addAttribute("secrets", secrets);
        return "secrets";
    }
    
    @ResponseBody
    @RequestMapping(value = "/secrets/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id){
        SecretNote secret = secretNoteRepository.findOne(id);
        return secret.toString();
    }
    
    @RequestMapping(value = "/secrets/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id){
        secretNoteRepository.delete(id);
        return "redirect:/secrets";
    }
    
    @RequestMapping(value="/secrets", method = RequestMethod.POST)
    public String addNew(@RequestParam String title, @RequestParam String content){
        Account account = loggedInAccountService.getAuthenticatedAccount();
        SecretNote secret = new SecretNote();
        secret.setOwner(account.getUsername());
        secret.setTitle(title);
        secret.setContent(content);
        secretNoteRepository.save(secret);
        return "redirect:/secrets";
    }


    
}
