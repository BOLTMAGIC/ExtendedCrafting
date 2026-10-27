# Expanded Plus 26.1.2 port

## Target and source provenance

- Target approved on 2026-10-27: Minecraft 26.1.2, NeoForge, Expanded Plus including
  both Epic 11x11 and Legendary 13x13 normal/automatic tables.
- Local branch: `port/expanded-plus-26.1.2`.
- PR history base: BOLTMAGIC/ExtendedCrafting `1.20`, commit
  `e4b4d774d9930232ec38ee7606c73eff8c4eff71`. The port is submitted as a source
  snapshot on that base, with the upstream 26.1 implementation provenance below.
  Bolt currently has no 26.1 target branch. Retarget the draft PR to a dedicated
  new-version branch before merging so that Forge 1.20 remains maintained.
- Vanilla/upstream base: BlakeBr0/ExtendedCrafting `26.1`, commit
  `e54a4a4170d990dbd49973afae07043d68f81a14` (Extended Crafting 8.0.3).
- Plus reference: adjacent `extendedcrafting/` checkout, commit
  `ebaa418bad0a720c04004d813e3bfe4f171de66e`, including the Legendary JEI click-area fix.
- Original block/item identifiers and artwork are preserved. Java implementations
  are adapted from the current upstream 26.1 classes, including the transfer API,
  ValueInput/ValueOutput, data components, and GUI extraction.
- Build target stays at upstream's NeoForge 26.1.2.71 and Cucumber 9.0.0.
  Initial fork version is 8.1.0; increment it before subsequent artifact builds.

## Features carried over

Both added tiers have block/item, block entity, menu, screen, capability,
creative-tab, crafting recipe, loot, item definition, and pickaxe-tag entries.
Epic materials, translated names, JEI categories/transfers/click areas, Jade
providers, and the larger Recipe Maker grids are included.

Tier inference supports sizes through 13x13 and shapeless recipes through 169
ingredients. JEI lists unrestricted recipes only in their smallest fitting tier.
All six auto tables default to 8 ticks per craft and expose the Plus per-tier
configuration keys. Import retains transaction rollback and checks the energy
cost for every item, rather than allowing a batch to overdraw its available power.
Legendary energy capacity retains Plus's existing 16x multiplier.

The Recipe Maker's datapack mode uses the first hotbar slot as output, carries
output count/data components, and writes the current tier for shapeless tables.
CraftTweaker remains disabled/stubbed as in the 26.1 upstream; its old Forge
integration is not represented as supported by this port.

## Supporting corrections

- Auto-table recipe saving uses TABLE recipes; upstream queried FLUX_CRAFTER.
- Selected recipe matching uses the saved grid and current table tier.
- Recipe storage keeps output counts, refreshes its selected grid, and bounds
  recipe indices read from storage.
- Added screens use current API calls and align result, progress, toggle, and
  ghost positions to their original Plus textures.
- The fork has its own display/archive identity and no upstream auto-update URL.

## Container-click evidence

The official Mojang 26.1.2 client was downloaded from the URL in the official
version manifest and verified against SHA1 `4e618f09a0c649dde3fdf829df443ce0b8831e65`.
JDK 25 `javap -c -p` of `ServerboundContainerClickPacket` shows that `<clinit>`
passes `128` as argument 3 to the four-argument `ByteBufCodecs.map` call.

The new mixin changes that one argument to `max(original, 256)`. This is sufficient
for the largest added menu (207 slots total) and retains a finite limit. It replaces
the old 1.20.1 constructor redirect, which targets an API absent in 26.1.2.
The mixin has one required injection. Bytecode inspection establishes the vanilla
call site, but actual NeoForge mixin application still requires runtime validation.
The inspected Mojang JAR is outside the repository and must not be committed.

## Validation boundary

Source/data review only: no Gradle task, build, GameTest, client, or dedicated
server has been run. The user/project rule requires separate authorization for
builds/tests. This is a source contribution; no JAR was installed or published.
Existing Forge 1.20.1 checkout was left unchanged.

Completed static checks: all 379 resource JSON files and the mod TOML parse;
added models/textures and imports within this mod resolve to existing files;
`git diff --check` reports no whitespace errors. These checks do not establish
Java compilation or working gameplay.

Before treating this port as playable:

1. Compile/package with JDK 25 and resolve any compiler/dependency issues.
2. Start a dedicated server and client to verify registry and mixin application.
3. Check all four new screens, including ghost slots and controls at practical GUI scales.
4. Check 11x11/13x13 shaped and shapeless recipes, exact tier restrictions,
   JEI transfer/click areas, shift-click, quick-craft, and a craft changing over 128 slots.
5. Check automatic import, energy exhaustion/zero cost, saved recipes after reload
   and break/place, output-full behavior, and multi-item recipe results.
6. Check recipes, drops, names, mining tools, Jade information, and Recipe Maker exports.

No compatibility with direct migration of 1.20.1 worlds/NBT is claimed.
