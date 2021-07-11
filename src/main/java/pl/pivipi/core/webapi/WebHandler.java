package pl.pivipi.core.webapi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		String uri = t.getRequestURI().toString().substring(1);
		String[] parts = uri.split("/");
		//Player player = Bukkit.getPlayer(parts[0]);
		OfflinePlayer player = Bukkit.getOfflinePlayer(parts[0]);
		int rCode = 200;
		String response = "OK";
		if (player == null) {
			rCode = 404;
			response = "Player " + parts[0] + " not found";
		} else {
			try {
				if (EnumUtils.isValidEnum(Statistic.class, parts[1].toUpperCase())) {
					int res;
					Statistic statistic = Statistic.valueOf(parts[1].toUpperCase());
					if (parts.length < 2 && statistic.getType() != Statistic.Type.UNTYPED) {
						rCode = 406;
						response = "Statistic type " + statistic.toString() + " requires " + statistic.getType().toString() + " argument";
					} else if (statistic.getType() != Statistic.Type.UNTYPED) {
						if (statistic.getType() == Statistic.Type.BLOCK || statistic.getType() == Statistic.Type.ITEM) {
							if (EnumUtils.isValidEnum(Material.class, parts[2].toUpperCase())) {
								res = player.getStatistic(statistic, Material.valueOf(parts[2].toUpperCase()));
								rCode = 200;
								response = Integer.toString(res);
							} else {
								rCode = 404;
								response = "Unknown Material " + parts[2].toUpperCase();
							}
						} else if (statistic.getType() == Statistic.Type.ENTITY) {
							if (EnumUtils.isValidEnum(EntityType.class, parts[2].toUpperCase())) {
								res = player.getStatistic(statistic, EntityType.valueOf(parts[2].toUpperCase()));
								rCode = 200;
								response = Integer.toString(res);
							} else {
								rCode = 404;
								response = "Unknown EntityType " + parts[2].toUpperCase();
							}
						}
					} else {
						res = player.getStatistic(statistic);
						rCode = 200;
						response = Integer.toString(res);
					}
				} else {
					rCode = 404;
					response = "Unknown Statistic " + parts[1].toUpperCase();
				}
			} catch (IllegalArgumentException e) {
				rCode = 406;
				response = e.getMessage();
			}
		}
		t.sendResponseHeaders(rCode, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
