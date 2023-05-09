package javacode.server.springelectronriftstatisticswebapp;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javacode.server.springelectronriftstatisticswebapp.model.ItemData;
import javacode.server.springelectronriftstatisticswebapp.model.ItemDescData;
import javacode.server.springelectronriftstatisticswebapp.model.ItemStatsData;
import javacode.server.springelectronriftstatisticswebapp.model.SectionData;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.staticdata.item.InventoryDataStats;
import no.stelar7.api.r4j.pojo.lol.staticdata.item.Item;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HashExample {
    private static final String INSERT_IMAGE = "UPDATE users SET accountimage = ? WHERE ID = ?";
    private static final String SELECT_IMAGE_BY_NAME = "SELECT AccountImage FROM users WHERE ID = ?";

    public static void main (String[] args) throws SQLException, IOException, TemplateException {
        Configuration cfg;

        cfg = new Configuration(Configuration.VERSION_2_3_31);

        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File("C:\\Users\\User1\\Documents\\GitHub\\SpringElectronRiftStatisticsWebApp\\src\\main\\resources\\templates"));
        cfg.setTemplateLoader(fileTemplateLoader);

        String html;
        Template template;
        template = cfg.getTemplate("unloggedItemList.ftl");
        Map<String, Object> data = new HashMap<>();

        final R4J r4J = new R4J(SecretFile.CREDS);
        DDragonAPI api = r4J.getDDragonAPI();
        Supplier<FileSystemCacheProvider> fileCache = () -> new FileSystemCacheProvider();
        DataCall.setCacheProvider(fileCache.get());

        MatchListBuilder builder = new MatchListBuilder();

        Summoner sum = Summoner.byPUUID(LeagueShard.UNKNOWN, "xU1ajEqoM2-a8escGrjQUmRahpa_pJU-WWxeDruOhmJ8SWZBsUW_l7YXaXgvMQtPzcQuC9aUBeBcCA");
        builder = builder.withPuuid(sum.getPUUID()).withPlatform(sum.getPlatform());
        List<LeagueEntry> league = sum.getLeagueEntry();

        Map<Integer, Item> items = api.getItems();
        ArrayList<String> itemListStarterItems = new ArrayList<>(List.of("Doran's Shield", "Doran's Blade", "Doran's Ring", "Dark Seal", "Cull", "Scorchclaw Pup", "Gustwalker Hatchling", "Mosstomper Seedling", "Guardian's Horn", "Guardian's Orb", "Guardian's Blade", "Guardian's Hammer", "Spellthief's Edge", "Steel Shoulderguards", "Relic Shield", "Spectral Sickle", "Tear of the Goddess"));
        ArrayList<String> itemListStarterItemsIDs = new ArrayList<>(List.of("1054", "1055", "1056", "1082", "1083", "1101", "1102", "1103", "2051", "3070", "3112", "3177", "3184", "3850", "3854", "3858", "3862"));

        ArrayList<String> itemListBoots = new ArrayList<>(List.of("Boots", "Slightly Magical Footwear", "Berserker's Greaves", "Boots of Swiftness", "Sorcerer's Shoes", "Plated Steelcaps", "Mercury's Treads", "Mobility Boots", "Ionian Boots of Lucidity"));
        ArrayList<String> itemListBootsIDs = new ArrayList<>(List.of("1001", "2422", "3006", "3009", "3020", "3047", "3111", "3117", "3158"));

        ArrayList<String> itemListWards = new ArrayList<>(List.of("Scarecrow Effigy", "Stealth Ward", "Farsight Alteration", "Oracle Lens"));
        ArrayList<String> itemListWardsIDs = new ArrayList<>(List.of("3330", "3340", "3363", "3364"));

        ArrayList<String> itemListConsumables = new ArrayList<>(List.of("Eye of the Herald", "Health Potion", "Refillable Potion", "Corrupting Potion", "Total Biscuit of Everlasting Will", "Poro-Snax", "Control Ward", "Elixir of Iron", "Elixir of Sorcery", "Elixir of Wrath", "Minion Dematerializer", "Your Cut", "Stopwatch", "Broken Stopwatch"));
        ArrayList<String> itemListConsumablesIDs = new ArrayList<>(List.of("3513", "2003", "2010", "2031", "2033", "2052", "2055", "2138", "2139", "2140", "2403", "2420", "2421", "3400"));

        ArrayList<String> itemListBasicItems = new ArrayList<>(List.of("Faerie Charm", "Rejuvenation Bead", "Cloak of Agility", "Blasting Wand", "Sapphire Crystal", "Ruby Crystal", "Cloth Armor", "Null-Magic Mantle", "Long Sword", "Pickaxe", "B. F. Sword", "Dagger", "Amplifying Tome", "Needlessly Large Rod", "Sheen"));
        ArrayList<String> itemListBasicItemsIDs = new ArrayList<>(List.of("1004", "1006", "1018", "1026", "1027", "1028", "1029", "1033", "1036", "1037", "1038", "1042", "1052", "1058", "3057"));

        ArrayList<String> itemListEpicItems = new ArrayList<>(List.of("Giant's Belt", "Chain Vest", "Recurve Bow", "Vampiric Scepter", "Negatron Cloak", "Kircheis Shard", "Last Whisper", "Glacial Buckler", "Phage", "Hearthbound Axe", "Winged Moonplate", "Kindlegem", "Bramble Vest", "Tiamat", "Warden's Mail", "Zeal", "Aegis of the Legion", "Fiendish Codex", "Aether Wisp", "Forbidden Idol", "Caulfield's Warhammer", "Serrated Dirk", "Watchful Wardstone", "Quicksilver Sash", "Hextech Alternator", "Seeker's Armguard", "Hexdrinker", "Spectre's Cowl", "Crystalline Bracer", "Lost Chapter", "Catalyst of Aeons", "Oblivion Orb", "Blighting Jewel", "Verdant Barrier", "Leeching Leer", "Bandleglass Mirror", "Ironspike Whip", "Bami's Cinder", "Noonquiver", "Rageknife", "Executioner's Calling", "Targon's Buckler", "Frostfang", "Harrowing Crescent", "Runesteel Spaulders"));
        ArrayList<String> itemListEpicItemsIDs = new ArrayList<>(List.of("1011", "1031", "1043", "1053", "1057", "2015", "3024", "3035", "3044", "3051", "3066", "3067", "3076", "3077", "3082", "3086", "3105", "3108", "3113", "3114", "3123", "3133", "3134", "3140", "3145", "3155", "3191", "3211", "3801", "3802", "3803", "3851", "3855", "3859", "3863", "3916", "4630", "4632", "4635", "4638", "4642", "6029", "6660", "6670", "6677"));

        ArrayList<String> itemListLegendaryItems = new ArrayList<>(List.of("Abyssal Mask", "Edge of Night", "Warmog's Armor", "Mercurial Scimitar", "Serpent's Fang", "Shadowflame", "Winter's Approach", "Archangel's Staff", "Void Staff", "Mikael's Blessing", "Anathema's Chains", "Morellonomicon", "Essence Reaver", "Serylda's Grudge", "Fimbulwinter", "Ardent Censer", "Force of Nature", "Mortal Reminder", "Shard of True Ice", "Wit's End", "Zhonya's Hourglass", "Spirit Visage", "Navori Quickblades", "Spear Of Shojin", "Nashor's Tooth", "Zeke's Convergence", "Muramana", "Youmuu's Ghostblade", "Silvermere Dawn", "Axiom Arc", "Guardian Angel", "Banshee's Veil", "Gargoyle Stoneplate", "Frozen Heart", "Black Cleaver", "Staff of Flowing Water", "Sterak's Gage", "Pauldrons of Whiterock", "Guinsoo's Rageblade", "Phantom Dancer", "Stormrazor", "Sunfire Aegis", "Rabadon's Deathcap", "Horizon Focus", "Black Mist Scythe", "Blade of The Ruined King", "Hullbreaker", "Randuin's Omen", "The Collector", "Rapid Firecannon", "Infinity Edge", "Bloodthirster", "Thornmail", "Titanic Hydra", "Ravenous Hydra", "Bulwark of the Mountain", "Knight's Vow", "Chempunk Chainsword", "Lich Bane", "Redemption", "Turbo Chemtank", "Chemtech Putrifier", "Cosmic Drive", "Dead Man's Plate", "Death's Dance", "Demonic Embrace", "Lord Dominik's Regards", "Manamune", "Maw of Malmortius", "Mejai's Soulstealer", "Runaan's Hurricane", "Rylai's Crystal Scepter", "Seraph's Embrace", "Umbral Glaive", "Vigilant Wardstone"));
        ArrayList<String> itemListLegendaryItemsIDs = new ArrayList<>(List.of("3003", "3004", "3011", "3026", "3031", "3033", "3036", "3040", "3041", "3042", "3046", "3050", "3053", "3065", "3068", "3071", "3072", "3074", "3075", "3083", "3085", "3089", "3091", "3094", "3095", "3100", "3102", "3107", "3109", "3110", "3115", "3116", "3119", "3121", "3124", "3135", "3139", "3142", "3143", "3153", "3156", "3157", "3161", "3165", "3179", "3181", "3193", "3222", "3504", "3508", "3742", "3748", "3814", "3853", "3857", "3860", "3864", "4401", "4628", "4629", "4637", "4643", "4645", "6035", "6333", "6609", "6616", "6664", "6675", "6676", "6694", "6695", "6696", "8001", "8020"));

        ArrayList<String> itemListMythicItems = new ArrayList<>(List.of("Crown of the Shattered Queen", "Luden's Tempest", "Locket of the Iron Solari", "Liandry's Anguish", "Iceborn Gauntlet", "Immortal Shieldbow", "Jak'Sho, The Protean", "Kraken Slayer", "Imperial Mandate", "Hextech Rocketbelt", "Trinity Force", "Divine Sunderer", "Night Harvester", "Heartsteel", "Shurelya's Battlesong", "Moonstone Renewer", "Goredrinker", "Stridebreaker", "Duskblade of Draktharr", "Rod of Ages", "Eclipse", "Everfrost", "Radiant Virtue", "Prowler's Claw", "Evenshroud", "Riftmaker", "Galeforce"));
        ArrayList<String> itemListMythicItemsIDs = new ArrayList<>(List.of("2065", "3001", "3078", "3084", "3152", "3190", "4005", "4633", "4636", "4644", "6617", "6630", "6631", "6632", "6653", "6655", "6656", "6657", "6662", "6665", "6667", "6671", "6672", "6673", "6691", "6692", "6693"));

        ArrayList<String> itemListOrnnItems = new ArrayList<>(List.of("Caesura", "Eye of Luden", "Reliquary of the Golden Dawn", "Liandry's Lament", "Frozen Fist", "Bloodward", "The Unspoken Parasite", "Wyrmfallen Sacrifice", "Seat of Command", "Upgraded Aeropack", "Infinity Force", "Deicide", "Vespertide", "Leviathan", "Shurelya's Requiem", "Starcaster", "Ceaseless Hunger", "Dreamshatter", "Draktharr's Shadowcarver", "Convergence", "Syzygy", "Eternal Winter", "Primordial Dawn", "Sandshrike's Claw", "Equinox", "Icathia's Curse", "Typhoon"));
        ArrayList<String> itemListOrnnItemsIDs = new ArrayList<>(List.of("7000", "7001", "7002", "7005", "7006", "7007", "7008", "7009", "7010", "7011", "7012", "7013", "7014", "7015", "7016", "7017", "7018", "7019", "7020", "7021", "7022", "7023", "7024", "7025", "7026", "7027", "7028"));

        ArrayList<String> itemListChampionItems = new ArrayList<>(List.of("Kalista's Black Spear", "Fire at Will", "Death's Daughter", "Raise Morale"));
        ArrayList<String> itemListChampionItemsIDs = new ArrayList<>(List.of("3600", "3901", "3902", "3903"));

        ArrayList<SectionData> sections = new ArrayList<>();

        ArrayList<String> itemDepth1 = new ArrayList<>();

        ArrayList<String> s2 = new ArrayList<>();
        for (Item item : items.values()) {
            String name = item.getName();
            if (name.contains("<br>")) {
                name = name.replaceAll("<br>", "");
                name = name.replaceAll("<.+?>", "");
            }
            InventoryDataStats ivdata = item.getStats();
            ArrayList<String> tags = new ArrayList<>();
            for (String tag : item.getTags()) {
                if (! itemDepth1.contains(tag)) {
                    tags.add(tag);
                }
            }
            itemDepth1.addAll(tags);

            ArrayList<String> stats = new ArrayList<>();
            stats = new ArrayList<>(Arrays.asList(item.getSanitizedDescription().split("\\n")));
            Iterator<String> it = stats.iterator();
            while (it.hasNext()) {
                String x = it.next();
                String[] x1 = x.split(" ", 2);
                String x2 = x1[x1.length - 1];
                if (! s2.contains(x2) && x.length() < 30) {
                    s2.add(x2);
                }
            }
        }

        sections.add(SeparateStats(api, itemListStarterItemsIDs, false, "Starter items"));
        sections.add(SeparateStats(api, itemListWardsIDs, false, "Wards"));
        sections.add(SeparateStats(api, itemListConsumablesIDs, false, "Consumables"));
        sections.add(SeparateStats(api, itemListBootsIDs, false, "Boots"));
        sections.add(SeparateStats(api, itemListBasicItemsIDs, false, "Basic items"));
        sections.add(SeparateStats(api, itemListEpicItemsIDs, false, "Epic items"));
        sections.add(SeparateStats(api, itemListLegendaryItemsIDs, false, "Legendary items"));
        sections.add(SeparateStats(api, itemListMythicItemsIDs, false, "Mythic items"));
        sections.add(SeparateStats(api, itemListOrnnItemsIDs, false, "Ornn items"));
        sections.add(SeparateStats(api, itemListChampionItemsIDs, true, "Champion items"));

        data.put("sectionList", sections);

        StringWriter out = new StringWriter();
        template.process(data, out);
        html = out.toString();

        FileWriter writer = new FileWriter("itemList.html");
        writer.write(html);
        writer.close();

    }

    private static SectionData SeparateStats (DDragonAPI api, ArrayList<String> itemListIDs, boolean isChampionItems, String sectionType) {
        SectionData section = new SectionData();
        section.setName(sectionType);
        ArrayList<ItemData> items = new ArrayList<>();
        for (String s : itemListIDs) {
            Item item = api.getItem(Integer.parseInt(s));
            ItemData itemData = new ItemData();
            ItemStatsData itemStatsData = new ItemStatsData();
            ItemDescData itemDescData = new ItemDescData();
            String name = item.getName();
            if (name.contains("Spear Of")) {
                name = name;
            }
            Integer type = 1;
            String price = item.getGold().getTotal() + "g";
            if (isChampionItems) {
                name = name.replaceAll("<br>", "-");
                name = name.replaceAll("<.+?>", "");
                if (name.contains("-")) {
                    String[] x = name.split("-");
                    name = x[0];
                    price = x[1];
                    type = 2;
                }
            }
            itemData.setName(name);
            itemData.setGoldType(type);
            itemData.setPrice(price);
            itemData.setID(String.valueOf(item.getId()));
            String [] x = item.getDescription().split("</stats>");
            for (int i = 0; i < x.length; i++) {
                x[i] = x[i].replaceAll("<passive>", ":=:");
                x[i] = x[i].replaceAll("<br>", "\n");
                x[i] = x[i].replaceAll("<.+?>", "");
            }
            String stats = "";
            String passives = "";
            if (x[0].isBlank() && itemData.getName().equals("Fire at Will")) {
                passives = item.getPlaintext();
                itemDescData = new ItemDescData(passives);
                itemData.addItemDescData(itemDescData);
            }else if (x.length == 2 && !x[1].isBlank() || !x[0].isBlank()) {
                stats = x[x.length - 2];
                passives = x[x.length-1];
                String [] y = stats.split("\\n");
                for (String str : y) {
                    if (!str.isBlank() || !str.isEmpty()) {
                        itemStatsData = new ItemStatsData(str);
                        itemData.addItemStatData(itemStatsData);
                    }
                }
                String [] z = passives.split("\\n");
                for (String str : z) {
                    if (!str.isBlank() || !str.isEmpty()) {
                        String [] str1 = str.split(":=:");
                        for (String value : str1) {
                            String [] str2 = value.split(":");
                            if (str2.length <= 2 && !value.isBlank()) {
                                itemDescData = new ItemDescData(value);
                                itemData.addItemDescData(itemDescData);
                            }else if (str2.length % 2 == 0 && !value.isBlank()) {
                                for (String value2 : str2) {
                                    itemDescData = new ItemDescData(value2);
                                    itemData.addItemDescData(itemDescData);
                                }
                            }
                        }
                    }
                }
            }else if (x.length == 1 && !x[0].isBlank()){
                stats = x[x.length - 1];
                System.out.println("Stats:" + "\n" + stats);
            }
            items.add(itemData);
        }
        section.setItems(items);
        return section;
    }



        /*
        Move speed, base mana regen, base health regen, health, critical strike chance, ability power, mana, health, armor, magic resist, attack damage, attack speed, life steal, Ability Haste, Heal and Shield Power, Magic Penetration, Armor Penetration, Tenacity,
         */


