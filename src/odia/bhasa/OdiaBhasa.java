
package odia.bhasa;
import odia.bhasa.Data.OdiaSymbol;
import odia.bhasa.Data.Symbol;
import odia.bhasa.Data.SymbolNode;
public class OdiaBhasa
{
    //symbolList: Head node of symbol linked list
    //currentNode: Pointer node
    //srartNode: Pointer to first character to be displayed on screen
    public static SymbolNode symbolList,startNode,currentNode;
    
    public static boolean lang=false;
    //Initialize head node for symbolList
    public static void init()
    {
        symbolList=new SymbolNode(null,null);
        currentNode=symbolList;
        OdiaSymbol.validSymbol();
    }
    //create the linked list (for testing)
    
     public static void initializeString()
    {
        
        SymbolNode prev=symbolList, next;
        Symbol s;
        
        for(int i=1;i<=50;i++)
        {
        s=OdiaSymbol.getSymbol("A");
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=new Symbol('X');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=new Symbol('a');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=new Symbol(' ');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=new Symbol('y');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=new Symbol('i');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=OdiaSymbol.getSymbol("A");
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=OdiaSymbol.getSymbol("K");
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=OdiaSymbol.getSymbol("A");
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        
        s=OdiaSymbol.getSymbol("K");
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        if(i==20)
        {
            s=new Symbol('\n');
            next=new SymbolNode(s,prev);
            prev.next=next;
            prev=next;
        }
        }
        s=new Symbol('t');
        next=new SymbolNode(s,prev);
        prev.next=next;
        prev=next;
        //currentNode=next;
    }
     
     public static void main(String[] args)
    {
        init();
        //initializeString();
        new odia.bhasa.ui.MainWindow();
    }
}
