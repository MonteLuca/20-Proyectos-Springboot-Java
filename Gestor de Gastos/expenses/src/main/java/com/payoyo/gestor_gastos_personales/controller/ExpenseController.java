package com.payoyo.gestor_gastos_personales.controller;

import com.payoyo.gestor_gastos_personales.entity.Expense;
import com.payoyo.gestor_gastos_personales.entity.enums.CategoryEnum;
import com.payoyo.gestor_gastos_personales.entity.enums.PaymentMethodEnum;
import com.payoyo.gestor_gastos_personales.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable CategoryEnum category) {
        List<Expense> expenses = expenseService.getAllExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Expense>> getExpensesByDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Expense> expenses = expenseService.getAllExpensesByDateBetween(startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/payment-method/{paymentMethod}")
    public ResponseEntity<List<Expense>> getExpensesByPaymentMethod(
            @PathVariable PaymentMethodEnum paymentMethod) {
        List<Expense> expenses = expenseService.getAllExpensesByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/reports/by-category")
    public ResponseEntity<List<Map<String, Object>>> getReportByCategory() {
        List<Map<String, Object>> report = expenseService.getReportByCategory();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/period")
    public ResponseEntity<Map<String, Object>> getReportByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> report = expenseService.getReportByPeriod(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/current-month")
    public ResponseEntity<Map<String, Object>> getCurrentMonthReport() {
        Map<String, Object> report = expenseService.getCurrentMonthReport();
        return ResponseEntity.ok(report);
    }

}