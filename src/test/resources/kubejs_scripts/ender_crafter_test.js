//Shaped
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_ender_crafter',
  pattern: [
    'AAA',
    'A A',
    'AAA'
  ],
  key: {
    A: {
      item: 'minecraft:gold_ingot'
    }
  },
  result: {
    item: 'minecraft:stone'
  }
  })
  .id('minecraft:stone');
});

//Shapeless
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shapeless_ender_crafter',
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