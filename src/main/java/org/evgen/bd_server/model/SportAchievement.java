package org.evgen.bd_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sport_achievement")
public class SportAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @ManyToOne
    private Sportsman sportsman;

    @JoinColumn
    @ManyToOne
    private Coach coach;

    @Column
    private String name;

    @Column
    private int discharge;

}
