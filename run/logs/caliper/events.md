# Event Analysis

This file contains an analysis on the various forge event busses. This first
table lists events that have listeners, in order of most listeners. Further
specifics on the listenrs. This data is anonymous, and is not automatically
submitted to any online service.

| Event Name                                   | Listener Count |
|----------------------------------------------|----------------|
| ConfigChangedEvent$OnConfigChangedEvent      | 3              |
| ColorHandlerEvent$Item                       | 2              |
| WorldEvent$Load                              | 2              |
| WorldEvent$Save                              | 2              |
| TickEvent$ClientTickEvent                    | 2              |
| GuiScreenEvent$InitGuiEvent$Post             | 1              |
| ChunkEvent$Unload                            | 1              |
| PlayerEvent$PlayerLoggedInEvent              | 1              |
| TextureStitchEvent$Pre                       | 1              |
| WorldEvent$Unload                            | 1              |
| AnvilUpdateEvent                             | 1              |
| EntityJoinWorldEvent                         | 1              |
| TickEvent$ServerTickEvent                    | 1              |
| LootTableLoadEvent                           | 1              |
| FMLNetworkEvent$ClientConnectedToServerEvent | 1              |


## ColorHandlerEvent$Item
| Owner           | Method                     | Location                                     | Priority | Source                                                     | RecieveCanceled |
|-----------------|----------------------------|----------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | registerItemHandlers       | net.minecraftforge.client.ForgeClientHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Veilings        | registerItemColourHandlers | com.sirsquidly.veilings.init.VeilingsItems   | normal   | creaturesfromdarkness-0.1.1.jar                            | false           |


## GuiScreenEvent$InitGuiEvent$Post
| Owner            | Method    | Location                   | Priority | Source                                                    | RecieveCanceled |
|------------------|-----------|----------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | onGuiInit | mezz.jei.input.MouseHelper | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## ChunkEvent$Unload
| Owner           | Method        | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|---------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onChunkUnload | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## PlayerEvent$PlayerLoggedInEvent
| Owner           | Method      | Location | Priority | Source                                                     | RecieveCanceled |
|-----------------|-------------|----------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | playerLogin | forge    | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## TextureStitchEvent$Pre
| Owner            | Method             | Location                           | Priority | Source                                                    | RecieveCanceled |
|------------------|--------------------|------------------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | handleTextureRemap | mezz.jei.startup.ProxyCommonClient | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## ConfigChangedEvent$OnConfigChangedEvent
| Owner            | Method          | Location                                                | Priority | Source                                                     | RecieveCanceled |
|------------------|-----------------|---------------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Veilings         | onConfigChanged | com.sirsquidly.veilings.config.Config$ConfigSyncHandler | normal   | creaturesfromdarkness-0.1.1.jar                            | false           |
| Minecraft Forge  | onConfigChanged | forge                                                   | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Had Enough Items | onConfigChanged | mezz.jei.startup.ProxyCommonClient                      | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar  | false           |


## WorldEvent$Unload
| Owner           | Method            | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-------------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onDimensionUnload | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## WorldEvent$Load
| Owner           | Method          | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-----------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Bookshelf       | onWorldLoaded   | net.darkhax.bookshelf.Bookshelf                | normal   | Bookshelf-1.12.2-2.3.590.jar                               | false           |
| Minecraft Forge | onDimensionLoad | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## AnvilUpdateEvent
| Owner     | Method        | Location                        | Priority | Source                       | RecieveCanceled |
|-----------|---------------|---------------------------------|----------|------------------------------|-----------------|
| Bookshelf | onAnvilUpdate | net.darkhax.bookshelf.Bookshelf | normal   | Bookshelf-1.12.2-2.3.590.jar | false           |


## EntityJoinWorldEvent
| Owner           | Method            | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-------------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onEntityJoinWorld | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## WorldEvent$Save
| Owner            | Method          | Location                                       | Priority | Source                                                     | RecieveCanceled |
|------------------|-----------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge  | onDimensionSave | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Had Enough Items | onWorldSave     | mezz.jei.startup.ProxyCommonClient             | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar  | false           |


## TickEvent$ServerTickEvent
| Owner           | Method       | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|--------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onServerTick | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## LootTableLoadEvent
| Owner    | Method     | Location                                    | Priority | Source                          | RecieveCanceled |
|----------|------------|---------------------------------------------|----------|---------------------------------|-----------------|
| Veilings | onLootLoad | com.sirsquidly.veilings.common.CommonEvents | normal   | creaturesfromdarkness-0.1.1.jar | false           |


## FMLNetworkEvent$ClientConnectedToServerEvent
| Owner            | Method                    | Location                           | Priority | Source                                                    | RecieveCanceled |
|------------------|---------------------------|------------------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | onClientConnectedToServer | mezz.jei.startup.ProxyCommonClient | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## TickEvent$ClientTickEvent
| Owner           | Method        | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|---------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Bookshelf       | onClientTick  | net.darkhax.bookshelf.Bookshelf                | normal   | Bookshelf-1.12.2-2.3.590.jar                               | false           |
| Minecraft Forge | checkSettings | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
