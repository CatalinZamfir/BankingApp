package org.sda.BankingApp.types;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ClientData")
@Table(name = "client_data")

public class ClientData {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cnp")
    private long cnp;

    @Column(name = "email")
    private String email;

    @OneToMany(
            mappedBy = "client_data",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Account> account = new ArrayList<>();

    public ClientData() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }
}