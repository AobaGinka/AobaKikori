package com.aobaginka.aoba_kikori;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aoba_kikori extends JavaPlugin implements Listener {
    private int numberOfPlayer;
    private AobaKikoriPlayer[] playerdate;

    private final Material[] logs = {
            Material.ACACIA_LOG,
            Material.SPRUCE_LOG,
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.DARK_OAK_LOG
    };
    private final Material[] ores = {
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.COPPER_ORE,
            Material.LAPIS_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE
    };
    private final Material[] axes = {
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE
    };
    private final Material[] pickaxes = {
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
    };


    private static void breakBlock(Block block, ItemStack tool){
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        World world = block.getWorld();
        Material type = block.getType();

        block.breakNaturally(tool);

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                for(int k = -1; k < 2; k++){
                    if(world.getBlockAt(x+i,y+j,z+k).getType() == type){
                        breakBlock(world.getBlockAt(x+i,y+j,z+k),tool);
                    }
                }
            }
        }
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.playerdate = new AobaKikoriPlayer[getServer().getMaxPlayers()];
        this.numberOfPlayer = 0;
        getLogger().info("[Aoba_Kikori]プラグインが有効になりました。");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("[Aoba_Kikori]プラグインが無効になりました。");
    }


    private void sendHelp(CommandSender sender){
        sender.sendMessage("---<AobaKikori>---");
        sender.sendMessage("/aobakikori kikori (on/off):  木こり機能をon/offする");
        sender.sendMessage("/aobakikori ore (on/off): 鉱石の一括破壊機能をon/offする");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
        if(args.length == 0) {
            sendHelp(sender);
        }else{
            if(sender instanceof Player){
                if (args[0].equalsIgnoreCase("ore")) {
                    if (args.length == 1) {
                        sendHelp(sender);
                    }else if(args[1].equalsIgnoreCase("on")) {
                        for (int i = 0; i < this.numberOfPlayer; i++) {
                            if (this.playerdate[i].getName() == sender.getName()) {
                                this.playerdate[i].setStatusOfOre(true);
                                sender.sendMessage("[AobaKikori]鉱石の一括破壊機能をONにしました");
                            }
                        }
                    }else if (args[1].equalsIgnoreCase("off")) {
                        for (int i = 0; i < this.numberOfPlayer; i++) {
                            if (this.playerdate[i].getName() == sender.getName()) {
                                this.playerdate[i].setStatusOfOre(false);
                                sender.sendMessage("[AobaKikori]鉱石の一括破壊機能をOFFにしました");
                            }
                        }
                    }
                }else if (args[0].equalsIgnoreCase("kikori")) {
                    if (args.length == 1) {
                        sendHelp(sender);
                    }else if (args[1].equalsIgnoreCase("on")) {
                        for (int i = 0; i < this.numberOfPlayer; i++) {
                            if (this.playerdate[i].getName() == sender.getName()) {
                                this.playerdate[i].setStatusOfKikori(true);
                                sender.sendMessage("[AobaKikori]木こり機能をONにしました");
                            }
                        }
                    }else if (args[1].equalsIgnoreCase("off")) {
                        for (int i = 0; i < this.numberOfPlayer; i++) {
                            if (this.playerdate[i].getName() == sender.getName()) {
                                this.playerdate[i].setStatusOfKikori(false);
                                sender.sendMessage("[AobaKikori]木こり機能をOFFにしました");
                            }
                        }
                    }
                }
                }else {
                sender.sendMessage("[AobaKikori]プレイヤーで実行してください");
            }
        }
        return true;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Boolean isLog = false;
        Boolean isOre = false;
        Boolean isPickaxe = false;
        Boolean isAxe = false;
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        for(int i = 0; i < this.logs.length; i++){
            if(block.getType() == this.logs[i]){
                isLog = true;
            }
        }
        for(int i = 0; i < this.ores.length; i++){
            if(block.getType() == this.ores[i]){
                isOre = true;
            }
        }

        if(isLog == true){
            for(int i = 0; i < this.numberOfPlayer; i++){
                if(this.playerdate[i].getName() == event.getPlayer().getName()){
                    if(this.playerdate[i].getStatusOfKikori() == false){
                        return;
                    }
                }
            }
            for(int i = 0; i < this.axes.length; i++){
                if(tool.getType() == this.axes[i]) {
                    isAxe = true;
                }
            }
            if(isAxe == true){
                if(event.getPlayer().isSneaking() == true){
                    breakBlock(block,tool);
                }
            }
        }else if(isOre == true){
            for(int i = 0; i < this.numberOfPlayer; i++){
                if(this.playerdate[i].getName() == event.getPlayer().getName()){
                    if(this.playerdate[i].getStatusOfOre() == false){
                        return;
                    }
                }
            }
            for(int i = 0; i < this.pickaxes.length; i++){
                isPickaxe = true;
            }
            if(isPickaxe == true){
                breakBlock(block,tool);
            }
        }
    }


    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        this.playerdate[this.numberOfPlayer] = new AobaKikoriPlayer(event.getPlayer().getName());
        numberOfPlayer++;
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        int number = 0;

        for(int i = 0; i < this.numberOfPlayer; i++){
            if(this.playerdate[i].getName() == event.getPlayer().getName()){
                this.playerdate[i] = null;
                number = i;
            }
        }

        for(int i = number; i < this.numberOfPlayer-1; i++){
            this.playerdate[i] = this.playerdate[i+1];
        }

        this.numberOfPlayer--;
    }

}
