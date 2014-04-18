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
 * Torganization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "torganization", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Torganization implements java.io.Serializable {

	// Fields
	private String pid;// 虚拟属性，用于获得当前机构的父机构ID

	private String id;
	private Torganization torganization;
	private String address;
	private String code;
	private Date createdatetime;
	private String iconcls;
	private String name;
	private Integer seq;
	private Date updatedatetime;
	private Set<Tresource> tresources = new HashSet<Tresource>(0);
	private Set<Tuser> tusers = new HashSet<Tuser>(0);
	private Set<Torganization> torganizations = new HashSet<Torganization>(0);

	// Constructors

	/** default constructor */
	public Torganization() {
	}

	/** minimal constructor */
	public Torganization(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Torganization(String id, Torganization torganization, String address, String code, Date createdatetime, String iconcls, String name, Integer seq, Date updatedatetime, Set<Tresource> tresources, Set<Tuser> tusers, Set<Torganization> torganizations) {
		this.id = id;
		this.torganization = torganization;
		this.address = address;
		this.code = code;
		this.createdatetime = createdatetime;
		this.iconcls = iconcls;
		this.name = name;
		this.seq = seq;
		this.updatedatetime = updatedatetime;
		this.tresources = tresources;
		this.tusers = tusers;
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
	@JoinColumn(name = "TORGANIZATIONID")
	public Torganization getTorganization() {
		return this.torganization;
	}

	public void setTorganization(Torganization torganization) {
		this.torganization = torganization;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CODE", length = 200)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Column(name = "ICONCLS", length = 100)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
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
	@JoinTable(name = "torganization_tresource", schema = "", joinColumns = { @JoinColumn(name = "TORGANIZATION_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) })
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "torganization_tuser", schema = "", joinColumns = { @JoinColumn(name = "TORGANIZATION_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) })
	public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "torganization")
	public Set<Torganization> getTorganizations() {
		return this.torganizations;
	}

	public void setTorganizations(Set<Torganization> torganizations) {
		this.torganizations = torganizations;
	}
	
	/**
	 * 用于业务逻辑的字段，注解@Transient代表不需要持久化到数据库中
	 * 
	 * @return
	 */
	@Transient
	public String getPid() {
		if (torganization != null && !StringUtils.isBlank(torganization.getId())) {
			return torganization.getId();
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}