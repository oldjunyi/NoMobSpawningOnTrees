package com.mmyzd.nmsot.rule;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawnListManager;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleSet extends Rule {

	public Rule[] rule;
	public boolean[] black;

	public RuleSet(String[] data) {
		int n = data.length;
		ArrayList<Rule> u = new ArrayList<Rule>();
		ArrayList<Boolean> v = new ArrayList<Boolean>();
		for (int i = 0; i < n; i++) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("Now parsing " + data[i] + "");
			boolean flip = false;
			int m = data[i].length();
			LinkedList<Character> s = new LinkedList<Character>();
			for (int j = 0; j < m; j++)
				s.add(data[i].charAt(j));
			s.add('#');
			char c = skipSpace(s);
			if (c == '-')
				flip = true;
			if (c == '-' || c == '+' || c == '@')
				s.removeFirst();
			if (c == '#')
				continue;
			try {
				if (c == '@') {
					SpawnListManager.parse(s);
				} else {
					Rule rule = expr(s);
					if (skipSpace(s) != '#')
						throw new Exception("Syntax error");
					u.add(rule);
					v.add(flip);
				}
			} catch (Exception e) {
				StringBuilder debugInfo = new StringBuilder();
				int space = data[i].length() - s.size() + 1;
				while (space-- > 0)
					debugInfo.append(' ');
				while (!s.isEmpty()) {
					debugInfo.append(s.getFirst());
					s.removeFirst();
				}
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("  Failed at " + debugInfo.toString());
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("  Caused by " + e.toString());
			}
		}
		n = u.size();
		rule = new Rule[n];
		black = new boolean[n];
		for (int i = 0; i < n; i++) {
			rule[i] = u.get(i);
			black[i] = !v.get(i);
		}
	}

	public static char skipSpace(LinkedList<Character> s) {
		while (Character.isWhitespace(s.getFirst()))
			s.removeFirst();
		return s.getFirst();
	}

	public static boolean isDelimiter(char c) {
		return Character.isWhitespace(c) || ":#()!~|&,".indexOf(c) != -1;
	}

	public static boolean getTokenEqualsIgnoreCase(LinkedList<Character> s, String target) {
		target = target.toLowerCase();
		if (isDelimiter(skipSpace(s))) {
			if (target.length() == 1 && target.charAt(0) == s.getFirst()) {
				s.removeFirst();
				return true;
			}
			return false;
		}
		int n = target.length();
		for (int i = 0; i <= n; i++) {
			char c = Character.toLowerCase(s.getFirst());
			if ((i == n && !isDelimiter(c)) || (i != n && c != target.charAt(i))) {
				while (i > 0)
					s.addFirst(target.charAt(--i));
				return false;
			}
			if (i != n)
				s.removeFirst();
		}
		return true;
	}

	public static String getToken(LinkedList<Character> s) {
		StringBuilder u = new StringBuilder();
		char c = skipSpace(s);
		u.append(c);
		s.removeFirst();
		if (isDelimiter(c))
			return u.toString();
		while (!isDelimiter(c = s.getFirst())) {
			u.append(c);
			s.removeFirst();
		}
		return u.toString();
	}

	public static void nextPart(LinkedList<Character> s) throws Exception {
		if (!getTokenEqualsIgnoreCase(s, ":"))
			throw new Exception("Syntax error, \":\" is required");
	}

	Rule expr(LinkedList<Character> s) throws Exception {
		ArrayList<Rule> list = new ArrayList<Rule>();
		list.add(term(s));
		char o = '|';
		while (skipSpace(s) == o) {
			s.removeFirst();
			if (s.getFirst() == o)
				s.removeFirst();
			list.add(term(s));
		}
		if (list.size() == 0)
			throw new Exception("Empty expression");
		if (list.size() == 1)
			return list.get(0);
		return new RuleOr(list);
	}

	Rule term(LinkedList<Character> s) throws Exception {
		ArrayList<Rule> list = new ArrayList<Rule>();
		list.add(factor(s));
		char o = '&';
		while (skipSpace(s) == o) {
			s.removeFirst();
			if (s.getFirst() == o)
				s.removeFirst();
			list.add(factor(s));
		}
		if (list.size() == 0)
			throw new Exception("Empty expression");
		if (list.size() == 1)
			return list.get(0);
		return new RuleAnd(list);
	}

	Rule factor(LinkedList<Character> s) throws Exception {
		boolean not = false;
		for (; "!~".indexOf(skipSpace(s)) != -1; s.removeFirst())
			not = !not;
		Rule rule = null;
		if (getTokenEqualsIgnoreCase(s, "(")) {
			rule = expr(s);
			if (!getTokenEqualsIgnoreCase(s, ")"))
				throw new Exception("Unmatched parentheses");
		} else {
			if (getTokenEqualsIgnoreCase(s, "woodlogs")) {
				rule = new RuleWood();
			} else if (getTokenEqualsIgnoreCase(s, "block")) {
				rule = new RuleBlock(s);
			} else if (getTokenEqualsIgnoreCase(s, "position")) {
				rule = new RulePosition(s);
			} else if (getTokenEqualsIgnoreCase(s, "material")) {
				rule = new RuleMaterial(s);
			} else if (getTokenEqualsIgnoreCase(s, "mob")) {
				rule = new RuleMob(s);
			} else if (getTokenEqualsIgnoreCase(s, "mobtype")) {
				rule = new RuleMobType(s);
			} else if (getTokenEqualsIgnoreCase(s, "dim")) {
				rule = new RuleDimension(s);
			} else if (getTokenEqualsIgnoreCase(s, "chance")) {
				rule = new RuleChance(s);
			} else if (getTokenEqualsIgnoreCase(s, "biome")) {
				rule = new RuleBiome(s);
			} else if (getTokenEqualsIgnoreCase(s, "biometype")) {
				rule = new RuleBiomeType(s);
			} else if (getTokenEqualsIgnoreCase(s, "moonphase")) {
				rule = new RuleMoonPhase(s);
			} else if (getTokenEqualsIgnoreCase(s, "light")) {
				rule = new RuleLight(s);
			} else if (getTokenEqualsIgnoreCase(s, "daytime")) {
				rule = new RuleDayTime(s);
			} else if (getTokenEqualsIgnoreCase(s, "spawner")) {
				rule = new RuleSpawner();
			}
		}
		if (rule == null)
			throw new Exception("Invalid tag <" + getToken(s) + ">");
		if (rule instanceof RuleNot && not)
			return ((RuleNot) rule).rule;
		if (not)
			return new RuleNot(rule);
		return rule;
	}

	public boolean apply(SpawningEntry entry) {
		boolean result = false;
		int n = rule.length;
		for (int i = 0; i < n; i++)
			if (rule[i].apply(entry))
				result = black[i];
		return result;
	}

}
