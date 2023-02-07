package com.idmoon.relics.relics;

import com.idmoon.relics.*;
import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.inventory.PlayerInventory;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.HumanEntity;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public final class Splinter extends Relic{

  {
    item = Material.WOOD_SWORD;
    id = "splinter";

    name = "&6Заноза";
    description = new String[]{"&fТрудно &7вытащить&f...",
                               "&3Ослабляет, &8замедляет&f и &2отравляет&f противника."};

    enabled = plugin.getConfig().getBoolean("enabledRelics." + this.id);   
  }

  public Splinter(Relics plugin){
    super(plugin);
    if(plugin.getRelicList().get(id) == null) plugin.getRelicList().put(this.id, this);
  }

  @EventHandler
  public void act(EntityDamageByEntityEvent event){
    if ((event.getDamager() instanceof Player)
    && (event.getEntity() instanceof LivingEntity)) {
      Player hunter = (Player) event.getDamager();
      if (plugin.isRelic(hunter.getInventory().getItemInMainHand(), id)){
        if (plugin.isRelicEnabled(this.id)){
          if(hunter.getLevel() > 0){
            //check if player is blocking
            if (!((event.getEntity() instanceof HumanEntity) && ((HumanEntity) event.getEntity()).isBlocking())){
              hunter.setLevel(hunter.getLevel() - 1);
              ArrayList<PotionEffect> appliedEffects = new ArrayList<PotionEffect>();
              appliedEffects.add(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1));
              appliedEffects.add(new PotionEffect(PotionEffectType.SLOW,     100, 1));
              appliedEffects.add(new PotionEffect(PotionEffectType.POISON,   100, 1));
              ((LivingEntity) event.getEntity()).addPotionEffects(appliedEffects);
            }
          } else {
            hunter.sendMessage("Недостаточно опыта для использования.");
          }
        } else {
          hunter.sendMessage("Эта реликвия не разрешена на сервере.");
        }
      }
    }
  }
}

