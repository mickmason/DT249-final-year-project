/*
 * Seciton 4 Description Logics, OWL profiles, Inference
 * Inference examples - ex 4.X Class description-based instance relationship inference
 */

package section_5_exmaples;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.iterator.* ;
import java.io.InputStream ;
import com.hp.hpl.jena.util.FileManager;

/**
 *
 * @author Michael
 */
public class ClassDescriptionBasedInference {
    public static void main(String [] args) {
        //Set up a base model and an inference model of the Family Ontology
        OntModel familyBase = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM) ;
        OntModel familyInf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, familyBase) ;

        //The family ontology namespace
        String gensNS = "http://www.owl-ontologies.com/generations.owl#" ;

        //Get an InputStream on the file and read it into the base ontology
        String familyOntOwlFile = "C:\\Users\\Michael\\DT249\\year_4\\proj_2013\\Ontologies\\FamilyOntology\\generations-family-ontology-extended_rdf.owl" ;
        InputStream in = FileManager.get().open(familyOntOwlFile) ;
        if (in == null) {
            throw new IllegalArgumentException("File not found at: " + familyOntOwlFile) ;
        }
        familyBase.read(in,null) ;
        //Show members of Class BrotherInLaw in the base ontology
        OntClass brotherInLawBase = familyBase.getOntClass(gensNS+"BrotherInLaw") ;
        OntClass brotherInLawInf = familyInf.getOntClass(gensNS+"BrotherInLaw") ;
        System.out.println("Instances of the " + brotherInLawBase.getLocalName() + " class in the base graph:") ;
        //Get instances of BrotherInLaw in the base ontology
        ExtendedIterator it = brotherInLawBase.listInstances(false) ;
        if (it.hasNext()) {
            while (it.hasNext()) {
                System.out.println(it.next()) ;
            }
        } else {
            System.out.println("Class " + brotherInLawBase.getLocalName() + " has no instances in base modle") ;
        }
        System.out.println("Instances of the " + brotherInLawInf.getLocalName() + " class in the inference graph:") ;
        it = brotherInLawInf.listInstances(false) ;
        while (it.hasNext()) {
            System.out.println(it.next()) ;
        }
    }//main()
}