//        StringWriter out = new StringWriter();
//        template.process(data, out);
//        html = out.toString();

//        FileWriter writer = new FileWriter("Prueba.html");
//        writer.write(html);
//        writer.close();






//        String soloQtier = "";
//        String soloQtierShort = "";
//        String soloQlp = "";
//        String soloQgames = "";
//        String soloQwinrate = "";
//        String flexQtier = "";
//        String flexQtierShort = "";
//        String flexQlp = "";
//        String flexQgames = "";
//        String flexQwinrate = "";
//        Integer wins = 0;
//        Integer losses = 0;
//        for (LeagueEntry entry : league) {
//            if (entry.getQueueType() == GameQueueType.RANKED_SOLO_5X5) {
//                soloQtier = entry.getTierDivisionType().prettyName();
//                soloQtierShort = entry.getTierDivisionType().prettyName().split(" ")[0].toLowerCase();
//                soloQlp = String.valueOf(entry.getLeaguePoints());
//                soloQgames = String.valueOf(entry.getWins() + entry.getLosses());
//                soloQwinrate = String.valueOf((int) ((double) entry.getWins() / (entry.getWins() + entry.getLosses()) * 100));
//            } else if (entry.getQueueType() == GameQueueType.RANKED_FLEX_SR) {
//                flexQtier = entry.getTierDivisionType().prettyName();
//                flexQtierShort = entry.getTierDivisionType().prettyName().split(" ")[0].toLowerCase();
//                flexQlp = String.valueOf(entry.getLeaguePoints());
//                flexQgames = String.valueOf(entry.getWins() + entry.getLosses());
//                flexQwinrate = String.valueOf((int) ((double) entry.getWins() / (entry.getWins() + entry.getLosses()) * 100));
//            }
//        }
//        if (soloQtier.equals("")) {
//            soloQtier = "Unranked";
//            soloQtierShort = "";
//            soloQlp = "";
//            soloQgames = "";
//            soloQwinrate = "";
//        }
//        if (flexQtier.equals("")) {
//            flexQtier = "Unranked";
//            flexQtierShort = "";
//            flexQlp = "";
//            flexQgames = "";
//            flexQwinrate = "";
//        }
//
//        MatchListBuilder builderMT = new MatchListBuilder();
//        List<String> last20 = builder.withCount(20).get();
//        MatchBuilder mb = new MatchBuilder(sum.getPlatform());
//        LOLMatch m;
//        HashMap<String, Integer> participants = new HashMap<>();
//        HashMap<ChampionData, Integer> champions = new HashMap<>();
//        HashMap<StaticChampion, Integer> championsStatic = new HashMap<>();
//        ArrayList<MatchData> matchData = new ArrayList<>();
//        for (String s : last20) {
//            m = mb.withId(s).getMatch();
//            MatchData md = new MatchData();
//            if (Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).compareTo(Duration.ofMinutes(4)) > 0) {
//                for (MatchParticipant p : m.getParticipants()) {
//                    if (!p.getSummonerId().equals(sum.getSummonerId())) {
//                        if (!participants.containsKey(p.getSummonerId())) {
//                            participants.put(p.getSummonerId(), 1);
//                        } else {
//                            participants.put(p.getSummonerId(), participants.get(p.getSummonerId()) + 1);
//                        }
//                    }else {
//                        md.setMatchId(s);
//                        String name = p.getChampionName();
//                        md.setChampName(p.getChampionName());
//                        md.setChampID(String.valueOf(p.getChampionId()));
//                        String gametype = m.getQueue().prettyName();
//                        switch (gametype) {
//                            case "5v5 Dynamic Queue":
//                                gametype = "Normal queue";
//                                break;
//                            case "5v5 Dynamic Ranked Solo Queue":
//                                gametype = "Ranked Solo";
//                                break;
//                            case "5v5 Ranked Flex Queue":
//                                gametype = "Ranked Flex";
//                                break;
//                        }
//                        md.setGameType(gametype);
//                        ZonedDateTime createdate = m.getMatchCreationAsDate();
//
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
//                        long noOfDaysBetween = ChronoUnit.DAYS.between(createdate, ZonedDateTime.now());
//                        if (noOfDaysBetween < 1) {
//                            md.setGameDate("hace " + ChronoUnit.HOURS.between(createdate, ZonedDateTime.now()) + " horas");
//                        }else if (noOfDaysBetween < 10) {
//                            if (noOfDaysBetween == 1) {
//                                md.setGameDate("hace " + noOfDaysBetween + " día");
//                            } else {
//                                md.setGameDate("hace " + noOfDaysBetween + " días");
//                            }
//                        } else {
//                            md.setGameDate(createdate.format(formatter));
//                        }
//                        double kills = p.getKills();
//                        double deaths = p.getDeaths();
//                        double assists = p.getAssists();
//
//                        if (p.getDeaths() == 0) {
//                            String KDA = String.valueOf(kills + assists);
//                            BigDecimal bd = new BigDecimal(KDA).setScale(1, RoundingMode.HALF_EVEN);
//                            md.setKDA(bd.doubleValue() + " KDA");
//                        } else {
//                            String KDA = String.valueOf((kills+assists)/deaths);
//                            BigDecimal bd = new BigDecimal(KDA).setScale(1, RoundingMode.HALF_EVEN);
//                            md.setKDA(bd.doubleValue() + " KDA");
//                        }
//                        md.setLongKDA((int) kills + " / " + (int) deaths + " / " + (int) assists);
//                        String csMin = String.valueOf((int) ((p.getTotalMinionsKilled() + p.getNeutralMinionsKilled()) / Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).toMinutes()));
//                        md.setCsMin(csMin + " CS/min");
//                        md.setCsTotal(p.getTotalMinionsKilled() + p.getNeutralMinionsKilled() + " CS");
//
//                        RoleType rt = p.getRole();
//                        if (!gametype.equals("ARAM")) {
//                            switch (rt) {
//                                case CARRY -> md.setMatchRole("ADC");
//                                case SUPPORT -> md.setMatchRole("SUPPORT");
//                                case SOLO -> md.setMatchRole("TOP");
//                                case NONE -> md.setMatchRole("JUNGLE");
//                                case DUO -> md.setMatchRole("MID");
//                            }
//                        }else {
//                            md.setMatchRole("");
//                        }
//                        if (p.didWin()) {
//                            wins++;
//                            md.setGameResult("VICTORY");
//                        }else {
//                            losses++;
//                            md.setGameResult("DEFEAT");
//                        }
//                        StaticChampion champ = api.getChampion(p.getChampionId());
//                        if (!championsStatic.containsKey(champ)) {
//                            championsStatic.put(champ, 1);
//                            ChampionData champData = new ChampionData();
//                            String nam = champ.getName();
//                            nam = nam.replace(" ", "");
//                            champData.setName(nam);
//                            champData.setID(String.valueOf(champ.getId()));
//                            if (p.didWin()) {
//                                champData.addWin();
//                            } else {
//                                champData.addLoss();
//                            }
//                            champData.updateWinrate();
//                            champData.hasAppeared();
//
//                            champData.setKDA(p.getKills(), p.getDeaths(), p.getAssists());
//                            champions.put(champData, 1);
//                        }else {
//                            championsStatic.put(champ, championsStatic.get(champ) + 1);
//                            ChampionData champData;
//                            for (ChampionData c : champions.keySet()) {
//                                if (c.getID().equals(String.valueOf(champ.getId()))) {
//                                    champData = c;
//                                    if (p.didWin()) {
//                                        champData.addWin();
//                                    } else {
//                                        champData.addLoss();
//                                    }
//                                    champData.updateWinrate();
//                                    champData.hasAppeared();
//
//                                    champData.setKDA(p.getKills(), p.getDeaths(), p.getAssists());
//                                    champions.put(champData, champions.get(champData) + 1);
//                                }
//                            }
//                        }
//                        matchData.add(md);
//                    }
//                }
//            }
//        }
//
//        List<SummonerData> orderedSummonersList = new ArrayList<>();
//        for (String mp : participants.keySet()) {
//            if (mp != null) {
//                SummonerData sd = new SummonerData();
//                sd.setName(mp);
//                sd.setGamesPlayedTogether(String.valueOf(participants.get(mp)));
//                orderedSummonersList.add(sd);
//            }
//        }
//        Collections.sort(orderedSummonersList);
//
//        List<ChampionData> orderedChampionsList = new ArrayList<>();
//        for (Map.Entry<ChampionData, Integer> entry : champions.entrySet()) {
//            orderedChampionsList.add(entry.getKey());
//        }
//        Collections.sort(orderedChampionsList);
//
//        ArrayList<SummonerData> mostPlayedWithSummoners = new ArrayList<>();
//        int count = 0;
//        for (SummonerData entry : orderedSummonersList) {
//            if (count >= 4) {
//                break;
//            }
//            Summoner summ = Summoner.bySummonerId(LeagueShard.EUW1, entry.getName());
//            entry.setImgID(String.valueOf(summ.getProfileIconId()));
//            entry.setName(summ.getName());
//            mostPlayedWithSummoners.add(entry);
//            count++;
//        }
//
//        ArrayList<ChampionData> mostPlayedChampions = new ArrayList<>();
//        ArrayList<ChampionData> lastChampions = new ArrayList<>();
//        count = 0;
//        for (ChampionData entry : orderedChampionsList) {
//            if (count < 3) {
//                lastChampions.add(entry);
//            }
//            if (count >= 6) {
//                break;
//            }
//            mostPlayedChampions.add(entry);
//            count++;
//        }
//
//        if (!soloQlp.equals("")) {
//            soloQlp = soloQlp + " LP";
//        }
//        if (!flexQlp.equals("")) {
//            flexQlp = flexQlp + " LP";
//        }
//        if (!soloQwinrate.equals("")) {
//            soloQwinrate = soloQwinrate + "%";
//        }
//        if (!flexQwinrate.equals("")) {
//            flexQwinrate = flexQwinrate + "%";
//        }
//        if (!soloQgames.equals("")) {
//            soloQgames = soloQgames + " Games";
//        }
//        if (!flexQgames.equals("")) {
//            flexQgames = flexQgames + " Games";
//        }
//
//        data.put("profileLevel", sum.getSummonerLevel());
//        data.put("profileImageID", String.valueOf(sum.getProfileIconId()).replace(".", ""));
//        data.put("profileUsername", sum.getName());
//        data.put("isLinkedAccount", "this account is not linked yet");
//        data.put("soloQtier", soloQtier);
//        data.put("soloQlp", soloQlp);
//        data.put("soloQgames", soloQgames);
//        data.put("soloQwinrate", soloQwinrate);
//        data.put("flexQtier", flexQtier);
//        data.put("flexQlp", flexQlp);
//        data.put("flexQgames", flexQgames);
//        data.put("flexQwinrate", flexQwinrate);
//        data.put("soloQimage", soloQtierShort);
//        data.put("flexQimage", flexQtierShort);
//        data.put("championIndex", mostPlayedChampions);
//        data.put("summonerIndex", mostPlayedWithSummoners);
//        data.put("last20Games", wins + "W " + losses + "L");
//        data.put("last20index", lastChampions);
//        data.put("historyIndex", matchData);







