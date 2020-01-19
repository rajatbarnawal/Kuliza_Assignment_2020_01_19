package com.example.demo.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity(name = "Father")
@Table(name = "father_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "f_PhoneNo", "f_Email" }) })

public class Father implements Serializable{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "f_Name", updatable = true)
	@NotBlank(message = "Please provide father detail")
	private String fName;

	@Column(name = "f_Address", updatable = true)
	@NotEmpty(message = "Please provide father address")
	@Size(min = 6, max = 20, message = "Address must be  6 to 20 char long")
	private String fAddress;

	@Column(name = "f_sex", updatable = true)
	@NotNull(message = "Please provide father gender")
	private String fSex;

	@Column(name = "f_PhoneNo", updatable = true)
	@NotEmpty(message = "Please provide father mobile number")
	@Size(min = 10, max = 15, message = "Mobile must be 10 to 15 char long")
	private String fPhoneNo;

	@Column(name = "f_Wife_Name", updatable = true)
	@NotBlank(message = "Please provide your wife name")
	private String fWifeName;

	@Column(name = "f_NoChild", updatable = true)
	private Long fNoChild;
	
	
	@Column(name = "f_dob")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime fdob;
	
	@Column(name = "f_age", updatable = true)
	private Long fage;

	@Email
	@Column(name = "f_Email", updatable = true)
	private String fEmail;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH }, orphanRemoval = true, mappedBy = "father_id" )
	@JsonIgnoreProperties("father_id")
	private List<Son> son = new ArrayList<Son>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH }, orphanRemoval = true, mappedBy = "father_id")
	@JsonIgnoreProperties("father_id")
	private List<Daughter> daughter = new ArrayList<Daughter>();

	public Father() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Father(Long id, @NotBlank(message = "Please provide father detail") String fName,
			@NotEmpty(message = "Please provide father address") @Size(min = 6, max = 20, message = "Address must be  6 to 20 char long") String fAddress,
			@NotNull(message = "Please provide father gender") String fSex,
			@NotEmpty(message = "Please provide father mobile number") @Size(min = 10, max = 15, message = "Mobile must be 10 to 15 char long") String fPhoneNo,
			@NotBlank(message = "Please provide your wife name") String fWifeName, Long fNoChild, LocalDateTime fdob,
			Long fage, @Email String fEmail, List<Son> son, List<Daughter> daughter) {
		super();
		this.id = id;
		this.fName = fName;
		this.fAddress = fAddress;
		this.fSex = fSex;
		this.fPhoneNo = fPhoneNo;
		this.fWifeName = fWifeName;
		this.fNoChild = fNoChild;
		this.fdob = fdob;
		this.fage = fage;
		this.fEmail = fEmail;
		this.son = son;
		this.daughter = daughter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfAddress() {
		return fAddress;
	}

	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}

	public String getfSex() {
		return fSex;
	}

	public void setfSex(String fSex) {
		this.fSex = fSex;
	}

	public String getfPhoneNo() {
		return fPhoneNo;
	}

	public void setfPhoneNo(String fPhoneNo) {
		this.fPhoneNo = fPhoneNo;
	}

	public String getfWifeName() {
		return fWifeName;
	}

	public void setfWifeName(String fWifeName) {
		this.fWifeName = fWifeName;
	}

	public Long getfNoChild() {
		return fNoChild;
	}

	public void setfNoChild(Long fNoChild) {
		this.fNoChild = fNoChild;
	}

	public LocalDateTime getFdob() {
		return fdob;
	}

	public void setFdob(LocalDateTime fdob) {
		this.fdob = fdob;
	}

	public Long getFage() {
		return fage;
	}

	public void setFage(Long fage) {
		this.fage = fage;
	}

	public String getfEmail() {
		return fEmail;
	}

	public void setfEmail(String fEmail) {
		this.fEmail = fEmail;
	}

	public List<Son> getSon() {
		return son;
	}

	public void setSon(List<Son> son) {
		this.son = son;
	}

	public List<Daughter> getDaughter() {
		return daughter;
	}

	public void setDaughter(List<Daughter> daughter) {
		this.daughter = daughter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daughter == null) ? 0 : daughter.hashCode());
		result = prime * result + ((fAddress == null) ? 0 : fAddress.hashCode());
		result = prime * result + ((fEmail == null) ? 0 : fEmail.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((fNoChild == null) ? 0 : fNoChild.hashCode());
		result = prime * result + ((fPhoneNo == null) ? 0 : fPhoneNo.hashCode());
		result = prime * result + ((fSex == null) ? 0 : fSex.hashCode());
		result = prime * result + ((fWifeName == null) ? 0 : fWifeName.hashCode());
		result = prime * result + ((fage == null) ? 0 : fage.hashCode());
		result = prime * result + ((fdob == null) ? 0 : fdob.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((son == null) ? 0 : son.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Father other = (Father) obj;
		if (daughter == null) {
			if (other.daughter != null)
				return false;
		} else if (!daughter.equals(other.daughter))
			return false;
		if (fAddress == null) {
			if (other.fAddress != null)
				return false;
		} else if (!fAddress.equals(other.fAddress))
			return false;
		if (fEmail == null) {
			if (other.fEmail != null)
				return false;
		} else if (!fEmail.equals(other.fEmail))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (fNoChild == null) {
			if (other.fNoChild != null)
				return false;
		} else if (!fNoChild.equals(other.fNoChild))
			return false;
		if (fPhoneNo == null) {
			if (other.fPhoneNo != null)
				return false;
		} else if (!fPhoneNo.equals(other.fPhoneNo))
			return false;
		if (fSex == null) {
			if (other.fSex != null)
				return false;
		} else if (!fSex.equals(other.fSex))
			return false;
		if (fWifeName == null) {
			if (other.fWifeName != null)
				return false;
		} else if (!fWifeName.equals(other.fWifeName))
			return false;
		if (fage == null) {
			if (other.fage != null)
				return false;
		} else if (!fage.equals(other.fage))
			return false;
		if (fdob == null) {
			if (other.fdob != null)
				return false;
		} else if (!fdob.equals(other.fdob))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (son == null) {
			if (other.son != null)
				return false;
		} else if (!son.equals(other.son))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Father [id=" + id + ", fName=" + fName + ", fAddress=" + fAddress + ", fSex=" + fSex + ", fPhoneNo="
				+ fPhoneNo + ", fWifeName=" + fWifeName + ", fNoChild=" + fNoChild + ", fdob=" + fdob + ", fage=" + fage
				+ ", fEmail=" + fEmail + ", son=" + son + ", daughter=" + daughter + "]";
	}

	
	
	

}
