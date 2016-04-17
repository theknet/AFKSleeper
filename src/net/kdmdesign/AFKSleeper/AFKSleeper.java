package net.kdmdesign.AFKSleeper;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class AFKSleeper extends JavaPlugin {

	private PlayerStatusManager statusManager;
	
	@Override
	public void onEnable() {
		
		this.statusManager = new PlayerStatusManager(this);
		this.getServer().getPluginManager().registerEvents(this.statusManager, this);
		
		this.statusManager.createInitialStatus();
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerCheckingTask(this.statusManager), 1000, 1000);
	}
	
	@Override
	public void onDisable() {
		
		HandlerList.unregisterAll();
		this.getServer().getScheduler().cancelAllTasks();
	}
}
