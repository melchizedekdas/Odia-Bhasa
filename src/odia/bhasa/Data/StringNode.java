
package odia.bhasa.Data;
//To be used for serialization
public class StringNode implements java.io.Serializable
{
    public static class StringSymbol implements java.io.Serializable
    {
        public String st;
        public boolean lang;
        public StringSymbol (String st,boolean lang)
        {
            this.st=st;
            this.lang=lang;
        }
    }
    public StringSymbol ss;
    public StringNode prev;
    public StringNode next;
    public StringNode(StringSymbol ss,StringNode prev)
    {
        this.ss=ss;
        this.prev=prev;
        next=null;
    }
    public static StringNode getStringList(SymbolNode symbolList2)
    {
        SymbolNode symbolList=symbolList2;
        StringNode stringList=new StringNode(null,null);
        StringNode temp1=stringList,temp2;
        while(symbolList.next!=null)
        {
            symbolList=symbolList.next;
            Symbol s=symbolList.s;
            StringSymbol ss=new StringSymbol(s.st, s.lang);
            temp2=new StringNode(ss,temp1);
            temp1.next=temp2;
            temp1=temp2;
        }
        return stringList;
    }
    public static SymbolNode getSymbolList(StringNode stringList2)
    {
        StringNode stringList=stringList2;
        SymbolNode symbolList=new SymbolNode(null,null);
        SymbolNode temp1=symbolList,temp2;
        while(stringList.next!=null)
        {
            stringList=stringList.next;
            
            StringSymbol ss=stringList.ss;
            Symbol s;
            if(ss.lang)
                s=new Symbol(ss.st.charAt(0));
            else
            {
                s=OdiaSymbol.getSymbol(ss.st);
            }
            temp2=new SymbolNode(s,temp1);
            temp1.next=temp2;
            temp1=temp2;
        }
        return symbolList;
    }
}
