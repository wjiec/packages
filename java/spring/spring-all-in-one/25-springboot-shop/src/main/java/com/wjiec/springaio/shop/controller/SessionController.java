package com.wjiec.springaio.shop.controller;

import com.wjiec.springaio.shop.config.WebSecurityConfig;
import com.wjiec.springaio.shop.domain.User;
import com.wjiec.springaio.shop.dto.LoginParam;
import com.wjiec.springaio.shop.dto.RegisterParam;
import com.wjiec.springaio.shop.repository.UserRepository;
import com.wjiec.springaio.shop.type.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
public class SessionController {

    private final UserRepository userRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal Session session,
                        @RequestParam(value = "error", required = false) String error) {
        if (session != null && session.getUsername() != null) {
            return "redirect:/";
        }

        model.addAttribute("login", new LoginParam());
        if (error != null) {
            model.addAttribute("error", "Username or passcode invalid");
        }
        return "user/login";
    }

    @PostMapping("/session")
    public String processLogin(Principal principal) {
        log.info(principal.toString());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("register", new RegisterParam());
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("register") @Validated RegisterParam param, BindingResult errors,
                                  WebSecurityConfig.Md5PasswordEncoder passwordEncoder) {
        if (errors.hasErrors()) {
            return "user/register";
        }

        if (!param.getPasscode().equals(param.getCheckedPasscode())) {
            errors.rejectValue("checkedPasscode", "", "checked code not equals to passcode");

            return "user/register";
        }

        userRepository.save(User.builder()
            .username(param.getUsername())
            .password(passwordEncoder.encode(param.getPasscode()))
            .build());
        return "redirect:/login";
    }

}
