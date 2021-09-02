package pl.pivipi.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import net.md_5.bungee.api.ChatColor;

public class CmdZakup implements CommandExecutor {
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			if (args.length >= 2) {
				String msg = "";
				for (int i=1;i<args.length;i++) {
					msg += args[i] + (i<args.length-1 ? " " : "");
				}
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.AQUA + args[0] + ChatColor.GOLD + " just bought " + ChatColor.AQUA + msg + ChatColor.GOLD + "!");
				Bukkit.broadcastMessage("");
				return true;
			}
		}
		return false;
    }
}
