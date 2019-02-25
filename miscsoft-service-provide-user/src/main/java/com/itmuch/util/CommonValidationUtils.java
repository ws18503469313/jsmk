package com.itmuch.util;

import org.apache.commons.lang3.StringUtils;

public class CommonValidationUtils {
	public static boolean isValidEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		String regex = "^[a-zA-Z0-9_-]+\\.*[a-zA-Z0-9_-]*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		return email.matches(regex);
	}

	public static boolean isValidIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return false;
		}
		String regex = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
		return idCard.matches(regex);
	}

	public static boolean isValidMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		String regex = "^1[34578]\\d{9}$";
		return mobile.matches(regex);
	}
}