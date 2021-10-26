package com.example.country;

import com.example.country.models.Country;
import com.example.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
public class CountryApplication {

//	private final CountryRepository countryRepository;

//	public CountryApplication(CountryRepository countryRepository) {
//		this.countryRepository = countryRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(CountryApplication.class, args);
	}

//	@PostConstruct
//	private void init() {
//		String[] countryCodes = Locale.getISOCountries();
//		List<Country> countries = new ArrayList<>();
//		for (String countryCode : countryCodes) {
//			Locale obj = new Locale("", countryCode);
//			Country country = new Country(obj.getDisplayCountry(), obj.getCountry());
//			countries.add(country);
//		}
//		Collections.sort(countries);
//		countryRepository.saveAll(countries);
//	}



}
