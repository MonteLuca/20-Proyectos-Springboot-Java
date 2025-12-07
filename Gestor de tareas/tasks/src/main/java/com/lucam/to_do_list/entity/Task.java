package com.lucam.to_do_list.entity;

import com.lucam.to_do_list.enums.Priority;
import com.lucam.to_do_list.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_priority", columnList = "priority"),
        @Index(name = "idx_due_date", columnList = "due_date")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Size(min = 3, max = 100, message = "El titulo debe tener entre 3 a 100 caracteres")
    @NotBlank(message = "El titulo debe ser obligatorio")
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 500, message = "La descripcion no debe superar los 500 caracteres")
    @Column(length = 500)
    private String description;

    @NotNull(message = "El estado de la tarea es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.PENDING;

    @NotNull(message = "La prioridad de la tarea debe ser obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdA;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isOverDue(){
        return dueDate != null && LocalDate.now().isAfter(dueDate) && status != Status.COMPLETED;
    }

    public boolean isDueToday() {
        return dueDate != null && dueDate.equals(LocalDate.now());
    }

    public void markAsInProgress() {
        this.status = Status.IN_PROGRESS;
    }

    public void markAsCompleted() {
        this.status = Status.COMPLETED;
    }

}