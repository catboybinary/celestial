package meow.binary.celestial.init;

import meow.binary.celestial.content.item.ExampleRelic;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static meow.binary.celestial.Celestial.MODID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = create("example_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_RELIC = create("example_relic", ExampleRelic::new);

    public static RegistryObject<Item> create(String id, Supplier<? extends Item> item) {
        return ITEMS.register(id, item);
    }
}
