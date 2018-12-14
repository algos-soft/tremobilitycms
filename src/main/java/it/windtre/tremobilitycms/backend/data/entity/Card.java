package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "element")
    private Long element = null;
    public Long getElement() {
        return this.element;
    }
    public void setElement(Long element) {
        this.element = element;
    }

    @Column(name = "logo")
    private String logo = null;
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Column(name = "state")
    private String state = null;
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "actionlaunch")
    private String actionLaunch = null;
    public String getActionLaunch() {
        return this.actionLaunch;
    }
    public void setActionLaunch(String actionLaunch) {
        this.actionLaunch = actionLaunch;
    }

    @Column(name = "actionmode")
    private String actionMode = null;
    public String getActionMode() {
        return this.actionMode;
    }
    public void setActionMode(String actionMode) {
        this.actionMode = actionMode;
    }

    @Column(name = "backgroundcolor")
    private String backgroundColor = null;
    public String getBackgroundColor() {
        return this.backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Column(name = "backgroundimage")
    private String backgroundImage = null;
    public String getBackgroundImage() {
        return this.backgroundImage;
    }
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Column(name = "descriptionalign")
    private String descriptionAlign = null;
    public String getDescriptionAlign() {
        return this.descriptionAlign;
    }
    public void setDescriptionAlign(String descriptionAlign) {
        this.descriptionAlign = descriptionAlign;
    }

    @Column(name = "descriptioncolor")
    private String descriptionColor = null;
    public String getDescriptionColor() {
        return this.descriptionColor;
    }
    public void setDescriptionColor(String descriptionColor) {
        this.descriptionColor = descriptionColor;
    }

    @Column(name = "descriptionfont")
    private String descriptionFont = null;
    public String getDescriptionFont() {
        return this.descriptionFont;
    }
    public void setDescriptionFont(String descriptionFont) {
        this.descriptionFont = descriptionFont;
    }

    @Column(name = "descriptionfontsize")
    private String descriptionFontSize = null;
    public String getDescriptionFontSize() {
        return this.descriptionFontSize;
    }
    public void setDescriptionFontSize(String descriptionFontSize) {
        this.descriptionFontSize = descriptionFontSize;
    }

    @Column(name = "descriptionfontstyle")
    private String descriptionFontStyle = null;
    public String getDescriptionFontStyle() {
        return this.descriptionFontStyle;
    }
    public void setDescriptionFontStyle(String descriptionFontStyle) {
        this.descriptionFontStyle = descriptionFontStyle;
    }

    @Column(name = "descriptiontext")
    private String descriptionText = null;
    public String getDescriptionText() {
        return this.descriptionText;
    }
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    @Column(name = "titlealign")
    private String titleAlign = null;
    public String getTitleAlign() {
        return this.titleAlign;
    }
    public void setTitleAlign(String titleAlign) {
        this.titleAlign = titleAlign;
    }

    @Column(name = "titlecolor")
    private String titleColor = null;
    public String getTitleColor() {
        return this.titleColor;
    }
    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    @Column(name = "titlefont")
    private String titleFont = null;
    public String getTitleFont() {
        return this.titleFont;
    }
    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }

    @Column(name = "titlefontsize")
    private String titleFontSize = null;
    public String getTitleFontSize() {
        return this.titleFontSize;
    }
    public void setTitleFontSize(String titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    @Column(name = "titlefontstyle")
    private String titleFontStyle = null;
    public String getTitleFontStyle() {
        return this.titleFontStyle;
    }
    public void setTitleFontStyle(String titleFontStyle) {
        this.titleFontStyle = titleFontStyle;
    }

    @Column(name = "titletext")
    private String titleText = null;
    public String getTitleText() {
        return this.titleText;
    }
    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public static String getEntityName() {
        return " Card";
    }
}
