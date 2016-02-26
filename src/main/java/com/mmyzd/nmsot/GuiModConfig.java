package com.mmyzd.nmsot;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class GuiModConfig extends GuiConfig {
	public GuiModConfig(GuiScreen parent) {
		super(
			parent,
			new ConfigElement(NoMobSpawningOnTrees.instance.config.file.getCategory("general")).getChildElements(),
			NoMobSpawningOnTrees.MODID,
			false,
			false,
			GuiConfig.getAbridgedConfigPath(NoMobSpawningOnTrees.instance.config.file.toString())
		);
	}
}