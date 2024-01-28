package com.example.tosshelperappserver.domain.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Organization")
@Getter
@Setter
@ToString(of = {"id", "orgName"})
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgName;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Author> authors = new ArrayList<>();

}
