package it.windtre.tremobilitycms.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity(name="UserInfo")
@Table(name = "cms_user")
public class User extends AbstractEntity {

	@NotEmpty
	@Email
	@Size(max = 255)
	@Column(name = "mail", unique = true)
	private String email;

	@NotNull
	@Size(min = 4, max = 255)
	@Column(name = "passwordhash")
	private String passwordHash;

	@NotBlank
	@Size(max = 255)
	@Column(name = "firstname")
	private String firstName;

	@NotBlank
	@Size(max = 255)
	@Column(name = "lastname")
	private String lastName;

	@NotBlank
	@Size(max = 255)
	@Column(name = "role")
	private String role;

	@Size(max = 2083)
	private String photoUrl;

	@Column(name = "locked")
	private boolean locked = false;

	@PrePersist
	@PreUpdate
	private void prepareData(){
		this.email = email == null ? null : email.toLowerCase();
	}

	public User() {
		// An empty constructor is needed for all beans
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public static String getEntityName() {
		return " Utente";
	}

}
