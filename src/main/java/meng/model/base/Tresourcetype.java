package meng.model.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Tresourcetype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tresourcetype", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tresourcetype implements java.io.Serializable {

	// Fields

	private String id;
	private Date createdatetime;
	private String description;
	private String name;
	private Date updatedatetime;
	private Set<Tresource> tresources = new HashSet<Tresource>(0);

	// Constructors

	/** default constructor */
	public Tresourcetype() {
	}

	/** minimal constructor */
	public Tresourcetype(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Tresourcetype(String id, Date createdatetime, String description, String name, Date updatedatetime, Set<Tresource> tresources) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.description = description;
		this.name = name;
		this.updatedatetime = updatedatetime;
		this.tresources = tresources;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 7)
	public Date getCreatedatetime() {
		if (this.createdatetime != null)
		{
			return this.createdatetime;
		}
		return new Date();
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATETIME", length = 7)
	public Date getUpdatedatetime() {
		if (this.updatedatetime != null)
		{
			return this.updatedatetime;
		}
		return new Date();
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tresourcetype")
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

}