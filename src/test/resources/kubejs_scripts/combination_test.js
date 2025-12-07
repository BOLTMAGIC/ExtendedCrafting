ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:combination',
  powerCost: 100000,
  input: {
    item: 'minecraft:iron_ingot'
  },
  ingredients: [
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    },
    {
      item: 'minecraft:gold_ingot'
    }
  ],
  result: {
    item: 'minecraft:stone'
  }
  })
  .id('minecraft:stone');
});