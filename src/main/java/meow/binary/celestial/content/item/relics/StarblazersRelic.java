package meow.binary.celestial.content.item.relics;

import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootCollections;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.NBTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;

import java.text.CompactNumberFormat;

public class StarblazersRelic extends RelicItem {
    public static final String TAG_MOONLIGHT = "moonlight";

    public StarblazersRelic() {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public RelicData constructDefaultRelicData() {
        return RelicData.builder()
                .abilities(AbilitiesData.builder()
                        .ability(AbilityData.builder("constellation_mode")
                                .active(CastType.TOGGLEABLE)
                                .stat(StatData.builder("movement_speed")
                                        .initialValue(0.75f, 1.25f)
                                        .upgradeModifier(UpgradeOperation.ADD, 0.15f)
                                        .formatValue(a -> MathUtils.round(a, 1))
                                        .build())
                                .stat(StatData.builder("moonlight_capacity")
                                        .initialValue(80f, 125f)
                                        .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2f)
                                        .formatValue(a -> MathUtils.round(a, 1))
                                        .build())
                                .maxLevel(5)
                                .build())
                        .ability(AbilityData.builder("sun_and_moon")
                                .requiredLevel(5)
                                .maxLevel(0)
                                .build())
                        .build())
                .leveling(new LevelingData(200, 5, 225))
                .loot(LootData.builder()
                        .entry(LootCollections.END)
                        .entry(LootCollections.SCULK)
                        .build())
                .build();
    }


    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player) || player.level().isClientSide)
            return;

        float maxCapacity = (float) getAbilityValue(stack, "constellation_mode", "moonlight_capacity");
        float moonlight = NBTUtils.getFloat(stack, TAG_MOONLIGHT, maxCapacity);
        //player.sendSystemMessage(Component.literal("Moonlight: " + moonlight));

        if (isAbilityTicking(stack, "constellation_mode")) moonlight -= 0.1f;

        int phase = player.level().getMoonPhase();
        if(player.level().isNight()) moonlight += 0.1f * (Math.abs(phase - 4) + 1f);

        moonlight = Mth.clamp(moonlight, 0f, maxCapacity);

        NBTUtils.setFloat(stack, TAG_MOONLIGHT, moonlight);
    }
}
