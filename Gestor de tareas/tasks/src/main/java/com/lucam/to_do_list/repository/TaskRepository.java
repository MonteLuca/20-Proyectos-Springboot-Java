package com.lucam.to_do_list.repository;

import com.lucam.to_do_list.entity.Task;
import com.lucam.to_do_list.enums.Priority;
import com.lucam.to_do_list.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Busca todas las tareas con un estado especifico
    // SQL : SELECT * FROM tasks WHERE status = ?
    List<Task> findByStatus(Status status);

    // Busca todas las tareas con una proridad especifica
    // SQL: SELECT * FROM tasks WHERE priority = ?
    List<Task> findByPriority(Priority priority);

    // Busca tareas filtrando por estado Y prioridad simultáneamente
    // SQL: SELECT * FROM tasks WHERE status = ? AND priority = ?
    List<Task> findByStatusAndPriority(Status status, Priority priority);

    // Busca tareas cuya fecha limite este dentro de un rango
    // SQL: SELECT * FROM tasks WHERE due_date BETWEEN ? AND ?
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    // Busca tareas con una fecha limite exacta
    // SQL: SELECT * FROM tasks WHERE due_date = ?
    List<Task> findByDueDate(LocalDate dueDate);

    // Cuenta cuántas tareas hay con un estado especifico
    Long countByStatus(Status status);

    // Encuentra todas las tareas vencidas (overdue)
    // JPQL (Similar a SQL pero orientado a Objetos)
    @Query("SELECT t FROM Task t WHERE t.dueDate < :today AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("today") LocalDate today);

    // Encuentra tareas que vencen hoy y aun no estan completas
    @Query("SELECT t FROM Task t WHERE t.dueDate = :today AND t.status != 'COMPLETED'")
    List<Task> findTasksDueToday(@Param("today") LocalDate today);

    // Busca tareas por termino de busqueda en titulo o descripcion
    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Task> searchByTitleOrDescription(@Param("searchTerm") String searchTerm);

}