package meow.binary.celestial.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static meow.binary.celestial.Celestial.MODID;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final BlockBehaviour.Properties BLOCK_PROPS = BlockBehaviour.Properties.of();
    public static final Item.Properties ITEM_PROPS = new Item.Properties();

    public static final RegistryObject<Block> EXAMPLE_BLOCK = create("example_block", () -> new Block(BLOCK_PROPS));

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        for (RegistryObject<? extends Block> block : BLOCKS.getEntries())
            ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<Block> create(String id, Supplier<? extends Block> block) {
        return BLOCKS.register(id, block);
    }
}
