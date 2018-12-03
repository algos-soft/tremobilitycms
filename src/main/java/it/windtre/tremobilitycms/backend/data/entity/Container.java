package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Container extends AbstractEntity {

    @Id
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String color = null;
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    private String image = null;
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    private Integer columns = null;
    public Integer getColumns() {
        return this.columns;
    }
    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    private String state = null;
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }

    private String description = null;
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public static String getEntityName() {
        return " Home Page";
    }
}
