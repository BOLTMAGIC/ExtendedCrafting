ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:compressor',
  powerCost: 500000,
  inputCount: 10000,
  ingredient: {
    item: 'minecraft:carrot'
  },
  catalyst: {
    item: 'extendedcrafting:ender_catalyst'
  },
  result: {
    item: 'minecraft:stone'
  }
  })
  .id('minecraft:stone');
});