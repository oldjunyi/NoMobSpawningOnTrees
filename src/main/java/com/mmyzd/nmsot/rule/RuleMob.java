package com.mmyzd.nmsot.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.entity.Entity;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleMob extends Rule {

	public Class<? extends Entity> entityClass = null;
	public HashSet<Class<? extends Entity>> entityClasses = null;

	private static HashMap<String, HashSet<Class<? extends Entity>>> classNames = null, mobNames = null, mobFullNames;

	public RuleMob(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		if (classNames == null) {
			classNames = new HashMap<String, HashSet<Class<? extends Entity>>>();
			mobNames = new HashMap<String, HashSet<Class<? extends Entity>>>();
			mobFullNames = new HashMap<String, HashSet<Class<? extends Entity>>>();
			List<EntityEntry> entityList = ForgeRegistries.ENTITIES.getValues();
			for (EntityEntry e : entityList) {
				Class<? extends Entity> key = e.getEntityClass();
				String value = e.getName().toLowerCase().replaceAll("\\s", "");
				addMobLookup(mobFullNames, value, key);
				addMobLookup(mobNames, value.substring(value.indexOf('.') + 1), key);
				addMobLookup(classNames, key.getSimpleName().toLowerCase(), key);
			}
		}
		String name = RuleSet.getToken(s).toLowerCase();
		HashSet<Class<? extends Entity>> classNameGroup = classNames.get(name);
		HashSet<Class<? extends Entity>> mobNameGroup = mobNames.get(name);
		HashSet<Class<? extends Entity>> mobFullNameGroup = mobFullNames.get(name);
		if (mobFullNameGroup != null && mobFullNameGroup.size() == 1) {
			entityClass = mobFullNameGroup.iterator().next();
		} else if (classNameGroup != null && classNameGroup.size() == 1) {
			entityClass = classNameGroup.iterator().next();
		} else if (mobNameGroup != null && mobNameGroup.size() == 1) {
			entityClass = mobNameGroup.iterator().next();
		} else if (mobFullNameGroup != null) {
			entityClasses = mobFullNameGroup;
		} else if (classNameGroup != null) {
			entityClasses = classNameGroup;
		} else if (mobNameGroup != null) {
			entityClasses = mobNameGroup;
		}
		if (entityClasses == null && entityClass == null) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("Can not find this mob: " + name);
		}
	}

	private void addMobLookup(HashMap<String, HashSet<Class<? extends Entity>>> lookup, String key,
			Class<? extends Entity> value) {
		HashSet<Class<? extends Entity>> group = lookup.get(key);
		if (group == null) {
			lookup.put(key, group = new HashSet<Class<? extends Entity>>());
		}
		group.add(value);
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entityClasses == null ? entityClass == entry.entity.getClass()
				: entityClasses.contains(entry.entity.getClass());
	}

}
