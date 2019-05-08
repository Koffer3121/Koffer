package com.example.koffer.model;

public class UserInformation {
    public boolean isTransportist;

    public UserInformation() {

    }

    public UserInformation(boolean isCarrier) {
        this.isTransportist = isCarrier;
    }

    public boolean isCarrier() {
        return isTransportist;
    }

    public void setCarrier(boolean carrier) {
        isTransportist = carrier;
    }
}
