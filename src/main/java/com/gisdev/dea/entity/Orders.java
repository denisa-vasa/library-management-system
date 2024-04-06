package com.gisdev.dea.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gisdev.dea.dataType.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders extends AbstractEntity{

    @NotNull(message = "Status should not be empty!")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Price should not be empty!")
    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("orderList") // Exclude user from serialization
    private Users users;

}
