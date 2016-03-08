package im.sgg.ka.jp;

public class _Launcher {

    public static void main(String[] args) {
	    Library lib = new Library();

        lib.addBook("Java Programming", "Oleg Yuschenko",   "Computer");
        lib.addBook("Java Programming", "Oleg Yuschenko",   "computers");  // lower case
        lib.addBook("Java Programming", "Oleg Yuschenko",   "Computers");
        lib.addBook("Java Bases",       "Oleg Yuschenko",   "Computers");
        lib.addBook("Book3",            "Oleg Yuschenko",   "Computers");
        lib.addBook("Book4",            "Oleg Yuschenko",   "Computers");
        lib.addBook("Book5",            "Oleg Yuschenko",   "Computers");
        lib.addBook("Book6",            "Oleg Yuschenko",   "Computers");

        lib.addBook("Java for Kids",    "Yakov Fain",       "computers");
        lib.addBook("Java in 24Hours",  "Yakov Fain",       "Computers");
        lib.addBook("Enterprise Web Development",    "Yakov Fain",       "computers");

        lib.addBook("Java for Dummies", "Barry Burd",       "Computers");

        lib.findByGenre("Computer");
        lib.findByGenre("Computers");
        lib.findByGenre("Computers");
        lib.findByGenre("Computers");

        lib.findbyAuthor("Oleg Yuschenko");

        lib.findByGenre("Computers");

    }
}
