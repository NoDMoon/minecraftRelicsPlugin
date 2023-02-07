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

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class StarDust extends Relic{

  {
    item = Material.IRON_HOE;
    id = "star_dust";

    name = "&f&oЗвездная&r &lпыль";
    description = new String[]{"&fВсепроникающий &lсвет.",
                               "&fИспользуйте, чтобы выпустить &lсгусток света&r.",
                               "&0Тьма&f процветает в &8пустоте&f.",
                               "&fНо &oвсегда&r уступает место &lчистому свету&r."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public StarDust(Relics plugin){
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
            if (player.getLocation().getBlock().getLightLevel() > 4){
              if(player.getLevel() > 0){
                player.setLevel(player.getLevel() - 1);
                player.launchProjectile(Snowball.class).setMetadata(this.id, new FixedMetadataValue(plugin, true));
              } else {
                player.sendMessage("Недостаточно опыта для использования.");
              } 
            } else {
              player.sendMessage("Недостаточно света.");
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
      if ((event.getHitEntity() != null)&& (event.getHitEntity() instanceof Damageable)){
        ((Damageable) event.getHitEntity()).damage(10.0);
        ((LivingEntity) event.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1));
      }    
    }
  }
}

