package com.example.koffer.model;

public class Suitcase {

    private String id;
    private String name;
    private String email;
    private String telephone;
    private String dni;
    private String quantity;
    private String kg;
    private String pickUpAddress;
    private String deliveryAddress;
    private boolean carrierAsigned;

    public Suitcase(){

    }

    public Suitcase(String name, String email, String telephone, String dni, String quantity, String kg, String deliveryAddress, String pickUpAddress,Boolean carrierAsigned) {

    public Suitcase() {

    }

    public Suitcase(String id, String name, String email, String telephone, String dni, String quantity, String kg, String pickUpAddress) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.dni = dni;
        this.quantity = quantity;
        this.kg = kg;
        this.pickUpAddress = pickUpAddress;
        this.deliveryAddress = deliveryAddress;
        this.carrierAsigned = carrierAsigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public boolean isCarrierAsigned() {
        return carrierAsigned;
      
    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
