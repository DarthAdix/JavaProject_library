package model;

import java.io.Serializable;

public class Library implements Serializable {
    private static final int MAX_PUBLICATIONS = 2000;
    private Publication[] publications = new Publication[MAX_PUBLICATIONS];
    private int publicationsNumber;

    public Publication[] getPublications() {
        Publication[] result = new Publication[MAX_PUBLICATIONS];
        for (int i = 0; i < publicationsNumber; i++) {
            result[i] = publications[i];
        }
        return result;
    }

    public void addBook (Book book){
        addPublication(book);
    }

    public void addMagazine (Magazine magazine){
        addPublication(magazine);
    }

    public void addPublication (Publication publication){
        if (publicationsNumber >= MAX_PUBLICATIONS){
            throw new ArrayIndexOutOfBoundsException("Maksymalna ilość publikacji osągnięta " + MAX_PUBLICATIONS);
        }
        publications[publicationsNumber] = publication;
        publicationsNumber++;
    }
}
