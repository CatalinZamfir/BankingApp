package org.sda.BankingApp.types;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ClientData")
@Table(name = "client_data")

public class ClientData {

    @Id
    @Column(name = "username")
    private int username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cnp")
    private int cnp;

    @Column(name = "email")
    private String email;

    @OneToMany(
            mappedBy = "client_data",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Accounts> accounts = new ArrayList<>();

    public ClientData() {
    }

}