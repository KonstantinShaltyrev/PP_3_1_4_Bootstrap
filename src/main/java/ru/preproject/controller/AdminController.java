package ru.preproject.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.preproject.model.User;
import ru.preproject.service.UserService;
import ru.preproject.util.UserValidator;

import java.security.Principal;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public AdminController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPanel(ModelMap model, Principal principal) {
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()).get());
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("admCreateUser", new User());
        model.addAttribute("roleName", "USER");
        model.addAttribute("errorFlag", "false");
        model.addAttribute("updatedUser", new User());

        return "admin/admin_panel";
    }

    @PostMapping(value ="/create")
    public String createNewUser(@Valid @ModelAttribute("admCreateUser") User admCreatedUser, BindingResult bindingResult, String roleName, ModelMap model, Principal principal) {
        userValidator.validate(admCreatedUser, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", userService.findByEmail(principal.getName()).get());
            model.addAttribute("userList", userService.findAll());
            model.addAttribute("errorFlag", "true");
            model.addAttribute("updatedUser", new User());

            return "admin/admin_panel";
        }
        userService.addUser(admCreatedUser, roleName);
        return "redirect:/admin";
    }

    @PostMapping(value ="/update")
    public String updateUser(@Validated @ModelAttribute("user") User updatedUser, BindingResult bindingResult, String roleName) {
        userValidator.validate(updatedUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/admin_panel";
        }

        userService.updateUser(updatedUser, roleName);
        return "redirect:/admin";
    }

    @PostMapping(value ="/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }
}