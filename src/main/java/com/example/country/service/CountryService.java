package com.example.country.service;


import com.example.country.models.Country;
import com.example.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;


    public List<Country> getAll(){
        return countryRepository.findAll();
    }
}
