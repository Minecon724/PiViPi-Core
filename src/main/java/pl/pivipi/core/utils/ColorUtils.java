package pl.pivipi.core.utils;

import java.text.DecimalFormat;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {
    
	public static String colorTps(Double tps) {
		if (tps >= 20.00) {
			return ChatColor.DARK_GREEN + new DecimalFormat("##.##").format(tps);
		} else if (tps >= 19.95) {
			return ChatColor.GREEN + new DecimalFormat("##.##").format(tps);
		} else if (tps >= 18.5) {
			return ChatColor.GOLD + new DecimalFormat("##.##").format(tps);
		} else if (tps >= 16.0) {
			return ChatColor.RED + new DecimalFormat("##.##").format(tps);
		} else {
			return ChatColor.DARK_RED + new DecimalFormat("##.##").format(tps);
		}
	}
	
	public static String colorPing(int ping) {
		if (ping <= 90) {
			return ChatColor.DARK_GREEN + Integer.toString(ping);
		} else if (ping <= 179) {
			return ChatColor.GREEN + Integer.toString(ping);
		} else if (ping <= 299) {
			return ChatColor.GOLD + Integer.toString(ping);
		} else if (ping <= 499) {
			return ChatColor.RED + Integer.toString(ping);
		} else {
			return ChatColor.DARK_RED + Integer.toString(ping);
		}
	}
}
