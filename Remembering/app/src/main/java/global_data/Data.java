package global_data;

import android.app.Application;
import db.DB_Book;
import db.DB_Note;
import db.DB_Record;
import db.DB_Word;
import logic_class.User;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;
import logic_class.word_class.Word2Review;
import logic_class.word_class.WordList;

import java.util.*;

/*
Reviewing for wordlist book
 */

public class Data extends Application {
    private WordList wordList;
    private VocabularyBook ReviewingBook;
    private User user;
    private Set<Word> Notes;
    private Set<VocabularyBook> vocabularyBooks;

    //for wordList
    public void BuildWordList()
    {
        if (wordList==null)
            setWordList();
    }

    public void setWordList() {
        WordList wordList=new WordList(user.getAmount()*10,user.getAmount()*50,user.getLearningBook());
        this.wordList = wordList;
        this.ReviewingBook=user.getLearningBook();
    }

    public Word2Review sendWord() {
        Word2Review word=wordList.sendWord();
        Iterator<Word> iterator=Notes.iterator();
        while (iterator.hasNext())
        {
            Word noteWord=iterator.next();
            if (noteWord.getEnglish().equals(word.getWordEnglish()))
            {
                word.setWordNote(noteWord.getNote());
                break;
            }
        }
        return word;
    }

    public int getRemainNew() {
        return wordList.getNew_num();
    }

    public int getRemainOld() {
        return wordList.getOld_num();
    }

    public int getRemainAll() {
        return wordList.getAll_num();
    }

    public int getListAmount()
    {
        return wordList.getList_Amount();
    }

    public boolean isReviewEnd() {
        return wordList.isEmpty();
    }

    public void deleteFromWordList(Word2Review w) {
        wordList.deleteFromList(w);
    }

    public void cancelDelete(Word2Review w)
    {
        wordList.cancelDelete(w);
    }

    public void updateRecord(Word word)
    {
//        String book=word.getVB();
//        Iterator<VocabularyBook> bookIterator=vocabularyBooks.iterator();
//        while (bookIterator.hasNext())
//        {
//            VocabularyBook v=bookIterator.next();
//            if (book.equals(v.getName()))
//            {

        VocabularyBook v=ReviewingBook;
        v.deletefromBook(word);
        v.add2Book(word);
        DB_Record db_record=new DB_Record(word,v,this.user.getEmail());
        db_record.start();
//                    DB_Word db_word=new DB_Word(false,word,v);
//                    db_word.start();
//                break;
//            }
//        }
    }

    public String getTitle(){
        if (wordList.isEmpty())
            return user.getLearningBook().getName();
        else
            return ReviewingBook.getName();
    }

    public boolean isBookOver()
    {
        return user.getLearningBook().isOver();
    }

    //for User
    public void setUser(User user)
    { ;
        this.user=user;
        Notes=user.getNoteSet();
        vocabularyBooks=user.getBookSet();
//        Log.e("Data:",user.getName());
    }

    public int getDays() {
        return user.getDays();
    }

    public Date getLastDate()
    {
        return user.getLastDate();
    }

    public String getName() {
        return user.getName();
    }

    public String getSign() {
        return user.getSign();
    }

    public int getAvatar() {
        return user.getAvatar();
    }

    public int getMode() {
        return user.getMode();
    }

    public int getAmount() {
        return user.getAmount();
    }

    public VocabularyBook getLearningBook()
    {
        return user.getLearningBook();
    }

    public void updateDate(int days,Date date)
    {
        user.setDays(days);
        user.setLastDate(date);
        user.updateDate();
    }

    public void setMode(int mode) {
        user.changeMode(mode);
    }

    public void setAmount(int amount) {
        user.changeAmount(amount);
    }

    public void setName(String name) {
        user.changeName(name);
    }

    public void setSign(String sign) {
        user.changeSign(sign);
    }

    public boolean checkPassword(String pwd) throws InterruptedException {
        return user.checkPassword(pwd);
    }

    public void setPassword(String pwd) {
        user.changePassword(pwd);
    }

    public void setVocabularyBook(VocabularyBook vb)
    {
        user.changeLearningBook(vb);
    }



    //to be replace
    public String getEmail()
    {
        return user.getEmail();
    }

    //for note list

    public List<Word> getNoteData() {
        List<Word> noteData=new ArrayList<>(Notes);
        Collections.sort(noteData);
        return noteData;
    }

    public boolean add2Note(Word w) {
        DB_Note db_note=new DB_Note(1,w,user.getEmail());
        db_note.start();
        return  Notes.add(w);
    }

    public boolean deletefromNote(Word w)
    {
        DB_Note db_note=new DB_Note(2,w,user.getEmail());
        db_note.start();
        return Notes.remove(w);
    }

    public void updateNote(Word W)
    {
        DB_Note db_note=new DB_Note(3,W,user.getEmail());
        db_note.start();
        Notes.remove(W);
        Notes.add(W);
    }
    
    //for word

    public void createWordinBook(Word word)
    {
        String book=word.getVB();
        Iterator<VocabularyBook> bookIterator=vocabularyBooks.iterator();
        while (bookIterator.hasNext())
        {
            VocabularyBook v=bookIterator.next();
            if (book.equals(v.getName()))
            {
                v.add2Book(word);
                DB_Word db_word=new DB_Word(true,word,v,user.getEmail());
                db_word.start();
                break;
            }
        }
    }

    public void updateWordinBook(Word word)
    {
        String book=word.getVB();
        Iterator<VocabularyBook> bookIterator=vocabularyBooks.iterator();
        while (bookIterator.hasNext())
        {
            VocabularyBook v=bookIterator.next();
            if (book.equals(v.getName()))
            {
                v.deletefromBook(word);
                v.add2Book(word);
                DB_Word db_word=new DB_Word(false,word,v);
                db_word.start();
                break;
            }
        }
    }


    public List<VocabularyBook> getBookList()
    {
        List<VocabularyBook> books=new ArrayList<>(vocabularyBooks);
        Collections.sort(books);
        return books;
    }

    public void addBook2DataBase(VocabularyBook vocabularyBook)
    {
        DB_Book db_book=new DB_Book(vocabularyBook,false,user.getEmail());
        db_book.start();
        add2BookList(vocabularyBook);
    }

    public boolean add2BookList(VocabularyBook vocabularyBook)
    {
        return vocabularyBooks.add(vocabularyBook);
    }

    public boolean deletefromBookList(VocabularyBook vocabularyBook)
    {
        return vocabularyBooks.remove(vocabularyBook);
    }

    public void updateBook(VocabularyBook vocabularyBook)
    {
        DB_Book db_book=new DB_Book(vocabularyBook,true,user.getEmail());
        db_book.start();
        deletefromBookList(vocabularyBook);
        add2BookList(vocabularyBook);
    }
}
