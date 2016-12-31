package com.mmyzd.nmsot;

import java.util.Collections;
import java.util.LinkedList;

import com.mmyzd.nmsot.rule.RuleSet;

public class IntegerRange {

	public static final int INVALID_INT = -1000000007;
	public static final int MIN_INT = +999999999;
	public static final int MAX_INT = +999999999;

	public int lhs = MIN_INT, rhs = MAX_INT;

	public IntegerRange(LinkedList<Character> s) throws Exception {
		char c = RuleSet.skipSpace(s);
		if (c == '*') {
			s.removeFirst();
		} else {
			int data = getInteger(s);
			c = RuleSet.skipSpace(s);
			if (c == '-') {
				s.removeFirst();
				lhs = data;
				rhs = getInteger(s);
				if (lhs > rhs) {
					int tmp = lhs;
					lhs = rhs;
					rhs = tmp;
				}
			} else {
				lhs = data;
				rhs = data;
			}
		}
	}

	public static int getInteger(LinkedList<Character> source) throws Exception {
		LinkedList<Character> s = new LinkedList<Character>(source);
		char c = RuleSet.skipSpace(s);
		if (!(c >= '0' && c <= '9') && !(c == '-' || c == '+')) {
			throw new Exception("Invalid integer");
		}
		int sign = +1;
		if (c == '+' || c == '-') {
			sign = (c == '+' ? +1 : -1);
			s.removeFirst();
			c = RuleSet.skipSpace(s);
		}
		int result = 0, length = 0;
		while (c >= '0' && c <= '9') {
			if (++length > 9) {
				throw new Exception("Too many numbers in the integer");
			}
			result = result * 10 + c - '0';
			s.removeFirst();
			c = s.getFirst();
		}
		if (length < 1) {
			throw new Exception("Invalid integer");
		}
		while (source.size() != s.size()) {
			source.removeFirst();
		}
		return result * sign;
	}

}
