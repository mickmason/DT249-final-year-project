/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package section_3_examples;
import com.hp.hpl.jena.rdf.model.* ;
import com.hp.hpl.jena.sparql.vocabulary.FOAF ;
import com.hp.hpl.jena.vocabulary.RDF ;
import com.hp.hpl.jena.util.FileManager ;
import java.io.* ;

/**
 *
 * @author Michael
 */
public class ExTBLFOAF {

    public static void main(String[] args) {
        //Read some data from the Web and add it to a model
        Model tblFOAFModel = ModelFactory.createDefaultModel() ;

        InputStream in = FileManager.get().open("http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf") ;
        if (in != null) {
            tblFOAFModel.read(in, "N-TRIPLE") ;
        } 
        //Run a query for all "foaf:knows" predicates
        System.out.println() ;
        System.out.println("All statements with FOAF.knows as predicate: ") ;
        StmtIterator iter = tblFOAFModel.listStatements(null, FOAF.knows, (RDFNode) null) ;
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement() ;
            Resource sub = stmt.getSubject() ;
            Property pred = stmt.getPredicate() ;
            RDFNode obj = stmt.getObject() ;

            System.out.print("Sub: " + sub + "\t") ;
            System.out.print("Pred: " + pred + "\t") ;
            if (obj instanceof Resource) {
                System.out.println("Obj: " + obj.toString()) ;
            } else {
                System.out.println("Obj: \"" + obj + "\"") ;
            }
        }

        //Write the model as RDF/XML to file. It was read as N-TRIPLE
        try {
            OutputStream out = new FileOutputStream("colin_foaf.rdf") ;
            tblFOAFModel.write(out, "RDF/XML") ;
        } catch (IOException ioe) {
            System.out.println(ioe.getCause()) ;
        }

        System.out.println("Compare two models in different sierializations but containing the same set of statements: ") ;
        in = FileManager.get().open("colin_foaf.rdf") ;
        Model newColinFOAF = ModelFactory.createDefaultModel().read(in, "RDF/XML") ;
        if (tblFOAFModel.isIsomorphicWith(newColinFOAF)) {
            System.out.println("The models match!") ;
        } else {
            System.out.println("The models don't match!") ;
        }

       
        //Get a list of all namespaces used in the model.
        NsIterator nsIter = tblFOAFModel.listNameSpaces() ;
        while (nsIter.hasNext()) {
            System.out.println("NS: " + nsIter.nextNs()) ;
        }

        System.out.println() ;
        System.out.println("All statements with FOAF.knows as predicate: ") ;
        iter = tblFOAFModel.listStatements(null, FOAF.knows, (RDFNode) null) ;
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement() ;
            Resource sub = stmt.getSubject() ;
            Property pred = stmt.getPredicate() ;
            RDFNode obj = stmt.getObject() ;

            System.out.print("Sub: " + sub + "\t") ;
            System.out.print("Pred: " + pred + "\t") ;
            if (obj instanceof Resource) {
                System.out.println("Obj: " + obj.toString()) ;
            } else {
                System.out.println("Obj: \"" + obj + "\"") ;
            }
        }


    }//end main()
}//end class
