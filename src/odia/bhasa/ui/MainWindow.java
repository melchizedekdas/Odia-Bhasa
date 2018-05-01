
package odia.bhasa.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;
import odia.bhasa.Data.StringNode;
import odia.bhasa.Data.SymbolNode;
import odia.bhasa.OdiaBhasa;
//Main window for Odia Bhasa
public class MainWindow extends JFrame
{
    static int minHeight=300,minWidth=500,spWidth=200;
    static JRadioButtonMenuItem eng,odia;
    //fontmetrics: Used to get width and accent of english symbol
    public static FontMetrics fontMetrics;
    public MainWindow()
    {
        
        setBackground(Color.WHITE);
        setTitle("Odia Bhasa");
        setBounds(200,20,620,680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set minimum dimension
        Dimension d=new Dimension();
        d.height=minHeight;
        d.width=minWidth;
        setMinimumSize(d);
        //Set menu bar
        JMenuBar menubar=new JMenuBar();
        //Set File Menu
        FileMenuHandler fmh=new FileMenuHandler();
        JMenuItem newMenu=new JMenuItem("New");
        newMenu.addActionListener(fmh);
        JMenuItem open=new JMenuItem("Open");
        open.addActionListener(fmh);
        JMenuItem saveas=new JMenuItem("Save As");
        saveas.addActionListener(fmh);
        JMenu filemenu=new JMenu("File");
        filemenu.add(newMenu);
        filemenu.add(open);
        filemenu.add(saveas);
        menubar.add(filemenu);
        //Set Language menu
        LangMenuHandler al=new LangMenuHandler();
        eng=new JRadioButtonMenuItem("English");
        eng.setSelected(OdiaBhasa.lang);
        eng.addActionListener(al);
        odia=new JRadioButtonMenuItem("Odia");
        odia.setSelected(!OdiaBhasa.lang);
        odia.addActionListener(al);
        JMenu languagemenu=new JMenu("Language");
        languagemenu.add(eng);
        languagemenu.add(odia);
        menubar.add(languagemenu);
        //Set Language processing menu
        JMenuItem spelling=new JMenuItem("Check Spelling");
        JMenuItem grammar=new JMenuItem("Check grammar");
        JMenuItem translate1=new JMenuItem("Translate English to Odia");
        JMenuItem translate2=new JMenuItem("Translate Odia to English");
        JMenu lpmenu=new JMenu("Language Processing");
        lpmenu.add(spelling);
        lpmenu.add(grammar);
        lpmenu.add(translate1);
        lpmenu.add(translate2);
        menubar.add(lpmenu);
        
        setJMenuBar(menubar);
        
        JPanel content=new JPanel();
        content.setLayout(new BorderLayout(2,2));
        //Border
        Border b=BorderFactory.createLineBorder(Color.BLACK);
        /*
        //Add toolbar
        MyToolBar tb=new MyToolBar();
        tb.setBorder(b);
        content.add(tb, BorderLayout.NORTH);
        //Add side panel
        SidePanel sp=new SidePanel();
        
        sp.setBorder(b);
        
        content.add(sp, BorderLayout.EAST);
        * 
        */
        //Add main panel
        TextPanel tp=new TextPanel();
        tp.setBorder(b);
        JScrollPane scrollPane = new JScrollPane(tp);
        content.add(scrollPane, BorderLayout.CENTER);
        add(content);
        setResizable(true);
        setVisible(true);
    }
    private static class LangMenuHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if("English".equals(e.getActionCommand()))
                OdiaBhasa.lang=true;
            else
                OdiaBhasa.lang=false;
            eng.setSelected(OdiaBhasa.lang);
            odia.setSelected(!OdiaBhasa.lang);
        }
    }
    
    private class FileMenuHandler implements ActionListener
    {
        private class FilterFileName extends javax.swing.filechooser.FileFilter
        {

            @Override
            public boolean accept(File pathname)
            {
                String filename=pathname.getName();
                if((filename.lastIndexOf(".obh"))==(filename.length()-4))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                return ("Odia Bhasa .obh");
            }
            
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if("New".equals(e.getActionCommand()))
            {
                OdiaBhasa.init();
                TextPanel.init();
            }
            else if("Open".equals(e.getActionCommand()))
            {
                final JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FilterFileName());
                int returnVal = fc.showOpenDialog(MainWindow.this);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    String filename=file.getName();
                    if(!((filename.lastIndexOf(".obh"))==(filename.length()-4)))
                        return;
                    try
                    {
                        FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        OdiaBhasa.init();
                        StringNode stringList=new StringNode(null,null);
                        StringNode temp=stringList, temp2;
                        do
                        {
                            
                            temp2 = (StringNode) in.readObject();
                            temp.next=temp2;
                            temp2.prev=temp;
                            
                            temp=temp2;
                            
                        }while(temp.next!=null);
                        OdiaBhasa.symbolList=StringNode.getSymbolList(stringList);
                        OdiaBhasa.startNode=OdiaBhasa.symbolList;
                        OdiaBhasa.currentNode=OdiaBhasa.symbolList;
                        
                        TextPanel.init();
                        
                        in.close();
                        fileIn.close();
                    }
                    catch(IOException i)
                    {
                    }
                    catch(ClassNotFoundException c)
                    {
                    }
                    
                }
                else
                {
                }
            }
            else if("Save As".equals(e.getActionCommand()))
            {
                final JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FilterFileName());
                int returnVal = fc.showSaveDialog(MainWindow.this);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    String filename=file.getName();
                    if(!((filename.lastIndexOf(".obh"))==(filename.length()-4)))
                    {
                        String temppath=file.getPath();
                        file=new File(temppath+".obh");
                    }
                    try
                    {
                        FileOutputStream fileOut =new FileOutputStream(file);
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        StringNode temp=StringNode.getStringList(OdiaBhasa.symbolList);
                        while(temp.next!=null)
                        {
                            temp=temp.next;
                            out.writeObject(temp);
                        }
                        out.close();
                        fileOut.close();
                    }
                    catch(IOException i)
                    {
                    }
                }
                else
                {
                }
            }
        }
    }
}
