package org.s3team.Player.Model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

public class Player implements Subscriber {

    private final Id<Player> id;
    private final Name name;
    private final Email email;
    private final boolean subscribed;


    public Player(Id<Player> id, Name name, Email email, boolean subscribed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subscribed = subscribed;
    }

    public static Player create(String name, String email, boolean subscribed) {
        return new Player(null, new Name(name), new Email(email), subscribed);
    }


    public static Player rehydrate(int id, String name, String email, boolean subscribed) {
        return new Player(new Id<Player>(id), new Name(name), new Email(email), subscribed);
    }

    public Id<Player> getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", subscribed=" + subscribed +
                '}';
    }

    @Override
    public void notification(String message) {
        System.out.println("NOTIFICATION to " + name + ":" + message);
    }
}
