package com.example.country.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "country")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country implements Comparable<Country>{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    public Country(String name, String code){
        this.name = name;
        this.code = code;
    }

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.getName());
    }
}
