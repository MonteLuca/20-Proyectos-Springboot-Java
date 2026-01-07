package com.payoyo.gestor_gastos_personales.repository;

import com.payoyo.gestor_gastos_personales.entity.Expense;
import com.payoyo.gestor_gastos_personales.entity.enums.CategoryEnum;
import com.payoyo.gestor_gastos_personales.entity.enums.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryOrderByDateDesc(CategoryEnum category);

    List<Expense> findByPaymentMethodOrderByDateDesc(PaymentMethodEnum paymentMethod);

    List<Expense> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate);

    List<Expense> findAllByOrderByDateDesc();

    List<Expense> findByCategoryAndDateBetweenOrderByDateDesc(CategoryEnum category, LocalDate startDate, LocalDate endDate);

}