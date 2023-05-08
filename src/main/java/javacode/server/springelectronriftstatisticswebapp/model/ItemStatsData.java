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
        String[] statsArray = stats.split(" ");
        this.number = statsArray[0];
        this.type = statsArray[1];
        assignIconType();
    }

    private void assignIconType () {
        switch (type) {
            case "Ability Power" -> iconType = "Ability_power_icon";
            case "Armor" -> iconType = "Armor_icon";
            case "Armor Penetration" -> iconType = "Armor_penetration_icon";
            case "Attack Damage" -> iconType = "Attack_damage_icon";
            case "Attack Speed" -> iconType = "Attack_speed_icon";
            case "Ability Haste" -> iconType = "Cooldown_reduction_icon";
            case "Critical Strike Chance" -> iconType = "Critical_strike_chance_icon";
            // seguir por Heal and Shield power icon.
            case "Health" -> iconType = "Health_icon";
            case "Base Health Regen" -> iconType = "Health_regeneration_icon";
            case "Mana" -> iconType = "Mana_icon";
            case "ManaRegen" -> iconType = "Mana_regeneration_icon";
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
