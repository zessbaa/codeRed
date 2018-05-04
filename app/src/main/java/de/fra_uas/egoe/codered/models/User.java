package de.fra_uas.egoe.codered.models;

public class User {

    private String email;
    private String name;
    private String address;
    private String phone;
    private String gender;
    private String organisation;
    private String profile_picture;



    private String birth;
    private double latitude, longitude;
    private boolean is_active, is_receiver;


    public User() {}

    public User(String email, String name, String address, String phone, String gender, String birth, String organisation, String profile_picture, double latitude, double longitude, boolean is_active, boolean is_receiver) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.organisation = organisation;
        this.profile_picture = profile_picture;
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_active = is_active;
        this.is_receiver = is_receiver;
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_receiver() {
        return is_receiver;
    }

    public void setIs_receiver(boolean is_receiver) {
        this.is_receiver = is_receiver;
    }
}
