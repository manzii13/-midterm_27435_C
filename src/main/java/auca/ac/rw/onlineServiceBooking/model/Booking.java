package auca.ac.rw.onlineServiceBooking.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import auca.ac.rw.onlineServiceBooking.model.enums.EBookingStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EBookingStatus status;

    // One-to-Many: one CLIENT user → many bookings
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToMany
    @JoinTable(name = "booking_services", // the join table name
            joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))

    private List<ServiceEntity> services;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    public Booking() {}

    public UUID getId() { return id; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public EBookingStatus getStatus() { return status; }
    public void setStatus(EBookingStatus status) { this.status = status; }

    public User getClient() { return client; }
    public void setClient(User client) { this.client = client; }

    public List<ServiceEntity> getServices() { return services; }
    public void setServices(List<ServiceEntity> services) { this.services = services; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

}
