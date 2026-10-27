<recipetype:extendedcrafting:compressor>.addRecipe("test_compressor", <item:minecraft:apple>, <tag:item:c:ingots/iron>, 50, <tag:item:c:ingots/gold>, 10000, 100);

var recipes = <recipetype:extendedcrafting:compressor>.allRecipes;

println("There are " + recipes.length + " compressor crafting recipes");