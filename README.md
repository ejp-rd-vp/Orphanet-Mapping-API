# Orphanet-Mappings-API
This project focuses on the Orphanet mapping API.

The Orphanet nomenclature is a multilingual, standardised, controlled medical terminology specific to rare diseases, that includes all clinical entities registered in the Orphanet database (www.orpha.net). Each clinical entity (disorder, group of disorders, or subtype of a disorder) is associated with a unique numerical identifier named ORPHAcode, as well as a preferred term, synonyms, and a definition.

### Mapping Alignments
Orphanet maintains an alignment between clinical entities with external terminologies or resources (ICD-10, OMIM, UMLS, MeSH, MedDRA and GARD).
The mapping includes a semantic link that specifies the relationship between an ORPHAcode and the external terminologies.
The alignments specify the comparability between terminologies by defining if the concepts are perfectly equivalent (exact mapping) or not.

E (Exact mapping: the two concepts are equivalent). 

NTBT (ORPHAcode's Narrower Term maps to a Broader Term).

BTNT (ORPHAcode's Narrower Term maps to a Broader Term).

### API parameters
`/: 
    get:
      description: "Get information about the API."`
 
Get mappings with a query for a clinical entity (resources). From any terminology listed to another (orphanet, omim, umls, mesh, meddra, icd). Based on Orphanet's mappings.

`/mapping?form={origin}&code={code}&to={destination} will return results for a clinical entity (resources), from any terminology listed to another (orphanet, omim, umls, mesh, meddra, icd). Based on Orphanets mappings. "`

ex: [http://purl.org/orphanetws/mapping/
ex: [http://purl.org/orphanetws/mapping?from=icd&code=Q87.4] 

ex: [http://purl.org/orphanetws/mapping?from=icd&code=Q87.4&to=omim] 

ex : [http://purl.org/orphanetws/mapping?from=orphanet&code=558&to=omim&to=icd]
   
### Disclaimer
The content of this API represents the views of the author only and is his/her sole responsibility; it cannot be considered to reflect the views of the European Commission and/or the Consumers, Health, Agriculture and Food Executive Agency or any other body of the European Union. The European Commission and the Agency do not accept any responsibility for use that may be made of the information it contains.

We have chosen to apply the Commons Attribution 4.0 International (CC BY 4.0) to all copyrightable parts of our databases. This means that you are free to copy, distribute, display and make commercial use of these databases in all legislations, provided you give us credit.
