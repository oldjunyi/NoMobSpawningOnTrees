package com.mmyzd.nmsot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

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
<<<<<<< HEAD
	public void processCommand(ICommandSender sender, String[] word) {
		if (word.length != 1) {
			sender.addChatMessage(new ChatComponentText("Usage: /nmsot <help|reload>"));
		} else if (word[0].toLowerCase().equals("help")) {
			sender.addChatMessage(new ChatComponentText("Commands for <No Mob Spawning on Trees> MOD:"));
			sender.addChatMessage(new ChatComponentText("  /nmsot help  -  Get help"));
			sender.addChatMessage(new ChatComponentText("  /nmsot reload  -  Reload configuration"));
		} else if (word[0].toLowerCase().equals("reload")) {
			SpawnListManager.reload();
=======
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.addChatMessage(new ChatComponentText("Usage: /nmsot <help|reload>"));
		} else if (args[0].toLowerCase().equals("help")) {
			sender.addChatMessage(new ChatComponentText("Commands for <No Mob Spawning on Trees> MOD:"));
			sender.addChatMessage(new ChatComponentText("  /nmsot help  -  Get help"));
			sender.addChatMessage(new ChatComponentText("  /nmsot reload  -  Reload configuration"));
		} else if (args[0].toLowerCase().equals("reload")) {
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
			NoMobSpawningOnTrees.instance.config.reload();
			sender.addChatMessage(new ChatComponentText("NoMobSpawningOnTrees.cfg has been reloaded."));
		} else {
			sender.addChatMessage(new ChatComponentText("Usage: /nmsot <help|reload>"));
		}
	}

	@Override
<<<<<<< HEAD
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] word, BlockPos pos) {
		if (word.length != 1) return null;
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < subCommand.length; i++)
			if (subCommand[i].startsWith(word[0].toLowerCase())) {
=======
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length != 1) return null;
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < subCommand.length; i++)
			if (subCommand[i].startsWith(args[0].toLowerCase())) {
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
				ret.add(subCommand[i]);
				return ret;
			}
		ret.add(subCommand[0]);
		return ret;
	}

}
