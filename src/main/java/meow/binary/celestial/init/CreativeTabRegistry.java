package meow.binary.celestial.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static meow.binary.celestial.Celestial.MODID;
import static meow.binary.celestial.init.ItemRegistry.EXAMPLE_ITEM;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> BLOCKS = create("celestial_blocks", Blocks.MAGENTA_GLAZED_TERRACOTTA::asItem);
    public static final RegistryObject<CreativeModeTab> ITEMS = create("celestial_items", EXAMPLE_ITEM);

    public static RegistryObject<CreativeModeTab> create(String id, Supplier<Item> icon) {
        return CREATIVE_MODE_TABS.register(id, () -> CreativeModeTab.builder()
                .icon(() -> icon.get().getDefaultInstance())
                .build());
    }
}
