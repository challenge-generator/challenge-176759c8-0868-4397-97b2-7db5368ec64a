package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que mapea la tabla de clientes en el modelo normalizado.
 * Almacena la información personal y crediticia de cada cliente.
 */
@Entity
@Table(name = "customer", indexes = {
    @Index(name = "idx_customer_email", columnList = "email", unique = true),
    @Index(name = "idx_customer_identification", columnList = "identification_number", unique = true),
    @Index(name = "idx_customer_credit_score", columnList = "credit_score"),
    @Index(name = "idx_customer_status", columnList = "status")
})
public class CustomerEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "identification_type", nullable = false, length = 50)
    private String identificationType;

    @Column(name = "identification_number", nullable = false, length = 50)
    private String identificationNumber;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "occupation", length = 150)
    private String occupation;

    @Column(name = "employer_name", length = 200)
    private String employerName;

    @Column(name = "monthly_income", precision = 19, scale = 4)
    private java.math.BigDecimal monthlyIncome;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "risk_category", length = 50)
    private String riskCategory;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_loan_date")
    private LocalDateTime lastLoanDate;

    @Column(name = "total_loans_count")
    private Integer totalLoansCount;

    @Column(name = "active_loans_count")
    private Integer activeLoansCount;

    @Column(name = "delinquent_loans_count")
    private Integer delinquentLoansCount;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Version
    @Column(name = "version")
    private Long version;

    public CustomerEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public java.math.BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(java.math.BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLoanDate() {
        return lastLoanDate;
    }

    public void setLastLoanDate(LocalDateTime lastLoanDate) {
        this.lastLoanDate = lastLoanDate;
    }

    public Integer getTotalLoansCount() {
        return totalLoansCount;
    }

    public void setTotalLoansCount(Integer totalLoansCount) {
        this.totalLoansCount = totalLoansCount;
    }

    public Integer getActiveLoansCount() {
        return activeLoansCount;
    }

    public void setActiveLoansCount(Integer activeLoansCount) {
        this.activeLoansCount = activeLoansCount;
    }

    public Integer getDelinquentLoansCount() {
        return delinquentLoansCount;
    }

    public void setDelinquentLoansCount(Integer delinquentLoansCount) {
        this.delinquentLoansCount = delinquentLoansCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (totalLoansCount == null) {
            totalLoansCount = 0;
        }
        if (activeLoansCount == null) {
            activeLoansCount = 0;
        }
        if (delinquentLoansCount == null) {
            delinquentLoansCount = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}