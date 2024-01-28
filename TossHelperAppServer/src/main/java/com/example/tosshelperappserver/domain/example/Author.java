package com.example.tosshelperappserver.domain.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author")
@Getter
@Setter
@ToString(of = {"id", "name", "age", "gender"})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String gender;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> book = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

}