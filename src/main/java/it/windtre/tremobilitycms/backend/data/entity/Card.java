package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card extends AbstractEntity {

    @Id
    private Long id = null;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private Long element = null;
    public Long getElement() {
        return this.element;
    }
    public void setElement(Long element) {
        this.element = element;
    }

    private String logo = null;
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    private String state = null;
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }

    private String actionLaunch = null;
    public String getActionLaunch() {
        return this.actionLaunch;
    }
    public void setActionLaunch(String actionLaunch) {
        this.actionLaunch = actionLaunch;
    }

    private String actionMode = null;
    public String getActionMode() {
        return this.actionMode;
    }
    public void setActionMode(String actionMode) {
        this.actionMode = actionMode;
    }

    private String backgroundColor = null;
    public String getBackgroundColor() {
        return this.backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private String backgroundImage = null;
    public String getBackgroundImage() {
        return this.backgroundImage;
    }
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    private String descriptionAlign = null;
    public String getDescriptionAlign() {
        return this.descriptionAlign;
    }
    public void setDescriptionAlign(String descriptionAlign) {
        this.descriptionAlign = descriptionAlign;
    }

    private String descriptionColor = null;
    public String getDescriptionColor() {
        return this.descriptionColor;
    }
    public void setDescriptionColor(String descriptionColor) {
        this.descriptionColor = descriptionColor;
    }

    private String descriptionFont = null;
    public String getDescriptionFont() {
        return this.descriptionFont;
    }
    public void setDescriptionFont(String descriptionFont) {
        this.descriptionFont = descriptionFont;
    }

    private String descriptionFontSize = null;
    public String getDescriptionFontSize() {
        return this.descriptionFontSize;
    }
    public void setDescriptionFontSize(String descriptionFontSize) {
        this.descriptionFontSize = descriptionFontSize;
    }

    private String descriptionFontStyle = null;
    public String getDescriptionFontStyle() {
        return this.descriptionFontStyle;
    }
    public void setDescriptionFontStyle(String descriptionFontStyle) {
        this.descriptionFontStyle = descriptionFontStyle;
    }

    private String descriptionText = null;
    public String getDescriptionText() {
        return this.descriptionText;
    }
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    private String titleAlign = null;
    public String getTitleAlign() {
        return this.titleAlign;
    }
    public void setTitleAlign(String titleAlign) {
        this.titleAlign = titleAlign;
    }

    private String titleColor = null;
    public String getTitleColor() {
        return this.titleColor;
    }
    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    private String titleFont = null;
    public String getTitleFont() {
        return this.titleFont;
    }
    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }

    private String titleFontSize = null;
    public String getTitleFontSize() {
        return this.titleFontSize;
    }
    public void setTitleFontSize(String titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    private String titleFontStyle = null;
    public String getTitleFontStyle() {
        return this.titleFontStyle;
    }
    public void setTitleFontStyle(String titleFontStyle) {
        this.titleFontStyle = titleFontStyle;
    }

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
