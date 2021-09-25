package com.example.country.service;

import com.example.country.exception.RoomNotFoundException;
import com.example.country.models.Room;
import com.example.country.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;


    @Test
    public void getAll() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());

        given(roomRepository.findAll()).willReturn(rooms);

        List<Room> expected = roomService.getAll();

        assertEquals(expected, rooms);
        verify(roomRepository).findAll();
    }

    @Test
    public void getRoomIfFound() {
        Room room = new Room(1L,"KZ",true,"KZ");
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        Room exceptedRoom = roomRepository.findById(room.getId()).orElseThrow();
        assertThat(exceptedRoom).isSameAs(room);
        verify(roomRepository).findById(room.getId());

    }
    @Test(expected = RoomNotFoundException.class)
    public void getShouldThrowExceptionWhenRoomDoesntExist() {
        Room room = new Room(1L,"KZ",true,"KZ");

        given(roomRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        roomService.get(room.getId());
    }

    @Test
    public void create() {
        Room room = new Room();
        room.setName("KZ");
        room.setCountryCode("KZ");
        when(roomRepository.save(ArgumentMatchers.any(Room.class))).thenReturn(room);

        Room created = roomService.create(room);

        assertThat(created.getName()).isSameAs(room.getName());
        verify(roomRepository).save(room);
    }

    @Test
    public void deleteRoomIfFound() {
        Room room = new Room(1L,"KZ",true,"KZ");
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        roomService.delete(room.getId());
        verify(roomRepository).deleteById(room.getId());
    }

    @Test(expected = RuntimeException.class)
    public void deleteShouldThrowExceptionWhenRoomDoesntExist(){
        Room room = new Room(1L,"KZ",true,"KZ");
        given(roomRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        roomService.delete(room.getId());
    }


    @Test
    public void updateIfFound() {
        Room room = new Room(1L,"KZ",true,"KZ");


        given(roomRepository.findById(room.getId())).willReturn(Optional.of(room));
        roomService.update(room.getId());

        verify(roomRepository).save(room);
        verify(roomRepository).findById(room.getId());
    }

    @Test(expected = RuntimeException.class)
    public void updateShouldThrowExceptionWhenRoomDoesntExist() {
        Room room = new Room(1L,"KZ",true,"KZ");


        given(roomRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        roomService.update(room.getId());
    }

    @Test
    public void createAll() {
        List<Room> rooms = new ArrayList<>();
        Room room = new Room();
        room.setName("kz");
        Room room1 = new Room();
        room1.setName("ru");
        rooms.add(room);
        rooms.add(room1);

        given(roomRepository.saveAll(rooms)).willReturn(rooms);

        List<Room> exceptedRooms = roomService.createAll(rooms);
        assertEquals(exceptedRooms,rooms);
        verify(roomRepository).saveAll(rooms);
    }

}