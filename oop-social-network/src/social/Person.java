package social;

import java.util.LinkedHashSet;
import java.util.Set;

public class Person {

    private final String code;
    private final String name;
    private final String surname;
    private final Set<Person> friends = new LinkedHashSet<>();
    private final Set<String> groups = new LinkedHashSet<>();

    public Person(String code, String name, String surname) {
        this.code = code;
        this.name = name;
        this.surname = surname;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addFriend(Person person) {
        if(person != null && person != this) {
            friends.add(person);
        }
    }

    public Set<Person> getfriends() {
        return friends;
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public void addGroup(String groupName) {
        if(groupName != null) {
            groups.add(groupName);
        }
    }

    public Set<String> getGroups() {
        return groups;
    }

    public int getNumberOfFriends() {
        return friends.size();
    }

    public int getNumberOfGroups() {
        return groups.size();
    }

    @Override
    public String toString() {
        return code + " " + name + " " + surname;
    }
}