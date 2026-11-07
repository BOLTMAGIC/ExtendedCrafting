# Expanded Plus 1.21.1 port

## Target and sources

- Target: Minecraft 1.21.1, NeoForge 21.1.18 (upstream's pin), Cucumber 1.21.1-8.0.13, Java 21.
- Local branch: `port/expanded-plus-1.21.1`.
- Base: BlakeBr0/ExtendedCrafting `1.21`, commit `ee1fe2c` (Extended Crafting 7.0.9).
- Plus reference: BOLTMAGIC/ExtendedCrafting `26.1.2` (the Expanded Plus 26.1.2 port) and
  `1.20` (`e4b4d77`) for 1.20-era behaviour on the same `IItemHandler` API.
- Fork version starts at `1.21.1-1.0.0`; archive name `ExtendedCraftingExpandedPlus`.

## How it was ported

New Epic (11x11) and Legendary (13x13) classes were produced with a three-way merge:
upstream 1.21 `Ultimate*` (ours) / upstream 26.1 `Ultimate*` (base) / Plus 26.1.2
`Epic*`/`Legendary*` (theirs). Only Plus-specific changes (grid size, tier, slot
indices, GUI coordinates) were carried over; every API call is the 1.21 one
(`BaseItemStackHandler`, `IItemHandler`, `GuiGraphics`, `CompoundTag`).

Resources: block models from the 1.20 Plus checkout; item models with `display`
transforms in `models/item/` (1.21.1 has no `assets/<ns>/items/`); 26.1 recipe
ingredient strings converted to 1.21 `{"item"}`/`{"tag"}` objects.

## Features carried over

- Epic and Legendary normal/auto tables: block, item, block entity, menu, screen,
  capabilities, creative tab, recipes, loot, pickaxe tag, Jade, JEI categories,
  transfer handlers and click areas. Epic catalyst and component.
- Tier inference up to 13x13 shaped and 169-ingredient shapeless recipes. JEI lists
  each recipe only in its own tier.
- Per-tier auto table craft time configs (`autoTable<Tier>CraftTime`, default 8 ticks),
  replacing upstream's single `autoTableTimeRequired`. Epic/Legendary energy = 16x.
- Auto table import takes the whole source stack per tick (as 1.20 Plus) and checks the
  insert energy cost before every item.
- Recipe Maker: first hotbar slot is the recipe output (count and optional data
  components), shapeless table recipes record their tier, 11x11/13x13 grids, and a
  third `KubeJS` creative variant that wraps the JSON in `ServerEvents.recipes`.
- `ServerboundContainerClickPacket` changed-slot limit raised from 128 to 256 so a
  full 13x13 click does not kick the client. Bytecode of NeoForge 21.1 shows the same
  `ByteBufCodecs.map(..., 128)` call in `<clinit>` as 26.1.2, so the same `@ModifyArg` is used.

## Supporting corrections

- Auto table `matchesSelectedRecipe` compares against the saved grid and table tier.
- Vanilla recipe fallback when saving an auto table recipe only applies to the Basic
  table with `useVanillaRecipes` enabled, matching the crafting logic.
- Recipe storage bounds the recipe index on save/load and refreshes the selected grid.
- `neoforge.mods.toml` Minecraft range is `[1.21.1,1.21.2)` (upstream had `[1.21,1.21.1)`).

## Validation boundary

Static checks: no conflict markers, no 26.1-only API names, all resource JSON and
the mod TOML parse, every new blockstate/model/texture reference resolves, required
lang keys present. `gradlew build` with JDK 21 succeeds (2026-11-07; only upstream's existing
deprecation warnings). Not yet run in game: the mixin application, the new screens,
11x11/13x13 recipes, JEI transfer/click areas and automatic import still need a playtest.
