# VH Table Fix audit and 26.1.2 implementation

## Published Forge 1.20.1 findings

The [public 6.1.9 artifact, CurseForge file 9062683](https://www.curseforge.com/minecraft/mc-mods/extended-crafting-expanded-reforked/files/9062683)
was inspected as an archive and with `javap`, without running Minecraft.

Artifact: `ExtendedCrafting-1.20.1-6.1.9.jar`.
SHA256: `94c16fbfcda302de5c6483c35e6edf97e313168ce952aed834d6fb3e4fa7e4b2`.

The shipped artifact contains the VH helper classes and `vhtablefix.mixins.json`,
but its manifest has no `MixinConfigs` registration and there is no refmap file.
No class references that configuration name and no `META-INF/services` entry
provides an alternative bootstrap in this JAR. The helper constructor only
prints `VHTableFix loaded`; that line does not prove the injection is active.

The published redirect also replaces the vanilla `limitValue` wrapper with an
unbounded allocation lambda. Its claimed 4096 cap is only used in a log message.
Every packet with over 128 changed slots produces a warning, including normal
large crafts if the mixin is separately activated.

These are confirmed artifact defects. The reported server incidents have no
available crash log, and their exact mod set/artifact is unknown. They have not
been conclusively attributed to these defects.

## Why taking a 13x13 result can trigger the symptom

The original restriction counts changed entries in the click packet. A full
13x13 craft can change 169 input slots plus output/player-inventory slots.
Recipes changing fewer entries can avoid the 128 limit. If the injection is
inactive, a decode error such as `Value N is larger than limit 128` can disconnect
the player before the server processes the click. Client/JVM crashes, recipe
callback exceptions, and watchdog failures require their own diagnosis.

The reviewed normal-table menu has output 0, inputs 1..169, and player slots
170..205 (206 total). The auto-table menu has preview 0, inputs 1..169, output
170, and player slots 171..206 (207 total). No off-by-one problem was found in
their shift-click ranges during this focused review.

## Corresponding protection in this NeoForge 26.1.2 branch

| Forge defect | 26.1.2 implementation |
| --- | --- |
| Missing packaged mixin registration | `META-INF/neoforge.mods.toml` declares `extendedcrafting.mixins.json` through `[[mixins]]`. |
| Missing Forge/SRG refmap | Minecraft 26.1.2 is unobfuscated; the mixin and invocation explicitly use `remap = false`. |
| Unbounded replacement factory | `@ModifyArg(index = 3)` changes only the `ByteBufCodecs.map` limit, retaining vanilla validation before allocation. |
| WARN on every large craft | No per-packet logging is added. |
| Old constructor injection | The required injection targets the new codec call in `<clinit>`. |

The finite 256 limit covers the largest added menu's 207 slots. The common mixin
applies on both client and dedicated server because the codec validates the map
count during both encoding and decoding. It does not copy the old Forge helper
or its constructor redirect into the new loader.

The official Mojang 26.1.2 client was verified against SHA1
`4e618f09a0c649dde3fdf829df443ce0b8831e65`. Static bytecode inspection confirms
the packet's four-argument `ByteBufCodecs.map(..., 128)` call and its bounded map
codec. These observations support the target choice, not a claim of successful
NeoForge runtime injection.

## Validation still required

No Gradle task, build, automated test, game client, or dedicated server has been
run for this port. Review the packaged TOML/config and verify actual mixin
application on both sides, then cover a full 13x13 craft, ordinary/Shift-click
output retrieval, JEI transfer, and rejection of counts above the finite limit.
The downloaded Forge and Mojang JARs are inspection inputs and are not included
in this repository.
