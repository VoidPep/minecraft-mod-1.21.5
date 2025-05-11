package net.voidpep.template.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.voidpep.template.TemplateMod;

import java.util.function.Function;


public class ModItems {
    private static final int ONE_SECOND_IN_TICKS = 20;

    public static final ConsumableComponent CONSUMABLE_COMPONENT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.SPEED, ONE_SECOND_IN_TICKS * 30, 3), 1.0f))
            .build();
    public static final FoodComponent FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .build();

    public static final Item POG_CHAMP = register(
            "pog_champ",
            Item::new,
            new Item.Settings().food(FOOD_COMPONENT, CONSUMABLE_COMPONENT)
    );

    public static void registerModItems() {
        TemplateMod.LOGGER.info("Registering mod items for " + TemplateMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(POG_CHAMP));
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TemplateMod.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }
}
