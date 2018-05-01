package odia.bhasa.Data;
//Represesnts symbols of different languages
public class Symbol
{
    //The characters or sequence of characters that uniquely identify the symbol
    public String st;
    //Language true=English, false=Odia
    public boolean lang=false;
    protected Symbol(String st,boolean lang)//to be used indrectly by OdiaSymbol constructor for odia symbols
    {
        this.st=st;
        this.lang=lang;
    }
    public Symbol(char ch)//to be used explicitly for english symbols
    {
        this.st=Character.toString(ch);
        this.lang=true;
    }
}
