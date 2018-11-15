package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Workspace extends AbstractEntity {

	@NotBlank(message = "{bakery.name.required}")
	@Size(max = 255)
	@Column(unique = true)
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private String enabled;
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	private String titleLabel;
	public String getTitleLabel() {
		return titleLabel;
	}
	public void setTitleLabel(String titleLabel) {
		this.titleLabel = titleLabel;
	}

	private String subtitleLabel;
	public String getSubtitleLabel() {
		return subtitleLabel;
	}
	public void setSubtitleLabel(String subtitleLabel) {
		this.subtitleLabel = subtitleLabel;
	}

	private String footerLabel;
	public String getFooterLabel() {
		return footerLabel;
	}
	public void setFooterLabel(String footerLabel) {
		this.footerLabel = footerLabel;
	}

	private String titleVisible;
	public String getTitleVisible() {
		return titleVisible;
	}
	public void setTitleVisible(String titleVisible) {
		this.titleVisible = titleVisible;
	}

	private String subtitleVisible;
	public String getSubtitleVisible() {
		return subtitleVisible;
	}
	public void setSubtitleVisible(String subtitleVisible) {
		this.subtitleVisible = subtitleVisible;
	}

	private String footerVisible;
	public String getFooterVisible() {
		return footerVisible;
	}
	public void setFooterVisible(String footerVisible) {
		this.footerVisible = footerVisible;
	}

	private String camera;
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {this.camera = camera;}

	private String flashingEnabled;
	public String getFlashingEnabled() {
		return flashingEnabled;
	}
	public void setFlashingEnabled(String flashingEnabled) {this.flashingEnabled = flashingEnabled;}

	private Integer flashingInterval;
	public Integer getFlashingInterval() {
		return flashingInterval;
	}
	public void setflashingInterval(Integer flashingInterval) {this.flashingInterval = flashingInterval;}

	private String backgroundImageName;
	public String getBackgroundImageName() {
		return backgroundImageName;
	}
	public void setBackgroundImageName(String backgroundImageName) {this.backgroundImageName = backgroundImageName;}

	private String watermarkImageName;
	public String getWatermarkImageName() {
		return watermarkImageName;
	}
	public void setWatermarkImageName(String watermarkImageName) {this.watermarkImageName = watermarkImageName;}

	private String company;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {this.company = company;}

	private String facebookKey;
	public String getFacebookKey() {
		return facebookKey;
	}
	public void setFacebookKey(String facebookKey) {this.facebookKey = facebookKey;}

	private String facebookEnabled;
	public String getFacebookEnabled() {
		return facebookEnabled;
	}
	public void setFacebookEnabled(String facebookEnabled) {this.facebookEnabled = facebookEnabled;}

	private String facebookPage;
	public String getFacebookPage() {
		return facebookPage;
	}
	public void setFacebookPage(String facebookPage) {this.facebookPage = facebookPage;}

	private String titleUserLabel;
	public String getTitleUserLabel() {
		return titleUserLabel;
	}
	public void setTitleUserLabel(String titleUserLabel) {
		this.titleUserLabel = titleUserLabel;
	}

	private String subtitleUserLabel;
	public String getSubtitleUserLabel() {
		return subtitleUserLabel;
	}
	public void setSubtitleUserLabel(String subtitleUserLabel) {
		this.subtitleUserLabel = subtitleUserLabel;
	}

	private String titleUserVisible;
	public String getTitleUserVisible() {
		return titleUserVisible;
	}
	public void setTitleUserVisible(String titleUserVisible) {
		this.titleUserVisible = titleUserVisible;
	}

	private String subtitleUserVisible;
	public String getSubtitleUserVisible() {
		return subtitleUserVisible;
	}
	public void setSubtitleUserVisible(String subtitleUserVisible) {
		this.subtitleUserVisible = subtitleUserVisible;
	}

	private String titleColor;
	public String getTitleColor() {
		return titleColor;
	}
	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	private String subtitleColor;
	public String getSubtitleColor() {
		return subtitleColor;
	}
	public void setSubtitleColor(String subtitleColor) {
		this.subtitleColor = subtitleColor;
	}

	private String titleUserColor;
	public String getTitleUserColor() {
		return titleUserColor;
	}
	public void setTitleUserColor(String titleUserColor) {
		this.titleUserColor = titleUserColor;
	}

	private String subtitleUserColor;
	public String getSubtitleUserColor() {
		return subtitleUserColor;
	}
	public void setSubtitleUserColor(String subtitleUserColor) {
		this.subtitleUserColor = subtitleUserColor;
	}

	@Override
	public String toString() {
		return name;
	}
}
