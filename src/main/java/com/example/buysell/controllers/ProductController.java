package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.ProductCity;
import com.example.buysell.models.enums.ProductHealth;
import com.example.buysell.models.enums.ProductType;
import com.example.buysell.repositories.PaymentRepository;
import com.example.buysell.services.CashAccountService;
import com.example.buysell.services.PaymentService;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.List;
import java.util.Map;


import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final PaymentRepository paymentRepository;
    private final ProductService productService;

    private final PaymentService paymentService;

    private final CashAccountService cashAccountService;


    @GetMapping("/")
    public String products(@RequestParam(required = false) Map<String, String> form, Principal principal, Model model) {
        if (form.values().size() == 0){
            model.addAttribute("products",productService.listProducts());
        }else {
            model.addAttribute("products",productService.listProductsByTypeAndCityAndHealth(form));
        }
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("cities", ProductCity.values());
        model.addAttribute("health", ProductHealth.values());
        model.addAttribute("types", ProductType.values());
        return "products";
    }
    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        User user = productService.getUserByPrincipal(principal);
        if(cashAccountService.findByUserId(user.getId()).getBalance() > 3){

            productService.saveProduct(principal, product, file1, file2, file3);
            return "redirect:/my/products";
        }
        else {
            return "redirect:/user/cashAccount/deposit";
        }
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        model.addAttribute("cities", ProductCity.values());
        model.addAttribute("health", ProductHealth.values());
        model.addAttribute("types", ProductType.values());
        return "my-products";
    }
}
