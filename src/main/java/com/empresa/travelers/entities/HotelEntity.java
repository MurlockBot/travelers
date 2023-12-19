package com.empresa.travelers.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity(name = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String address;

    private Integer rating;

    private BigDecimal price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "hotel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<ReservationEntity> reservations;
}
