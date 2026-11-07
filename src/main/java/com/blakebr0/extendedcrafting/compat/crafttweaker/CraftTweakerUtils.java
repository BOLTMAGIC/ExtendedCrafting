package com.blakebr0.extendedcrafting.compat.crafttweaker;

import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import net.minecraft.world.item.ItemStack;

public final class CraftTweakerUtils {
	public static String getItemStackString(ItemStack stack) {
		return ItemStackUtil.getCommandString(stack);
	}
}
