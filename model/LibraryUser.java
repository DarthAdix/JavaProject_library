package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryUser extends User {
    private List<Publication> borrowHistory = new ArrayList<>();
    private List<Publication> borrowedPublications = new ArrayList<>();


    public LibraryUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    public List<Publication> getBorrowHistory() {
        return borrowHistory;
    }

    public List<Publication> getBorrowedPublications() {
        return borrowedPublications;
    }

    private void addPublicationToHistory(Publication publication){
        borrowHistory.add(publication);
    }

    public void borrowPublication(Publication publication){
        borrowedPublications.add(publication);
    }

    public boolean returnPublication(Publication publication){
        boolean result = false;
        if(borrowedPublications.remove(publication)){
            result = true;
            addPublicationToHistory(publication);
        }
        return result;
    }

    @Override
    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getPesel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LibraryUser that = (LibraryUser) o;
        return Objects.equals(borrowHistory, that.borrowHistory) &&
                Objects.equals(borrowedPublications, that.borrowedPublications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), borrowHistory, borrowedPublications);
    }
}
