package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.*;


@Entity
@Table(name = "zoneitem")
public class Zoneitem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
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

    @Column(name = "position")
    private Integer position = null;
    public Integer getPosition() {
        return this.position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "price")
    private Double price = null;
    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "serviceitem")
    private Long serviceitem = null;
    public Long getServiceitem() {
        return this.serviceitem;
    }
    public void setServiceitem(Long serviceitem) {
        this.serviceitem = serviceitem;
    }

    @Column(name = "shape")
    private String shape = null;
    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    @Column(name = "smstext")
    private String smstext = null;
    public String getSmstext() {
        return this.smstext;
    }
    public void setSmstext(String smstext) {
        this.smstext = smstext;
    }

    @Column(name = "value")
    private String value = null;
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "city")
    private String city = null;
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    //added fields

    private String serviceName = null;
    public String getServiceName() {
        return this.serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    private String serviceitemName = null;
    public String getServiceitemName() {
        return this.serviceitemName;
    }
    public void setServiceitemName(String serviceitemName) {
        this.serviceitemName = serviceitemName;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Zoneitem {\n");
        sb.append("  id: ").append(this.id).append("\n");
        sb.append("  name: ").append(this.name).append("\n");
        sb.append("  position: ").append(this.position).append("\n");
        sb.append("  price: ").append(this.price).append("\n");
        sb.append("  serviceitem: ").append(this.serviceitem).append("\n");
        sb.append("  shape: ").append(this.shape).append("\n");
        sb.append("  smstext: ").append(this.smstext).append("\n");
        sb.append("  value: ").append(this.value).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static String getEntityName() {
        return " Dettaglio";
    }
}
