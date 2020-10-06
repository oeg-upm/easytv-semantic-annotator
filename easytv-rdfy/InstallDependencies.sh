#!/bin/bash

mvn install:install-file -Dfile=lib/virt_jena.jar -DgroupId=com.openlink.virtuoso -DartifactId=virt_jena3 -Dversion=3.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/virtjdbc3.jar -DgroupId=com.openlink.virtuoso -DartifactId=virtjdbc3 -Dversion=3.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/hdt-java-core-2.0.jar -DgroupId=org.rdfhdt -DartifactId=hdt-java-core -Dversion=2.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/original-rmlmapper-4.3.2-r92.jar -DgroupId=be.ugent.rml -DartifactId=rmlmapper -Dversion=4.3.2 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/original-rmlmapper-4.5.0-r141.jar -DgroupId=be.ugent.rml -DartifactId=rmlmapper -Dversion=4.5 -Dpackaging=jar -DgeneratePom=true