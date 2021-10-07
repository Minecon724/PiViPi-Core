package pl.pivipi.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import pl.pivipi.core.Core;

public class CmdCore implements CommandExecutor {
	
	private Core plugin;
	
	public CmdCore(Core plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.YELLOW + "------ PiViPi ------");
		sender.sendMessage(ChatColor.BLUE + "v" + plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.BLUE + "https://pivipi.net");
		sender.sendMessage(ChatColor.YELLOW + "--------------------");
		return true;
	}
}
