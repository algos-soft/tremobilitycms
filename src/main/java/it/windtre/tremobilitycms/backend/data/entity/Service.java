package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Service extends AbstractEntity {

    @Id
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String city = null;
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    private Double lat = null;
    public Double getLat() {
        return this.lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    private Double lon = null;
    public Double getLon() {
        return this.lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Size(max = 255)
    @Column(unique = true)
    private String name = null;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String type = null;
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    private String code = null;
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    private String info = null;
    public String getInfo() {
        return this.info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    private String icon = null;
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String email = null;
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    private String sender = null;
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sms = null;
    public String getSms() {
        return this.sms;
    }
    public void setSms(String sms) {
        this.sms = sms;
    }

    private String telephone = null;
    public String getTelephone() {
        return this.telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private String web = null;
    public String getWeb() {
        return this.web;
    }
    public void setWeb(String web) {
        this.web = web;
    }

    /*
    private List<Serviceitem> items = null;
    public List<Serviceitem> getItems() {
        return this.items;
    }
    public void setItems(List<Serviceitem> items) {
        this.items = items;
    }*/


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Service {\n");
        sb.append("  city: ").append(this.city).append("\n");
        sb.append("  code: ").append(this.code).append("\n");
        sb.append("  email: ").append(this.email).append("\n");
        sb.append("  icon: ").append(this.icon).append("\n");
        sb.append("  id: ").append(this.id).append("\n");
        sb.append("  info: ").append(this.info).append("\n");
        sb.append("  lat: ").append(this.lat).append("\n");
        sb.append("  lon: ").append(this.lon).append("\n");
        sb.append("  name: ").append(this.name).append("\n");
        sb.append("  sender: ").append(this.sender).append("\n");
        sb.append("  sms: ").append(this.sms).append("\n");
        sb.append("  telephone: ").append(this.telephone).append("\n");
        sb.append("  type: ").append(this.type).append("\n");
        sb.append("  web: ").append(this.web).append("\n");
        sb.append("}\n");
        //sb.append("  items: ").append(this.items.size()).append("\n");
        return sb.toString();
    }
}
