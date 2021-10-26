package com.example.country.service;

import com.example.country.exception.RoomNotFoundException;
import com.example.country.models.Room;
import com.example.country.repository.RoomRepository;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;


    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room get(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
}

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        roomRepository.deleteById(id);
    }

    public void update(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        room.setStatus(!room.isStatus());
        roomRepository.save(room);

    }

    public List<Room> createAll(List<Room> rooms) {
        return roomRepository.saveAll(rooms);
    }


    public Boolean checkIp(Room room) throws IOException{
        try (WebServiceClient client = new WebServiceClient.Builder(565526, "MxP8cy6HjNKNHiHU").host("geolite.info").build()) {

            InetAddress ipAddress = getIpADress();
            String IsoCode = client.country(ipAddress).getCountry().getIsoCode();
            if (!IsoCode.equals(room.getCountryCode())) {
                return false;
            }
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static InetAddress getIpADress() throws IOException {
        URL url = new URL("https://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return InetAddress.getByName(br.lines().collect(Collectors.joining()));
    }



}
