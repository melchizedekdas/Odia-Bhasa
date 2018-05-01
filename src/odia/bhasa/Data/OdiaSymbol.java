
package odia.bhasa.Data;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import odia.bhasa.ui.MainWindow;

//Subclass of Symbol represents Odia characters
public class OdiaSymbol extends Symbol
{
    //symbolId: Primary key for valid symbols and 0 for invalid symbols
    public int symbolId;
    //width: width in pixels of symbol image
    public int width;
    //img: stores symbol image
    public BufferedImage img;
    //valid: Array stores valid Odia symbols
    private static OdiaSymbol valid[];
    public static int maxLength=5;
    //symbolcount: Total no. of Odia symbols
    private static int symbolcount;
    
    //initialize valid array and symbolcount
    public static void validSymbol()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("pics\\imgmetadata.txt")))
		{
                        int a,i=0;
			String sCurrentLine,fword,sword;
                        sCurrentLine = br.readLine();
                        symbolcount=Integer.parseInt(sCurrentLine);
                        valid=new OdiaSymbol[symbolcount];
			while ((sCurrentLine = br.readLine()) != null)
                        {
				a=sCurrentLine.indexOf(' ');
                                fword=sCurrentLine.substring(0, a);
                                sword=sCurrentLine.substring(a+1);
                                valid[i]=new OdiaSymbol();
                                valid[i].st=fword;
                                valid[i].symbolId=i+1;
                                valid[i].width=Integer.parseInt(sword);
                                try
                                {
                                    valid[i].img =ImageIO.read(new File("pics\\"+(i+1)+".gif"));
                                }
                                catch (IOException e)
                                {
                                    System.out.println("File not found");
                                }
                                i++;
			}
 
		} catch (IOException e) {
			System.exit(0);
		} 
    }
    
    private OdiaSymbol(String st,int symbolId,int width)
    {
        super(st,false);
        this.symbolId=symbolId;
        this.width=width;
    }
    private OdiaSymbol(String st)
    {
        this(st,0,MainWindow.fontMetrics.stringWidth(st));
    }
    private OdiaSymbol()
    {
        this(null,0,0);
    }
    //Get Symbol from valid array if string is valid else create Symbol
    public static OdiaSymbol getSymbol(String st)
    {
        for(int i=0;i<symbolcount;i++)
        {
            if(st.equals(valid[i].st))
                return valid[i];
            
        }
        
        
                //Invalid String
                if(st.length()<=maxLength)
                    return (new OdiaSymbol(st));
                else
                    return null;
    }
   
}
