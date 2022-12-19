package com.example.buysell.models;

import com.example.buysell.models.enums.Role;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Email is not found")
    @Column(unique = true, updatable = false, length = 50, nullable = false)
    private String email;
    @NotBlank(message = "Please fill the number")
    @Pattern(regexp = "\\+375[0-9]{9}", message = "Bad formed number...")
    @Column(length = 15, nullable = false)
    private String phoneNumber;
    @NotBlank(message = "Please fill the name")
    @Length(min = 4, max = 30, message = "Error name length")
    @Column(length = 30, nullable = false)
    private String name;
    private boolean active;
    private String activationCode;
    @Column(length = 1000, nullable = false)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint( columnNames = {"user_id", "roles"}))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user",
            orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    @Transient
    private CashAccount cashAccount;
    public CashAccount getCashAccount() {
        return cashAccount;
    }
    public void addCashAccount(CashAccount cashAccount){
        this.cashAccount = cashAccount;
        cashAccount.setUser(this);
    }
    public void removeCashAccount() {
        if ((cashAccount != null)){
            cashAccount.setUser(null);
        }
        this.cashAccount = null;
    }
    public void addProductToUser(Product product) {
        product.setUser(this);
        products.add(product);
    }

    public void removeProduct(Product product) {
        product.setUser(null);
        products.remove(product);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    public List<Product> getProducts() {
        return products;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    // security config

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
