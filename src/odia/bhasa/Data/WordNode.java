
package odia.bhasa.Data;

public class WordNode
{
    public Word w;
    public WordNode prev;
    public WordNode next;
    public WordNode(Word w,WordNode prev)
    {
        this.w=w;
        this.prev=prev;
        next=null;
    }
}
