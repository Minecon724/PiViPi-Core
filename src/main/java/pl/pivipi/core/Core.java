package pl.pivipi.core;

import org.bukkit.plugin.java.JavaPlugin;

import pl.pivipi.core.commands.CmdIp;
import pl.pivipi.core.commands.CmdStat;
import pl.pivipi.core.commands.CmdZakup;

public class Core extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getCommand("zakup").setExecutor(new CmdZakup());
		getCommand("stat").setExecutor(new CmdStat());
		getCommand("ip").setExecutor(new CmdIp());
	}
	
}
