package com.example.country.controller;

import com.example.country.exception.RoomNotFoundException;
import com.example.country.models.Room;
import com.example.country.repository.CountryRepository;
import com.example.country.service.RoomService;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RoomService roomService;
    private final CountryRepository countryRepository;


    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("rooms", roomService.getAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Long id,
                      Model model) throws IOException {
        Room room;
        try {
            room = roomService.get(id);
        }catch(RoomNotFoundException e){
            model.addAttribute("error", "No such file");
            return "error";
        }
        try (WebServiceClient client = new WebServiceClient.Builder(565526, "MxP8cy6HjNKNHiHU").host("geolite.info").build()) {

            InetAddress ipAddress = getIpADress();
            String IsoCode = client.country(ipAddress).getCountry().getIsoCode();
            if (!IsoCode.equals(room.getCountryCode())) {
                model.addAttribute("error", "Permission Denied");
                return "error";
            }
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("room", room);
        return "room";
    }

    @GetMapping("/add")
    public String showSignUpForm(Model model, Room room) {
        model.addAttribute("countries", countryRepository.findAll());
        return "add-room";
    }

    @PostMapping("/{id}")
    public ModelAndView updateRoom(@PathVariable("id") long id, Model model) {
        roomService.update(id);
        return new ModelAndView("redirect:" + id);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteRoom(@PathVariable("id") long id){
        roomService.delete(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/addRoom")
    public ModelAndView addRoom(Room room) {
        roomService.create(room);
        return new ModelAndView("redirect:/");
    }


    private static InetAddress getIpADress() throws IOException {
        URL url = new URL("https://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return InetAddress.getByName(br.lines().collect(Collectors.joining()));
    }


}