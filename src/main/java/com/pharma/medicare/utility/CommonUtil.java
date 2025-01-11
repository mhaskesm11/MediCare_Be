package com.pharma.medicare.utility;

import java.sql.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	public CommonUtil() {}	
	
	public static String getString(Object object) {
		String value = "";
		try {
			value = mapper.writeValueAsString(object);
		} catch (Exception e) {

		}
		return value;
	}
	
	public static boolean isNotNull(Object object) {
		if(object!=null) {
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
	
	public static void checkAppendConditionForDateField(StringBuilder stringBuilder, String field,
			String value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(" DATE("+field).append(") = ");
	        stringBuilder.append("'"+ value +"'" );
	        stringBuilder.append(" And");
	    }
	}


	public static void checkAppendConditionForStringField(StringBuilder stringBuilder, String field,
			String value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(" "+field).append(" like ");
	        stringBuilder.append("'%" + value + "%'");
	        stringBuilder.append(" And");
	    }
	}
	
	public static void checkAppendConditionForDoubleValue(StringBuilder stringBuilder, String field,
			Double value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(" "+field).append(" = ");
	        stringBuilder.append( value );
	        stringBuilder.append(" And");
	    }
	}
	
	public static void checkAppendConditionForDateField(StringBuilder stringBuilder, String field,
			Date value) {
		if (CommonUtil.isNotNull(value)) {
	        stringBuilder.append(" DATE("+field).append(") = ");
	        stringBuilder.append("'"+ value +"'" );
	        stringBuilder.append(" And");
	    }
	}

	public static void checkAppendConditionForDateComparison(StringBuilder stringBuilder, String field,
			String saleType) {
		if(CommonUtil.isNotNull(saleType)) {
			
			if(saleType.equalsIgnoreCase("todaysSale")) {
				 stringBuilder.append(" DATE("+field).append(") = ");
			        stringBuilder.append(" CURDATE() " );
			        stringBuilder.append(" And");
				
			}else if(saleType.equalsIgnoreCase("weeklySale")) {
				 stringBuilder.append(" YEARWEEK("+field +", 1").append(") = ");
			        stringBuilder.append("YEARWEEK(CURDATE(), 1) ");
			        stringBuilder.append(" And");			        
				
			}else if(saleType.equalsIgnoreCase("monthlySale")) {
				 stringBuilder.append(" YEAR("+field).append(") = ");
			        stringBuilder.append("YEAR(CURDATE()) And MONTH("+ field +") = " );
			        stringBuilder.append("MONTH(CURDATE()) ");
			        stringBuilder.append(" And");
				
			}else if(saleType.equalsIgnoreCase("yearlySale")) {
				 stringBuilder.append(" YEAR("+field).append(") = ");
			        stringBuilder.append("YEAR(CURDATE()) " );
			        stringBuilder.append(" And");
				
			}else if(saleType.equalsIgnoreCase("allSale")) {
				 stringBuilder.append(" DATE("+field).append(")  ");
			        stringBuilder.append(" And");
				
			}
		}		
		
	}


	

}
