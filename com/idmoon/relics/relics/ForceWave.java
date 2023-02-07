package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import org.bukkit.inventory.PlayerInventory;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import org.bukkit.metadata.Metadatable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;


public final class ForceWave extends Relic{

  {
    item = Material.NETHER_STAR;
    id = "force_wave";

    name = "&bСиловая &3волна";
    description = new String[]{"&fВнутренняя &bсила&f.",
                               "&fСоберите &lэнергию&r в себе.",
                               "&fЗатем &lраспрямитесь&r, оттолкнув всех от себя."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public ForceWave(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerToggleSneakEvent event){
    Player player = event.getPlayer();
    if (plugin.isRelic(player.getInventory().getItemInMainHand(), id)
    || plugin.isRelic(player.getInventory().getItemInOffHand(), id)){
      if (plugin.isRelicEnabled(this.id)){
        if (!event.isSneaking()){
          if(player.getLevel() > 0){
            player.setLevel(player.getLevel() - 1);
            for(Entity e: player.getNearbyEntities(3,3,3)){
              if ((e instanceof LivingEntity) && !(e.hasMetadata("NPC"))){
                e.setVelocity(e.getLocation().toVector().subtract(player.getLocation().toVector()).normalize());
              }
            }
          } else {
            player.sendMessage("Не хватает опыта для использования");
          }
        }
      } else {
        player.sendMessage("Эта реликвия не разрешена на сервере.");
      }
    }
  }
}

