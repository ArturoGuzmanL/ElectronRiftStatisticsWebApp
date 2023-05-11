package javacode.server.springelectronriftstatisticswebapp.HtmlFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import javacode.server.springelectronriftstatisticswebapp.SecretFile;
import javacode.server.springelectronriftstatisticswebapp.model.ChampionData;
import javacode.server.springelectronriftstatisticswebapp.model.ItemData;
import javacode.server.springelectronriftstatisticswebapp.model.ItemDescData;
import javacode.server.springelectronriftstatisticswebapp.model.ItemStatsData;
import javacode.server.springelectronriftstatisticswebapp.model.MatchData;
import javacode.server.springelectronriftstatisticswebapp.model.SectionData;
import javacode.server.springelectronriftstatisticswebapp.model.SummonerData;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.RoleType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.staticdata.champion.StaticChampion;
import no.stelar7.api.r4j.pojo.lol.staticdata.item.InventoryDataStats;
import no.stelar7.api.r4j.pojo.lol.staticdata.item.Item;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;

public class HtmlFactory {
    private static final String DEFAULT_USER_IMAGE = "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";
    Configuration cfg;
    final R4J r4J = new R4J(SecretFile.CREDS);
    DDragonAPI api = r4J.getDDragonAPI();
    Supplier<FileSystemCacheProvider> fileCache= () -> new FileSystemCacheProvider();
    private static HtmlFactory instance;

