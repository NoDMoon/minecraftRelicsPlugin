package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import org.bukkit.inventory.PlayerInventory;

import org.bukkit.entity.Player;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class Hook extends Relic{

  {
    item = Material.FISHING_ROD;
    id = "hook";

    name = "&7Крюк";
    description = new String[]{"&fЯ - &8Бетмен&f.",
                               "&fЗацепитесь за блок и &oлетите&r&f."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public Hook(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerFishEvent event){
    Player player = event.getPlayer();
    if (event.getState() == PlayerFishEvent.State.IN_GROUND){
      if (plugin.isRelic(player.getInventory().getItemInMainHand(), id)
      || plugin.isRelic(player.getInventory().getItemInOffHand(), id)){
        if (plugin.isRelicEnabled(this.id)){
          player.setVelocity(event.getHook().getLocation().toVector().subtract(player.getLocation().toVector()).normalize());
        } else {
          player.sendMessage("Эта реликвия не разрешена на сервере.");
        }
      }
    }
  }
}

