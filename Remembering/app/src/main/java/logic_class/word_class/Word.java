package logic_class.word_class;

import global_data.Data;
import logic_class.VocabularyBook;

import java.util.Set;

/*
Word can only be create by word create time
English can be set only word create time
Chinese can be modified by word change
Note can be set by word create and modified by note change/create/ word change
if Note = null then it should be removed from data, if Note be created it should be add to data
Hint can be set by word create and modified by word change
ReviewTime should be 0 at first, =0 for new word, 0<rt<3 for old word,
Easy should be true by create time, can be modified only through review time, word easy will not be chosen from book to wrod list
VB should be set at least one at word create time to link to a book,then can be add to other books
 */
public class Word implements Comparable<Word> {

    private String English;
    private String Chinese;
    private String Hint;
    private String Note;
    private int ReviewTime;
    private boolean Easy;
    private String VB;

    //for note
    public Word(String English,String note)
    {
        this.English=English;
        this.Note=note;
    }

    //for word
    public Word(String english,String chinese,String book)
    {
        this.English=english;
        this.Chinese=chinese;
        this.Note="";
        this.Hint="";
        this.ReviewTime=0;
        this.Easy=false;
        this.VB=book;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Word))
            return false;
        Word word = (Word)obj;
        return this.English.equals(word.English);
    }

    @Override
    public int hashCode()
    {
        return English.hashCode();
    }

    @Override
    public int compareTo(Word word)
    {
        return  this.English.compareTo(word.English);
    }

    //get
    public String getEnglish() {
        return English;
    }

    public String getChinese() {
        return Chinese;
    }

    public String getNote() {
        return Note;
    }

    public String getHint() {
        return Hint;
    }

    public String getVB()
    {
        return VB;
    }

    public int getReviewTime()
    {
        return ReviewTime;
    }

    //set
    public boolean isEasy()
    {
        return this.Easy;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    public void setNote(String note)
    {
        this.Note=note;
    }

    public void setChinese(String chinese)
    {
        this.Chinese=chinese;
    }

    public void setEasy()
    {
        Easy=true;
    }

    public void cancelEasy()
    {
        Easy=false;
    }

    public void setReviewTime(int reviewTime)
    {
        this.ReviewTime=reviewTime;
    }

    //for review
    public void know()
    {
        this.ReviewTime++;
    }

    public void cancelKnow()
    {
        this.ReviewTime--;
    }




}
