# Extended Crafting: Expanded Plus

NeoForge **26.1.2** source port of the Expanded Plus fork, based on
[BlakeBr0/ExtendedCrafting's 26.1 branch](https://github.com/BlakeBr0/ExtendedCrafting/tree/26.1).

- Epic (11x11) and Legendary (13x13) crafting tables and automatic variants.
- All six auto-table tiers have individual crafting times, defaulting to 8 ticks.
- Automatic tables can import multiple items per tick, paying energy per committed item.
- JEI categories, recipe transfer, click areas, and Jade tier information for both added tiers.
- Recipes without a tier restriction appear in the smallest JEI table category that fits them.
- Recipe Maker reads all 121/169 input slots. Datapack output uses the first hotbar slot;
  shapeless table recipes retain the table tier.
- A bounded container-click codec adjustment supports clicks changing more than 128 slots.

## Requirements

Minecraft 26.1.2, NeoForge 26.1.2.71 (the pinned development version), and
Cucumber 26.1.2-9.0.0 or a compatible newer version. Development uses JDK 25.
JEI, Jade, and Patchouli versions follow the upstream 26.1 branch.
CraftTweaker integration remains disabled in the upstream 26.1 source.

## Port status

Version: `26.1.2-8.1.0`. This checkout has **not been compiled or tested in Minecraft**.
See [PORTING.md](PORTING.md) for source provenance, validation boundaries, and remaining checks.
No release artifact has been produced or published.

This mod retains the `extendedcrafting` namespace and replaces the original mod;
installing both together is unsupported. A Forge 1.20.1 save is not a supported
migration source for this NeoForge 26.1.2 port.

## Credits and license

Original Extended Crafting by BlakeBr0. Expanded changes by WithersChat,
Expanded Plus by BOLT_M4G1C, and this port by Knozyy.
The original Plus art and registry IDs are retained.

- [Original Extended Crafting](https://github.com/BlakeBr0/ExtendedCrafting)
- [Expanded Plus source](https://github.com/BOLTMAGIC/ExtendedCrafting)
- [MIT License](LICENSE)
