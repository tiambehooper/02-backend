package com.luv2code.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id" )
    private int id;

    @Column(name = "code" )
    private String code;

    @Column(name = "name" )
    private String name;

    @OneToMany(mappedBy = "country" )
    @JsonIgnore
    private List<State> states;
}
