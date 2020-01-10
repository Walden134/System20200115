package org.hust.bean.hydro;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Runoff {
	private String id;
	private String rcpId;
	private String gcmId;
	private String stationName;
	private String year;
	private Date date;
	private String runoff;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRcpId() {
		return rcpId;
	}

	public void setRcpId(String rcpId) {
		this.rcpId = rcpId;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRunoff() {
		return runoff;
	}

	public void setRunoff(String runoff) {
		this.runoff = runoff;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String toString() {
		return "Runoff [rcpId=" + rcpId + ", gcmId=" + gcmId + ", date=" + sdf.format(date) + ", runoff=" + runoff
				+ "]";
	}

}
