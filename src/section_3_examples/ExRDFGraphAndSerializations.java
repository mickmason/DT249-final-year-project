/*
 * Example showing the creation and manipulation of RDF graphs using the Jena framework.
 */

package section_3_examples;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.util.FileManager;
import java.net.* ;
import java.io.* ;
/**
 *
 * @author D06110699
 */
public class ExRDFGraphAndSerializations {
    public static void main(String[] args) {
        //Use file "scheme" as  FileReader thinks it's reading from a URL.
        String inputFile = "file:///C:/Users/Michael/DT249/year_4/proj_2013/Ontologies/MovieOntAsRDF.rdf" ;
        //Namespace used in the exmaple
        String mo = "http://www.movieontology.org/2009/11/09/movieontology.owl#" ;
        //Create a default RDF model
        Model moModel = ModelFactory.createDefaultModel() ;
        //Get an inputstream on the source file
        InputStream in = FileManager.get().open(inputFile) ;
        //Read the file
        if (in != null) {
            try {
                moModel.read(inputFile, null) ;
            } catch (Exception e) {
                System.out.println(e.getMessage()) ;
            }
        } else {
            throw new IllegalArgumentException("File not found at: " + inputFile) ;
        } 
       try {
            in.close() ;
        } catch (IOException ioe) {
            System.out.println(ioe.getCause()) ;
        }

        /* ***
         *  The graph is now created and in memery
            Example - Add the film Prizzi's Honour to the graph along with a bunch of statements about it
         *** */
       //Retrieve the required Properties from the model
        Property hasName = moModel.createProperty(mo+"hasName") ;
        Property title = moModel.getProperty(mo+"title") ;
        Property hasDirector = moModel.getProperty(mo+"hasDirector") ;
        Property hasProducer = moModel.getProperty(mo+"hasProducer") ;
        Property hasMaleActor = moModel.getProperty(mo+"hasMaleActor") ;
        Property hasFemaleActor = moModel.getProperty(mo+"hasFemaleActor") ;
        Property hasRole = moModel.getProperty(mo+"hasRole") ;
        Property playsRole = moModel.getProperty(mo+"playsRole") ;


        //Create each resource and assert statements about them by chaining addLiteral() and addProperty() calls to the createResource() call
        Resource johnHuston = moModel.createResource(mo+"John_Huston").addLiteral(hasName, "John Huston") ;
        Resource johnForeman = moModel.createResource(mo+"John_Foreman")
                .addLiteral(hasName, "John Foreman") ;
        //Create the Roles - Charlie Partana
        Resource charliePartana = moModel.createResource(mo+"Charlie_Partana")
                .addLiteral(hasName, "Charlie Partana") ;
        //Maerose Prizzi
        Resource maerose = moModel.createResource(mo+"Maerose_Prizzi")
                .addLiteral(hasName, "Maerose Prizzi") ;
        //Irene Walker
        Resource irene = moModel.createResource(mo+"Irene_Walker")
                .addLiteral(hasName, "Irene Walker") ;
        //Create the actors - Angelica Huston
        Resource angelicaHuston = moModel.createResource(mo+"Angelica_Huston")
                .addLiteral(hasName, "Angelica Huston")
                .addProperty(playsRole, maerose) ;
        //retrieve Jack from the model
        Resource jack = moModel.getResource(mo+"Jack_Nicholson")
                .addProperty(playsRole, charliePartana) ;
        //Kathleen Turner
        Resource kathleenTurner = moModel.createResource(mo+"Kathleen_Turner")
                .addLiteral(hasName, "Kathleen_Turner")
                .addProperty(playsRole, irene) ;
        //Create a new resource to represent the Movie Prizzis Honor and add assertions
        Resource prizzisHonor = moModel.createResource(mo+"Prizzis_Honor")
                .addLiteral(title, "Prizzi's Honour")
                .addProperty(hasRole, charliePartana)
                .addProperty(hasRole, maerose)
                .addProperty(hasRole, irene)
                //Dir and producer
                .addProperty(hasProducer, johnForeman) 
                //Add the actors
                .addProperty(hasMaleActor,jack)
                .addProperty(hasFemaleActor,angelicaHuston)
                .addProperty(hasFemaleActor, kathleenTurner) ;
        moModel.add(prizzisHonor, hasDirector, johnHuston) ;

        /* End of exmaple ----------------------------------------------------*/
        //Write the model as RDF/XML to file. It was read as N-TRIPLE
        OutputStream out = null ;        
        try {
            //Write the file as RDF/XML
            out = new FileOutputStream("MovieOntAsRdfXml.rdf") ;
            moModel.write(out, "RDF/XML") ;
            //Write the file as TURTLE
            out = new FileOutputStream("MovieOntAsTurtle.rdf") ;
            moModel.write(out, "TURTLE") ;
            //Write the file as N-TRIPLES
            out = new FileOutputStream("MovieOntAsNTriples.rdf") ;
            moModel.write(out, "N-TRIPLES") ;
            //Write the file as N3
            out = new FileOutputStream("MovieOntAsN3.rdf") ;
            moModel.write(out, "N3") ;
        } catch (IOException ioe) {
            System.out.println(ioe.getCause()) ;
        } finally {
            try {

                out.close() ;
            }catch (IOException ioe) {
                System.out.println(ioe.getCause()) ;
            }
            moModel.write(System.out, "TURTLE") ;
        }
    }//end main()
}