//        TimelineBuilder tb = new TimelineBuilder(sum.getPlatform());
//
//        StringWriter sw = new StringWriter();
//        JsonWriter sb = new JsonWriter(sw);
//        sb.beginObject();
//
//        int i = 0;
//        for (String matchid : all) {
//            if (i++ > 10) break;
//            tb = tb.withId(matchid);
//            mb = mb.withId(matchid);
//
//            LOLMatch match1 = mb.getMatch();
//            LOLTimeline lolTimeline = tb.getTimeline();
//
//            List<TimelineFrameEvent> events = lolTimeline.getFrames().stream()
//                    .flatMap(frame -> frame.getEvents().stream())
//                    .collect(Collectors.toList());
//
//// Filtrar los eventos relacionados con el participante en cuestión (por ejemplo, participante con ID = 1)
//            List<TimelineFrameEvent> participantEvents = events.stream()
//                    .filter(event -> event.getParticipantId() == 1 && event.getType().equals(EventType.SKILL_LEVEL_UP))
//                    .collect(Collectors.toList());
//
//// Ordenar los eventos según su timestamp
//            Collections.sort(participantEvents, Comparator.comparingLong(TimelineFrameEvent::getTimestamp));
//
//// Determinar el orden en el que el participante subió sus habilidades
//            for (int j = 0; j < participantEvents.size(); j++) {
//                TimelineFrameEvent event = participantEvents.get(j);
////                System.out.println("El participante subió su habilidad " + event.getSkillSlot() + " en el evento número " + (j+1));
//            }


