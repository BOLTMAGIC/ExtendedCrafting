<recipetype:extendedcrafting:table>.addShaped("test_shaped_3x3_with_reuse", 1, <item:minecraft:stick>, [
  [<tag:item:c:gems/diamond>, <item:minecraft:air>],
  [<item:minecraft:diamond>.reuse(), <item:minecraft:diamond>],
  [<item:minecraft:diamond>, <item:minecraft:air>]
]);

<recipetype:extendedcrafting:table>.addShaped("test_shaped_3x3_with_reuse_no_tier", 0, <item:minecraft:carrot>, [
  [<tag:item:c:ingots/iron>, <item:minecraft:air>],
  [<item:minecraft:iron_ingot>.reuse(), <item:minecraft:iron_ingot>],
  [<item:minecraft:iron_ingot>, <item:minecraft:air>]
]);

//<recipetype:extendedcrafting:table>.remove(<item:extendedcrafting:crystaltine_ingot>);
//
//<recipetype:extendedcrafting:table>.addShaped("65f26d24-ccb0-4b76-b622-5915c4ee2b72", 0, <item:minecraft:iron_sword>.withTag({Damage: 0, Enchantments: [{lvl: 1, id: "minecraft:bane_of_arthropods" as string}]}), [
//	[<item:extendedcrafting:recipe_maker>.withJsonComponent(<componenttype:extendedcrafting:recipe_maker>, {type: "CraftTweaker", shapeless: true}), <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:extendedcrafting:ender_star>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:gold_ingot>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:air>, <item:minecraft:coal>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:air>, <item:extendedcrafting:ender_alternator>, <item:minecraft:air>, <item:minecraft:air>],
//	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>]
//]);

<recipetype:extendedcrafting:table>.addShapeless("aab3beed-286c-47de-b0c2-a116bf5ada6f", 0, <item:minecraft:potato>, [
	<item:extendedcrafting:recipe_maker>.withJsonComponent(<componenttype:extendedcrafting:recipe_maker>, {type: "CraftTweaker", shapeless: true}), <item:extendedcrafting:ender_star>.reuse(), <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:coal>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:extendedcrafting:ender_alternator>
]);

<recipetype:extendedcrafting:table>.addShapeless("aab3beed-286c-47de-b0c2-a116badsf5ada6f", 0, <item:minecraft:carrot>, [
	<item:extendedcrafting:ender_star>, <item:extendedcrafting:ender_star>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:coal>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:extendedcrafting:ender_alternator>
]);

<recipetype:extendedcrafting:table>.addShapeless("aab3beed-286c-47de-b0badsf5ada6f", 0, <item:minecraft:carrot>, [
	<item:extendedcrafting:ender_star>, <item:minecraft:diamond_pickaxe>.anyDamage().reuse(), <item:minecraft:gold_ingot>.reuse(), <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:minecraft:gold_ingot>, <item:extendedcrafting:ender_alternator>
]);

<recipetype:extendedcrafting:table>.addShaped("6f07b4c3-12f0-4c54-9c3b-0e661c4c6f3b", 0, <item:minecraft:cobblestone>, [
	[<item:minecraft:stone>, <item:extendedcrafting:recipe_maker>.withJsonComponent(<componenttype:extendedcrafting:recipe_maker>, {type: "Datapack", shapeless: false}), <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>],
	[<item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>, <item:minecraft:air>]
]);

var recipes = <recipetype:extendedcrafting:table>.allRecipes;

println("There are " + recipes.length + " table crafting recipes");