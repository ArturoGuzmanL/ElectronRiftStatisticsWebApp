package javacode.server.springelectronriftstatisticswebapp.HtmlFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import javacode.server.springelectronriftstatisticswebapp.SecretFile;
import javacode.server.springelectronriftstatisticswebapp.model.ChampionData;
import javacode.server.springelectronriftstatisticswebapp.model.MatchData;
import javacode.server.springelectronriftstatisticswebapp.model.SummonerData;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public String  loginPageAction (boolean logged, User ... args) {
        if (logged) {
            try {
                User user = args[0];
                String html;
                String img = "data:image/jpeg;base64,";
                byte[] bytesImg = user.getAccountimage();
                String base64Img;
                if (bytesImg == null) {
                    base64Img = DEFAULT_USER_IMAGE;
                } else {
                    base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
                }
                img += base64Img;

                Template template = cfg.getTemplate("loggedIndex.ftl");
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
                Template template = cfg.getTemplate("unloggedIndex.ftl");
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

    public String summonerPage (boolean logged, String summonerPUUID, User ... args) {
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
            Summoner summoner = SummonerAPI.getInstance().getSummonerByPUUID(LeagueShard.UNKNOWN, summonerPUUID);

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
            for (String s : last20) {
                m = mb.withId(s).getMatch();
                MatchData md = new MatchData();
                if (Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).compareTo(Duration.ofMinutes(4)) > 0) {
                    for (MatchParticipant p : m.getParticipants()) {
                        if (!p.getSummonerId().equals(summoner.getSummonerId())) {
                            if (!participants.containsKey(p.getSummonerId())) {
                                participants.put(p.getSummonerId(), 1);
                            } else {
                                participants.put(p.getSummonerId(), participants.get(p.getSummonerId()) + 1);
                            }
                        }else {
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
                            }else if (noOfDaysBetween < 10) {
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
                                String KDA = String.valueOf((kills+assists)/deaths);
                                BigDecimal bd = new BigDecimal(KDA).setScale(1, RoundingMode.HALF_EVEN);
                                md.setKDA(bd.doubleValue() + " KDA");
                            }
                            md.setLongKDA((int) kills + " / " + (int) deaths + " / " + (int) assists);
                            String csMin = String.valueOf((int) ((p.getTotalMinionsKilled() + p.getNeutralMinionsKilled()) / Duration.between(m.getGameStartAsDate(), m.getGameEndAsDate()).toMinutes()));
                            md.setCsMin(csMin + " CS/min");
                            md.setCsTotal(p.getTotalMinionsKilled() + p.getNeutralMinionsKilled() + " CS");

                            RoleType rt = p.getRole();
                            if (!gametype.equals("ARAM")) {
                                switch (rt) {
                                    case CARRY -> md.setMatchRole("ADC");
                                    case SUPPORT -> md.setMatchRole("SUPPORT");
                                    case SOLO -> md.setMatchRole("TOP");
                                    case NONE -> md.setMatchRole("JUNGLE");
                                    case DUO -> md.setMatchRole("MID");
                                }
                            }else {
                                md.setMatchRole("");
                            }
                            if (p.didWin()) {
                                wins++;
                                md.setGameResult("VICTORY");
                            }else {
                                losses++;
                                md.setGameResult("DEFEAT");
                            }
                            StaticChampion champ = api.getChampion(p.getChampionId());
                            if (!championsStatic.containsKey(champ)) {
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
                            }else {
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
            }

            List<SummonerData> orderedSummonersList = new ArrayList<>();
            for (String mp : participants.keySet()) {
                if (mp != null) {
                    SummonerData sd = new SummonerData();
                    sd.setName(mp);
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
                Summoner summ = Summoner.bySummonerId(LeagueShard.EUW1, entry.getName());
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
                if (count >= 6) {
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
            data.put("championIndex", mostPlayedChampions);
            data.put("summonerIndex", mostPlayedWithSummoners);
            data.put("last20Games", wins + "W " + losses + "L");
            data.put("last20index", lastChampions);
            data.put("historyIndex", matchData);

            StringWriter out = new StringWriter();
            template.process(data, out);
            html = out.toString();

            return html;

        }catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
