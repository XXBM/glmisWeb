package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.IssuerDeserialize;
import com.glmis.jsonDeserialize.PractisingCertificationDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 执业资格信息
 */

@Entity
@Table(name = "p_employeeAssPractisingCertification")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssPractisingCertification implements Serializable {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**证书
	 */
	@ManyToOne
	@JoinColumn(name = "practisingCertification_id")
	@JsonDeserialize(using = PractisingCertificationDeserialize.class)
	private PractisingCertification practisingCertification;
	/**注册编号
	 */

	@Column(unique = true)
	private String registerNo;
	/**证书编号
	 */
	@Column(unique = true)
	private String certificateNo;
	/**聘用企业
	 */
	private String servedOrganization;
	/**获得时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar issuedDate;
	/**照片
	 */
	@Lob
	@Basic
	@Column(name = "photo",columnDefinition = "LongBlob")
	private byte[] photo;
	/**员工
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;
	/**发证机构
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "issuer_id")
	@JsonDeserialize(using = IssuerDeserialize.class)
	private Issuer issuer;

	public EmployeeAssPractisingCertification() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PractisingCertification getPractisingCertification() {
		return practisingCertification;
	}

	public void setPractisingCertification(PractisingCertification practisingCertification) {
		this.practisingCertification = practisingCertification;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getServedOrganization() {
		return servedOrganization;
	}

	public void setServedOrganization(String servedOrganization) {
		this.servedOrganization = servedOrganization;
	}

	public Calendar getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Calendar issuedDate) {
		this.issuedDate = issuedDate;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
}

