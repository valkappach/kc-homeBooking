package com.kcbooking.kchomebooking.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal perNightPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany(mappedBy ="generalExtras", fetch=FetchType.EAGER)
    private Collection<Reservation> reservations=new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * Allows different pricing models
     */
    public enum Type {
        Premium, Basic
    }

    /**
     * Extra can be reused for any additional items contributing to the total reservation price.
     */
    public enum Category {
        General, Food
    }

    public Extra(String description, BigDecimal perNightPrice, Type type, Category category) {
        this.description = description;
        this.perNightPrice = perNightPrice;
        this.type = type;
        this.category = category;
    }

    public Extra() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPerNightPrice() {
        return perNightPrice;
    }

    public BigDecimal getTotalPrice(long totalNights) {
        return perNightPrice.multiply(BigDecimal.valueOf(totalNights));
    }

    public void setPerNightPrice(BigDecimal perNightPrice) {
        this.perNightPrice = perNightPrice;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extra extra = (Extra) o;
        return Objects.equals(description, extra.description) &&
                type == extra.type &&
                category == extra.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, category);
    }

    @Override
    public String toString() {
        return "Extra{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", perNightPrice=" + perNightPrice +
                ", type=" + type +
                ", category=" + category +
                '}';
    }
}
