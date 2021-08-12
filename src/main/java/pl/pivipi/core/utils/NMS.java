package pl.pivipi.core.utils;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;

public class NMS {
	public static Object serverInstance;
    public static Field tpsField;

	
	public final static String name = Bukkit.getServer().getClass().getPackage().getName();
    public final static String version = name.substring(name.lastIndexOf('.') + 1);
    
    public static Class<?> getClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Double getTPS(int time) {
        try {
            double[] tps = ((double[]) tpsField.get(serverInstance));
            return tps[time];
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
