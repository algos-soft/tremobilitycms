package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "container")
public class Container extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "color")
    private String color = null;
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "image")
    private String image = null;
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "columns")
    private Integer columns = null;
    public Integer getColumns() {
        return this.columns;
    }
    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    @Column(name = "state")
    private String state = null;
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "description")
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
