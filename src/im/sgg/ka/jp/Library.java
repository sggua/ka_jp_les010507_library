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
    private int searchIteration;
    private int lastSearch = -1;    // 0 - byGenre, 1 - byAuthor
    private String lastSearchKey;
    private int searchPerCall;
    private int unicID;             // position in array of books
    private int qtyOfBooks;
    private int qtyOfAuthors;
    private int[] qtyOfBooksByGenre = new int[genres.length];
    private int[] qtyOfBooksByAuthor = new int[1];

    public Library() {
        this(5);
    }
    public Library(int searchPerCall) {
        this.searchPerCall = searchPerCall;
        this.books = new String[5][RECORD_LENGTH];
        this.authors = new String[5];
        this.sorted = new String[5][RECORD_LENGTH];
        this.byGenre = new String[genres.length][5][RECORD_LENGTH];
        this.byAuthor = new String[5][5][RECORD_LENGTH];
    }

    public void addBook(String name, String author, String genre){
        outln("\nAdding the book \""+name+"\" by "+author+", "+genre+".");
        if (isGenreOk(genre)) genre=getGenre(genre);
        else {outError("Incorrect genre \""+genre+"\".");return;}
        if (isBookInLibrary(name,author,genre)) {
            outWarning("This book (\""+name+"\" by "+author+", "+genre+") is already added to the library.");
            return;
        }
        unicID++;
        qtyOfBooks++;

        addBookToBookList   (name, author, genre);
        addBookToGenreList  (name, author, genre);
        addBookToAuthorList (name, author, genre);

    }

    private void addBookToBookList(String name, String author, String genre){
        out("\t\t to the general list . . .");
        addBooks();
        books[unicID][0]=name;
        books[unicID][1]=author;
        books[unicID][2]=genre;
        outln("\tdone.");
    }

    private void addBookToGenreList(String name, String author, String genre){
        out("\t\t to the genre's list . . .");
        int id = getGenID(genre);
        int maxId = getByGenreMaxIDX(genre);
        addByGenre(id);
        byGenre[id][maxId][0]=name;
        byGenre[id][maxId][1]=author;
        qtyOfBooksByGenre[id]++;
        outln("\tdone.");
    }

    private void addBookToAuthorList(String name, String author, String genre){
        out("\t\t to the author's list . . .");
        int id = getAuthorID(author);
        addAuthor(author);
        int maxId = getByAuthorMaxIDX(author);
        addByAuthor(id);
        byAuthor[id][maxId][0]=name;
        byAuthor[id][maxId][1]=genre;
        qtyOfBooksByAuthor[id]++;
        outln("\tdone.");
    }



    public void findByGenre(String genre){
        if (lastSearch!=0 || ! lastSearchKey.equals(genre)) {
            lastSearch=0;
            searchCounter=0;
            searchIteration=1;
            lastSearchKey=genre;
        }
        findByGenre(genre,searchCounter);
        searchCounter+=searchPerCall;
        searchIteration++;
    }
    public void findbyAuthor(String author){
        if (lastSearch!=1 || ! lastSearchKey.equals(author)) {
            lastSearch=1;
            searchCounter=0;
            searchIteration=1;
            lastSearchKey=author;
        }
        findbyAuthor(author,searchCounter);
        searchCounter+=searchPerCall;
        searchIteration++;
    }

    private void findByGenre(String genre, int from){
        outln("\nSearching books by genre \""+genre+"\"\tIteration #"+searchIteration);
        int id = getGenID(genre);
        if (id<0) {
            outError("Incorrect genre: "+genre);
            return;
        }
        int to = from+searchPerCall;
        if (to>byGenre[id].length) to = byGenre[id].length;
        for (int i=from; i<to; i++)
            if (isArray(byGenre[id]) && byGenre[id][i]!=null && byGenre[id][i][0]!=null && byGenre[id][i][1]!=null)
                outln("\t["+genre+"]\t" + byGenre[id][i][0]
                        +", "+byGenre[id][i][1]);
    }

    private void findbyAuthor(String author, int from){
        outln("\nSearching books by author \""+author+"\"\tIteration #"+searchIteration);
        int id=getAuthorID(author);
        if (id<0) {
            outError("Author not found: "+author);
            return;
        }
        int to = from+searchPerCall;
        if (to>byAuthor[id].length) to = byAuthor[id].length;
        for (int i=from; i<to; i++)
            if (byAuthor[id][i]!=null && byAuthor[id][i][0]!=null && byAuthor[id][i][1]!=null)
                outln("\t["+author+"]\t" + byAuthor[id][i][0]+", "+byAuthor[id][i][1]);
    }


    private int getByAuthorMaxIDX(String a) {
        if (qtyOfBooksByAuthor==null) return 0;
        else return qtyOfBooksByAuthor[getAuthorID(a)];
    }

    private int getByGenreMaxIDX(String g) {
        return qtyOfBooksByGenre[getGenID(g)];
    }

    private int getAuthorID(String a){
        if (authors==null || authors.length<1) return -1;
        for (int i=0;i<authors.length;i++){
            if (authors[i]!=null && authors[i].equals(a)) return i;
            if (authors[i]==null) return i;
        }
        return qtyOfAuthors; // new author
    }
    private int getGenID(String g){
        for (int i=0;i<genres.length;i++){
            if (genres[i].equals(g)) return i;
        }
        return -1;
    }

    public boolean isBookInLibrary (String name, String author, String genre){
        if (byGenre==null) return false;
        int id = getGenID(genre);
        for (String g[] : byGenre[id])
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

    private void addAuthor(String a){
        if (! existsAuthor(a)) {
            if (authors.length-5<=qtyOfAuthors) addAuthors();
            authors[qtyOfAuthors]=a;
            while (qtyOfBooksByAuthor==null || qtyOfBooksByAuthor.length-1<qtyOfAuthors) {
                qtyOfBooksByAuthor=addArrayCells(qtyOfBooksByAuthor);
            }
            qtyOfBooksByAuthor[qtyOfAuthors]=0;
            qtyOfAuthors++;
        }
    }

    private boolean existsAuthor(String a){
        for (String aut:authors) if (aut!=null && aut.equals(a)) return true;
        return false;
    }

    private void addAuthors(){
        if (! isAuthorsOK() ) return;
        authors = addArrayCells(authors);
    }

    private void addBooks(){
        if (! isBooksOK() ) return;
        books = addArrayCells(books);
    }

    private void addByGenre(int genreID){
        if (! isByGenreOK() || qtyOfBooksByGenre[genreID]<byGenre[genreID].length) return;
        byGenre[genreID] = addArrayCells(byGenre[genreID]);
    }

    private void addByAuthor(int authID){
        if (! isByAuthorOK() ) return;
        if (qtyOfBooksByAuthor==null || qtyOfBooksByAuthor[authID]>=byAuthor[authID].length ){
            byAuthor[authID] = addArrayCells(byAuthor[authID]);
        }
    }

    private String[][] addArrayCells (String[][] arr) {
        int newSize = (int) (arr.length * 1.30);    // +30%
        if (newSize < 3) newSize = 3;    // tail for 3 records if short array
        String[][] newArray = new String[newSize][RECORD_LENGTH];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i][0] != null)
                System.arraycopy(arr[i], 0, newArray[i], 0, arr[i].length);
        }
        return newArray;
    }

    private int[] addArrayCells (int[] arr) {
        int newSize;
        if (arr==null) {
            newSize = 3;    // +3
        } else {
            newSize = (int) (arr.length * 1.30);    // +30%
        }
        if (newSize < 3) newSize = 3;    // tail for 3 records if short array
        int[] newArray = new int[newSize];
        if (arr!=null) System.arraycopy(arr, 0, newArray, 0, arr.length);
        return newArray;
    }

    private String[] addArrayCells (String[] arr) {
        int newSize = (int) (arr.length * 1.30);    // +30%
        if (newSize < 3) newSize = 3;    // tail for 3 records if short array
        String[] newArray = new String[newSize];
        System.arraycopy(arr, 0, newArray, 0, arr.length);
        return newArray;
    }

    private boolean isByAuthorOK(){
        if (! isArray(byAuthor)) {
            outError("byAuthor=null or empty");
            return false;
        }
        return true;
    }
    private boolean isByGenreOK(){
        if (! isArray(byGenre)) {
            outError("byGenre=null or empty");
            return false;
        }
        return true;
    }

    private boolean isBooksOK(){
        if (! isArray(books)) {
            outError("books=null or empty");
            return false;
        }
        return true;
    }

    private boolean isAuthorsOK(){
        if (! isArray(authors)) {
            outError("authors=null or empty");
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
    private boolean isArray(String[][][] strArray) {
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
        outln("ERROR:\t"+s);
    }
    private void outWarning(String s){
        outln("WARNING:\t"+s);
    }
    private void outln(String s){
        out(s+"\n");
    }
    private void out(String s){
        System.out.print(s);
    }
}
