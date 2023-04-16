package javacode.server.springelectronriftstatistics.controller;

import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import javacode.server.springelectronriftstatistics.model.User;
import javacode.server.springelectronriftstatistics.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

import com.merakianalytics.orianna.Orianna;
@RestController
@RequestMapping("/api/")
public class UserController {
    ArrayList<Region> regions = new ArrayList<>();
    {
        regions.add(Region.NORTH_AMERICA);
        regions.add(Region.EUROPE_NORTH_EAST);
        regions.add(Region.EUROPE_WEST);
        regions.add(Region.KOREA);
        regions.add(Region.JAPAN);
        regions.add(Region.BRAZIL);
        regions.add(Region.LATIN_AMERICA_NORTH);
        regions.add(Region.LATIN_AMERICA_SOUTH);
        regions.add(Region.OCEANIA);
        regions.add(Region.RUSSIA);
        regions.add(Region.TURKEY);
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

    @GetMapping("/htmlRequests/login/{uid}")
    public ResponseEntity<String> login(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            String html = htmlFactory.loginPageAction(user.get());
            return new ResponseEntity<>(html, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ------------- General actions  ------------- //

    @GetMapping("/browse/{username}")
    public String browse(@PathVariable("username") String username) {
        ArrayList<Summoner> summoners = new ArrayList<>();
        for (Region region : regions) {
            summoners.add( Orianna.summonerNamed(username).withRegion(region).get());
        }

        for (Summoner summoner: summoners) {

            String htmlFragment = "<li class=\"browserItem\">" +
                    "<a href=\"\" class=\"browserLink\">" +
                    "<div class=\"cardContainer\">" +
                    "<div class=\"browserCard\">" +
                    "<img src=\"data:image/jpeg;base64,{0}\" class=\"cardBackground\" alt=\"{1}\">" +
                    "</div>" +
                    "<div class=\"cardPhoto\">" +
                    "<img src=\"data:image/jpeg;base64,{0}\" class=\"cardBackground\" alt=\"{1}\">" +
                    "</div>" +
                    "</div>" +
                    "<span class=\"browserName\">" +
                    "<span class=\"browserSummName\">{2}</span>" +
                    "<span class=\"browserSummRegion\">{3}</span>" +
                    "</span>" +
                    "</a>" +
                    "</li>";


        }
        return "";
    }
}