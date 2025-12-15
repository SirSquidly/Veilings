package com.sirsquidly.veilings.config;

/**
 * 	This is simply a static form of the config, for reference throughout the mod.
 */
public class ConfigCache
{
    /** Config for specific Veilings. */
    public static final int cus_basHth = Config.entity.custodian.baseHealth;
    public static final int cus_basAtk = Config.entity.custodian.baseAttackDamage;

    public static final int dft_basHth = Config.entity.deft.baseHealth;
    public static final int dft_basAtk = Config.entity.deft.baseAttackDamage;

    public static final int drm_basHth = Config.entity.dramatist.baseHealth;
    public static final int drm_basAtk = Config.entity.dramatist.baseAttackDamage;


    /** Mood */
    public static final boolean mod_trnWikEnb = Config.entity.mood.enableTurningWicked;
    public static final boolean mod_sunHatEnb = Config.entity.mood.enableSunlightDislike;
    public static final boolean mod_zomFerEnb = Config.entity.mood.enableZombieFear;
    public static final int mod_fstNamSft = Config.entity.mood.veilingNewName;
    public static final int mod_dthSft = Config.entity.mood.veilingSympathy;
    public static final int mod_mltSft = Config.entity.mood.veilingMultiply;

    /** Multiplying */
    public static final boolean mlt_mltEnb = Config.entity.multiply.enableMultiplying;
    public static final boolean mlt_spnEpo = Config.entity.multiply.enableSpawnExplosion;
    public static final boolean mlt_irtPntMod = Config.entity.multiply.spawnInheritsParentMood;
}