package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import org.bukkit.event.block.Action;

import org.bukkit.inventory.ItemStack;

import org.bukkit.entity.Snowball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;

import org.bukkit.metadata.Metadatable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.Location;
import org.bukkit.World;

public final class Mjolnir extends Relic{

  {
    item = Material.STONE_AXE;
    id = "mjolnir";

    name = "&bМьёльнир";
    description = new String[]{"&fИ &bнебо &7пало&f...",
                               "&7Используйте&f, чтобы вызвать &bшаровую молнию&f.",
                               "&fВ месте, куда она приземлится, &9ударит &bмолния&f.",
                               "&fРаботает только под открытым &bнебом&f."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public Mjolnir(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerInteractEvent event){
    Player player = event.getPlayer();
    if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
      if (event.hasItem()){
        if (plugin.isRelic(event.getItem(), id)){
          if (plugin.isRelicEnabled(this.id)){
            if(player.getLevel() > 0){
              player.setLevel(player.getLevel() - 1);
              player.launchProjectile(Snowball.class).setMetadata(this.id, new FixedMetadataValue(plugin, true));
            } else {
              player.sendMessage("Недостаточно опыта для использования");
            }
          } else {
            player.sendMessage("Эта реликвия не разрешена на сервере.");
          }
        }
      }
    }
  }

  @EventHandler
  public void act(ProjectileHitEvent event){
    if (event.getEntity().hasMetadata(this.id)){
      Location loc = event.getEntity().getLocation();
      World wrld = event.getEntity().getWorld();
      if (loc.getBlockY() >= wrld.getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ())){
        wrld.strikeLightning(loc);
      }    
    }
  }
}