//            List<TimelineDamageData> wierdEntries = new ArrayList<>();
//            lolTimeline.getFrames().forEach(frame -> {
//                frame.getEvents().forEach(event -> {
//                    if (event.getType().equals(EventType.LEVEL_UP)) {
//                        event.
//                    }
//                }
//            });
//            if (wierdEntries.size() > 0) {
//                sb.name(tb.getID());
//                sb.beginArray();
//                for (TimelineDamageData wierdEntry : wierdEntries) {
//                    sb.jsonValue(Utils.getGson().toJson(wierdEntry));
//                }
//                sb.endArray();
//            }


//        sb.endObject();
//        sb.flush();
//
//        String output = Utils.getGson().toJson(new JsonParser().parse(sw.toString()));
//        Files.write(Paths.get("C:\\Users\\user1\\Desktop\\errors.json"), output.getBytes(StandardCharsets.UTF_8));



//        Configuration cfg;
//
//        cfg = new Configuration(Configuration.VERSION_2_3_31);
//
//        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File("C:\\Users\\User1\\Documents\\GitHub\\SpringElectronRiftStatisticsWebApp\\src\\main\\resources\\templates"));
//        cfg.setTemplateLoader(fileTemplateLoader);
//
//        String html;
//        Template template;
//        template = cfg.getTemplate("unloggedChampList.ftl");
//
//
//        Map<Integer, StaticChampion> list = api.getChampions();
//        Integer championNumber = list.size();
//        List<StaticChampion> champions = new ArrayList<>(list.values());
//        Map<String, Object> data = new HashMap<>();
//        Map<String, String> values = new HashMap<>();
//
//        for (int i = 0; i < championNumber; i++) {
//            StaticChampion champion = champions.get(i % championNumber);
//            values.put(champion.getName(), String.valueOf(champion.getId()));
//        }
//
//        List<Map.Entry<String, String>> listSorted = new ArrayList<>(values.entrySet());
//
//        listSorted.sort(new Comparator<Map.Entry<String, String>>() {
//            public int compare (Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                return o1.getKey().compareTo(o2.getKey());
//            }
//        });
//
//        Map<String, String> sortedMap = new LinkedHashMap<>();
//
//        for (Map.Entry<String, String> entry : listSorted) {
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//
//        data.put("championIndex", sortedMap);
//
//        StringWriter out = new StringWriter();
//        template.process(data, out);
//        html = out.toString();
//
//        FileWriter writer = new FileWriter("championList.html");
//        writer.write(html);
//        writer.close();



