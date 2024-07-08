package com.pharma.medicare.utility;

import java.sql.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.medicare.domain.CustomerDetails;

public class CommonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	public CommonUtil() 
	{
	
    }
	
	
	public static String getString(Object object) {
		String value = "";
		try {
			value = mapper.writeValueAsString(object);
		} catch (Exception e) {

		}
		return value;
	}
	
	public static boolean isNotNull(Object object) {
		if(object!=null && !object.toString().isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static Boolean isNotNull(String value) {
		if(value!=null && !value.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Double value) {
		if(value!=null && value!=0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Date value) {
		if(value!=null) {
			return true;
		}
		return false;
	}


	public static void checkAppendConditionForStringField(StringBuilder stringBuilder, String field,
			String value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(field).append(" like ");
	        stringBuilder.append("'%" + value + "%'");
	        stringBuilder.append(" And");
	    }
	}
	
	public static void checkAppendConditionForDoubleValue(StringBuilder stringBuilder, String field,
			Double value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(field).append(" = ");
	        stringBuilder.append( value );
	        stringBuilder.append(" And");
	    }
	}
	
	public static void checkAppendConditionForDateField(StringBuilder stringBuilder, String field,
			String value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append("DATE("+field).append(") = ");
	        stringBuilder.append("'"+ value +"'" );
	        stringBuilder.append(" And");
	    }
	}


	

}
