package com.payoyo.gestor_gastos_personales.service.impl;

import com.payoyo.gestor_gastos_personales.entity.Expense;
import com.payoyo.gestor_gastos_personales.entity.enums.CategoryEnum;
import com.payoyo.gestor_gastos_personales.entity.enums.PaymentMethodEnum;
import com.payoyo.gestor_gastos_personales.exceptions.ExpenseNotFoundException;
import com.payoyo.gestor_gastos_personales.repository.ExpenseRepository;
import com.payoyo.gestor_gastos_personales.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;


    @Override
    public Expense createExpense(Expense expense) {

        if (expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del gasto no puede ser futura");
        }

        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {

        Expense existingExpense = getExpenseById(id);

        if (expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del gasto no puede ser futura");
        }

        existingExpense.setDescription(expense.getDescription());
        existingExpense.setDate(expense.getDate());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setPaymentMethod(expense.getPaymentMethod());
        existingExpense.setCategory(expense.getCategory());

        return expenseRepository.save(existingExpense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderByDateDesc();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @Override
    public void deleteExpense(Long id) {

        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);

    }

    @Override
    public List<Expense> getAllExpensesByCategory(CategoryEnum category) {
        return expenseRepository.findByCategoryOrderByDateDesc(category);
    }

    @Override
    public List<Expense> getAllExpensesByPaymentMethod(PaymentMethodEnum paymentMethod) {
        return expenseRepository.findByPaymentMethodOrderByDateDesc(paymentMethod);
    }

    @Override
    public List<Expense> getAllExpensesByDateBetween(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetweenOrderByDateDesc(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> getReportByCategory() {
        List<Expense> allExpenses = expenseRepository.findAll();

        Map<CategoryEnum, List<Expense>> expensesByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));

        List<Map<String, Object>> report = new ArrayList<>();

        for (Map.Entry<CategoryEnum, List<Expense>> entry : expensesByCategory.entrySet()) {

            CategoryEnum category = entry.getKey();
            List<Expense> expenses = entry.getValue();

            BigDecimal totalAmount = expenses.stream()
                    .map(Expense::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> categoryReport = new HashMap<>();
            categoryReport.put("category", category.name());
            categoryReport.put("totalAmount", totalAmount);
            categoryReport.put("expenseCount", expenses.size());

            report.add(categoryReport);
        }

        report.sort((a, b) -> {
            BigDecimal totalA = (BigDecimal) a.get("totalAmount");
            BigDecimal totalB = (BigDecimal) b.get("totalAmount");
            return totalB.compareTo(totalA);
        });

        return report;

    }

    @Override
    public Map<String, Object> getReportByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Expense> allExpenses = expenseRepository.findByDateBetweenOrderByDateDesc(startDate, endDate);

        BigDecimal totalAmount = allExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int expenseCount = allExpenses.size();

        BigDecimal averageExpense = BigDecimal.ZERO;
        if (expenseCount > 0) {
            averageExpense = totalAmount.divide(
                    BigDecimal.valueOf(expenseCount), 2, RoundingMode.HALF_UP);
        }

        Map<String, Object> report = new HashMap<>();

        report.put("startDate", startDate.toString());
        report.put("endDate", endDate.toString());
        report.put("totalAmount", totalAmount);
        report.put("expenseCount", expenseCount);
        report.put("averageExpense", averageExpense);

        return report;

    }

    @Override
    public Map<String, Object> getCurrentMonthReport() {

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> monthExpenses = expenseRepository.findByDateBetweenOrderByDateDesc(startOfMonth, endOfMonth);

        BigDecimal totalAmount = monthExpenses.stream().
                map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Map<CategoryEnum, BigDecimal> totalsByCategory = monthExpenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Expense::getAmount,
                                BigDecimal::add
                        )
                ));

        CategoryEnum mostExpensive = null;
        CategoryEnum leastExpensive = null;

        if (!totalsByCategory.isEmpty()) {

            mostExpensive = totalsByCategory.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);


            leastExpensive = totalsByCategory.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }

        Map<String, Object> report = new HashMap<>();
        report.put("month",now.getMonth().name());
        report.put("year", now.getYear());
        report.put("totalAmount", totalAmount);
        report.put("expenseCount", monthExpenses.size());
        report.put("mostExpensiveCategory", mostExpensive != null ? mostExpensive.name() : null);
        report.put("leastExpensiveCategory", leastExpensive != null ? leastExpensive.name() : null);

        return report;

    }

}