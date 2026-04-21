package com.stocksaathi.sales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(
        name = "customers",
        indexes = {
                @Index(name = "idx_phone_business", columnList = "phone, businessId")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_phone_business", columnNames = {"phone", "businessId"}),
                @UniqueConstraint(name = "uk_email_business", columnNames = {"email", "businessId"})
        }
)
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    private String email;

    private String address;

    @Column(nullable = false)
    private UUID businessId;

    private BigDecimal totalDue;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
