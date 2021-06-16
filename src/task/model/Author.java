package task.model;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private String firstName;
    private String lastName;
    private List<Book> authoredBooks = new ArrayList<>();

    private Author() {
    }

    public static Author createAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return author;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooksContainer() {
        return authoredBooks;
    }
}
