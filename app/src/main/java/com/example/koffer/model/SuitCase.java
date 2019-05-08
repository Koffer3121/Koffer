package com.example.koffer.model;

public class SuitCase {

    public String name;
    public String email;
    public String telephone;
    public String dni;
    public String quantity;
    public String kg;
    public String pickUpAddress;
    public String deliveryAddress;

    public SuitCase(){

    }

    public SuitCase(String name, String email, String telephone, String dni, String quantity, String kg, String deliveryAddress, String pickUpAddress) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.dni = dni;
        this.quantity = quantity;
        this.kg = kg;
        this.pickUpAddress = pickUpAddress;
        this.deliveryAddress = deliveryAddress;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }
}
