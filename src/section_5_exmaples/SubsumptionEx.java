/*
 * Seciton 4 Description Logics, OWL profiles, Inference
 * Inference examples - ex 4.1
 */
package section_5_exmaples;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.iterator.* ;
import java.io.InputStream ;
import com.hp.hpl.jena.util.FileManager;

public class SubsumptionEx {
    public static void main(String[] args) {
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
        //Get and instance of class Father from the base model and one from the inferred model
        OntClass fatherBase = familyBase.getOntClass(gensNS+"Father") ;
        OntClass fatherInf = familyInf.getOntClass(gensNS+"Father") ;
        //Get an iterator over the base model Father instance
        ExtendedIterator superClasses = fatherBase.listSuperClasses() ;
        System.out.println("Base model " + fatherBase.getLocalName()+ " class superclasses:") ;
        OntClass c = null ;
        while (superClasses.hasNext()) {
            c = (OntClass) superClasses.next() ;
            System.out.println(gensNS + c.getLocalName()) ;
        }
        superClasses = fatherInf.listSuperClasses() ;
        System.out.println("Inference model " + fatherBase.getLocalName()+ " class superclasses:") ;
        while (superClasses.hasNext()) {
            c = (OntClass) superClasses.next() ;
            if(c.isRestriction() == true || c.getLocalName() == null ) {
                continue ;
            } else {
                System.out.println(gensNS + c.getLocalName()) ;
            }
            //System.out.println(c) ;
        }
    }
}
