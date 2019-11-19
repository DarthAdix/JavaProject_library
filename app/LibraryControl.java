package app;

import exception.NoSuchOptionException;
import io.ConsolePrinter;
import io.DataReader;
import model.Library;
import model.Book;
import model.Magazine;
import model.Publication;

import java.util.InputMismatchException;

public class LibraryControl {
    //zmienna do komunikacji z użytkownikiem
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);

    //klasa przechowująca dane
    private Library library = new Library();

    //główna pętla kontroli programu
    public void controlLoop(){
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option){
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
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Wybrana opcja nie istnieje");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption(){
        boolean optionOk = false;
        Option option = null;
        while (!optionOk){
            try{
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e){
                printer.printLine(e.getMessage() + ", podaj ponownie");
            } catch (InputMismatchException e){
                printer.printLine("To nie liczba");
            }
        }
        return option;
    }
    private void printOptions(){
        printer.printLine("Wybierz opcję:");
        for(Option option: Option.values()){
            System.out.println(option);
        }
    }

    private void addBook(){
        try {
            Book book = dataReader.readAndCreateBook();
            library.addBook(book);
        } catch (InputMismatchException e){
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e){
            printer.printLine("Limit osiągnięty, nie można dodać");
        }

    }

    private void printBooks(){
        Publication[] publications = library.getPublications();
        printer.printBooks(publications);
    }

    private void addMagazine(){
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addMagazine(magazine);
        } catch (InputMismatchException e){
            printer.printLine("Nie udało się utworzyć, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e){
            printer.printLine("Limit osiągnięty, nie można dodać");
        }

    }

    private void printMagazines(){
        Publication[] publications = library.getPublications();
        printer.printMagazines(publications);
    }

    private void exit(){
        printer.printLine("Koniec programu");
        dataReader.close();

    }
}
