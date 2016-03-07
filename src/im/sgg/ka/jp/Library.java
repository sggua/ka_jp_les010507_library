package im.sgg.ka.jp;

/**
 * Created by sergiy on 06.03.16.
 * Java Programmer lessons
 * kademika.com
 */
public class Library {
    private String[] headLine = {"Name","Author","Genre"} ;
    private int RECORD_LENGTH=headLine.length;
    private String[][] books;
    private String[][] sorted;
    private String[] genres = {"Fantasy","Food","Computers","Fiction","History"};
    private String[][][] byGenre;
    private String[][][] byAuthor;
    private String[] authors;
    private int byGenreID;
    private int byAuthorID;
    private int searchCounter;
    private int lastSearch = -1;    // 0 - byGenre, 1 - byAuthor
    private int searchPerCall;
    private int unicID;             // position in array of books
    private int qtyOfBooks;

    public Library() {
        this(5);
    }
    public Library(int searchPerCall) {
        this.searchPerCall = searchPerCall;
        this.books = new String[1][RECORD_LENGTH];
        this.authors = new String[1];
        this.sorted = new String[1][RECORD_LENGTH];
        this.byGenre = new String[genres.length][1][RECORD_LENGTH];
        this.byAuthor = new String[1][1][RECORD_LENGTH];
    }

    public int getSearchPerCall() {
        return searchPerCall;
    }

    public void addBook(String name, String author, String genre){
        if (isGenreOk(genre)) genre=getGenre(genre);
        else {outError("Incorrect genre \""+genre+"\".");return;}
        if (isBookInLibrary(name,author,genre)) {
            out("This book (\""+name+"\" by "+author+", "+genre+") is already added to the library");
            return;
        }
        unicID+=1;
        qtyOfBooks+=1;
        addBooks();
        books[unicID][0]=name;
        books[unicID][1]=author;
        books[unicID][2]=genre;
//        byGenre[getGenID(genre)][getByGenreMaxIDX(genre)][0]=name;
//        byGenre[getGenID(genre)][getByGenreMaxIDX(genre)][1]=author;
//        byAuthor[getAuthorID(author)][getByAuthorMaxIDX(author)][0]=name;
//        byAuthor[getAuthorID(author)][getByAuthorMaxIDX(author)][1]=genre;

    }

    public void findByGenre(String genre){
        if (lastSearch!=0) {
            lastSearch=0;
            searchCounter=0;
        }
        findByGenre(genre,searchCounter);
        searchCounter+=searchPerCall;
    }
    public void findbyAuthor(String author){
        if (lastSearch!=1) {
            lastSearch=1;
            searchCounter=0;
        }
        findbyAuthor(author,searchCounter);
        searchCounter+=searchPerCall;
    }
    public void findByGenre(String genre, int from){
        for (int i=from; i<from+searchPerCall; i++)
            if (byGenre[getGenID(genre)][i]!=null)
                out ("["+genre+"]\t" + byGenre[getGenID(genre)][i][0]
                        +", "+byGenre[getGenID(genre)][i][1]);
    }

    public void findbyAuthor(String author, int from){
        for (int i=from; i<from+searchPerCall; i++)
            if (byAuthor[getAuthorID(author)][i]!=null)
            out ("["+author+"]\t" + byAuthor[getAuthorID(author)][i][0]+", "+byAuthor[getAuthorID(author)][i][1]);
    }


    private int getByAuthorMaxIDX(String g) {
        return byGenre[getGenID(g)].length;
    }

    private int getByGenreMaxIDX(String g) {
        return byGenre[getGenID(g)].length;
    }

    private int getAuthorID(String a){
        if (authors==null || authors.length<1) return 0;
        for (int i=0;i<authors.length;i++){
            if (genres[i].equals(a)) return i;
        }
        return authors.length; // new author
    }
    private int getGenID(String g){
        for (int i=0;i<genres.length;i++){
            if (genres[i].equals(g)) return i;
        }
        return -1;
    }

    public boolean isBookInLibrary (String name, String author, String genre){
        if (byGenre==null) return false;
        for (String g[] : byGenre[getGenID(genre)])
            if (g!=null && g[0]!=null && g[0].equals(name) && g[1].equals(author)) return true;
        return false;
    }

    public boolean isGenreOk(String g){
        return !getGenre(g).equals("");
    }

    private String getGenre(String str){
        if (str.equals("")) return "";
        for (String g:genres) {
            if (g.toLowerCase().equals(str.toLowerCase())) return g;
        }
        return "";
    }
    private void addBooks(){
        if (! isBooksOK() ) return;
        int newSize=(int)(books.length*1.30);	// +30%
        while (newSize-qtyOfBooks<3) newSize++;	// tail for 3 records if short array
        String[][] newArray = new String[newSize][RECORD_LENGTH];
//        if (books.length>0) {
            for (int i=0;i<qtyOfBooks;i++) 	{
                if (books[i]!=null && books[i][0]!=null)
                    System.arraycopy(books[i],
                            0,
                            newArray[i], 0,
                            books[i].length);
            }
//        }
        books=newArray;
    }

    private boolean isBooksOK(){
        if (! isArray(books)) {
            outError("books=null or empty");
            return false;
        }
        return true;
    }
    private boolean isArray(int[] intArray){
        if (intArray==null || intArray.length<1 )	{
            outError("intArray=null or shorter than 1");
            return false;
        } else{
            return true;
        }
    }
    private boolean isArray(String[][] strArray){
        if (strArray==null || strArray.length<1 )	{
            outError("strArray=null or shorter than 1");
            return false;
        } else{
            return true;
        }
    }
    private boolean isArray(String[] strArray){
        if (strArray==null || strArray.length<1 )	{
            outError("strArray=null or shorter than 1");
            return false;
        } else{
            return true;
        }
    }

    private void outError(String s){
        out("ERROR:\t"+s);
    }
    private void out(String s){
        System.out.println(s);
    }
}
