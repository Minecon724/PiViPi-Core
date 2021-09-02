package pl.pivipi.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import pl.pivipi.core.utils.NMS;
import pl.pivipi.core.utils.ColorUtils;

public class CmdStat implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Runtime r = Runtime.getRuntime();
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("tps")) {
				sender.sendMessage(ChatColor.GREEN + "Server TPS: " + ColorUtils.colorTps(NMS.getTPS(0)));
			} else if (args[0].equalsIgnoreCase("ram")) {
				sender.sendMessage(ChatColor.GREEN + "Memory used: " + ChatColor.AQUA + Long.toString((r.totalMemory() - r.freeMemory()) / 1048576) + "MB");
				sender.sendMessage(ChatColor.GREEN + "Free memory: " + ChatColor.AQUA + Long.toString(r.freeMemory() / 1048576) + "MB");
				sender.sendMessage(ChatColor.GREEN + "Allocated memory: " + ChatColor.AQUA + Long.toString(r.totalMemory() / 1048576) + "MB");
				sender.sendMessage(ChatColor.GREEN + "Max usage: " + ChatColor.AQUA + Long.toString(r.maxMemory() / 1048576) + "MB");
			} else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.GREEN + "Server view distance: " + ChatColor.AQUA + Integer.toString(Bukkit.getViewDistance()) + " chunks");
				sender.sendMessage(ChatColor.GREEN + "Java version: " + ChatColor.AQUA + System.getProperty("java.version"));
				sender.sendMessage(ChatColor.GREEN + "CPU threads: " + ChatColor.AQUA + Integer.toString(r.availableProcessors()));
			} else {
				sender.sendMessage(ChatColor.AQUA + "/stat tps" + ChatColor.GREEN + " - view server TPS.");
				sender.sendMessage(ChatColor.AQUA + "/stat ram" + ChatColor.GREEN + " - view RAM usage.");
				sender.sendMessage(ChatColor.AQUA + "/stat info" + ChatColor.GREEN + " - other info.");
			}
		} else {
			sender.sendMessage(ChatColor.AQUA + "/stat tps" + ChatColor.GREEN + " - view server TPS.");
			sender.sendMessage(ChatColor.AQUA + "/stat ram" + ChatColor.GREEN + " - view RAM usage.");
			sender.sendMessage(ChatColor.AQUA + "/stat info" + ChatColor.GREEN + " - other info.");
		}
		return true;
	}
}
