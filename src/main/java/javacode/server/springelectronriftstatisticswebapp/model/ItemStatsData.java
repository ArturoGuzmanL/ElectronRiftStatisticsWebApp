package javacode.server.springelectronriftstatisticswebapp.model;

public class ItemStatsData {
    String iconType;
    String number;
    String type;

    public ItemStatsData(String iconType, String number, String type) {
        this.iconType = iconType;
        this.number = number;
        this.type = type;
    }

    public ItemStatsData() {
    }

    public ItemStatsData (String stats) {
        String[] statsArray = stats.split(" ", 2);
        this.number = statsArray[0];
        this.type = statsArray[1];
        assignIconType();
    }

    private void assignIconType () {
        switch (type) {
            case "Ability Power" -> iconType = "Ability_power_icon";
            case "Armor" -> iconType = "Armor_icon";
            case "Armor Penetration", "Lethality" -> iconType = "Armor_penetration_icon";
            case "Attack Damage" -> iconType = "Attack_damage_icon";
            case "Attack Speed" -> iconType = "Attack_speed_icon";
            case "Ability Haste" -> iconType = "Cooldown_reduction_icon";
            case "Critical Strike Chance" -> iconType = "Critical_strike_chance_icon";
            case "Heal and Shield Power" -> iconType = "Heal_and_shield_power_icon";
            case "Health" -> iconType = "Health_icon";
            case "Base Health Regen" -> iconType = "Health_regeneration_icon";
            case "Life Steal" -> iconType = "Life_steal_icon";
            case "Magic Penetration" -> iconType = "Magic_penetration_icon";
            case "Magic Resist" -> iconType = "Magic_resistance_icon";
            case "Mana" -> iconType = "Mana_icon";
            case "Base Mana Regen" -> iconType = "Mana_regeneration_icon";
            case "Move Speed" -> iconType = "Movement_speed_icon";
            case "Omnivamp" -> iconType = "Omnivamp_icon";
            case "Tenacity" -> iconType = "Tenacity_icon";
            default -> iconType = "";
        }
    }

    public String getIconType() {
        return iconType;
    }
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
