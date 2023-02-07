package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

import org.bukkit.inventory.ItemStack;

import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Blaze;


import org.bukkit.World;
import org.bukkit.Location;

public final class IfritFlame extends Relic{

  {
    item = Material.BLAZE_ROD;
    id = "ifrit_flame";

    name = "&6Пламя &eифрита";
    description = new String[]{"&fИгры с &6огнем...",
                               "&fИспользуйте, чтобы вызвать &6пламя &fифрита.",
                               "&fБудьте осторожны: &6пламя &eживое&f...",
                               "&fНе работает, если &bнамокло&f."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public IfritFlame(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(PlayerInteractEvent event){
    Player player = event.getPlayer();
    World world = player.getWorld();
    Location location = player.getLocation();
    if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
      if (event.hasItem()){
        if (plugin.isRelic(event.getItem(), id)){
          if (plugin.isRelicEnabled(this.id)){
            if (world.hasStorm()
            && (world.getHighestBlockYAt(location) <= location.getBlockY())
            || location.getBlock().isLiquid()){
              player.sendMessage("Слишком мокро для огня.");
            } else{
              if(player.getLevel() > 0){
                player.setLevel(player.getLevel() - 1);
                if ((world.getTime()%10) == 3){
                  ((Blaze) world.spawnEntity(location, EntityType.BLAZE)).setTarget(player);
                } else {
                  player.launchProjectile(SmallFireball.class);
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
  }
}

