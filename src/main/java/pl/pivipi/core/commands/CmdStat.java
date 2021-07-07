package pl.pivipi.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import pl.pivipi.core.utils.ColorUtils;

public class CmdStat implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Runtime r = Runtime.getRuntime();
		if (args[0].equalsIgnoreCase("tps")) {
			sender.sendMessage(ChatColor.GREEN + "TPS serwera: " + ColorUtils.colorTps(MinecraftServer.getServer().recentTps[0]));
		} else if (args[0].equalsIgnoreCase("ram")) {
			sender.sendMessage(ChatColor.GREEN + "Uzyta pamiec: " + ChatColor.AQUA + Long.toString((r.totalMemory() - r.freeMemory()) / 1048576) + "MB");
			sender.sendMessage(ChatColor.GREEN + "Wolna pamiec: " + ChatColor.AQUA + Long.toString(r.freeMemory() / 1048576) + "MB");
			sender.sendMessage(ChatColor.GREEN + "Zaalokowana pamiec: " + ChatColor.AQUA + Long.toString(r.totalMemory() / 1048576) + "MB");
			sender.sendMessage(ChatColor.GREEN + "Maksymalne uzycie: " + ChatColor.AQUA + Long.toString(r.maxMemory() / 1048576) + "MB");
		} else if (args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(ChatColor.GREEN + "Odleglosc widzenia na serwerze: " + ChatColor.AQUA + Integer.toString(Bukkit.getViewDistance()) + " chunkow");
			sender.sendMessage(ChatColor.GREEN + "Wersja Javy: " + ChatColor.AQUA + System.getProperty("java.version"));
			sender.sendMessage(ChatColor.GREEN + "Procesory: " + ChatColor.AQUA + Integer.toString(r.availableProcessors()));
		} else {
			sender.sendMessage(ChatColor.AQUA + "/stat tps" + ChatColor.GREEN + " - zobacz TPS serwera.");
			sender.sendMessage(ChatColor.AQUA + "/stat ram" + ChatColor.GREEN + " - zobacz uzycie RAMu przez serwer.");
			sender.sendMessage(ChatColor.AQUA + "/stat info" + ChatColor.GREEN + " - inne informacje.");
		}
		return true;
	}
}