//        LazyList<String> matches = sum.getLeagueGames().getLazy();
//        MatchBuilder match = new MatchBuilder(sum.getPlatform(), matches.get(0));
//        TimelineBuilder timeline = new TimelineBuilder(sum.getPlatform());
//
//        LOLMatch matchlol = match.getMatch();
//        GameQueueType type = matchlol.getQueue();
//        System.out.println(type.commonName());
//        System.out.println(sum.getPlatform().getRealmValue());


//        Orianna.setDefaultRegion(Region.EUROPE_WEST);
//        Summoner summoner = Orianna.summonerNamed("YoSoyMiguel13").get();
//        MatchHistory.Builder match = summoner.matchHistory();
//        MatchHistory matchHistory = match.get();
//        Match match1 = matchHistory.get(0);
//        System.out.println(match1.getMode());

//        Image image = summoner.getProfileIcon().getImage();
//        String summonerImg = image.getURL();
//        String summonerName = summoner.getName();
//        String summonerRegion = summoner.getRegion().toString();
//
//        Map<String, String> values = new HashMap<>();
//        values.put("Img", summonerImg);
//        values.put("SummName", summonerName);
//        values.put("SummReg", summonerRegion);
//        StringSubstitutor sub = new StringSubstitutor(values);
//
//        String htmlFragment = "<li class=\"browserItem\">" +
//                "<a href=\"\" class=\"browserLink\">" +
//                "<div class=\"cardContainer\">" +
//                "<div class=\"browserCard\">" +
//                "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
//                "</div>" +
//                "<div class=\"cardPhoto\">" +
//                "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
//                "</div>" +
//                "</div>" +
//                "<span class=\"browserName\">" +
//                "<span class=\"browserSummName\">${SummName}</span>" +
//                "<span class=\"browserSummRegion\">${SummReg}</span>" +
//                "</span>" +
//                "</a>" +
//                "</li>";
//
//        String formattedHtml = sub.replace(htmlFragment);












