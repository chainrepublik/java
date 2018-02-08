package chainrepublik.agents.VM.PARSER;

import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLParser;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testParser;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.ParseTree;


/**
 *
 * @author technicalsupport
 */
public class CGUI 
{
   public void show(testParser parser, ParseTree tree)
   {
       JFrame frame = new JFrame("Antlr AST");
       JPanel panel = new JPanel();
       TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()), tree);
       
       viewr.setScale(1.5);//scale a little
       panel.add(viewr);
       frame.add(panel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(500,500);
       frame.setVisible(true);
   }
   
   public void show(SQLParser parser, ParseTree tree)
   {
       JFrame frame = new JFrame("Antlr AST");
       JPanel panel = new JPanel();
       TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()), tree);
       
       viewr.setScale(1.5);//scale a little
       panel.add(viewr);
       frame.add(panel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(500,500);
       frame.setVisible(true);
   }
}
