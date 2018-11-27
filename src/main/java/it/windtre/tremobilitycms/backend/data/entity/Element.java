package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Element extends AbstractEntity {

    @Id
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String mode = null;
    public String getMode() {
        return this.mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    private Integer posColumn = null;
    public Integer getPosColumn() {
        return this.posColumn;
    }
    public void setPosColumn(Integer posColumn) {
        this.posColumn = posColumn;
    }

    private Integer posRow = null;
    public Integer getPosRow() {
        return this.posRow;
    }
    public void setPosRow(Integer posRow) {
        this.posRow = posRow;
    }

    private Integer posSpan = null;
    public Integer getPosSpan() {
        return this.posSpan;
    }
    public void setPosSpan(Integer posSpan) {
        this.posSpan = posSpan;
    }

    private String state = null;
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
