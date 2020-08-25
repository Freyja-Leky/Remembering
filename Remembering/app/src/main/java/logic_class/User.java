package logic_class;

/*
info part
name/sign/email/avatar/bookSet/NoteSet

learning setting part
days/amount/mode/LearningBook

record day
last Date
 */

import com.example.freyja.testthree.R;
import db.*;
import logic_class.word_class.Word;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class User {

    //info
    private String name;
    private String email;
    private String sign;
    private int avatar;
    private Date lastDate;
    private int days;
    private Set<VocabularyBook> bookSet;
    private Set<Word> noteSet;

    //for learning setting
    //1 for remember;2 for spell
    private int mode;
    //50
    private int amount;
    private VocabularyBook LearningBook;


    //for database
    public User(String email){
        this.email=email;
        avatar= R.drawable.avatar;
        bookSet=new HashSet<VocabularyBook>();
        noteSet=new HashSet<Word>();
    }

    //for new user
    public User(String email,String name) throws ParseException {
        this.email=email;
        this.name=name;
        this.sign="";
        this.days=0;
        this.mode=1;
        this.amount=1;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        this.lastDate=dateFormat.parse("2016-12-1");
        avatar= R.drawable.avatar;
        bookSet=new HashSet<VocabularyBook>();
        noteSet=new HashSet<Word>();

        LearningBook=new VocabularyBook("testbook2","");
        DB_Search_Book db_search_book=new DB_Search_Book(LearningBook,email);
        db_search_book.start();
        try {
            db_search_book.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LearningBook=db_search_book.getVocabularyBook();
    }

    //for user info
    public String getName() {
        return name;
    }

    public void changeName(String name) {
        DB_Change_Info db_change_info=new DB_Change_Info(name,true,this.email);
        db_change_info.start();
        setName(name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public boolean checkPassword(String pwd) throws InterruptedException {
        DB_Check_Password db_check_password=new DB_Check_Password(this.email,pwd);
        db_check_password.start();
        db_check_password.join();
        return db_check_password.getResult();
    }

    public void changePassword(String pwd)
    {
        DB_Change_Password db_change_password=new DB_Change_Password(this.email,pwd);
        db_change_password.start();
    }

    public String getSign() {
        return sign;
    }

    public void changeSign(String sign) {
        DB_Change_Info db_change_info=new DB_Change_Info(sign,false,this.email);
        db_change_info.start();
        setSign(sign);
    }

    public void setSign(String sign){
        this.sign = sign;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public Set<Word> getNoteSet()
    {
       return noteSet;
    }

    public void addNote(Word word)
    {
        noteSet.add(word);
    }

    public Set<VocabularyBook> getBookSet()
    {
        return bookSet;
    }

    public boolean addBook(VocabularyBook vocabularyBook)
    {
        return bookSet.add(vocabularyBook);
    }
    //for learning setting

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void changeMode(int mode)
    {
        DB_Change_Mode db_change_mode=new DB_Change_Mode(mode,true,this.email);
        db_change_mode.start();
        setMode(mode);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void changeAmount(int amount)
    {
        DB_Change_Mode db_change_mode=new DB_Change_Mode(amount,false,this.email);
        db_change_mode.start();
        setAmount(amount);
    }

    public VocabularyBook getLearningBook() {
        return LearningBook;
    }

    public void setLearningBook(VocabularyBook book) {
        this.LearningBook = book;
    }

    public void changeLearningBook(VocabularyBook book)
    {
        DB_Change_Mode db_change_mode=new DB_Change_Mode(book.getName(),this.email);
        db_change_mode.start();
        setLearningBook(book);
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public void updateDate()
    {
        DB_Date db_date=new DB_Date(this.lastDate,this.days,this.email);
        db_date.start();
    }
}
