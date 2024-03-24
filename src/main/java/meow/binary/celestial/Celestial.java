package meow.binary.celestial;

import com.mojang.logging.LogUtils;
import meow.binary.celestial.init.BlockRegistry;
import meow.binary.celestial.init.CreativeTabRegistry;
import meow.binary.celestial.init.ItemRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(Celestial.MODID)
public class Celestial {

    public static final String MODID = "celestial";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Celestial() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.register();
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CreativeTabRegistry.BLOCKS.get())
            for (RegistryObject<Item> i : BlockRegistry.ITEMS.getEntries()) {
                if (i.get() instanceof BlockItem item) event.accept(item);
            }
        if (event.getTab() == CreativeTabRegistry.ITEMS.get())
            for (RegistryObject<Item> i : ItemRegistry.ITEMS.getEntries()) {
                if (!(i.get() instanceof BlockItem)) event.accept(i.get());
            }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
