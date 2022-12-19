package com.example.buysell.services;

import com.example.buysell.models.CashAccount;
import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.ProductCity;
import com.example.buysell.models.enums.ProductHealth;
import com.example.buysell.models.enums.ProductType;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.buysell.models.enums.ProductCity;
import com.example.buysell.models.enums.ProductHealth;
import com.example.buysell.models.enums.ProductType;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final CashAccountRepository cashAccountRepository;
    private final PaymentRepository paymentRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public List<Product> listProductsByUserId(Long id) {

        return productRepository.findByUserId(id);
    }

    public List<Product> listProductsByTypeAndCityAndHealth(Map<String, String> form){
        Set<ProductType> types = new HashSet<>();
        Set<ProductHealth> healths = new HashSet<>();
        Set<ProductCity> cities = new HashSet<>();
        for (String key : form.values()){
            log.info(key);
            if (isPresentType(key)){
                types.add(Enum.valueOf(ProductType.class, key));
                log.info(key + " in types");
            }
            if (isPresentCity(key)){
                cities.add(Enum.valueOf(ProductCity.class, key));
                log.info(key + " in cities");
            }
            if (isPresentHealth(key)){
                healths.add(Enum.valueOf(ProductHealth.class, key));
                log.info(key + " in healths");
            }
        }
        if (cities.size() == 0){
            cities.addAll(Arrays.stream(ProductCity.values()).collect(Collectors.toSet()));
        }
        if (types.size() == 0){
            types.addAll(Arrays.stream(ProductType.values()).collect(Collectors.toSet()));
        }
        if (healths.size() == 0){
            healths.addAll(Arrays.stream(ProductHealth.values()).collect(Collectors.toSet()));
        }

        return productRepository.findByCityInAndTypeInAndHealthIn(cities, types, healths);
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        User user = getUserByPrincipal(principal);
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        paymentService.createPayment(cashAccountRepository.findByUserId(user.getId()), product);
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(product.getImages().get(0).getId());
        user.addProductToUser(product);
        userRepository.save(user);

    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                List<Image> images = product.getImages();
                user.removeProduct(product);
                product.removeImages();
                product.removePayment();
                productRepository.delete(product);
                imageRepository.deleteAll(images);


                log.info("Product with id = {} was deleted", id);
            }
            else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        }
        else {
            log.error("Product with id = {} is not found", id);
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    private static boolean isPresentCity(String city){
        try {
            Enum.valueOf(ProductCity.class, city);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean isPresentHealth(String health){
        try {
            Enum.valueOf(ProductHealth.class, health);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    private static boolean isPresentType(String type){
        try {
            Enum.valueOf(ProductType.class, type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
