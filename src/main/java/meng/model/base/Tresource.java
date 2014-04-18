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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Tresource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tresource", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tresource implements java.io.Serializable {

	// Fields
	
	private String pid;// 虚拟属性，用于获得当前资源的父资源ID

	private String id;
	private Tresourcetype tresourcetype;
	private Tresource tresource;
	private Date createdatetime;
	private String description;
	private String iconCls;
	private String name;
	private Integer seq;
	private String target;
	private Date updatedatetime;
	private String url;
	private Set<Trole> troles = new HashSet<Trole>(0);
	private Set<Tresource> tresources = new HashSet<Tresource>(0);
	private Set<Torganization> torganizations = new HashSet<Torganization>(0);

	// Constructors

	/** default constructor */
	public Tresource() {
	}

	/** minimal constructor */
	public Tresource(String id) {
		this.id = id;
	}

	/** full constructor */
	public Tresource(String id, Tresourcetype tresourcetype, Tresource tresource, Date createdatetime, String description, String iconcls, String name, Integer seq, String target, Date updatedatetime, String url, Set<Trole> troles, Set<Tresource> tresources, Set<Torganization> torganizations) {
		this.id = id;
		this.tresourcetype = tresourcetype;
		this.tresource = tresource;
		this.createdatetime = createdatetime;
		this.description = description;
		this.iconCls = iconcls;
		this.name = name;
		this.seq = seq;
		this.target = target;
		this.updatedatetime = updatedatetime;
		this.url = url;
		this.troles = troles;
		this.tresources = tresources;
		this.torganizations = torganizations;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRESOURCETYPE_ID")
	public Tresourcetype getTresourcetype() {
		return this.tresourcetype;
	}

	public void setTresourcetype(Tresourcetype tresourcetype) {
		this.tresourcetype = tresourcetype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRESOURCE_ID")
	public Tresource getTresource() {
		return this.tresource;
	}

	public void setTresource(Tresource tresource) {
		this.tresource = tresource;
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

	@Column(name = "ICONCLS", length = 100)
	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconcls) {
		this.iconCls = iconcls;
	}

	@Column(name = "NAME", length = 100)
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

	@Column(name = "TARGET", length = 100)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	@Column(name = "URL", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "trole_tresource", schema = "", joinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tresource")
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "torganization_tresource", schema = "", joinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TORGANIZATION_ID", nullable = false, updatable = false) })
	public Set<Torganization> getTorganizations() {
		return this.torganizations;
	}

	public void setTorganizations(Set<Torganization> torganizations) {
		this.torganizations = torganizations;
	}
	
	@Transient
	public String getPid() {
		if (tresource != null && !StringUtils.isBlank(tresource.getId())) {
			return tresource.getId();
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}