package auca.ac.rw.onlineServiceBooking.model;

import java.util.UUID;

import auca.ac.rw.onlineServiceBooking.model.enums.ELocationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ELocationType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent;

    public Location() {}
    
    public UUID getId() { return id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ELocationType getType() { return type; }
    public void setType(ELocationType type) { this.type = type; }

    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }


    
}
