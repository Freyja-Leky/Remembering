package logic_class;
import com.example.freyja.testthree.R;
import logic_class.word_class.Word;

import java.util.*;

/*
name, introduction for create a book
cover would be set by the first time it create
wordSet should be add later
 */

public class VocabularyBook implements Comparable<VocabularyBook> {
    private String name;
    private String introduction;
    private int cover;
    private Set<Word> wordSet;
    private int version;

    public VocabularyBook(String name,String introduction)
    {
        this.name=name;
        this.introduction=introduction;
        this.wordSet=new HashSet<Word>();
        int[] cArray={
                R.drawable.cover1,
                R.drawable.cover2,
                R.drawable.cover3,
                R.drawable.cover4,
                R.drawable.cover5
        };
        int index=new Random().nextInt(5);
        cover=cArray[index];
        if (name.equals("testbook1"))
            cover=cArray[0];
        if (name.equals("tb5"))
            cover=cArray[4];
        if (name.equals("tb6"))
            cover=cArray[1];
        if (name.equals("testbook2"))
            cover=cArray[2];
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof VocabularyBook))//instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
            return false;
        VocabularyBook vocabularyBook=(VocabularyBook)obj;
        return this.name.equals(vocabularyBook.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    public int compareTo(VocabularyBook vocabularyBook) {
        return this.name.compareTo(vocabularyBook.name);
    }

    //get
    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getCover()
    {
        return cover;
    }

    public List<Word> getWordList()
    {
        List<Word> wordList=new ArrayList<>(wordSet);
        Collections.sort(wordList);
        return wordList;
    }

    public Set<Word> getWordSet()
    {
        return wordSet;
    }

    public int getVersion()
    {
        return version;
    }

    //set
    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setVersion(int version)
    {
        this.version=version;
    }

    //opreate
    public boolean add2Book(Word word)
    {
        return wordSet.add(word);
    }

    public boolean deletefromBook(Word word)
    {
        return wordSet.remove(word);
    }

    public boolean isOver()
    {
        boolean isR=true;
        for (Word word:wordSet)
        {
            if (!word.isEasy()&&word.getReviewTime()<=3)
            {
                isR=false;
                break;
            }
        }
        return isR;
    }




}
