package com.example.buysell.controllers;

import com.example.buysell.models.Card;
import com.example.buysell.models.CashAccount;
import com.example.buysell.models.User;
import com.example.buysell.repositories.CashAccountRepository;
import com.example.buysell.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final CashAccountService cashAccountService;
    private final DepositService depositService;
    private final PaymentService paymentService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal,
                          Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("cashAccount", cashAccountService.findByUserId(user.getId()));
        return "profile";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(Principal principal, @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errorsMap", errorsMap);
            return "redirect:/registration";
        }
        else {
            if (!userService.createUser(user)) {
                model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
                return "registration";
            }
            model.addAttribute("message", "A message has been sent to your email. Follow the link and confirm registration");
            model.addAttribute("user", userService.getUserByPrincipal(principal));
            return "message-page";
        }
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", productService.listProductsByUserId(user.getId()));
        model.addAttribute("deposits", depositService.listDepositsByCashAccountId(user.getId()));
        model.addAttribute("payments", paymentService.listPaymentsByCashAccountId(user.getId()));
        return "user-info";


    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");

        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "message-page";
    }

    @GetMapping("/user/cashAccount")
    public String userBalance(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("cashAccount", cashAccountService.findByUserId(user.getId()));
        model.addAttribute("deposits", depositService.listDepositsByCashAccountId(user.getId()));
        model.addAttribute("payments", paymentService.listPaymentsByCashAccountId(user.getId()));
        return "cash-account";


    }

    @GetMapping("/user/cashAccount/deposit")
    public String depositPage(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("cashAccount", cashAccountService.findByUserId(user.getId()));
        return "deposit";


    }

    @PostMapping("/user/cashAccount/deposit")
    public String depositCreate(Card card, Model model, Principal principal, float sum) {
        User user = userService.getUserByPrincipal(principal);
        CashAccount cashAccount = cashAccountService.findByUserId(user.getId());
        cashAccountService.deposit(user.getId(), sum);
        depositService.createDeposit(cashAccount, card, sum);
        return "redirect:/user/cashAccount";


    }

}