    private HtmlFactory() throws IOException {
        DataCall.setCacheProvider(fileCache.get());
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(this.getClass().getResource("/templates").getPath()));
        cfg.setTemplateLoader(fileTemplateLoader);
    }

    public static synchronized HtmlFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new HtmlFactory();
        }
        return instance;
    }

    public String  loginPageAction (boolean logged, boolean initialization, User ... args) {
        if (logged) {
            try {
                User user = args[0];
                String html;
                Template template;
                String img = "data:image/jpeg;base64,";
                byte[] bytesImg = user.getAccountimage();
                String base64Img;
                if (bytesImg == null) {
                    base64Img = DEFAULT_USER_IMAGE;
                } else {
                    base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
                }
                img += base64Img;

                if (initialization) {
                    template = cfg.getTemplate("cleanLoggedIndex.ftl");
                }else {
                    template = cfg.getTemplate("loggedIndex.ftl");
                }
                Map<String, Object> data = new HashMap<>();
                data.put("Username", user.getUsername());
                data.put("UsernamePhoto", img);

                StringWriter out = new StringWriter();
                template.process(data, out);
                html = out.toString();

                return html;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            try {
                Template template;
                if (initialization) {
                    template = cfg.getTemplate("cleanUnloggedIndex.ftl");
                }else {
                 template = cfg.getTemplate("unloggedIndex.ftl");
                }
                StringWriter out = new StringWriter();
                template.process(null, out);
                String html = out.toString();

                return html;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String champList (boolean logged, User ... args) {
        try {
            String html;
            Template template;
            Map<String, Object> data = new HashMap<>();
            if (logged) {
                template = cfg.getTemplate("loggedChampList.ftl");
                User user = args[0];

                String img = "data:image/jpeg;base64,";
                byte[] bytesImg = user.getAccountimage();
                String base64Img;
                if (bytesImg == null) {
                    base64Img = DEFAULT_USER_IMAGE;
                }else {
                    base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
                }
                img += base64Img;

                data.put("Username", user.getUsername());
                data.put("UsernamePhoto", img);
            }else {
                template = cfg.getTemplate("unloggedChampList.ftl");
            }

            Map<Integer, StaticChampion> list = api.getChampions();
            Integer championNumber = list.size();
            List<StaticChampion> champions = new ArrayList<>(list.values());
            Map<String, String> values = new HashMap<>();

            for (int i = 0; i < championNumber; i++) {
                StaticChampion champion = champions.get(i % championNumber);
                values.put(champion.getName(), String.valueOf(champion.getId()));
            }

            List<Map.Entry<String, String>> listSorted = new ArrayList<>(values.entrySet());

            listSorted.sort(new Comparator<Map.Entry<String, String>>() {
                public int compare (Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });

            Map<String, String> sortedMap = new LinkedHashMap<>();

            for (Map.Entry<String, String> entry : listSorted) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            data.put("championIndex", sortedMap);


            StringWriter out = new StringWriter();
            template.process(data, out);
            html = out.toString();

            return html;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String summonerPage (boolean logged, String summonerPUUID, String leagueShard, User ... args) {
        try {
            String html;
            Template template;
            Map<String, Object> data = new HashMap<>();
            if (logged) {
                template = cfg.getTemplate("loggedSummonerProfile.ftl");
                User user = args[0];

                String img = "data:image/jpeg;base64,";
                byte[] bytesImg = user.getAccountimage();
                String base64Img;
                if (bytesImg == null) {
                    base64Img = DEFAULT_USER_IMAGE;
                } else {
                    base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
                }
                img += base64Img;

                data.put("Username", user.getUsername());
                data.put("UsernamePhoto", img);
            } else {
                template = cfg.getTemplate("unloggedSummonerProfile.ftl");
            }

            MatchListBuilder builder = new MatchListBuilder();
            Optional<LeagueShard> leagueS = LeagueShard.fromString(leagueShard);

            Summoner summoner = SummonerAPI.getInstance().getSummonerByPUUID(leagueS.get(), summonerPUUID);

            builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
            List<LeagueEntry> league = summoner.getLeagueEntry();
            String soloQtier = "";
            String soloQtierShort = "";
            String soloQlp = "";
            String soloQgames = "";
            String soloQwinrate = "";
            String flexQtier = "";
            String flexQtierShort = "";
            String flexQlp = "";
            String flexQgames = "";
            String flexQwinrate = "";
            Integer wins = 0;
            Integer losses = 0;
            for (LeagueEntry entry : league) {
                if (entry.getQueueType() == GameQueueType.RANKED_SOLO_5X5) {
                    soloQtier = entry.getTierDivisionType().prettyName();
                    soloQtierShort = entry.getTierDivisionType().prettyName().split(" ")[0].toLowerCase();
                    soloQlp = String.valueOf(entry.getLeaguePoints());
                    soloQgames = String.valueOf(entry.getWins() + entry.getLosses());
                    soloQwinrate = String.valueOf((int) ((double) entry.getWins() / (entry.getWins() + entry.getLosses()) * 100));
                } else if (entry.getQueueType() == GameQueueType.RANKED_FLEX_SR) {
                    flexQtier = entry.getTierDivisionType().prettyName();
                    flexQtierShort = entry.getTierDivisionType().prettyName().split(" ")[0].toLowerCase();
                    flexQlp = String.valueOf(entry.getLeaguePoints());
                    flexQgames = String.valueOf(entry.getWins() + entry.getLosses());
                    flexQwinrate = String.valueOf((int) ((double) entry.getWins() / (entry.getWins() + entry.getLosses()) * 100));
                }
            }
            if (soloQtier.equals("")) {
                soloQtier = "Unranked";
                soloQtierShort = "";
                soloQlp = "";
                soloQgames = "";
                soloQwinrate = "";
            }
            if (flexQtier.equals("")) {
                flexQtier = "Unranked";
                flexQtierShort = "";
                flexQlp = "";
                flexQgames = "";
                flexQwinrate = "";
            }

            MatchListBuilder builderMT = new MatchListBuilder();
            List<String> last20 = builder.withCount(20).get();
            MatchBuilder mb = new MatchBuilder(summoner.getPlatform());
            LOLMatch m;
            HashMap<String, Integer> participants = new HashMap<>();
            HashMap<ChampionData, Integer> champions = new HashMap<>();
            HashMap<StaticChampion, Integer> championsStatic = new HashMap<>();
            ArrayList<MatchData> matchData = new ArrayList<>();
            Integer matchError = 0;
            for (String s : last20) {
                try {
                    m = mb.withId(s).getMatch();
                    MatchData md = new MatchData();
                    if (Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).compareTo(Duration.ofMinutes(4)) > 0) {
                        for (MatchParticipant p : m.getParticipants()) {
                            if (! p.getSummonerId().equals(summoner.getSummonerId())) {
                                if (! participants.containsKey(p.getSummonerId())) {
                                    participants.put(p.getSummonerId(), 1);
                                } else {
                                    participants.put(p.getSummonerId(), participants.get(p.getSummonerId()) + 1);
                                }
                            } else {
                                md.setMatchId(s);
                                String name = p.getChampionName();
                                md.setChampName(p.getChampionName());
                                md.setChampID(String.valueOf(p.getChampionId()));
                                String gametype = m.getQueue().prettyName();
                                switch (gametype) {
                                    case "5v5 Dynamic Queue":
                                        gametype = "Normal queue";
                                        break;
                                    case "5v5 Dynamic Ranked Solo Queue":
                                        gametype = "Ranked Solo";
                                        break;
                                    case "5v5 Ranked Flex Queue":
                                        gametype = "Ranked Flex";
                                        break;
                                }
                                md.setGameType(gametype);
                                ZonedDateTime createdate = m.getMatchCreationAsDate();

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                                long noOfDaysBetween = ChronoUnit.DAYS.between(createdate, ZonedDateTime.now());
                                long noOfHoursBetween = ChronoUnit.HOURS.between(createdate, ZonedDateTime.now());
                                if (noOfHoursBetween < 1) {
                                    md.setGameDate("hace " + ChronoUnit.MINUTES.between(createdate, ZonedDateTime.now()) + " minutos");
                                } else if (noOfDaysBetween < 1) {
                                    md.setGameDate("hace " + ChronoUnit.HOURS.between(createdate, ZonedDateTime.now()) + " horas");
                                } else if (noOfDaysBetween < 10) {
                                    if (noOfDaysBetween == 1) {
                                        md.setGameDate("hace " + noOfDaysBetween + " día");
                                    } else {
                                        md.setGameDate("hace " + noOfDaysBetween + " días");
                                    }
                                } else {
                                    md.setGameDate(createdate.format(formatter));
                                }
                                double kills = p.getKills();
                                double deaths = p.getDeaths();
                                double assists = p.getAssists();

                                if (p.getDeaths() == 0) {
                                    String KDA = String.valueOf(kills + assists);
                                    BigDecimal bd = new BigDecimal(KDA).setScale(1, RoundingMode.HALF_EVEN);
                                    md.setKDA(bd.doubleValue() + " KDA");
                                } else {
                                    String KDA = String.valueOf((kills + assists) / deaths);
                                    BigDecimal bd = new BigDecimal(KDA).setScale(1, RoundingMode.HALF_EVEN);
                                    md.setKDA(bd.doubleValue() + " KDA");
                                }
                                md.setLongKDA((int) kills + " / " + (int) deaths + " / " + (int) assists);
                                String csMin = String.valueOf((int) ((p.getTotalMinionsKilled() + p.getNeutralMinionsKilled()) / Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).toMinutes()));
                                md.setCsMin(csMin + " CS/min");
                                md.setCsTotal(p.getTotalMinionsKilled() + p.getNeutralMinionsKilled() + " CS");

                                RoleType rt = p.getRole();
                                if (! gametype.equals("ARAM")) {
                                    switch (rt) {
                                        case CARRY -> md.setMatchRole("ADC");
                                        case SUPPORT -> md.setMatchRole("SUPPORT");
                                        case SOLO -> md.setMatchRole("TOP");
                                        case NONE -> md.setMatchRole("JUNGLE");
                                        case DUO -> md.setMatchRole("MID");
                                    }
                                } else {
                                    md.setMatchRole("");
                                }
                                if (p.didWin()) {
                                    wins++;
                                    md.setGameResult("VICTORY");
                                } else {
                                    losses++;
                                    md.setGameResult("DEFEAT");
                                }
                                StaticChampion champ = api.getChampion(p.getChampionId());
                                if (! championsStatic.containsKey(champ)) {
                                    championsStatic.put(champ, 1);
                                    ChampionData champData = new ChampionData();
                                    String nam = champ.getName();
                                    nam = nam.replace(" ", "");
                                    champData.setName(nam);
                                    champData.setID(String.valueOf(champ.getId()));
                                    if (p.didWin()) {
                                        champData.addWin();
                                    } else {
                                        champData.addLoss();
                                    }
                                    champData.updateWinrate();
                                    champData.hasAppeared();

                                    champData.setKDA(p.getKills(), p.getDeaths(), p.getAssists());
                                    champions.put(champData, 1);
                                } else {
                                    championsStatic.put(champ, championsStatic.get(champ) + 1);
                                    ChampionData champData;
                                    for (ChampionData c : champions.keySet()) {
                                        if (c.getID().equals(String.valueOf(champ.getId()))) {
                                            champData = c;
                                            if (p.didWin()) {
                                                champData.addWin();
                                            } else {
                                                champData.addLoss();
                                            }
                                            champData.updateWinrate();
                                            champData.hasAppeared();

                                            champData.setKDA(p.getKills(), p.getDeaths(), p.getAssists());
                                            champions.put(champData, champions.get(champData) + 1);
                                        }
                                    }
                                }
                                matchData.add(md);
                            }
                        }
                    }
                }catch (Exception e){
                    matchError++;
                    e.printStackTrace();
                }
            }

            List<SummonerData> orderedSummonersList = new ArrayList<>();
            for (String mp : participants.keySet()) {
                if (mp != null) {
                    SummonerData sd = new SummonerData();
                    sd.setName(mp);
                    sd.setRegion(leagueShard);
                    sd.setGamesPlayedTogether(String.valueOf(participants.get(mp)));
                    orderedSummonersList.add(sd);
                }
            }
            Collections.sort(orderedSummonersList);

            List<ChampionData> orderedChampionsList = new ArrayList<>();
            for (Map.Entry<ChampionData, Integer> entry : champions.entrySet()) {
                orderedChampionsList.add(entry.getKey());
            }
            Collections.sort(orderedChampionsList);

            ArrayList<SummonerData> mostPlayedWithSummoners = new ArrayList<>();
            int count = 0;
            for (SummonerData entry : orderedSummonersList) {
                if (count >= 4) {
                    break;
                }
                Summoner summ = Summoner.bySummonerId(leagueS.get(), entry.getName());
                entry.setImgID(String.valueOf(summ.getProfileIconId()));
                entry.setPUUID(summ.getPUUID());
                entry.setName(summ.getName());
                mostPlayedWithSummoners.add(entry);
                count++;
            }

            ArrayList<ChampionData> mostPlayedChampions = new ArrayList<>();
            ArrayList<ChampionData> lastChampions = new ArrayList<>();
            count = 0;
            for (ChampionData entry : orderedChampionsList) {
                if (count < 3) {
                    lastChampions.add(entry);
                }
                if (count >= 5) {
                    break;
                }
                mostPlayedChampions.add(entry);
                count++;
            }

            if (!soloQlp.equals("")) {
                soloQlp = soloQlp + " LP";
            }
            if (!flexQlp.equals("")) {
                flexQlp = flexQlp + " LP";
            }
            if (!soloQwinrate.equals("")) {
                soloQwinrate = soloQwinrate + "%";
            }
            if (!flexQwinrate.equals("")) {
                flexQwinrate = flexQwinrate + "%";
            }
            if (!soloQgames.equals("")) {
                soloQgames = soloQgames + " Games";
            }
            if (!flexQgames.equals("")) {
                flexQgames = flexQgames + " Games";
            }

            data.put("profileLevel", summoner.getSummonerLevel());
            data.put("profileImageID", String.valueOf(summoner.getProfileIconId()).replace(".", ""));
            data.put("profileUsername", summoner.getName());
            data.put("isLinkedAccount", "this account is not linked yet");
            data.put("soloQtier", soloQtier);
            data.put("soloQlp", soloQlp);
            data.put("soloQgames", soloQgames);
            data.put("soloQwinrate", soloQwinrate);
            data.put("flexQtier", flexQtier);
            data.put("flexQlp", flexQlp);
            data.put("flexQgames", flexQgames);
            data.put("flexQwinrate", flexQwinrate);
            data.put("soloQimage", soloQtierShort);
            data.put("flexQimage", flexQtierShort);

            if (! (matchError == 20)) {
                data.put("championIndex", mostPlayedChampions);
                data.put("summonerIndex", mostPlayedWithSummoners);
                data.put("last20Games", wins + "W " + losses + "L");
                data.put("last20index", lastChampions);
                data.put("historyIndex", matchData);
            }

            StringWriter out = new StringWriter();
            template.process(data, out);
            html = out.toString();

            return html;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String itemsPage (boolean logged, User ... args) {
        try {
            String html;
            Template template;
            Map<String, Object> data = new HashMap<>();
            if (logged) {
                template = cfg.getTemplate("loggedItemList.ftl");
                User user = args[0];

                String img = "data:image/jpeg;base64,";
                byte[] bytesImg = user.getAccountimage();
                String base64Img;
                if (bytesImg == null) {
                    base64Img = DEFAULT_USER_IMAGE;
                } else {
                    base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
                }
                img += base64Img;

                data.put("Username", user.getUsername());
                data.put("UsernamePhoto", img);
            } else {
                template = cfg.getTemplate("unloggedItemList.ftl");
            }
            Map<Integer, Item> items = api.getItems();
            ArrayList<String> itemListStarterItemsIDs = new ArrayList<>(List.of("1054", "1055", "1056", "1082", "1083", "1101", "1102", "1103", "2051", "3070", "3112", "3177", "3184", "3850", "3854", "3858", "3862"));
            ArrayList<String> itemListBootsIDs = new ArrayList<>(List.of("1001", "2422", "3006", "3009", "3020", "3047", "3111", "3117", "3158"));
            ArrayList<String> itemListWardsIDs = new ArrayList<>(List.of("3330", "3340", "3363", "3364"));
            ArrayList<String> itemListConsumablesIDs = new ArrayList<>(List.of("3513", "2003", "2010", "2031", "2033", "2052", "2055", "2138", "2139", "2140", "2403", "2420", "2421", "3400"));
            ArrayList<String> itemListBasicItemsIDs = new ArrayList<>(List.of("1004", "1006", "1018", "1026", "1027", "1028", "1029", "1033", "1036", "1037", "1038", "1042", "1052", "1058", "3057"));
            ArrayList<String> itemListEpicItemsIDs = new ArrayList<>(List.of("1011", "1031", "1043", "1053", "1057", "2015", "3024", "3035", "3044", "3051", "3066", "3067", "3076", "3077", "3082", "3086", "3105", "3108", "3113", "3114", "3123", "3133", "3134", "3140", "3145", "3155", "3191", "3211", "3801", "3802", "3803", "3851", "3855", "3859", "3863", "3916", "4630", "4632", "4635", "4638", "4642", "6029", "6660", "6670", "6677"));
            ArrayList<String> itemListLegendaryItemsIDs = new ArrayList<>(List.of("3003", "3004", "3011", "3026", "3031", "3033", "3036", "3040", "3041", "3042", "3046", "3050", "3053", "3065", "3068", "3071", "3072", "3074", "3075", "3083", "3085", "3089", "3091", "3094", "3095", "3100", "3102", "3107", "3109", "3110", "3115", "3116", "3119", "3121", "3124", "3135", "3139", "3142", "3143", "3153", "3156", "3157", "3161", "3165", "3179", "3181", "3193", "3222", "3504", "3508", "3742", "3748", "3814", "3853", "3857", "3860", "3864", "4401", "4628", "4629", "4637", "4643", "4645", "6035", "6333", "6609", "6616", "6664", "6675", "6676", "6694", "6695", "6696", "8001", "8020"));
            ArrayList<String> itemListMythicItemsIDs = new ArrayList<>(List.of("2065", "3001", "3078", "3084", "3152", "3190", "4005", "4633", "4636", "4644", "6617", "6630", "6631", "6632", "6653", "6655", "6656", "6657", "6662", "6665", "6667", "6671", "6672", "6673", "6691", "6692", "6693"));
            ArrayList<String> itemListOrnnItemsIDs = new ArrayList<>(List.of("7005", "7006", "7007", "7008", "7009", "7010", "7011", "7012", "7013", "7014", "7015", "7016", "7017", "7018", "7019", "7020", "7021", "7022", "7023", "7024", "7025", "7026", "7027", "7028", "7002", "7001", "7000"));
            ArrayList<String> itemListChampionItemsIDs = new ArrayList<>(List.of("3600", "3901", "3902", "3903"));

            ArrayList<SectionData> sections = new ArrayList<>();

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

            return html;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//      ArrayList<String> itemListStarterItems = new ArrayList<>(List.of("Doran's Shield", "Doran's Blade", "Doran's Ring", "Dark Seal", "Cull", "Scorchclaw Pup", "Gustwalker Hatchling", "Mosstomper Seedling", "Guardian's Horn", "Guardian's Orb", "Guardian's Blade", "Guardian's Hammer", "Spellthief's Edge", "Steel Shoulderguards", "Relic Shield", "Spectral Sickle", "Tear of the Goddess"));
//      ArrayList<String> itemListBoots = new ArrayList<>(List.of("Boots", "Slightly Magical Footwear", "Berserker's Greaves", "Boots of Swiftness", "Sorcerer's Shoes", "Plated Steelcaps", "Mercury's Treads", "Mobility Boots", "Ionian Boots of Lucidity"));
//      ArrayList<String> itemListWards = new ArrayList<>(List.of("Scarecrow Effigy", "Stealth Ward", "Farsight Alteration", "Oracle Lens"));
//      ArrayList<String> itemListConsumables = new ArrayList<>(List.of("Eye of the Herald", "Health Potion", "Refillable Potion", "Corrupting Potion", "Total Biscuit of Everlasting Will", "Poro-Snax", "Control Ward", "Elixir of Iron", "Elixir of Sorcery", "Elixir of Wrath", "Minion Dematerializer", "Your Cut", "Stopwatch", "Broken Stopwatch"));
//      ArrayList<String> itemListBasicItems = new ArrayList<>(List.of("Faerie Charm", "Rejuvenation Bead", "Cloak of Agility", "Blasting Wand", "Sapphire Crystal", "Ruby Crystal", "Cloth Armor", "Null-Magic Mantle", "Long Sword", "Pickaxe", "B. F. Sword", "Dagger", "Amplifying Tome", "Needlessly Large Rod", "Sheen"));
//      ArrayList<String> itemListEpicItems = new ArrayList<>(List.of("Giant's Belt", "Chain Vest", "Recurve Bow", "Vampiric Scepter", "Negatron Cloak", "Kircheis Shard", "Last Whisper", "Glacial Buckler", "Phage", "Hearthbound Axe", "Winged Moonplate", "Kindlegem", "Bramble Vest", "Tiamat", "Warden's Mail", "Zeal", "Aegis of the Legion", "Fiendish Codex", "Aether Wisp", "Forbidden Idol", "Caulfield's Warhammer", "Serrated Dirk", "Watchful Wardstone", "Quicksilver Sash", "Hextech Alternator", "Seeker's Armguard", "Hexdrinker", "Spectre's Cowl", "Crystalline Bracer", "Lost Chapter", "Catalyst of Aeons", "Oblivion Orb", "Blighting Jewel", "Verdant Barrier", "Leeching Leer", "Bandleglass Mirror", "Ironspike Whip", "Bami's Cinder", "Noonquiver", "Rageknife", "Executioner's Calling", "Targon's Buckler", "Frostfang", "Harrowing Crescent", "Runesteel Spaulders"));
//      ArrayList<String> itemListLegendaryItems = new ArrayList<>(List.of("Abyssal Mask", "Edge of Night", "Warmog's Armor", "Mercurial Scimitar", "Serpent's Fang", "Shadowflame", "Winter's Approach", "Archangel's Staff", "Void Staff", "Mikael's Blessing", "Anathema's Chains", "Morellonomicon", "Essence Reaver", "Serylda's Grudge", "Fimbulwinter", "Ardent Censer", "Force of Nature", "Mortal Reminder", "Shard of True Ice", "Wit's End", "Zhonya's Hourglass", "Spirit Visage", "Navori Quickblades", "Spear Of Shojin", "Nashor's Tooth", "Zeke's Convergence", "Muramana", "Youmuu's Ghostblade", "Silvermere Dawn", "Axiom Arc", "Guardian Angel", "Banshee's Veil", "Gargoyle Stoneplate", "Frozen Heart", "Black Cleaver", "Staff of Flowing Water", "Sterak's Gage", "Pauldrons of Whiterock", "Guinsoo's Rageblade", "Phantom Dancer", "Stormrazor", "Sunfire Aegis", "Rabadon's Deathcap", "Horizon Focus", "Black Mist Scythe", "Blade of The Ruined King", "Hullbreaker", "Randuin's Omen", "The Collector", "Rapid Firecannon", "Infinity Edge", "Bloodthirster", "Thornmail", "Titanic Hydra", "Ravenous Hydra", "Bulwark of the Mountain", "Knight's Vow", "Chempunk Chainsword", "Lich Bane", "Redemption", "Turbo Chemtank", "Chemtech Putrifier", "Cosmic Drive", "Dead Man's Plate", "Death's Dance", "Demonic Embrace", "Lord Dominik's Regards", "Manamune", "Maw of Malmortius", "Mejai's Soulstealer", "Runaan's Hurricane", "Rylai's Crystal Scepter", "Seraph's Embrace", "Umbral Glaive", "Vigilant Wardstone"));
//      ArrayList<String> itemListMythicItems = new ArrayList<>(List.of("Crown of the Shattered Queen", "Luden's Tempest", "Locket of the Iron Solari", "Liandry's Anguish", "Iceborn Gauntlet", "Immortal Shieldbow", "Jak'Sho, The Protean", "Kraken Slayer", "Imperial Mandate", "Hextech Rocketbelt", "Trinity Force", "Divine Sunderer", "Night Harvester", "Heartsteel", "Shurelya's Battlesong", "Moonstone Renewer", "Goredrinker", "Stridebreaker", "Duskblade of Draktharr", "Rod of Ages", "Eclipse", "Everfrost", "Radiant Virtue", "Prowler's Claw", "Evenshroud", "Riftmaker", "Galeforce"));
//      ArrayList<String> itemListOrnnItems = new ArrayList<>(List.of("Caesura", "Eye of Luden", "Reliquary of the Golden Dawn", "Liandry's Lament", "Frozen Fist", "Bloodward", "The Unspoken Parasite", "Wyrmfallen Sacrifice", "Seat of Command", "Upgraded Aeropack", "Infinity Force", "Deicide", "Vespertide", "Leviathan", "Shurelya's Requiem", "Starcaster", "Ceaseless Hunger", "Dreamshatter", "Draktharr's Shadowcarver", "Convergence", "Syzygy", "Eternal Winter", "Primordial Dawn", "Sandshrike's Claw", "Equinox", "Icathia's Curse", "Typhoon"));
//      ArrayList<String> itemListChampionItems = new ArrayList<>(List.of("Kalista's Black Spear", "Fire at Will", "Death's Daughter", "Raise Morale"));
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

}
