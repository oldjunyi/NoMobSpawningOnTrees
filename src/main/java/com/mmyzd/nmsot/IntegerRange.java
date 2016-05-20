package com.mmyzd.nmsot;

public class IntegerRange {
	
	public int lhs, rhs;
	
	public static IntegerRange parse(String token) throws Exception {
		IntegerRange ret = new IntegerRange();
		ret.lhs = -99999999;
		ret.rhs = +99999999;
		if (token.equals("*")) return ret;
		String lhsStr = getIntegerStringFrom(token, 0);
		if (!validateIntegerLength(lhsStr)) throw new Exception("Invalid range");
		ret.lhs = Integer.parseInt(lhsStr);
		if (lhsStr.length() == token.length()) {
			ret.rhs = ret.lhs;
		} else {
			String rhsStr = getIntegerStringFrom(token, lhsStr.length() + 1);
			if (!validateIntegerLength(rhsStr)) throw new Exception("Invalid range");
			ret.rhs = Integer.parseInt(rhsStr);
			if (ret.lhs > ret.rhs) {
				int tmp = ret.lhs;
				ret.lhs = ret.rhs;
				ret.rhs = tmp;
			}
			if (lhsStr.length() + rhsStr.length() + 1 != token.length())
				throw new Exception("Invalid range");
		}
		return ret;
	}
	
	public static String getIntegerStringFrom(String token, int start) {
		StringBuilder ret = new StringBuilder();
		for (int i = start; i < token.length(); i++) {
			char c = token.charAt(i);
			if ((c >= '0' && c <= '9') || ((c == '-' || c == '+') && i == start)) {
				ret.append(c);
			} else {
				break;
			}
		}
		if (ret.toString().equals("-") || ret.toString().equals("+")) return "";
		return ret.toString();
	}
	
	public static boolean validateIntegerLength(String num) {
		int len = num.length();
		if (num.startsWith("-") || num.startsWith("+")) len--;
		return len >= 1 && len <= 8;
	}
	
}
