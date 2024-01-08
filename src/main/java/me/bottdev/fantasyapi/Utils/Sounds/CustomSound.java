package me.bottdev.fantasyapi.Utils.Sounds;

public enum CustomSound {
    LONG_SWORD_DRAW("weapons:long_sword.draw"),
    LONG_SWORD_HIDE("weapons:long_sword.hide"),
    LONG_SWORD_ATTACK("weapons:long_sword.attack"),
    WEAPON_BLOCK_TYPE_1("weapons:weapon.block.type.1"),
    WEAPON_PARRY("weapons:weapon.parry.perfect"),
    STUN("fantasy:effect.stun"),
    OPEN_GATE("fantasy:other.open_hell_gate"),
    HORSE_WHISTLE("fantasy:other.horse_whistle");

    private final String id;

    CustomSound(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
