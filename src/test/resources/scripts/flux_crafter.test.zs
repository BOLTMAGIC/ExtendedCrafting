<recipetype:extendedcrafting:flux_crafter>.addShaped("test_flux_crafter_shaped", <item:minecraft:stick>, [
  [<item:minecraft:diamond>, <item:minecraft:air>],
  [<item:minecraft:diamond>, <item:minecraft:diamond>],
  [<item:minecraft:diamond>, <item:minecraft:air>]
], 50000, 500);

<recipetype:extendedcrafting:flux_crafter>.addShapeless("test_flux_crafter_shapeless", <item:minecraft:stone>, [
  <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:gold_ingot>, <item:minecraft:diamond>
], 25000);

var recipes = <recipetype:extendedcrafting:flux_crafter>.allRecipes;

println("There are " + recipes.length + " flux crafter recipes");