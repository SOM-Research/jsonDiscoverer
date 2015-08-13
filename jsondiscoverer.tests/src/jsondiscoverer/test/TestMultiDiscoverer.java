package jsondiscoverer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;

import jsondiscoverer.JsonMultiDiscoverer;
import jsondiscoverer.JsonSource;
import jsondiscoverer.JsonSourceSet;

public class TestMultiDiscoverer {

	@Test
	public void testDiscover() throws FileNotFoundException  {
		JsonSource source1 = new JsonSource("stopPosition");
		source1.addJsonData(null, new FileReader(new File("./testData/multidiscoverer/source1/json1.json")));
		
		JsonSource source2 = new JsonSource("waitingTimeStop");
		source2.addJsonData(null, new FileReader(new File("./testData/multidiscoverer/source2/json1.json")));

		JsonSource source3 = new JsonSource("horariesStopLineDirection");
		source3.addJsonData(null, new FileReader(new File("./testData/multidiscoverer/source3/json1.json")));
				
		JsonSourceSet sourceSet = new JsonSourceSet("composed");
		sourceSet.addJsonSource(source1);
		sourceSet.addJsonSource(source2);
		sourceSet.addJsonSource(source3);
		
		JsonMultiDiscoverer composer = new JsonMultiDiscoverer(sourceSet);
		EPackage ePackage = composer.discover();
		assertNotNull(ePackage);
		
		EClassifier eClassifierHoraries = ePackage.getEClassifier("HorariesStopLineDirection");
		assertTrue(eClassifierHoraries instanceof EClass);
		EClass eClassHoraries = (EClass) eClassifierHoraries;
		
		assertEquals(7,  eClassHoraries.getEStructuralFeatures().size());
		
		assertNotNull(eClassHoraries.getEStructuralFeature("arret"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("arret").getLowerBound());
		assertEquals(1, eClassHoraries.getEStructuralFeature("arret").getUpperBound());
		assertEquals("Arret", eClassHoraries.getEStructuralFeature("arret").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("ligne"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("ligne").getLowerBound());
		assertEquals(1, eClassHoraries.getEStructuralFeature("ligne").getUpperBound());
		assertEquals("Ligne", eClassHoraries.getEStructuralFeature("ligne").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("codeCouleur"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("codeCouleur").getLowerBound());
		assertEquals(1, eClassHoraries.getEStructuralFeature("codeCouleur").getUpperBound());
		assertEquals("EString", eClassHoraries.getEStructuralFeature("codeCouleur").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("plageDeService"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("plageDeService").getLowerBound());
		assertEquals(1, eClassHoraries.getEStructuralFeature("plageDeService").getUpperBound());
		assertEquals("EString", eClassHoraries.getEStructuralFeature("plageDeService").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("notes"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("notes").getLowerBound());
		assertEquals(-1, eClassHoraries.getEStructuralFeature("notes").getUpperBound());
		assertEquals("Note", eClassHoraries.getEStructuralFeature("notes").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("horaires"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("horaires").getLowerBound());
		assertEquals(-1, eClassHoraries.getEStructuralFeature("horaires").getUpperBound());
		assertEquals("ProchainsHoraire", eClassHoraries.getEStructuralFeature("horaires").getEType().getName());

		assertNotNull(eClassHoraries.getEStructuralFeature("prochainsHoraires"));
		assertEquals(1, eClassHoraries.getEStructuralFeature("prochainsHoraires").getLowerBound());
		assertEquals(-1, eClassHoraries.getEStructuralFeature("prochainsHoraires").getUpperBound());
		assertEquals("ProchainsHoraire", eClassHoraries.getEStructuralFeature("prochainsHoraires").getEType().getName());
		

		EClassifier eClassifierLigne = ePackage.getEClassifier("Ligne");
		assertTrue(eClassifierLigne instanceof EClass);
		EClass eClassLigne = (EClass) eClassifierLigne;
		
		assertEquals(7,  eClassLigne.getEStructuralFeatures().size());
		
		assertNotNull(eClassLigne.getEStructuralFeature("numLigne"));
		assertEquals(1, eClassLigne.getEStructuralFeature("numLigne").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("numLigne").getUpperBound());
		assertEquals("EString", eClassLigne.getEStructuralFeature("numLigne").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("directionSens1"));
		assertEquals(1, eClassLigne.getEStructuralFeature("directionSens1").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("directionSens1").getUpperBound());
		assertEquals("EString", eClassLigne.getEStructuralFeature("directionSens1").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("directionSens2"));
		assertEquals(1, eClassLigne.getEStructuralFeature("directionSens2").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("directionSens2").getUpperBound());
		assertEquals("EString", eClassLigne.getEStructuralFeature("directionSens2").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("accessible"));
		assertEquals(1, eClassLigne.getEStructuralFeature("accessible").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("accessible").getUpperBound());
		assertEquals("EBoolean", eClassLigne.getEStructuralFeature("accessible").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("etatTrafic"));
		assertEquals(1, eClassLigne.getEStructuralFeature("etatTrafic").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("etatTrafic").getUpperBound());
		assertEquals("EInt", eClassLigne.getEStructuralFeature("etatTrafic").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("libelleTrafic"));
		assertEquals(1, eClassLigne.getEStructuralFeature("libelleTrafic").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("libelleTrafic").getUpperBound());
		assertEquals("EString", eClassLigne.getEStructuralFeature("libelleTrafic").getEType().getName());
		
		assertNotNull(eClassLigne.getEStructuralFeature("typeLigne"));
		assertEquals(1, eClassLigne.getEStructuralFeature("typeLigne").getLowerBound());
		assertEquals(1, eClassLigne.getEStructuralFeature("typeLigne").getUpperBound());
		assertEquals("EInt", eClassLigne.getEStructuralFeature("typeLigne").getEType().getName());

		
		EClassifier eClassifierStopPosition = ePackage.getEClassifier("StopPosition");
		assertTrue(eClassifierStopPosition instanceof EClass);
		EClass eClassStopPosition = (EClass) eClassifierStopPosition;
		
		assertEquals(4,  eClassStopPosition.getEStructuralFeatures().size());
		
		assertNotNull(eClassStopPosition.getEStructuralFeature("codeLieu"));
		assertEquals(1, eClassStopPosition.getEStructuralFeature("codeLieu").getLowerBound());
		assertEquals(1, eClassStopPosition.getEStructuralFeature("codeLieu").getUpperBound());
		assertEquals("EString", eClassStopPosition.getEStructuralFeature("codeLieu").getEType().getName());
		
		assertNotNull(eClassStopPosition.getEStructuralFeature("libelle"));
		assertEquals(1, eClassStopPosition.getEStructuralFeature("libelle").getLowerBound());
		assertEquals(1, eClassStopPosition.getEStructuralFeature("libelle").getUpperBound());
		assertEquals("EString", eClassStopPosition.getEStructuralFeature("libelle").getEType().getName());
		
		assertNotNull(eClassStopPosition.getEStructuralFeature("distance"));
		assertEquals(1, eClassStopPosition.getEStructuralFeature("distance").getLowerBound());
		assertEquals(1, eClassStopPosition.getEStructuralFeature("distance").getUpperBound());
		assertEquals("EString", eClassStopPosition.getEStructuralFeature("distance").getEType().getName());
		
		assertNotNull(eClassStopPosition.getEStructuralFeature("ligne"));
		assertEquals(1, eClassStopPosition.getEStructuralFeature("ligne").getLowerBound());
		assertEquals(-1, eClassStopPosition.getEStructuralFeature("ligne").getUpperBound());
		assertEquals("Ligne", eClassStopPosition.getEStructuralFeature("ligne").getEType().getName());

		
		EClassifier eClassifierNote = ePackage.getEClassifier("Note");
		assertTrue(eClassifierNote instanceof EClass);
		EClass eClassNote = (EClass) eClassifierNote;
		
		assertEquals(4,  eClassNote.getEStructuralFeatures().size());
		
		assertNotNull(eClassNote.getEStructuralFeature("code"));
		assertEquals(1, eClassNote.getEStructuralFeature("code").getLowerBound());
		assertEquals(1, eClassNote.getEStructuralFeature("code").getUpperBound());
		assertEquals("EString", eClassNote.getEStructuralFeature("code").getEType().getName());
		
		assertNotNull(eClassNote.getEStructuralFeature("libelle"));
		assertEquals(1, eClassNote.getEStructuralFeature("libelle").getLowerBound());
		assertEquals(1, eClassNote.getEStructuralFeature("libelle").getUpperBound());
		assertEquals("EString", eClassNote.getEStructuralFeature("libelle").getEType().getName());
		
		assertNotNull(eClassNote.getEStructuralFeature("codeArret"));
		assertEquals(1, eClassNote.getEStructuralFeature("codeArret").getLowerBound());
		assertEquals(1, eClassNote.getEStructuralFeature("codeArret").getUpperBound());
		assertEquals("EString", eClassNote.getEStructuralFeature("codeArret").getEType().getName());
		
		assertNotNull(eClassNote.getEStructuralFeature("accessible"));
		assertEquals(1, eClassNote.getEStructuralFeature("accessible").getLowerBound());
		assertEquals(1, eClassNote.getEStructuralFeature("accessible").getUpperBound());
		assertEquals("EBoolean", eClassNote.getEStructuralFeature("accessible").getEType().getName());

		
		EClassifier eClassifierProchainsHoraire = ePackage.getEClassifier("ProchainsHoraire");
		assertTrue(eClassifierProchainsHoraire instanceof EClass);
		EClass eClassProchainsHoraire = (EClass) eClassifierProchainsHoraire;
		
		assertEquals(2,  eClassProchainsHoraire.getEStructuralFeatures().size());
		
		assertNotNull(eClassProchainsHoraire.getEStructuralFeature("heure"));
		assertEquals(1, eClassProchainsHoraire.getEStructuralFeature("heure").getLowerBound());
		assertEquals(1, eClassProchainsHoraire.getEStructuralFeature("heure").getUpperBound());
		assertEquals("EString", eClassProchainsHoraire.getEStructuralFeature("heure").getEType().getName());
		
		assertNotNull(eClassProchainsHoraire.getEStructuralFeature("passages"));
		assertEquals(1, eClassProchainsHoraire.getEStructuralFeature("passages").getLowerBound());
		assertEquals(-1, eClassProchainsHoraire.getEStructuralFeature("passages").getUpperBound());
		assertEquals("EString", eClassProchainsHoraire.getEStructuralFeature("passages").getEType().getName());

		
		EClassifier eClassifierArret = ePackage.getEClassifier("Arret");
		assertTrue(eClassifierArret instanceof EClass);
		EClass eClassArret = (EClass) eClassifierArret;
		
		assertEquals(1,  eClassArret.getEStructuralFeatures().size());
		
		assertNotNull(eClassArret.getEStructuralFeature("codeArret"));
		assertEquals(1, eClassArret.getEStructuralFeature("codeArret").getLowerBound());
		assertEquals(1, eClassArret.getEStructuralFeature("codeArret").getUpperBound());
		assertEquals("EString", eClassArret.getEStructuralFeature("codeArret").getEType().getName());

		
		EClassifier eClassifierWaitingTimeStop = ePackage.getEClassifier("WaitingTimeStop");
		assertTrue(eClassifierWaitingTimeStop instanceof EClass);
		EClass eClassWaitingTimeStop = (EClass) eClassifierWaitingTimeStop;
		
		assertEquals(6,  eClassWaitingTimeStop.getEStructuralFeatures().size());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("sens"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("sens").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("sens").getUpperBound());
		assertEquals("EInt", eClassWaitingTimeStop.getEStructuralFeature("sens").getEType().getName());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("terminus"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("terminus").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("terminus").getUpperBound());
		assertEquals("EString", eClassWaitingTimeStop.getEStructuralFeature("terminus").getEType().getName());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("infotrafic"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("infotrafic").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("infotrafic").getUpperBound());
		assertEquals("EBoolean", eClassWaitingTimeStop.getEStructuralFeature("infotrafic").getEType().getName());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("temps"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("temps").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("temps").getUpperBound());
		assertEquals("EString", eClassWaitingTimeStop.getEStructuralFeature("temps").getEType().getName());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("ligne"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("ligne").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("ligne").getUpperBound());
		assertEquals("Ligne", eClassWaitingTimeStop.getEStructuralFeature("ligne").getEType().getName());
		
		assertNotNull(eClassWaitingTimeStop.getEStructuralFeature("arret"));
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("arret").getLowerBound());
		assertEquals(1, eClassWaitingTimeStop.getEStructuralFeature("arret").getUpperBound());
		assertEquals("Arret", eClassWaitingTimeStop.getEStructuralFeature("arret").getEType().getName());
		
		
	}

}
