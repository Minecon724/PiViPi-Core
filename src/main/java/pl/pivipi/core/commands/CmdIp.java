package pl.pivipi.core.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import pl.pivipi.core.Core;
import pl.pivipi.core.utils.PCDetection;

public class CmdIp implements CommandExecutor {
	private Core plugin;
	
	public CmdIp(Core plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("core.ip")) {
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Podaj IP lub nick gracza!");
				return true;
			} else {
				String ip;
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					ip = args[0];
				} else {
					String addr = p.getAddress().toString();
			        String[] fullip;
			        String[] ipandport;
			        fullip = addr.split("/");
			        String sIpandPort = fullip[1];
			        ipandport = sIpandPort.split(":");
			        ip = ipandport[0];
				}
				PCDetection pcDetection = new PCDetection(plugin.configCfg.getString("proxycheck_apikey"));
				pcDetection.useSSL();
		        pcDetection.set_api_timeout(5000);
		        pcDetection.setUseVpn(true);
		        pcDetection.setUseAsn(true);
		        try {
					pcDetection.parseResults(ip);
				} catch (IOException e) {
					sender.sendMessage(e.getMessage());
				} catch (org.json.simple.parser.ParseException e) {
					sender.sendMessage(e.getMessage());
				}
		        if (pcDetection.status.equalsIgnoreCase("ok")) {
			        sender.sendMessage(ChatColor.GREEN+ "--- Informacje o " +ChatColor.AQUA + ip + ChatColor.GREEN + " ---");
			        sender.sendMessage(ChatColor.GREEN + "Proxy: " + ChatColor.AQUA + ((pcDetection.proxy.equalsIgnoreCase("yes")) ? "tak" : "nie") + ChatColor.GOLD + pcDetection.type);
			        sender.sendMessage(ChatColor.GREEN + "ASN: " + ChatColor.AQUA + pcDetection.asn);
			        sender.sendMessage(ChatColor.GREEN + "Dostawca: " + ChatColor.AQUA + pcDetection.provider);
			        sender.sendMessage(ChatColor.GREEN + "Kraj: " + ChatColor.AQUA + pcDetection.country + ChatColor.GOLD + " " + pcDetection.isocode);
			        sender.sendMessage(ChatColor.GREEN + "Kontynent: " + ChatColor.AQUA + pcDetection.continent);
			        sender.sendMessage(ChatColor.GREEN + "Region: " + ChatColor.AQUA + pcDetection.region + ChatColor.GOLD + " " + pcDetection.regioncode);
			        sender.sendMessage(ChatColor.GREEN + "Miasto: " + ChatColor.AQUA + pcDetection.city);
			        sender.sendMessage(ChatColor.GREEN + "Lokalizacja: " + ChatColor.AQUA + Double.toString(pcDetection.latitude)+ ", " + Double.toString(pcDetection.longitude));
			        if (pcDetection.status.equalsIgnoreCase("warning")) sender.sendMessage(ChatColor.RED + "Ostrzezenie: " + pcDetection.message);
		        } else if (pcDetection.status.equalsIgnoreCase("denied") || pcDetection.status.equalsIgnoreCase("error")) {
		        	sender.sendMessage(ChatColor.RED + "Blad: " + pcDetection.message);
		        }
			}
		}
		return true;
	}
}
