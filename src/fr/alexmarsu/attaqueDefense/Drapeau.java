package fr.alexmarsu.attaqueDefense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Drapeau {
	private ItemStack item;
	private BannerMeta meta;
	private Block block;
	
	public Drapeau(ItemStack item, BannerMeta meta, Block block){
		this.setItem(item);
		this.setMeta(meta);
		this.setBlock(block);
	}

	public ItemStack getItem() {
		return item;
	}

	private void setItem(ItemStack item) {
		this.item = item;
	}

	private BannerMeta getMeta() {
		return meta;
	}

	private void setMeta(BannerMeta meta) {
		this.meta = meta;
	}
	
	public void placerDrapeau(Location loc){
		Block b = loc.getBlock();
		b.setType(Material.STANDING_BANNER);
		b.setData(this.getBlock().getData(), true);
		CraftBanner banner = new CraftBanner(b);
		banner.setBaseColor(this.getMeta().getBaseColor());
		banner.setPatterns(patterns);
	}

	private Block getBlock() {
		return block;
	}

	private void setBlock(Block block) {
		this.block = block;
	}
	
}
