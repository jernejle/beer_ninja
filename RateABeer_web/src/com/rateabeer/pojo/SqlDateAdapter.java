package com.rateabeer.pojo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sun.el.parser.ParseException;

public class SqlDateAdapter extends XmlAdapter<String, Date> {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String marshal(java.sql.Date d) {
		return dateFormat.format(d);
	}

	public Date unmarshal(String v) throws java.text.ParseException,
			ParseException {
		java.sql.Date sqlDate = null;
		java.util.Date convertedDate = dateFormat.parse(v);
		sqlDate = new java.sql.Date(convertedDate.getTime());
		return sqlDate;
	}
}
