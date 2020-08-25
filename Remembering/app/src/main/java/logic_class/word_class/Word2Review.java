package logic_class.word_class;
/*
Word2Review should be create only by build wordlist time and will be only used at review time
is First time for try twice
type for new or old word, decided by word review time and use for different word list,type can only be decided once
 */

public class Word2Review {

    private Word word;
    private boolean isFirstTime;
    //true for new;false for old
    private boolean type;

    public Word2Review(Word word)
    {
        this.word=word;
        this.isFirstTime=true;
        if (word.getReviewTime()==0)
            this.type=true;
        else
            this.type=false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Word2Review))
            return false;
        Word2Review w2r = (Word2Review) obj;
        return this.word.equals(w2r.word);
    }

    //get
    public Word getWord()
    {
        return word;
    }

    public boolean getType() {
        return type;
    }

    public boolean getIsFirstTime()
    {
        return isFirstTime;
    }

    public String getWordEnglish()
    {
        return word.getEnglish();
    }

    public String getWordChinese()
    {
        return word.getChinese();
    }

    public String getWordNote()
    {
        return word.getNote();
    }

    public String getWordHint()
    {
        return word.getHint();
    }

    public boolean isEasy()
    {
        return word.isEasy();
    }

    //set
    public void setEasy()
    {
        word.setEasy();
    }

    public void cancelEasy()
    {
        word.cancelEasy();
    }

    public void setKnown()
    {
        word.know();
    }

    public void setWordNote(String note)
    {
        word.setNote(note);
    }

    public void Fail4FirstTime()
    {
        isFirstTime=false;
    }

    public void initialFirstTime()
    {
        isFirstTime=true;
    }
}
