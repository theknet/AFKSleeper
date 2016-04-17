package net.kdmdesign.AFKSleeper;

import org.bukkit.entity.Player;

public class AFKPlayer {

	private Player player;
	private AFKStatus status;
	private long lastUpdate;
	
	public AFKPlayer(Player player) {
		
		this.player = player;
		this.status = AFKStatus.ONLINE;
		this.lastUpdate = System.currentTimeMillis();
	}
	
	public void setStatus(AFKStatus status) {
		
		this.status = status;
		this.lastUpdate = System.currentTimeMillis();
	}
	
	public boolean isAFK() {
		
		return AFKStatus.AFK == this.status;
	}
	
	public boolean isOnline() {
		
		return AFKStatus.ONLINE == this.status;
	}
	
	public Player getPlayer() {
		
		return this.player;
	}
	
	public long getLastUpdate() {
		
		return this.lastUpdate;
	}
}
