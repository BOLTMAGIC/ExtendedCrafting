<recipetype:extendedcrafting:ender_crafter>.addShaped("test_ender_crafter_shaped", <item:minecraft:stick>, [
  [<item:minecraft:diamond>, <item:minecraft:air>],
  [<item:minecraft:diamond>, <item:minecraft:diamond>],
  [<item:minecraft:diamond>, <item:minecraft:air>]
], 500);

<recipetype:extendedcrafting:ender_crafter>.addShapeless("test_ender_crafter_shapeless", <item:minecraft:stone>, [
  <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:gold_ingot>, <item:minecraft:diamond>
]);

var recipes = <recipetype:extendedcrafting:ender_crafter>.allRecipes;

println("There are " + recipes.length + " ender crafter recipes");