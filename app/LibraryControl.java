package app;

import exception.*;
import io.ConsolePrinter;
import io.DataReader;
import io.file.FileManager;
import io.file.FileManagerBuilder;
import model.*;
import model.comparator.AlphabeticalTitleComparator;

import java.util.Arrays;
import java.util.InputMismatchException;

class LibraryControl {
    //zmienna do komunikacji z użytkownikiem
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę");
            library = new Library();
        }
    }

    //główna pętla kontroli programu
    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Wybrana opcja nie istnieje");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", podaj ponownie");
            } catch (InputMismatchException e) {
                printer.printLine("To nie liczba");
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję:");
        for (Option option : Option.values()) {
            System.out.println(option);
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Limit osiągnięty, nie można dodać");
        }

    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Limit osiągnięty, nie można dodać");
        }

    }

    private void addUser() {
        LibraryUser user = dataReader.createUser();
        try {
            library.addUser(user);
        } catch (UserAlreadyExistsException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printBooks() {
        printer.printBooks(library.getPublications().values());
    }

    private void printMagazines() {
        printer.printMagazines(library.getPublications().values());
    }

    private void printUsers() {
        printer.printUsers(library.getUsers().values());
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book)) {
                printer.printLine("Usunięto książkę");
            } else {
                printer.printLine("Brak wskazanej książki");
            }
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine)) {
                printer.printLine("Usunięto magazyn");
            } else {
                printer.printLine("Brak wskazanego magazynu");
            }
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        }
    }

//    private Publication[] getSortedPublications() {
//        Publication[] publications = library.getPublications();
//        Arrays.sort(publications, new AlphabeticalTitleComparator());
//        return publications;
//    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Wyeksportowano dane do pliku");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec programu");
        dataReader.close();

    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodaj książkę"),
        ADD_MAGAZINE(2, "Dodaj magazyn"),
        PRINT_BOOKS(3, "Wyświetl listę książek"),
        PRINT_MAGAZINES(4, "Wyświetl listę magazynów"),
        DELETE_BOOK(5, "Usuń książkę"),
        DELETE_MAGAZINE(6, "Usuń magazyn"),
        ADD_USER(7, "Dodaj czytelnika"),
        PRINT_USERS(8, "Wyświetl czytelników");

        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Nie ma opcji o id " + option);
            }
        }
    }
}
