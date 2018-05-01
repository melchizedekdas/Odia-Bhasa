
package odia.bhasa.ui;

  //Panel for text editing area

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import odia.bhasa.Data.OdiaSymbol;
import odia.bhasa.Data.Symbol;
import odia.bhasa.Data.SymbolNode;
import odia.bhasa.OdiaBhasa;

class TextPanel extends JPanel
    {
        //symHt: height of each character
        //fSize: Size of font
        //spaceX: Space to be left from top and bottom
        //spaceY: Space tobe left from either side
        //skipRows: Rows to be skipped before starting to print
        private static int symHt=30,fSize=0,spaceX=10,spaceY=10,totalRows,skipRows=0;
        //frun: true for the first time paintComponent executed
        private static boolean frun=true,showCursor=false;
        //panelWidth: Width of panel
        //panelHeight: Height of panel
        private int panelWidth,panelHeight;
        private static SymbolNode start;
    //fontmetrics: Used to get width and accent of english symbol
    private static FontMetrics fontMetrics;
        private JScrollBar ranger;
        public static void init()
        {
            start=OdiaBhasa.symbolList;
        }
        TextPanel()
        {
            setBackground(Color.WHITE);
            addKeyListener(new KeyboardInput());
            addMouseListener(new MouseInput());
            addFocusListener(new KeyboardInput());
            setLayout(new BorderLayout());
            ranger=new JScrollBar(JScrollBar.VERTICAL);
            ranger.setMinimum(0);
            ranger.setMaximum(totalRows);
            ranger.addAdjustmentListener(new AdjustmentListener() {

                @Override
                public void adjustmentValueChanged(AdjustmentEvent e)
                {
                    skipRows=e.getValue();
                    resetStartScroll();
                    repaint();
                }
            });
            start=OdiaBhasa.symbolList;
            add(ranger,BorderLayout.EAST);
        }
        public void setDimensions()
        {
            //Dimension d=((JFrame) SwingUtilities.getWindowAncestor(this)).getSize();
            Dimension d=getSize();
            boolean flag=false;
            if(panelWidth!=d.width)
            {
                panelWidth=d.width;
                flag=true;
            }
            if(panelHeight!=d.height)
            {
                panelHeight=d.height;
                flag=true;
            }
            if(flag)
            {
                resetStartResize();
            }
        }
        
        
        @Override
      public void paintComponent(Graphics g)
        {
         super.paintComponent(g);
         //increment count of no. of times paintComponent is called
         setDimensions();
         g.setColor(Color.red);
         //Paint boundary for Text area
         g.setColor(Color.black);
         //Set fsize
         if(frun)
         {
             frun=false;
            for (int i = 1; i <= 50; i++)
            {
                Font engfont = new Font("Times New Roman", Font.PLAIN, i);
                g.setFont(engfont);
                fontMetrics = g.getFontMetrics();
                int height=fontMetrics.getAscent()+fontMetrics.getDescent();
                if(height>=symHt-4)
                {
                    fSize=i;
                    break;
                }
            }
        }
         printString(g,0,0,start);
        }
        
        private void resetStartScroll()
        {
            Graphics g=getGraphics();
            //len: length of all characters present in current row
            //row: current row no.
            //start: Node after which printing is to be done
            //taX: 
            //tawidth: Text area width
            //taHeight: Text area height
            //taRows: Max no. of rows in Text area
            int taX=spaceX,taY=spaceY,taWidth=panelWidth-3*spaceX,taHeight=panelHeight-2*spaceY;
            int len=0;
            int row=0;
            boolean flag=true;
            //symWidth: Symbol width(varies from symbol to symbol)
            int symWidth;
            //Set font
            Font engfont = new Font("Times New Roman", Font.PLAIN, fSize);
            g.setFont(engfont);
            fontMetrics = g.getFontMetrics();
            MainWindow.fontMetrics=fontMetrics;
            
            
            SymbolNode temp=OdiaBhasa.symbolList,rowStart=OdiaBhasa.symbolList;
            Symbol s;
            while(temp.next!=null)
            {
                if(row>=skipRows && flag)
                {
                    start=temp;
                    return;
                }
                temp=temp.next;
                s=temp.s;
                //Get Character width
                if(s.lang) //English Symbol
                {
                    symWidth=fontMetrics.charWidth((s.st).charAt(0));
                    if( "\n".equals(s.st))//New line
                    {
                        row++;
                        len=0;
                        continue;
                    }
                }
                
                else //Odia Symbol
                {
                    symWidth=((OdiaSymbol)s).width;
                }
                
                if((len+symWidth>taWidth)&& len!=0)
                {
                    row++;
                    len=0;
                }
                
                len+=symWidth;
                //Word wrap
                if(s.lang && " ".equals(s.st))//Whitespace
                {
                    int l=getWordWidth(temp);
                    if(len!=0 && len+l>taWidth )
                    {
                        row++;
                        len=0;
                    }
                }
                
            }
        }
        
        
        private void resetStartResize()
        {
            Graphics g=getGraphics();
            //len: length of all characters present in current row
            //row: current row no.
            //start: Node after which printing is to be done
            //taX: 
            //tawidth: Text area width
            //taHeight: Text area height
            //taRows: Max no. of rows in Text area
            int taX=spaceX,taY=spaceY,taWidth=panelWidth-3*spaceX,taHeight=panelHeight-2*spaceY;
            int len=0;
            int row=0;
            
            //symWidth: Symbol width(varies from symbol to symbol)
            int symWidth;
            //Set font
            Font engfont = new Font("Times New Roman", Font.PLAIN, fSize);
            g.setFont(engfont);
            fontMetrics = g.getFontMetrics();
            MainWindow.fontMetrics=fontMetrics;
            
            boolean flag=true,flag2=true;
            SymbolNode temp=OdiaBhasa.symbolList,rowStart=OdiaBhasa.symbolList;
            Symbol s;
            while(temp.next!=null)
            {
                
                if(flag && flag2)
                {
                    rowStart=temp;
                    flag2=false;
                }
                if(temp.equals(start) && flag)
                {
                    start=rowStart;
                    flag=false;
                }
                temp=temp.next;
                s=temp.s;
                //Get Character width
                if(s.lang) //English Symbol
                {
                    symWidth=fontMetrics.charWidth((s.st).charAt(0));
                    if( "\n".equals(s.st))//New line
                    {
                        row++;
                        flag2=true;
                        len=0;
                        continue;
                    }
                }
                
                else //Odia Symbol
                {
                    symWidth=((OdiaSymbol)s).width;
                }
                
                if((len+symWidth>taWidth)&& len!=0)
                {
                    row++;
                    flag2=true;
                    len=0;
                }
                
                len+=symWidth;
                //Word wrap
                if(s.lang && " ".equals(s.st))//Whitespace
                {
                    int l=getWordWidth(temp);
                    if(len!=0 && len+l>taWidth )
                    {
                        row++;
                        flag2=true;
                        len=0;
                    }
                }
            }
            
            ranger.setMaximum(row);
        }
        
        
        //Print given String
        private void printString(Graphics g,int len,int row,SymbolNode start)
        {
            //len: length of all characters present in current row
            //row: current row no.
            //start: Node after which printing is to be done
            //taX: 
            //tawidth: Text area width
            //taHeight: Text area height
            //taRows: Max no. of rows in Text area
            int taX=spaceX,taY=spaceY,taWidth=panelWidth-3*spaceX,taHeight=panelHeight-2*spaceY,taRows=taHeight/symHt-1;
            
            //Clear area from where to start printing
            {
                g.clearRect(len+taX, row*symHt+taY, taWidth-len, symHt);
                g.clearRect(taX, (row+1)*symHt+taY, taWidth, (taRows-row)*symHt);
            }
            
            //symWidth: Symbol width(varies from symbol to symbol)
            int symWidth;
            //Set font
            Font engfont = new Font("Times New Roman", Font.PLAIN, fSize);
            g.setFont(engfont);
            fontMetrics = g.getFontMetrics();
            MainWindow.fontMetrics=fontMetrics;
            //acc: Accent of the font used for printibg english symbol
            int acc=fontMetrics.getAscent();
            
            if(OdiaBhasa.symbolList.equals(OdiaBhasa.currentNode) && showCursor)//Draw Cursor for currenNode=symbolList
                {
                    g.setColor(Color.blue);
                    g.drawLine(len+taX-1, row*symHt+taY, len+taX-1, (row+1)*symHt+taY);
                }
            g.setColor(Color.black);
            
            BufferedImage img;
            SymbolNode temp=start;
            Symbol s;
            while(temp.next!=null)
            {
                temp=temp.next;
                s=temp.s;
                
                //Get Character width
                if(s.lang) //English Symbol
                {
                    symWidth=fontMetrics.charWidth((s.st).charAt(0));
                    if( "\n".equals(s.st))//New line
                    {
                        row++;
                        len=0;
                        //No. of rows allowed exceeded
                        if(row>taRows) break;
                        if(temp.equals(OdiaBhasa.currentNode) && showCursor)
                        {
                            g.setColor(Color.blue);
                            g.drawLine(len+taX-1, row*symHt+taY, len+taX-1, (row+1)*symHt+taY);
                            g.setColor(Color.black);
                        }
                        continue;
                    }
                }
                
                else //Odia Symbol
                {
                    symWidth=((OdiaSymbol)s).width;
                }
                
                if((len+symWidth>taWidth)&& len!=0)
                {
                    row++;
                    len=0;
                }
                //No. of rows allowed exceeded
                if(row>taRows) break;
                
                //Print Character
                if(s.lang) //English Symbol
                {
                    g.drawString(s.st, len+taX, row*symHt+taY+acc);
                }
                else //Odia Symbol
                {
                    if(((OdiaSymbol)s).symbolId!=0)//Valid Symbol
                    {
                        img = ((OdiaSymbol)s).img;
                        g.drawImage(img,len+taX,row*symHt+taY,null);
                    }
                    else //Invalid symbol
                    {
                        g.setColor(Color.red);
                        g.drawString(s.st, len+taX, row*symHt+taY+acc);
                        g.setColor(Color.black);
                    }
                }
                len+=symWidth;
                //Display Cursor
                if(temp.equals(OdiaBhasa.currentNode) && showCursor)
                {
                    g.setColor(Color.blue);
                    g.drawLine(len+taX-1, row*symHt+taY, len+taX-1, (row+1)*symHt+taY);
                    g.setColor(Color.black);
                }
                //Word wrap
                if(s.lang && " ".equals(s.st))//Whitespace
                {
                    int l=getWordWidth(temp);
                    if(len!=0 && len+l>taWidth )
                    {
                        row++;
                        len=0;
                    }
                }
            }
        }
        //Get sum of width of all characters until next whitespace in pixels
        private int getWordWidth(SymbolNode start)
        {
            int len=0,symWidth;
            SymbolNode temp=start;
            
            Symbol s;
            while(temp.next!=null)
            {
                temp=temp.next;
                s=temp.s;
                
                //Get Character width
                if(s.lang) //English Symbol
                {
                    if(" ".equals(s.st) || "\n".equals(s.st))//Whitespace
                        break; //word end reached
                    
                    symWidth=fontMetrics.charWidth((s.st).charAt(0));
                }
                
                else //Odia Symbol
                {
                    symWidth=((OdiaSymbol)s).width;
                }
                len+=symWidth;
            }
            return len;
        }
        
        //Event Handlers
        public void currentNodeChanged()
        {
            if(!OdiaBhasa.currentNode.equals(OdiaBhasa.symbolList))
            {
                OdiaBhasa.lang=OdiaBhasa.currentNode.s.lang;
                MainWindow.eng.setSelected(OdiaBhasa.lang);
                MainWindow.odia.setSelected(!OdiaBhasa.lang);
            }
        }
        //Mouse events handler

        private class MouseInput implements MouseListener
        {
            @Override
            public void mousePressed(MouseEvent evt)
            {
                requestFocus();
            }
            
            @Override
            public void mouseReleased(MouseEvent evt){}
            
            @Override
            public void mouseClicked(MouseEvent evt){}
            
            @Override
            public void mouseEntered(MouseEvent evt){}
            
            @Override
            public void mouseExited(MouseEvent evt){}
        }
        //Keyboard events handler
        private class KeyboardInput implements KeyListener,FocusListener
        {
            @Override
            public void keyPressed(KeyEvent evt)
            {
                //Special keys common to both languages implemented
                int ch=evt.getKeyCode();
                
                if(ch==KeyEvent.VK_LEFT)//Move current node pointer and cursor one left
                {
                    if(OdiaBhasa.currentNode.prev!=null)
                    {
                        OdiaBhasa.currentNode=OdiaBhasa.currentNode.prev;
                        repaint();
                    }
                    currentNodeChanged();
                }
                else if(ch==KeyEvent.VK_RIGHT)//Move current node pointer and cursor one right
                {
                    if(OdiaBhasa.currentNode.next!=null)
                    {
                        OdiaBhasa.currentNode=OdiaBhasa.currentNode.next;
                        
                        repaint();
                    }
                    currentNodeChanged();
                }
                
            }
            
            @Override
            public void keyReleased(KeyEvent evt){}
            
            @Override
            public void keyTyped(KeyEvent evt)
            {
                char ch=evt.getKeyChar();
                if(ch==KeyEvent.VK_BACK_SPACE)//Delete one symbol to left
                {
                    deleteSymbolNodeLeft();
                    repaint();
                    currentNodeChanged();
                }
                else if(ch==KeyEvent.VK_DELETE)//Delete one symbol to right
                {
                    deleteSymbolNodeRight();
                    repaint();
                }
                else
                {
                    if(OdiaBhasa.lang)
                        engChar(ch);
                    else
                        odiaChar(ch);
                }
                resetStartResize();
            }
            private void engChar(char ch)
            {
                
                Symbol s;
                s=new Symbol(ch);
                insertSymbolNode(s);
                repaint();
            }
            private void odiaChar(char ch)
            {
                if((ch>='A' && ch<='Z')||(ch>='a' && ch<='z')||(ch>='0' && ch<='9'))
                {
                    insertOdiaSymbol(ch);
                    repaint();
                }
                else
                {
                    engChar(ch);
                }
            }
            private void insertOdiaSymbol(char ch)
            {
                SymbolNode prev=OdiaBhasa.currentNode,next=prev.next,newNode;
                Symbol s;
                //brandnew: Brand new node to be created
                boolean brandnew;
                if(prev.equals(OdiaBhasa.symbolList))
                {
                    
                    brandnew=true;
                }
                else
                {
                    if(prev.s.lang)
                    {
                        brandnew=true;
                    }
                    else
                    {
                        if(((OdiaSymbol)prev.s).symbolId!=0)
                        {
                            brandnew=true;
                        }
                        else
                        {
                            brandnew=false;
                        }
                    }
                }
                if(brandnew)//New node
                {
                    s=OdiaSymbol.getSymbol(Character.toString(ch));
                    newNode=new SymbolNode(s,prev);
                   
                }
                else//Update Invalid Odia symbol
                {
                    String st=prev.s.st;
                    s=OdiaSymbol.getSymbol(st+ch);
                    if(s==null)
                        return;
                    prev=prev.prev;
                    newNode=new SymbolNode(s,prev);
                }
                newNode.next=next;
                prev.next=newNode;
                if(next!=null)
                    next.prev=newNode;
                OdiaBhasa.currentNode=newNode;
            }
            private void insertSymbolNode(Symbol s)
            {
                SymbolNode prev=OdiaBhasa.currentNode, next=prev.next,newNode;
                newNode=new SymbolNode(s,prev);
                newNode.next=next;
                prev.next=newNode;
                if(next!=null)
                    next.prev=newNode;
                OdiaBhasa.currentNode=newNode;
            }
            private void deleteSymbolNodeLeft()
            {
                if(OdiaBhasa.currentNode.prev!=null)
                    {
                        SymbolNode nextNode=OdiaBhasa.currentNode.next;
                        OdiaBhasa.currentNode=OdiaBhasa.currentNode.prev;
                        OdiaBhasa.currentNode.next=nextNode;
                        if(nextNode!=null)
                            nextNode.prev=OdiaBhasa.currentNode;
                        
                    }
            }
            private void deleteSymbolNodeRight()
            {
                if(OdiaBhasa.currentNode.next!=null)
                    {
                        SymbolNode nextNode=OdiaBhasa.currentNode.next;
                        nextNode=nextNode.next;
                        OdiaBhasa.currentNode.next=nextNode;
                        if(nextNode!=null)
                            nextNode.prev=OdiaBhasa.currentNode;
                        
                    }
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                showCursor=true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                showCursor=false;
                repaint();
            }
        }
    }