package com.sirsquidly.veilings.init;

import com.sirsquidly.veilings.veilings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class VeilingsSounds
{
    private static List<SoundEvent> soundList = new ArrayList<SoundEvent>();

    public static SoundEvent ENTITY_VEILING_AGREE = soundReadyForRegister("entity.veiling.agree");
    public static SoundEvent ENTITY_VEILING_AMBIENT = soundReadyForRegister("entity.veiling.ambient");
    public static SoundEvent ENTITY_VEILING_AMBIENT_HAPPY = soundReadyForRegister("entity.veiling.ambient_happy");
    public static SoundEvent ENTITY_VEILING_AMBIENT_SAD = soundReadyForRegister("entity.veiling.ambient_sad");
    public static SoundEvent ENTITY_VEILING_DEATH = soundReadyForRegister("entity.veiling.death");
    public static SoundEvent ENTITY_VEILING_HURT = soundReadyForRegister("entity.veiling.hurt");
    public static SoundEvent ENTITY_VEILING_EAT = soundReadyForRegister("entity.veiling.eat");
    public static SoundEvent ENTITY_VEILING_MOOD_INCREASE = soundReadyForRegister("entity.veiling.mood_increase");
    public static SoundEvent ENTITY_VEILING_MOOD_DECREASE = soundReadyForRegister("entity.veiling.mood_decrease");
    public static SoundEvent ENTITY_VEILING_PANIC = soundReadyForRegister("entity.veiling.panic");
    public static SoundEvent ENTITY_VEILING_SHOOT = soundReadyForRegister("entity.veiling.shoot");
    public static SoundEvent ENTITY_VEILING_TANTRUM = soundReadyForRegister("entity.veiling.tantrum");

    public static SoundEvent ITEM_ATTACK_SCEPTRE_USE = soundReadyForRegister("item.command_sceptre.use");
    public static SoundEvent ITEM_VEILING_NAIL_USE = soundReadyForRegister("item.veiling_nail.use");
    public static SoundEvent ITEM_VEILING_OUTFIT_EQUIP = soundReadyForRegister("item.veiling_outfit.equip");
    public static SoundEvent ITEM_VEILING_OUTFIT_UNEQUIP_SHEARS = soundReadyForRegister("item.veiling_outfit.unequip_shears");

    public static SoundEvent ITEM_CALLING_BELL_USE1 = soundReadyForRegister("item.calling_bell.use1");
    public static SoundEvent ITEM_CALLING_BELL_USE2 = soundReadyForRegister("item.calling_bell.use2");
    public static SoundEvent ITEM_CALLING_BELL_USE3 = soundReadyForRegister("item.calling_bell.use3");

    public static SoundEvent ITEM_SPIRIT_DAGGER_USE = soundReadyForRegister("item.spirit_dagger.use");
    public static SoundEvent ITEM_SPIRIT_DAGGER_SHOOT = soundReadyForRegister("item.spirit_dagger.shoot");



    public static void registerSounds()
    { for (SoundEvent sounds : soundList) ForgeRegistries.SOUND_EVENTS.register(sounds); }

    private static SoundEvent soundReadyForRegister(String name)
    {
        ResourceLocation location = new ResourceLocation(veilings.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        soundList.add(event);

        return event;
    }
}