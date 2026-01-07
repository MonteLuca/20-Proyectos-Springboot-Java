package com.payoyo.gestor_gastos_personales.service;

import com.payoyo.gestor_gastos_personales.entity.Expense;
import com.payoyo.gestor_gastos_personales.entity.enums.CategoryEnum;
import com.payoyo.gestor_gastos_personales.entity.enums.PaymentMethodEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    Expense updateExpense(Long id, Expense expense);

    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    void deleteExpense(Long id);

    List<Expense> getAllExpensesByCategory(CategoryEnum category);

    List<Expense> getAllExpensesByPaymentMethod(PaymentMethodEnum paymentMethod);

    List<Expense> getAllExpensesByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Map<String,Object>> getReportByCategory();

    Map<String,Object> getReportByPeriod(LocalDate startDate, LocalDate endDate);

    Map<String,Object> getCurrentMonthReport();

}