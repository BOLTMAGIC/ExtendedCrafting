package com.blakebr0.extendedcrafting.lib;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.singularity.Singularity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public final class ModSingularities {
    public static final Singularity COAL = new Singularity(ExtendedCrafting.resource("coal"), "singularity.extendedcrafting.coal", new int[] { 0x363739, 0x261E24 }, Ingredient.of(Items.COAL));
    public static final Singularity COPPER = new Singularity(ExtendedCrafting.resource("copper"), "singularity.extendedcrafting.copper", new int[] { 0xFA977C, 0xBC5430 }, Ingredient.of(Items.COPPER_INGOT));
    public static final Singularity IRON = new Singularity(ExtendedCrafting.resource("iron"), "singularity.extendedcrafting.iron", new int[] { 0xE1E1E1, 0x6C6C6C }, Ingredient.of(Items.IRON_INGOT));
    public static final Singularity LAPIS_LAZULI = new Singularity(ExtendedCrafting.resource("lapis_lazuli"), "singularity.extendedcrafting.lapis_lazuli", new int[] { 0x678DEA, 0x1B53A7 }, Ingredient.of(Items.LAPIS_LAZULI));
    public static final Singularity REDSTONE = new Singularity(ExtendedCrafting.resource("redstone"), "singularity.extendedcrafting.redstone", new int[] { 0xFF0000, 0x8A0901 }, Ingredient.of(Items.REDSTONE));
    public static final Singularity GLOWSTONE = new Singularity(ExtendedCrafting.resource("glowstone"), "singularity.extendedcrafting.glowstone", new int[] { 0xFFD38F, 0xA06135 }, Ingredient.of(Items.GLOWSTONE_DUST));
    public static final Singularity GOLD = new Singularity(ExtendedCrafting.resource("gold"), "singularity.extendedcrafting.gold", new int[] { 0xFDF55F, 0xD98E04 }, Ingredient.of(Items.GOLD_INGOT));
    public static final Singularity DIAMOND = new Singularity(ExtendedCrafting.resource("diamond"), "singularity.extendedcrafting.diamond", new int[] { 0xA6FCE9, 0x1AACA8 }, Ingredient.of(Items.DIAMOND));
    public static final Singularity EMERALD = new Singularity(ExtendedCrafting.resource("emerald"), "singularity.extendedcrafting.emerald", new int[] { 0x7DF8AC, 0x008E1A }, Ingredient.of(Items.EMERALD));

    public static final Singularity ALUMINUM = new Singularity(ExtendedCrafting.resource("aluminum"), "singularity.extendedcrafting.aluminum", new int[] { 0xCACCDA, 0x9A9CA6 }, "c:ingots/aluminum");
    public static final Singularity TIN = new Singularity(ExtendedCrafting.resource("tin"), "singularity.extendedcrafting.tin", new int[] { 0xA0BEBD, 0x527889 }, "c:ingots/tin");
    public static final Singularity BRONZE = new Singularity(ExtendedCrafting.resource("bronze"), "singularity.extendedcrafting.bronze", new int[] { 0xD99F43, 0xBB6B3B }, "c:ingots/bronze");
    public static final Singularity SILVER = new Singularity(ExtendedCrafting.resource("silver"), "singularity.extendedcrafting.silver", new int[] { 0xC0CDD2, 0x5F6E7C }, "c:ingots/silver");
    public static final Singularity LEAD = new Singularity(ExtendedCrafting.resource("lead"), "singularity.extendedcrafting.lead", new int[] { 0x6C7D92, 0x323562 }, "c:ingots/lead");
    public static final Singularity STEEL = new Singularity(ExtendedCrafting.resource("steel"), "singularity.extendedcrafting.steel", new int[] { 0x565656, 0x232323 }, "c:ingots/steel");
    public static final Singularity NICKEL = new Singularity(ExtendedCrafting.resource("nickel"), "singularity.extendedcrafting.nickel", new int[] { 0xE1D798, 0xB1976C }, "c:ingots/nickel");
    public static final Singularity ELECTRUM = new Singularity(ExtendedCrafting.resource("electrum"), "singularity.extendedcrafting.electrum", new int[] { 0xF5F18E, 0x9E8D3E }, "c:ingots/electrum");
    public static final Singularity INVAR = new Singularity(ExtendedCrafting.resource("invar"), "singularity.extendedcrafting.invar", new int[] { 0xBCC5BB, 0x5D7877 }, "c:ingots/invar");
    public static final Singularity PLATINUM = new Singularity(ExtendedCrafting.resource("platinum"), "singularity.extendedcrafting.platinum", new int[] { 0x6FEAEF, 0x57B8BC }, "c:ingots/platinum");

    public static List<Singularity> getDefaults() {
        return List.of(
                COAL,
                IRON,
                LAPIS_LAZULI,
                REDSTONE,
                GLOWSTONE,
                GOLD,
                DIAMOND,
                EMERALD,

                ALUMINUM,
                COPPER,
                TIN,
                BRONZE,
                SILVER,
                LEAD,
                STEEL,
                NICKEL,
                ELECTRUM,
                INVAR,
                PLATINUM
        );
    }
}
