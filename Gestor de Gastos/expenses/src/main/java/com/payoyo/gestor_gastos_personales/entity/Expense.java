package com.payoyo.gestor_gastos_personales.entity;

import com.payoyo.gestor_gastos_personales.entity.enums.CategoryEnum;
import com.payoyo.gestor_gastos_personales.entity.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@Table(name = "expenses")
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripcion del gasto es obligatoria")
    @Size(min = 3, max = 200, message = "La descripcion del gasto debe tener entre 3 y 200 caracteres")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "La cantidad del gasto es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "Debe tener maximo 10 digitos enteros y 2 decimales")
    private BigDecimal amount;

    @Enumerated
    @NotNull(message = "La categoria del gasto es obligatoria")
    @Column(nullable = false)
    private CategoryEnum category;

    @NotNull(message = "La fecha del gasto debe ser obligatoria")
    @PastOrPresent(message = "La fecha del gasto no puede ser futura")
    private LocalDate date;

    @NotNull(message = "La forma de pago es obligatoria")
    @Column(nullable = false)
    @Enumerated
    private PaymentMethodEnum paymentMethod;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;

}