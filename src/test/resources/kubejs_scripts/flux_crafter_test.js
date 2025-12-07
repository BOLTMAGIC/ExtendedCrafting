//Shaped
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_flux_crafter',
  powerRequired: 100000,
  powerRate: 400,
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

//SHapeless
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shapeless_flux_crafter',
  powerRequired: 100000,
  powerRate: 400,
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