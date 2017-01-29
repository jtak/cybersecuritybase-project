
package sec.project.controller;

import java.util.ArrayDeque;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Message;
import sec.project.service.LoggedInAccountService;

@Controller
public class MessageController {

    @Autowired
    private LoggedInAccountService loggedInAccountService;
    
    private ArrayDeque<Message> messages;
    
    @PostConstruct
    public void init(){
        this.messages = new ArrayDeque<>();
    }
            
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("messages", messages);
        return "messages";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String submitForm(@RequestParam String message) {
        String username = loggedInAccountService.getAuthenticatedAccount().getUsername();
        messages.add(new Message(username, message));
        if(messages.size() > 8){
            messages.poll();
        }
        return "redirect:/messages";
    }

}

