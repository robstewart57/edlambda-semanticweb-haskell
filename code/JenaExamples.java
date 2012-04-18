
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class JenaExamples {

    // Example1
    final public void readFile(String filename) throws FileNotFoundException {
        String str = new Scanner(new File("file.rdf")).useDelimiter("\\Z").next();
        Model model = ModelFactory.createDefaultModel();
        InputStream is = new ByteArrayInputStream(str.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        model.read(br, null);
    }

    // Example2
    final public Collection<Triple> getTriples(Model model){
        Collection<Triple> triples = new LinkedList<Triple>();
        StmtIterator stmts = model.listStatements();
        Statement s;
        while (stmts.hasNext()){
            s = stmts.nextStatement();
            triples.add(s.asTriple());
        }
        return triples;
    }

    // Example3
    final public void decomposeTriple(Triple triple) {
        Node subj = triple.getSubject();
        Node pred = triple.getPredicate();
        Node obj = triple.getObject();
    }

    // Example4
    final public String checkNodeType(Node node){
        String str = null;
        if (node.isURI())         str = node + " is URI";
        else if(node.isBlank())   str = node + " is blank";
        else if(node.isLiteral()) str = node + " is literal";
        return str;
    }

    // Example5
    final public boolean isIsomorphic(Model m1, Model m2){
        return m1.isIsomorphicWith(m2);
    }

    // Example6
    final public Triple makeTriple1(String s, String p, String oURI){
        Model model = ModelFactory.createDefaultModel();
        Node subj = model.createResource(s).asNode();
        Node pred = model.createResource(p).asNode();
        Node obj = model.createResource(oURI).asNode();
        return new Triple(subj,pred,obj);
    }

    // Example7
    final public Triple makeTriple2(String s, String p, String oLiteral){
        Model model = ModelFactory.createDefaultModel();
        Node subj = model.createResource(s).asNode();
        Node pred = model.createResource(p).asNode();
        Node obj = model.createLiteral(oLiteral).asNode();
        return new Triple(subj,pred,obj);
    }

    // Example8
    final public Triple makeTriple3(String s, String p, String oLangLiteral){
        Model model = ModelFactory.createDefaultModel();
        Node subj = model.createResource(s).asNode();
        Node pred = model.createResource(p).asNode();
        Node obj = model.createLiteral(oLangLiteral,"en").asNode();
        return new Triple(subj,pred,obj);
    }

    // Example9
    final public Model mkModel(){
        Model model = ModelFactory.createDefaultModel();
        String foafNS = "http://xmlns.com/foaf/0.1/";
        model.setNsPrefix("foaf", foafNS);
        Property topic_interest = model.createProperty(foafNS, "topic_interest");
        Property account = model.createProperty(foafNS, "account");
        Resource me = model.createResource("http://example.org/users/robstewart");
        Resource twitter = model.createResource("https://twitter.com/#!/robstewartUK");
        Resource haskell = model.createResource(
                "http://dbpedia.org/resource/Haskell_(programming_language)");
        Statement stmt;
        stmt = model.createStatement(me, account, twitter);
        model.add(stmt);
        stmt = model.createStatement(me, topic_interest, haskell);
        model.add(stmt);
        return model;
    }
    
}
