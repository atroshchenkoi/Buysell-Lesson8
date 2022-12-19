package com.example.buysell.models;

import com.example.buysell.models.enums.ProductCity;
import com.example.buysell.models.enums.ProductHealth;
import com.example.buysell.models.enums.ProductType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;



    @Column(nullable = false)
    private float price;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductCity city;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductHealth health;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductType type;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
    private Payment payment;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "product", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    private User user;

    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() { dateOfCreated = LocalDateTime.now(); }
    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

    public void removeImages(){
        if (this.getImages().size() != 0){
            for (Image image: this.getImages()) {
                image.setProduct(null);
            }
        }
        this.images = null;
    }

    public void addPayment(Payment payment) {
        payment.setProduct(this);
        this.setPayment(payment);
    }

    public void removePayment(){
        if(this.getPayment() != null){
            this.getPayment().setProduct(null);
        }
        this.payment = null;
    }

}
