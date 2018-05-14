package com.example.project;


public class Field {

  private Long id;
    private String name;
    private String visitors;
    private boolean lights;
    private String info;
    private String streetAddress;
    private String location;
    private Double longitude, latitude;

    public Field() {}

    public Field(String name, Long id) {
        this.name = name;
        this.id = id;
        this.setVisitorAmount();
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStreet(String street) {
        this.streetAddress = street;
    }

    public String getStreet() {
        return streetAddress;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setGeo(Double lon, Double lat) {
        this.longitude = lon;
        this.latitude = lat;
    }

    public Double getLong() {
        return longitude;
    }
    public Double getLat() {
        return latitude;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void lightsOn() {
        this.lights = true;
    }

    public void lightsOff() {
        this.lights = false;
    }

    public boolean getLights() {
        return lights;
    }
    

    public String getVisitors() {
        return visitors;
    }

    public void setVisitors(String visitors) {
        this.visitors = visitors;
    }

    //Metod för att sätta planens belastningsgrad.
    private void setVisitorAmount() {

        double noiseLevel = Math.floor(Math.random() * 3) + 1;

        if (noiseLevel == 1) {
            this.visitors ="Låg belastning";
        }
        if (noiseLevel == 2) {
            this.visitors = "Medel belastning";
        }
        if (noiseLevel == 3) {
            this.visitors = "Hög belastning";
        }

    }

    @Override
  public String toString() {
    return "Field{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
