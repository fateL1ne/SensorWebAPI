package sk.tuke.SensorWebApi.server.jpa.entities.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Persona
{

    private static final String SECRET_PASS = ":)))))";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "firstName", nullable = false, length = 30)
    private String firstName;

    @Column (name = "lastName", nullable = false, length = 30)
    private String lastName;

    @Column (name = "active", nullable = false, length = 30)
    private boolean active;

    @Column (name = "username", nullable = false, length = 30)
    private String username;

    @Column (name = "password", nullable = false, length = 60)
    @JsonIgnoreProperties
    /**
     * hide password field from the Jackson parser
     */

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + SECRET_PASS + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active='" + active + '\'' +
                '}';
    }

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
