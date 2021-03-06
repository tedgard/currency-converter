package com.edgardndouna.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class QueryConversion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Version
	private Integer version;
	
	private String baseCurrency;
	private String targetCurrency;
	private Double amount;
	private String dateRate;
	private Double result;
	private Date dateQuery;
	
	@ManyToOne
	private User user;
	
	public QueryConversion(){
		this("", "", 0.0, "", 0.0, null);
	}
	
	public QueryConversion(String baseCurrency, String targetCurrency, Double amount, String dateRate, Double result, User user) {
		super();
		this.baseCurrency = baseCurrency;
		this.targetCurrency = targetCurrency;
		this.amount = amount;
		this.result = result;
		this.dateRate = dateRate;
		this.dateQuery = new Date();
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDateRate() {
		return dateRate;
	}

	public void setDateRate(String dateRate) {
		this.dateRate = dateRate;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String getDateQuery() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateToPrint = ""; 
		try { dateToPrint = formatter.format(dateQuery);
		} catch (Exception e) { dateToPrint = dateQuery.toString();} 
		
		return dateToPrint;
	}

	public void setDateQuery(Date dateQuery) {
		this.dateQuery = dateQuery;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((baseCurrency == null) ? 0 : baseCurrency.hashCode());
		result = prime * result + ((dateQuery == null) ? 0 : dateQuery.hashCode());
		result = prime * result + ((dateRate == null) ? 0 : dateRate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((targetCurrency == null) ? 0 : targetCurrency.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		QueryConversion other = (QueryConversion) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (baseCurrency == null) {
			if (other.baseCurrency != null)
				return false;
		} else if (!baseCurrency.equals(other.baseCurrency))
			return false;
		if (dateQuery == null) {
			if (other.dateQuery != null)
				return false;
		} else if (!dateQuery.equals(other.dateQuery))
			return false;
		if (dateRate == null) {
			if (other.dateRate != null)
				return false;
		} else if (!dateRate.equals(other.dateRate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (targetCurrency == null) {
			if (other.targetCurrency != null)
				return false;
		} else if (!targetCurrency.equals(other.targetCurrency))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryConversion [id=").append(id).append(", version=").append(version).append(", baseCurrency=")
				.append(baseCurrency).append(", targetCurrency=").append(targetCurrency).append(", amount=")
				.append(amount).append(", dateRate=").append(dateRate).append(", result=").append(result)
				.append(", dateQuery=").append(dateQuery).append(", user=").append(user).append("]");
		return builder.toString();
	}
	
}
