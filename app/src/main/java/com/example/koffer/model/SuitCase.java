package com.example.koffer.model;

public class SuitCase {

    String name;
    String email;
    String telephone;
    String dni;
    String quantity;
    String kg;

    public SuitCase(String name, String email, String telephone, String dni, String quantity, String kg) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.dni = dni;
        this.quantity = quantity;
        this.kg = kg;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDni() {
        return dni;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getKg() {
        return kg;
    }

}
