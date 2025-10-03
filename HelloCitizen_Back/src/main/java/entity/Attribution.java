package entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Attribution {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "gift_id")
    private Gift gift;

    @Enumerated(EnumType.STRING)
    private AttributionStatus attributionStatus;

    @Temporal(TemporalType.DATE)
    private LocalDate choiceDate;

    @Temporal(TemporalType.DATE)
    private LocalDate deliveryDate;

    private String shippingAddress;

    private Double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public AttributionStatus getAttributionStatus() {
        return attributionStatus;
    }

    public void setAttributionStatus(AttributionStatus attributionStatus) {
        this.attributionStatus = attributionStatus;
    }

    public LocalDate getChoiceDate() {
        return choiceDate;
    }

    public void setChoiceDate(LocalDate choiceDate) {
        this.choiceDate = choiceDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
