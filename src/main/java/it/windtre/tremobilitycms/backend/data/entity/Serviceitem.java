package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "serviceitem")
public class Serviceitem extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    private String name = null;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    private String description = null;
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "service")
    private Long service = null;
    public Long getService() {
        return this.service;
    }
    public void setService(Long service) {
        this.service = service;
    }

    @Column(name = "currency")
    private String currency = null;
    public String getCurrency() {
        return this.currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }


    // DURATION
    @Column(name = "durationdescription")
    private String durationDescription = null;
    public String getDurationDescription() { return this.durationDescription;}
    public void setDurationDescription(String description) { this.durationDescription = description;}

    @Column(name = "durationinterval")
    private Long durationInterval = null;
    public Long getDurationInterval() { return this.durationInterval;}
    public void setDurationInterval(Long interval) { this.durationInterval = interval;}

    @Column(name = "durationname")
    private String durationName = null;
    public String getDurationName() { return this.durationName;}
    public void setDurationName(String name) { this.durationName = name;}

    @Column(name = "durationtype")
    private String durationType = null;
    public String getDurationType() { return this.durationType;}
    public void setDurationType(String type) { this.durationType = type;}


    //  INFO ZONE
    @Column(name = "infozonename")
    private String infozoneName = null;
    public String getInfozoneName() {
        return this.infozoneName;
    }
    public void setInfozoneName(String name) {
        this.infozoneName = name;
    }

    @Column(name = "infozonetypez")
    private String infozoneTypez = null;
    public String getInfozoneTypez() {
        return this.infozoneTypez;
    }
    public void setInfozoneTypez(String typez) {
        this.infozoneTypez = typez;
    }

    //Added fields

    private String city = null;
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    private String serviceName = null;
    public String getServiceName() {
        return this.serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Serviceitem {\n");
        sb.append("  currency: ").append(this.currency).append("\n");
        sb.append("  description: ").append(this.description).append("\n");
        sb.append("  id: ").append(this.id).append("\n");
        sb.append("  name: ").append(this.name).append("\n");
        sb.append("  service: ").append(this.service).append("\n");
        sb.append("Duration {\n");
        sb.append("  description: ").append(this.durationDescription).append("\n");
        sb.append("  interval: ").append(this.durationInterval).append("\n");
        sb.append("  name: ").append(this.durationName).append("\n");
        sb.append("  type: ").append(this.durationType).append("\n");
        sb.append("}\n");
        sb.append(" InfoZone {\n");
        sb.append("  name: ").append(this.infozoneName).append("\n");
        sb.append("  typez: ").append(this.infozoneTypez).append("\n");
        sb.append("}\n");
        sb.append("}\n");
        return sb.toString();
    }

    public String getServiceDescription(Service serv) {
        String s = "";
        if (serv != null) {
            s = s + String.valueOf(serv.getId()) + " - ";
            s = s + serv.getCity() + " - ";
            s = s + serv.getName();
        }
        return s;
    }

    public static String getEntityName() {
        return " Biglietto";
    }
}
