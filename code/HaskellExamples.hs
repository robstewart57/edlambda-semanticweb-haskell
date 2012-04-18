module HaskellExamples where

import Text.RDF.RDF4H.XmlParser
import qualified Data.ByteString.Lazy.Char8 as B
import Data.RDF.TriplesGraph
import Data.RDF
import Data.Map as Map

-- Example 1
readRDFFile :: IO TriplesGraph
readRDFFile = do
  contents <- readFile "file.rdf"
  let (Right rdf) = parseXmlRDF Nothing Nothing (s2b contents)
  return rdf

-- Example 2
getTriples :: TriplesGraph -> Triples
getTriples triplesGraph = triplesOf triplesGraph

-- Example 3
decomposeTriple :: Triple -> (Subject,Predicate,Object)
decomposeTriple t = (subjectOf t,predicateOf t,objectOf t)

-- Example 4
checkNodeType :: Node -> String
checkNodeType node
  | isUNode node = (show node) ++ " is URI"
  | isBNode node = (show node) ++ " is blank"
  | isLNode node = (show node) ++ " is literal"

-- Example 5
isomorphicTest :: TriplesGraph -> TriplesGraph -> Bool
isomorphicTest m1 m2 = isIsomorphic m1 m2

-- Example 6
mkTriple1 :: String -> String -> String -> Triple
mkTriple1 s p o = 
  let subj = unode (s2b s)
      pred = unode (s2b p)
      obj =  unode (s2b o)
  in triple subj pred obj

-- Example 7
mkTriple2 :: String -> String -> String -> Triple
mkTriple2 s p o = 
  let subj = unode (s2b s)
      pred = unode (s2b p)
      obj =  lnode $ plainL (s2b o)
  in triple subj pred obj

-- Example 8
mkTriple3 :: String -> String -> String -> Triple
mkTriple3 s p o = 
  let subj = unode (s2b s)
      pred = unode (s2b p)
      obj =  lnode $ plainLL (s2b o) (s2b "en")
  in triple subj pred obj

-- Example 9
mkGraph :: TriplesGraph
mkGraph = mkRdf 
  [Triple
    ((unode . s2b) "http://example.org/users/robstewart")
    ((unode . s2b) "foaf:topic_interest")
    ((unode . s2b) "http://dbpedia.org/resource/Haskell_(programming_language)")
  ,Triple
    ((unode . s2b) "http://example.org/users/robstewart")
    ((unode . s2b) "foaf:account")
    ((unode . s2b) "https://twitter.com/#!/robstewartUK")
  ]
  Nothing
  (PrefixMappings
    (Map.fromList [ (s2b "foaf", s2b "http://xmlns.com/foaf/0.1/") ]))