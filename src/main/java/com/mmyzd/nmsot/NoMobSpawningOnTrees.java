package com.mmyzd.nmsot;

import java.util.Map;

import com.mmyzd.nmsot.rule.RuleSet;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = NoMobSpawningOnTrees.MODID, useMetadata = true, guiFactory = "com.mmyzd.nmsot.GuiFactory")
public class NoMobSpawningOnTrees {

	public static final String MODID = "nmsot";

	@Instance(MODID)
	public static NoMobSpawningOnTrees instance;

	public ConfigManager config;
	public RuleSet rules;
	public double accumulatedSpawningTries;

	public MinecraftServer server;

	@NetworkCheckHandler
	public boolean checkRemote(Map<String, String> name, Side side) {
		return true;
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandManager());
		server = event.getServer();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new ConfigManager(event.getModConfigurationDirectory());
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(config);
		rules = new RuleSet(config.blacklistRules.getStringList());
		accumulatedSpawningTries = 0;
	}

	@SubscribeEvent
	public void onMobSpawning(LivingSpawnEvent.CheckSpawn event) {
		final SpawningEntry entry = new SpawningEntry();
		entry.init(event);
		if (rules.apply(entry)) {
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.side != Side.SERVER || event.phase != TickEvent.Phase.START || server == null) {
			return;
		}

		accumulatedSpawningTries += config.extraSpawningTries.getDouble(0.0);
		int spawningTries = (int) Math.floor(accumulatedSpawningTries);
		accumulatedSpawningTries -= spawningTries;
		if (spawningTries == 0) {
			return;
		}

		final SpawnerAnimals spawner = new SpawnerAnimals();
		Integer[] ids = DimensionManager.getIDs((server.getTickCounter() & 511) == 0);
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i].intValue();
			if (id == 0 || server.getAllowNether()) {
				WorldServer worldserver = DimensionManager.getWorld(id);
				if (worldserver.getGameRules().getBoolean("doMobSpawning")) {
					for (int j = 0; j < spawningTries; j++) {
						spawner.findChunksForSpawning(worldserver, true, false,
								worldserver.getWorldInfo().getWorldTotalTime() % 400L == 0L);
					}
				}
			}
		}
	}

}
