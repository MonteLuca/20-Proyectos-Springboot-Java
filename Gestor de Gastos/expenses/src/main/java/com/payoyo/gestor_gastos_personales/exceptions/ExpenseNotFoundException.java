package com.payoyo.gestor_gastos_personales.exceptions;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException(Long id) {
        super(String.format("No se encontró el gasto con ID: %d", id));
    }
}