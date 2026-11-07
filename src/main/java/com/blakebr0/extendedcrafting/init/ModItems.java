package com.blakebr0.extendedcrafting.init;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.cucumber.item.BaseShinyItem;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.item.HandheldTableItem;
import com.blakebr0.extendedcrafting.item.RecipeMakerItem;
import com.blakebr0.extendedcrafting.item.SingularityItem;
import com.blakebr0.extendedcrafting.item.UltimateSingularityItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, ExtendedCrafting.MOD_ID);

	// register block items here for class load order purposes
	static {
		ModBlocks.BLOCK_ITEMS.forEach(REGISTRY::register);
	}

	public static final DeferredHolder<Item, Item> LUMINESSENCE = REGISTRY.register("luminessence", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> BLACK_IRON_INGOT = REGISTRY.register("black_iron_ingot", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> REDSTONE_INGOT = REGISTRY.register("redstone_ingot", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_REDSTONE_INGOT = REGISTRY.register("enhanced_redstone_ingot", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> ENDER_INGOT = REGISTRY.register("ender_ingot", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_ENDER_INGOT = REGISTRY.register("enhanced_ender_ingot", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> CRYSTALTINE_INGOT = REGISTRY.register("crystaltine_ingot", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> THE_ULTIMATE_INGOT = REGISTRY.register("the_ultimate_ingot", () -> new BaseItem(p -> p.rarity(Rarity.EPIC)));
	public static final DeferredHolder<Item, Item> BLACK_IRON_NUGGET = REGISTRY.register("black_iron_nugget", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> REDSTONE_NUGGET = REGISTRY.register("redstone_nugget", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_REDSTONE_NUGGET = REGISTRY.register("enhanced_redstone_nugget", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> ENDER_NUGGET = REGISTRY.register("ender_nugget", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_ENDER_NUGGET = REGISTRY.register("enhanced_ender_nugget", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> CRYSTALTINE_NUGGET = REGISTRY.register("crystaltine_nugget", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> THE_ULTIMATE_NUGGET = REGISTRY.register("the_ultimate_nugget", () -> new BaseItem(p -> p.rarity(Rarity.EPIC)));
	public static final DeferredHolder<Item, Item> BLACK_IRON_SLATE = REGISTRY.register("black_iron_slate", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> BASIC_CATALYST = REGISTRY.register("basic_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ADVANCED_CATALYST = REGISTRY.register("advanced_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ELITE_CATALYST = REGISTRY.register("elite_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ULTIMATE_CATALYST = REGISTRY.register("ultimate_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> EPIC_CATALYST = REGISTRY.register("epic_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> REDSTONE_CATALYST = REGISTRY.register("redstone_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_REDSTONE_CATALYST = REGISTRY.register("enhanced_redstone_catalyst", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> ENDER_CATALYST = REGISTRY.register("ender_catalyst", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_ENDER_CATALYST = REGISTRY.register("enhanced_ender_catalyst", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> CRYSTALTINE_CATALYST = REGISTRY.register("crystaltine_catalyst", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> THE_ULTIMATE_CATALYST = REGISTRY.register("the_ultimate_catalyst", () -> new BaseItem(p -> p.rarity(Rarity.EPIC)));
	public static final DeferredHolder<Item, Item> BASIC_COMPONENT = REGISTRY.register("basic_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ADVANCED_COMPONENT = REGISTRY.register("advanced_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ELITE_COMPONENT = REGISTRY.register("elite_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ULTIMATE_COMPONENT = REGISTRY.register("ultimate_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> EPIC_COMPONENT = REGISTRY.register("epic_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> REDSTONE_COMPONENT = REGISTRY.register("redstone_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_REDSTONE_COMPONENT = REGISTRY.register("enhanced_redstone_component", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> ENDER_COMPONENT = REGISTRY.register("ender_component", () -> new BaseItem());
	public static final DeferredHolder<Item, Item> ENHANCED_ENDER_COMPONENT = REGISTRY.register("enhanced_ender_component", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> CRYSTALTINE_COMPONENT = REGISTRY.register("crystaltine_component", () -> new BaseItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> THE_ULTIMATE_COMPONENT = REGISTRY.register("the_ultimate_component", () -> new BaseItem(p -> p.rarity(Rarity.EPIC)));
	public static final DeferredHolder<Item, Item> FLUX_STAR = REGISTRY.register("flux_star", () -> new BaseShinyItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> ENDER_STAR = REGISTRY.register("ender_star", () -> new BaseShinyItem(p -> p.rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> HANDHELD_TABLE = REGISTRY.register("handheld_table", HandheldTableItem::new);
	public static final DeferredHolder<Item, Item> RECIPE_MAKER = REGISTRY.register("recipe_maker", RecipeMakerItem::new);
	public static final DeferredHolder<Item, Item> SINGULARITY = REGISTRY.register("singularity", SingularityItem::new);
	public static final DeferredHolder<Item, Item> ULTIMATE_SINGULARITY = REGISTRY.register("ultimate_singularity", UltimateSingularityItem::new);
}
