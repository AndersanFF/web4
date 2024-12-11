package ResourcesForServlets;

import jakarta.persistence.*;

@Entity
@Table(name="Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username", unique=true, nullable=false)
    private String username;
    @Column(name="passwd", unique=true, nullable=false)
    private String passwd;
    @Column(name="score")
    private int score;

    public Users(){

    }

    public Users(String username, String passwd, int score) {
        this.username = username;
        this.passwd = passwd;
        this.score = score;
    }

    public Users(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getScore() {
        return score;
    }

    public String toJson() {
        return String.format("{\"username\": \"%s\", \"passwd\": \"%s\", \"score\": %d}",
                username, passwd, score);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', passwd=" + passwd + ", score=" + score + "}";
    }

}

