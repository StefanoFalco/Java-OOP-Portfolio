package social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Social {

    private final Map<String, Person> registered = new LinkedHashMap<>();
    private final Map<String, Set<String>> groups = new LinkedHashMap<>();

    /**
     * Creates a new account for a person.
     */
    public void addPerson(String code, String name, String surname) throws PersonExistsException {
        if(registered.containsKey(code)) {
            throw new PersonExistsException();
        }

        registered.put(code, new Person(code, name, surname));
    }

    /**
     * Retrieves information about the person given their account code.
     */
    public String getPerson(String code) throws NoSuchCodeException {
        Person person = registered.get(code);

        if(person == null) {
            throw new NoSuchCodeException();
        }

        return person.toString();
    }

    /**
     * Defines a bidirectional friendship relationship.
     */
    public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
        Person p1 = getPersonObject(codePerson1);
        Person p2 = getPersonObject(codePerson2);

        p1.addFriend(p2);
        p2.addFriend(p1);
    }

    /**
     * Retrieves first-level friends.
     */
    public Collection<String> listOfFriends(String codePerson) throws NoSuchCodeException {
        Person person = getPersonObject(codePerson);

        return person.getFriends().stream()
            .map(Person::getCode)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Retrieves second-level friends, preserving repetitions and excluding the starting person.
     */
    public Collection<String> friendsOfFriends(String codePerson) throws NoSuchCodeException {
        Person person = getPersonObject(codePerson);
        Collection<String> result = new ArrayList<>();

        for(Person friend : person.getFriends()) {
            for(Person secondLevel : friend.getFriends()) {
                if(!secondLevel.getCode().equals(codePerson)) {
                    result.add(secondLevel.getCode());
                }
            }
        }

        return result;
    }

    /**
     * Retrieves second-level friends without repetitions and excluding the starting person.
     */
    public Collection<String> friendsOfFriendsNoRepetition(String codePerson) throws NoSuchCodeException {
        return friendsOfFriends(codePerson).stream()
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Creates a new group with a single-word name.
     */
    public void addGroup(String groupName) {
        if(groupName == null) {
            return;
        }

        String trimmed = groupName.trim();

        if(trimmed.isEmpty() || trimmed.contains(" ")) {
            return;
        }

        groups.putIfAbsent(trimmed, new LinkedHashSet<>());
    }

    /**
     * Retrieves the list of groups.
     */
    public Collection<String> listOfGroups() {
        return new ArrayList<>(groups.keySet());
    }

    /**
     * Adds a person to a group.
     */
    public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
        Person person = getPersonObject(codePerson);
        Set<String> members = groups.get(groupName);

        if(members == null) {
            throw new NoSuchCodeException();
        }

        members.add(codePerson);
        person.addGroup(groupName);
    }

    /**
     * Retrieves the list of people in a group.
     */
    public Collection<String> listOfPeopleInGroup(String groupName) {
        Set<String> members = groups.get(groupName);

        if(members == null) {
            return null;
        }

        return new ArrayList<>(members);
    }

    /**
     * Retrieves the code of the person having the largest number of first-level friends.
     */
    public String personWithLargestNumberOfFriends() {
        return registered.values().stream()
            .max(Comparator.comparingInt(Person::getNumberOfFriends))
            .map(Person::getCode)
            .orElse(null);
    }

    /**
     * Retrieves the code of the person having the largest number of second-level friends.
     *
     * Repetitions are counted, while the starting person is excluded.
     * Ties are resolved in favor of the later registered person. This matches
     * the original coursework examples.
     */
    public String personWithMostFriendsOfFriends() {
        String bestCode = null;
        int bestScore = -1;

        for(Person person : registered.values()) {
            int score = 0;

            for(Person friend : person.getFriends()) {
                for(Person secondLevel : friend.getFriends()) {
                    if(!secondLevel.getCode().equals(person.getCode())) {
                        score++;
                    }
                }
            }

            if(score >= bestScore) {
                bestScore = score;
                bestCode = person.getCode();
            }
        }

        return bestCode;
    }

    /**
     * Retrieves the name of the group with the largest number of members.
     */
    public String largestGroup() {
        return groups.entrySet().stream()
            .max(Comparator.comparingInt(e -> e.getValue().size()))
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Retrieves the code of the person subscribed to the largest number of groups.
     */
    public String personInLargestNumberOfGroups() {
        return registered.values().stream()
            .max(Comparator.comparingInt(Person::getNumberOfGroups))
            .map(Person::getCode)
            .orElse(null);
    }

    private Person getPersonObject(String code) throws NoSuchCodeException {
        Person person = registered.get(code);

        if(person == null) {
            throw new NoSuchCodeException();
        }

        return person;
    }
}