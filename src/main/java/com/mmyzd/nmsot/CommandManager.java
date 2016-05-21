package com.mmyzd.nmsot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandManager extends CommandBase {
	
	static final String[] subCommand = {"reload", "help"};
	
	@Override
	public int getRequiredPermissionLevel() {
        return 2;
    }

	@Override
	public String getCommandName() {
		return "nmsot";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/nmsot <help|reload>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] word) throws CommandException {
		if (word.length != 1) {
			sender.addChatMessage(new TextComponentString("Usage: /nmsot <help|reload>"));
		} else if (word[0].toLowerCase().equals("help")) {
			sender.addChatMessage(new TextComponentString("Commands for <No Mob Spawning on Trees> MOD:"));
			sender.addChatMessage(new TextComponentString("  /nmsot help  -  Get help"));
			sender.addChatMessage(new TextComponentString("  /nmsot reload  -  Reload configuration"));
		} else if (word[0].toLowerCase().equals("reload")) {
			SpawnListManager.reload();
			NoMobSpawningOnTrees.instance.config.reload();
			sender.addChatMessage(new TextComponentString("NoMobSpawningOnTrees.cfg has been reloaded."));
		} else {
			sender.addChatMessage(new TextComponentString("Usage: /nmsot <help|reload>"));
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] word, BlockPos pos) {
		if (word.length != 1) return null;
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < subCommand.length; i++)
			if (subCommand[i].startsWith(word[0].toLowerCase())) {
				ret.add(subCommand[i]);
				return ret;
			}
		ret.add(subCommand[0]);
		return ret;
	}

}
