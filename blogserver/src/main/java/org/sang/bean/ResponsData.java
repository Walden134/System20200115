package org.sang.bean;

import java.util.List;

public class ResponsData {
	private boolean status;
	private String info;
	private List<Project> pros;
	private ProDetail px;
	private List<Double> ssp;
	private List<Double> sse;
	private List<Double> ccq;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Project> getPros() {
		return pros;
	}

	public void setPros(List<Project> pros) {
		this.pros = pros;
	}

	public ProDetail getPx() {
		return px;
	}

	public void setPx(ProDetail px) {
		this.px = px;
	}

	public List<Double> getSsp() {
		return ssp;
	}

	public void setSsp(List<Double> ssp) {
		this.ssp = ssp;
	}

	public List<Double> getSse() {
		return sse;
	}

	public void setSse(List<Double> sse) {
		this.sse = sse;
	}

	public List<Double> getCcq() {
		return ccq;
	}

	public void setCcq(List<Double> ccq) {
		this.ccq = ccq;
	}

}
