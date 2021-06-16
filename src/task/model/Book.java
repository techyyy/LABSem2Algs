package task.model;

public class Book implements Comparable<Book>{

    private Author author;
    private String title;
    private int yearOfRelease;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    private Book() {
    }

    public static Book createBook(Author author, String title, int yearOfRelease) {
        if(title == null) throw new IllegalArgumentException("Title can't be null.");
        if(author == null) throw new IllegalArgumentException("Author can't be null.");
        Book book = new Book();
        book.setAuthor(author);
        book.setYearOfRelease(yearOfRelease);
        book.setTitle(title);
        author.getBooksContainer().add(book);
        return book;
    }

    private int hash(byte[] bytes) {
        int h = 0;
        for (byte value : bytes) {
            h = 31 * h + (value & 255);
        }
        return h;
    }

    @Override
    public int hashCode() {
        return hash((author.getFirstName() + author.getLastName() + title).getBytes());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book other = (Book) obj;
        return other.title.equals(this.title);
    }

    @Override
    public int compareTo(Book book) {
        return this.hashCode() - book.hashCode();
    }
}