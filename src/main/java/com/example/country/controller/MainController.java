package com.example.country.controller;

import com.example.country.models.Room;
import com.example.country.service.CountryService;
import com.example.country.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RoomService roomService;
    private final CountryService countryService;


    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("rooms", roomService.getAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Long id,
                      Model model) throws IOException {
        Room room = roomService.get(id);

        if(!roomService.checkIp(room)){
            model.addAttribute("error","Permission Denied");
            return "error";
        }
        model.addAttribute("room", room);
        return "room";
    }

    @GetMapping("/add")
    public String showSignUpForm(Model model, Room room) {
        model.addAttribute("countries", countryService.getAll());
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



}