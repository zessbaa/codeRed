package de.fra_uas.egoe.codered.models;

public class Alert {

    private String status, priority, description, address, details, sender_id, receiver_id;
    private double latitude, longitude;
    private boolean is_public;

    public Alert() {}

    public Alert(String status, String priority, String description, String address, String details, String sender_id, String receiver_id, double latitude, double longitude, boolean is_public) {
        this.status = status;
        this.priority = priority;
        this.description = description;
        this.address = address;
        this.details = details;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_public = is_public;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }
}
