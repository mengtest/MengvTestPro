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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Trole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trole", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trole implements java.io.Serializable {

	// Fields

	private String id;
	private Date createdatetime;
	private String description;
	private String icon;
	private String name;
	private Integer seq;
	private Date updatedatetime;
	private Set<Tresource> tresources = new HashSet<Tresource>(0);
	private Set<Tuser> tusers = new HashSet<Tuser>(0);

	// Constructors

	/** default constructor */
	public Trole() {
	}

	/** minimal constructor */
	public Trole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Trole(String id, Date createdatetime, String description, String icon, String name, Integer seq, Date updatedatetime, Set<Tresource> tresources, Set<Tuser> tusers) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.description = description;
		this.icon = icon;
		this.name = name;
		this.seq = seq;
		this.updatedatetime = updatedatetime;
		this.tresources = tresources;
		this.tusers = tusers;
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

	@Column(name = "ICON", length = 100)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "trole_tresource", schema = "", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) })
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tuser_trole", schema = "", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) })
	public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

}