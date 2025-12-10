package org.s3team.Player.Model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

public class Player {

    private Id<Player> id;
    private Name name;
    private String email;
    private boolean subscribed;

    public Player() {
    }

    public Player(Name name, String email, boolean subscribed) {
        this.name = name;
        this.email = email;
        this.subscribed = subscribed;
    }

    public Id<Player> getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name=" + name +
                ", email='" + email + '\'' +
                ", subscribed=" + subscribed +
                '}';
    }
}
