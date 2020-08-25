package logic_class.word_class;

import android.util.Log;
import logic_class.VocabularyBook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/*
count for send
new/old word list for new/old
 */

public class WordList {
    private List<Word2Review> new_Word_List;
    private List<Word2Review> old_Word_List;
    private int count;
    private int list_length;

    public WordList(int amount_new, int amount_all, VocabularyBook vocabularyBook)
    {
        new_Word_List=new ArrayList<>();
        old_Word_List=new ArrayList<>();
        Iterator<Word> iterator=vocabularyBook.getWordSet().iterator();
        while (iterator.hasNext()&&(amount_new>0||amount_all >0))
        {
            Word word=iterator.next();
            if (word.isEasy()||word.getReviewTime()>3)
                continue;
            Word2Review word2Review=new Word2Review(word);
            if (word2Review.getType()&&amount_new>0)
            {

                new_Word_List.add(word2Review);
                amount_new--;
                amount_all--;
            }
            else if (!word2Review.getType()&&(amount_all-amount_new)>0)
            {
                old_Word_List.add(word2Review);
                amount_all--;
            }

        }
        list_length=getNew_num()+getOld_num();
        count=0;
    }

    public int getNew_num() {
        return new_Word_List.size();
    }

    public int getOld_num() {
        return old_Word_List.size();
    }

    public int getAll_num() {
        return new_Word_List.size()+old_Word_List.size();
    }

    public int getList_Amount()
    {
        return list_length;
    }

    public boolean isEmpty()
    {
        return (new_Word_List.isEmpty()&&old_Word_List.isEmpty());
    }

    public void deleteFromList(Word2Review w)
    {
        if (w.getType())
            new_Word_List.remove(w);
        else
            old_Word_List.remove(w);
    }

    public void cancelDelete(Word2Review w)
    {
        if (w.getType())
            new_Word_List.add(w);
        else
            old_Word_List.add(w);

    }

    public Word2Review sendWord()
    {
        if(new_Word_List.isEmpty())
        {
            Log.e("only old:"," Old" +old_Word_List.size());
            return old_Word_List.get(new Random().nextInt(old_Word_List.size()));
        }
        else if (old_Word_List.isEmpty())
        {
            Log.e("only new:"," new" +new_Word_List.size());
            return new_Word_List.get(new Random().nextInt(new_Word_List.size()));
        }
        else
        {
            if (count==4)
            {
                count=0;
                return new_Word_List.get(new Random().nextInt(new_Word_List.size()));
            }
            else
            {
                count++;
                return old_Word_List.get(new Random().nextInt(old_Word_List.size()));
            }
        }
    }
}
