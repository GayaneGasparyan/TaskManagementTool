package com.example.taskmanagementtool.controller;

import com.example.taskmanagementtool.enumPackage.Role;
import com.example.taskmanagementtool.model.Issue;
import com.example.taskmanagementtool.model.User;
import com.example.taskmanagementtool.repository.IssueRepository;
import com.example.taskmanagementtool.repository.UserRepository;
import com.example.taskmanagementtool.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(ModelMap map) {

        map.addAttribute("user", new User());
        return "login";
    }
//    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
//    public String addUser(@ModelAttribute(name = "user") User user) {
//        userRepository.save(user);
//        return "login";
//    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess() {
        CurrentUser principal = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUser().getRole() == Role.ADMIN) {
            return "redirect:/admin";
        } if (principal.getUser().getRole() == Role.USER) {
            return "redirect:/user";
        } if (principal.getUser().getRole() == Role.TEAM_LEAD) {
            return "redirect:/teamLead";
        }
        return "redirect:/";
    }

    @GetMapping("/login")

    public String log(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/home")
    public String home(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("user", new User());
        map.addAttribute("issues", issueRepository.findAll());
        map.addAttribute("issue", new Issue());
        return "index";
    }
}
