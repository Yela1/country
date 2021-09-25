package com.example.country.controller;

import com.example.country.models.Country;
import com.example.country.models.Room;
import com.example.country.repository.CountryRepository;
import com.example.country.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private CountryRepository countryRepository;


    @Test
    public void getAll() throws Exception{
        List<Room> rooms = new ArrayList<>();
        Room room = new Room("kz","kz");
        Room room1 = new Room("ru","ru");
        rooms.add(room); rooms.add(room1);

        when(roomService.getAll()).thenReturn(rooms);

        List<Room> exceptedRooms = roomService.getAll();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(model().attribute("rooms",exceptedRooms));

    }

    @Test
    public void get() throws Exception{
        Room room = new Room(1L,"kz",true,"KZ");
        when(roomService.get(1L)).thenReturn(room);
        Room exceptedRoom = roomService.get(room.getId());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("room"))
                .andExpect(model().attribute("room",exceptedRoom));


    }
    @Test
    public void getPermissionDeniedIfCountryIsDifferent() throws Exception{
        Room room = new Room(1L,"ru",true,"RU");
        when(roomService.get(1L)).thenReturn(room);
        String expectedError = "Permission Denied";
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(model().attribute("error",expectedError));


    }

    @Test
    public void showSignUpForm() throws Exception{
        List<Country> countries = new ArrayList<>();
        Country c1 = new Country("Kz","Kz");
        Country c2 = new Country("Ru","Ru");
        countries.add(c1); countries.add(c2);
        when(countryRepository.findAll()).thenReturn(countries);
        List<Country> excepted= countryRepository.findAll();
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-room"))
                .andExpect(model().attribute("countries",excepted));
    }

    @Test
    public void addRoom() throws Exception{
        Room room = new Room(1L,"KZ",true,"KZ");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/addRoom"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(redirectedUrl("/"));

    }
    @Test
    public void delete() throws Exception{
        Room room = new Room(1L,"KZ",true,"KZ");
        String s = "sldsd";
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(redirectedUrl("/"));
    }
}