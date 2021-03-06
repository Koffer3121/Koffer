package com.example.koffer.model;

public class Suitcase {

    private String uid;
    private String name;
    private String email;
    private String telephone;
    private String dni;
    private String quantity;
    private String kg;
    private String pickUpAddress;
    private String deliveryAddress;
    private String carrierAsigned;

    public Suitcase(){

    }

    public Suitcase(String uid, String name, String email, String telephone, String dni, String quantity, String kg, String pickUpAddress, String deliveryAddress, String carrierAsigned) {
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCarrierAsigned() {
        return carrierAsigned;
    }

    public void setCarrierAsigned(String carrierAsigned) {
        this.carrierAsigned = carrierAsigned;
    }
}
