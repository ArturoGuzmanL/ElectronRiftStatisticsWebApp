package javacode.server.springelectronriftstatisticswebapp.controller;


import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatisticswebapp.SecretFile;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import javacode.server.springelectronriftstatisticswebapp.repository.UserRepository;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.utils.SummonerCrawler;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/")

public class UserController {
    ArrayList<LeagueShard> regions = new ArrayList<>();
    {
        regions.add(LeagueShard.NA1);
        regions.add(LeagueShard.EUN1);
        regions.add(LeagueShard.EUW1);
        regions.add(LeagueShard.KR);
        regions.add(LeagueShard.JP1);
        regions.add(LeagueShard.BR1);
        regions.add(LeagueShard.LA1);
        regions.add(LeagueShard.LA2);
        regions.add(LeagueShard.OC1);
        regions.add(LeagueShard.RU);
        regions.add(LeagueShard.TR1);
    }

    final R4J r4J = new R4J(SecretFile.CREDS);
    DDragonAPI api = r4J.getDDragonAPI();
    Supplier<FileSystemCacheProvider> fileCache= () -> new FileSystemCacheProvider();

    public UserController() {
        DataCall.setCacheProvider(fileCache.get());
    }


    @Autowired
    UserRepository userRepository;
    @Autowired
    HtmlFactory htmlFactory;

//    @GetMapping("/all")
//    public List<User> list() {
//    }

    // ------------- Account actions  ------------- //

    @GetMapping("users/actions/login/{username}={password}")
    public ResponseEntity<String> get(@PathVariable("username") String username, @PathVariable("password") String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("users/actions/signup/{data}")
    public ResponseEntity<String> signup(@PathVariable("data") String data) {
        String[] userData = data.split("=");
        String username;
        String password;
        String email;
        if (userData.length == 4) {
            password = userData[0];
            username = userData[1];
            email = userData[2];
            password += userData[3];
        }else if (userData.length == 5) {
            password = userData[0];
            username = userData[1];
            password += userData[2];
            email = userData[3];
            password += userData[4];
        }else {
            //Algo ha salido mal
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> user2 = userRepository.findByEmail(email);
        if (user.isPresent() || user2.isPresent()) {
            //El usuario ya existe
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User newUser = new User(username, password, email);
            userRepository.save(newUser);
            return new ResponseEntity<>(newUser.getId(), HttpStatus.CREATED);
        }
    }

    @PostMapping("users/actions/profileimgupdt/{uid}")
    public ResponseEntity<String> login(@PathVariable("uid") String uid, @RequestBody byte[] profileImg) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            user.get().setAccountimage(profileImg);
            userRepository.save(user.get());
            return new ResponseEntity<>(user.get().getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ------------- Html petitions  ------------- //

    @GetMapping("/htmlRequests/home/{logged}/{uid}")
    public ResponseEntity<String> login(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), false , user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/home/initialization/{logged}/{uid}")
    public ResponseEntity<String> loginInitialization(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), true , user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), true);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/championlist/{logged}/{uid}")
    public ResponseEntity<String> championList(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.champList(true, user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.champList(false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/summonerPage/{PUUID}/{region}/{logged}/{uid}")
    public ResponseEntity<String> summonerPage(@PathVariable("logged") String logged, @PathVariable("uid") String uid, @PathVariable("PUUID") String PUUID, @PathVariable("region") String region) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.summonerPage(true, PUUID, region, user.get());
                if (html == null) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }else {
                    return new ResponseEntity<>(html, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.summonerPage(false, PUUID, region);
            if (html == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else {
                return new ResponseEntity<>(html, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/htmlRequests/itemlist/{logged}/{uid}")
    public ResponseEntity<String> itemList(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.itemsPage(true, user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.itemsPage(false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    // ------------- General actions  ------------- //

    @GetMapping("/browse/{username}/{appid}")
    public String browse(@PathVariable("username") String username, @PathVariable("appid") String appid) {
        StringBuilder formattedHtmlComplete = new StringBuilder();
        ArrayList<Summoner> summoners = new ArrayList<>();
        for (LeagueShard region : regions) {
            Summoner sum = Summoner.byName(region, username);
            if (sum != null) {
                summoners.add(sum);
            }
        }

        for (Summoner summoner: summoners) {
            String summonerImg = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/profile-icons/" + summoner.getProfileIconId() + ".jpg";
            Image image = null;
            try {
                image = ImageIO.read(new URL(summonerImg));
            } catch (IOException e) {
                System.out.println("Error when loading summoner " + summoner.getName() + " with region " + summoner.getPlatform().getRealmValue().toUpperCase());
            }
            if (image == null) {
                summonerImg = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/profile-icons/29.jpg";
            }

            String summonerName = summoner.getName();
            String summonerRegion = summoner.getPlatform().getRealmValue().toUpperCase();

            Map<String, String> values = new HashMap<>();
            values.put("Img", summonerImg);
            values.put("SummName", summonerName);
            values.put("SummReg", summonerRegion);
            values.put("summID", summoner.getPUUID());
            StringSubstitutor sub = new StringSubstitutor(values);

            String htmlFragment = "<li id=\"${summID}\" class=\"browserItem\">" +
                    "<div href=\"\" class=\"browserLink\">" +
                    "<div class=\"cardContainer\">" +
                    "<div class=\"browserCard\">" +
                    "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
                    "</div>" +
                    "<div class=\"cardPhoto\">" +
                    "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
                    "</div>" +
                    "</div>" +
                    "<span class=\"browserName\">" +
                    "<span class=\"browserSummName\">${SummName}</span>" +
                    "<span id=\"summoner${summID}Region\"class=\"browserSummRegion\">${SummReg}</span>" +
                    "</span>" +
                    "</div>" +
                    "</li>";

            String formattedHtml = sub.replace(htmlFragment);
            formattedHtmlComplete.append(formattedHtml);
        }
        return formattedHtmlComplete.toString();
    }
}