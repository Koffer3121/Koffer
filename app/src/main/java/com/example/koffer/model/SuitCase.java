package com.example.koffer.model;

public class SuitCase {

    String name;
    String email;
    String telephone;
    String dni;
    String quantity;
    String kg;
    String address;

    public SuitCase(String name, String email, String telephone, String dni, String quantity, String kg, String address) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.dni = dni;
        this.quantity = quantity;
        this.kg = kg;
        this.address = address;
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

    public String getAddress() {
        return address;
    }
}
