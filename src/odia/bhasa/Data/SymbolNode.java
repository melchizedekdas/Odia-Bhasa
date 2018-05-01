package odia.bhasa.Data;
//Double Linked list data structure for storing symbols
public class SymbolNode
{
    //s: Data node
    //prev: Pointer to previous element in the Linked list
    //next: Pointer to next element in the Linked list
    public Symbol s;
    public SymbolNode prev;
    public SymbolNode next;
    public SymbolNode(Symbol s,SymbolNode prev)
    {
        this.s=s;
        this.prev=prev;
        next=null;
    }
}
