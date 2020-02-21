package model;

import exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {
    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator){
        ArrayList<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator){
        ArrayList<LibraryUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public void addUser(LibraryUser user){
        if (users.containsKey(user.getPesel())){
            throw new UserAlreadyExistsException("Użystkownik o peselu: " + user.getPesel() + " już istnieje");
        }
        users.put(user.getPesel(), user);
    }

    public void addBook (Book book){
        addPublication(book);
    }

    public void addMagazine (Magazine magazine){
        addPublication(magazine);
    }

    public void addPublication (Publication publication){
        if (publications.containsKey(publication.getTitle())){
            throw new UserAlreadyExistsException("Publikacja o tytule: " + publication.getTitle() + " już istnieje");
        }
        publications.put(publication.getTitle(), publication);
    }

    public boolean removePublication (Publication publication){
        if (publications.containsValue(publication)){
            publications.remove(publication.getTitle());
            return true;
        } else {
            return false;
        }
    }
}
