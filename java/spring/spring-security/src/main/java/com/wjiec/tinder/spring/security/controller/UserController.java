package com.wjiec.tinder.spring.security.controller;

import com.wjiec.tinder.spring.security.dto.UserDTO;
import com.wjiec.tinder.spring.security.exception.UserAlreadyExistsException;
import com.wjiec.tinder.spring.security.mode.User;
import com.wjiec.tinder.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());

        return "user/registrationForm";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userDTO") @Validated UserDTO userDTO,
                               BindingResult result,
                               Model model) throws UserAlreadyExistsException {
        if (result.hasErrors()) {
            return "user/registrationForm";
        }

        model.addAttribute(this.userService.registerNewUser(userDTO));
        return "user/registerSuccess";
    }

    @GetMapping("/{identifier}")
    public String userDetail(Model model, @PathVariable String identifier) {
        User user = userService.loadUser(identifier);
        if (user == null) {
            return "detail";
        }

        model.addAttribute(user);
        return "user/detail";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(Model model) {
        model.addAttribute("errorMessage", "user already exists");
        return "user/registerFailure";
    }

}
