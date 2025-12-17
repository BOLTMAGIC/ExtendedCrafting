//Tier 1 Table
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_table',
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

//Tier 2 Table
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_table',
  pattern: [
    'AAAAA',
    'A   A',
    'A   A',
    'A   A',
    'AAAAA'
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

//Tier 3 Table
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_table',
  pattern: [
    'AAAAAAA',
    'A     A',
    'A     A',
    'A     A',
    'A     A',
    'A     A',
    'AAAAAAA'
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

//Tier 4 Table
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_table',
  pattern: [
    'AAAAAAAAA',
    'A       A',
    'A       A',
    'A       A',
    'A       A',
    'A       A',
    'A       A',
    'A       A',
    'AAAAAAAAA'
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

//Tier 5 Table
ServerEvents.recipes((event) => {
  // minecraft:stone
  event.custom({
  type: 'extendedcrafting:shaped_table',
  pattern: [
    'AAAAAAAAAAA',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'A         A',
    'AAAAAAAAAAA'
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
});