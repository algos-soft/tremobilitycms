package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "element")
public class Element extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "mode")
    private String mode = null;
    public String getMode() {
        return this.mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Column(name = "poscolumn")
    private Integer posColumn = null;
    public Integer getPosColumn() {
        return this.posColumn;
    }
    public void setPosColumn(Integer posColumn) {
        this.posColumn = posColumn;
    }

    @Column(name = "posrow")
    private Integer posRow = null;
    public Integer getPosRow() {
        return this.posRow;
    }
    public void setPosRow(Integer posRow) {
        this.posRow = posRow;
    }

    @Column(name = "posspan")
    private Integer posSpan = null;
    public Integer getPosSpan() {
        return this.posSpan;
    }
    public void setPosSpan(Integer posSpan) {
        this.posSpan = posSpan;
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
        return " Componente";
    }
}
