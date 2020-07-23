package pl.klewek.filmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.service.MyUserDetailsService;


@Controller
public class Registration {

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public Registration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Autowired
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/register")
    public String getRegistration(Model model) {
        model.addAttribute("newUser",new User());
        return "registrationPage";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user){
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        myUserDetailsService.addUser(user);
        return "redirect:/register";
    }

}