//        HtmlFactory htmlFactory = HtmlFactory.getInstance();
//        String username = "Prueba";
//        String rutaArchivo = "C:\\Users\\guzma\\Downloads\\users-AccountImage.jpg";
//        Path archivoPath = Paths.get(rutaArchivo);
//
//        // Leer archivo
//        try {
//            byte[] archivoBytes = Files.readAllBytes(archivoPath);
////            String html = htmlFactory.loginPageAction(username, archivoBytes);
//        } catch (IOException e) {
//        }



//        // Datos de la imagen
//        String ID = "aa0cc086fc9a130e2dae80598f63b9f9da64da7506f1207b6a8e84272c0f2972";
//        String base64Image = "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";
//
//// Decodificar la imagen de base64 a bytes
//        byte[] imageBytesIso = Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.ISO_8859_1));
//
//// Guardar la imagen como un archivo temporal
//        File tempFile = new File("tempFile.jpg");
//        FileOutputStream fos = new FileOutputStream(tempFile);
//        fos.write(imageBytesIso);
//        fos.close();
//
//        // Crear un FileInputStream a partir del archivo temporal
//        FileInputStream fis = new FileInputStream(tempFile);
//
//// Conexión a la base de datos
//        String url = "jdbc:mysql://localhost:3306/riftstatistics";
//        String user = "root";
//        String password = "";
//        Connection conn = DriverManager.getConnection(url, user, password);
//
//// Insertar la imagen en la base de datos utilizando un InputStream
//        try (PreparedStatement statement = conn.prepareStatement(INSERT_IMAGE)) {
//            statement.setBinaryStream(1, fis, (int) tempFile.length());
//            statement.setString(2, ID);
//            statement.executeUpdate();
//        }
//
//// Cerrar el FileInputStream y borrar el archivo temporal
//        fis.close();
//        tempFile.delete();
//// Cerrar la conexión
//        conn.close();

}
