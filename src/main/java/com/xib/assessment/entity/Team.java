package com.xib.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TEAM_MANAGERS",
            joinColumns = @JoinColumn( name = "team_id"),
            inverseJoinColumns = @JoinColumn( name = "manager_id")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Manager> managers;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Agent> agents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Manager> getManagers() {
        if(managers != null)
            return managers;
        else
            managers = new HashSet<>();

        return managers;
    }

    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }

    public Set<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", managers=" + managers +
                ", agents=" + agents +
                '}';
    }
}
