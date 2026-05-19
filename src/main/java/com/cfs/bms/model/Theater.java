package com.cfs.bms.model;

import java.util.List;

import org.hibernate.annotations.Collate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theater")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theater {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double ID;

    @Column(nullable = false)
    private String name;

    private String address;

    private String city;

    private Integer totalScreen;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Screen> screens;

}
