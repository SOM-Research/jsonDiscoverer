package fr.inria.atlanmod.discoverer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.internal.jobs.Counter;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.core.service.LauncherService;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

import fr.inria.atlanmod.json.Model;

public class JsonATLDiscoverer {

	private static final String bundleName = "ATLPSchain";

	private static final String statistics = "/files/statistics.txt";
	private static final String errorwarning = "/files/errandwarn.txt";

	private static final String counterPath = getLocation() + "resources/mycounter.xmi";
	private static final String varIdPath = getLocation() + "resources/variableidentificationclass.xmi";    

	private static final String inputModelPath = getLocation() + "resources/com.ibm.pdp.mdl.kernel.editor.javaxmi";
	private static final String pruningPath = getLocation() + "pruningk/pruning_";

	private static final String javaMetamodelPath = getLocation() + "metamodels/java.ecore";
	private static final String variableIdentificationMetamodelPath = getLocation() + "metamodels/variableidentification.ecore";
	private static final String counterMetamodelPath = getLocation() + "metamodels/counter.ecore";

	private static final String noJavaDocPath = getLocation() + "removejdock/nojavadoc_";

	private static final String slicingPath = getLocation() + "slicingk/slicing_";

	private static final String pruningTransformation = "/resources/NewPruning.asm";
	private static final String removingJavadocTransformation = "/resources/RemoveJavaDoc.asm";
	private static final String newDefaultSlicing = "/resources/NewDefaultSlicing.asm";

	private static Writer wstat = getWriter(statistics);
	private static Writer werwa = getWriter(errorwarning);


	public static final String getLocation() {

		return Platform.getBundle(bundleName).getLocation().replaceFirst("reference:", "");
	}

	public static Writer getWriter(String f) {

		URL url = Platform.getBundle(bundleName).getEntry(f);

		Writer output = null;

		try {
			File file = new File(FileLocator.resolve(url).toURI());    
			output = new BufferedWriter(new FileWriter(file));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

	public void run() throws IOException {
//
//		long start;
//		long end;
//		long global;
//
//		Model model = VariableIdentificationInjector(varIdPath);
//
//		int vars = 6;//model.getVariables().size();
//
//		System.out.println("size " + vars);
//
//		Counter counter = CounterInjector(counterPath);
//		resetCounter(counter);
//		CounterExtractor(counterPath,counter);
//		counter = CounterInjector(counterPath);
//
//		global = System.currentTimeMillis();
//		int actual = counter.getInt().getValue();
// 
//		while (vars > actual) {
//			System.out.println(actual);
//			start = 0;
//			end = 0;
//
//			wstat.append("Variable(" + actual + "): " + model.getVariables().get(actual).getName() + "\n");
//
//			start = System.currentTimeMillis();
//
//			Pruning(actual);
//
//			end = (System.currentTimeMillis() - start)/1000;
//			wstat.append("Pruning " + (int)end + "s\n");
//			//            
//			//            start = System.currentTimeMillis();
//			//            
//			//            RemovingJavaDoc(actual);
//			//            
//			//            end = (System.currentTimeMillis() - start)/1000;
//			//            wstat.append("Removing Java Doc " + (int)end + "s\n");
//			//            
//			//            start = System.currentTimeMillis();
//			//            
//			//            Slicing(actual);
//			//            
//			//            end = (System.currentTimeMillis() - start)/1000;
//			//            wstat.append("Slicing " + (int)end + "s\n");
//
//			wstat.flush();
//			werwa.flush();
//
//			//debug
//			System.out.println(actual+1 + " Class:" + model.getVariables().get(actual).getClass_() + " Variable:" +model.getVariables().get(actual).getName());
//
//			//increment counter
//			incrementCounter(counter);            
//			CounterExtractor(counterPath,counter);
//			counter = CounterInjector(counterPath);
//			actual = counter.getInt().getValue();
//
//
//		}
//
//		global = (System.currentTimeMillis() - global)/1000;
//
//		wstat.append("Global Timing: " + (int)global + "s\n");
//
//		wstat.close();
//		werwa.close();

	}

	public void Pruning(int counter) throws IOException {
//
//		try {
//
//			ILauncher launcher = new EMFVMLauncher();
//			IProgressMonitor ip = new NullProgressMonitor();
//
//			Map<String,String> inoutModels = new HashMap<String,String>();
//			inoutModels.put("IN", "JAVA");
//			inoutModels.put("IN1", "VARS");
//			inoutModels.put("IN2", "COUNT");
//
//			Map<String,String> paths = new HashMap<String,String>();
//
//			paths.put("JAVA", javaMetamodelPath);
//			paths.put("VARS", variableIdentificationMetamodelPath);
//			paths.put("COUNT", counterMetamodelPath);
//
//			paths.put("IN", inputModelPath);
//			paths.put("IN1", varIdPath);
//			paths.put("IN2", counterPath);
//
//			paths.put("REFINED#IN", pruningPath + counter + ".xmi");
//
//			Map<String, Object> options = new HashMap<String, Object>();
//
//			options.put("isRefiningTraceMode", new Boolean(true));
//
//			URL transformation  = PSLauncher.class.getResource(pruningTransformation);
//
//			LauncherService.launch(
//					ILauncher.RUN_MODE, 
//					ip, 
//					launcher, 
//					Collections.<String, String> emptyMap(), 
//					inoutModels, 
//					Collections.<String, String> emptyMap(),  
//					paths, 
//					options, 
//					Collections.<String, InputStream> emptyMap(), 
//					transformation.openStream()
//					);
//
//		} catch (Exception e) {
//			werwa.append(e.getMessage() + "\n");
//		}

	}

	public void RemovingJavaDoc(int counter) throws IOException {
//
//		ILauncher launcher = new EMFVMLauncher();
//		IProgressMonitor ip = new NullProgressMonitor();
//
//		Map<String,String> inoutModels = new HashMap<String,String>();
//		inoutModels.put("IN", "JAVA");
//
//		Map<String,String> paths = new HashMap<String,String>();
//		paths.put("IN", pruningPath + counter + ".xmi");
//		paths.put("REFINED#IN", noJavaDocPath + counter + ".xmi");
//
//		paths.put("JAVA", javaMetamodelPath);
//
//		Map<String, Object> options = new HashMap<String, Object>();
//
//		options.put("printExecutionTime",new Boolean(true));
//		options.put("isRefiningTraceMode", new Boolean(true));
//
//		URL transformation  = PSLauncher.class.getResource(removingJavadocTransformation);
//
//		try {
//			LauncherService.launch(
//					ILauncher.RUN_MODE, 
//					ip, 
//					launcher, 
//					Collections.<String, String> emptyMap(), 
//					inoutModels, 
//					Collections.<String, String> emptyMap(),  
//					paths, 
//					options, 
//					Collections.<String, InputStream> emptyMap(), 
//					transformation.openStream()
//					);
//
//		} catch (Exception e) {
//			werwa.append(e.getMessage() + "\n");
//		}

	}

	public void Slicing(int counter) throws IOException {
//
//		ILauncher launcher = new EMFVMLauncher();
//		IProgressMonitor ip = new NullProgressMonitor();
//
//		Map<String,String> inoutModels = new HashMap<String,String>();
//		inoutModels.put("IN", "JAVA");
//
//		Map<String,String> paths = new HashMap<String,String>();
//		paths.put("IN", noJavaDocPath + counter + ".xmi");
//		paths.put("OUT", slicingPath + counter + ".xmi");
//		paths.put("REFINED#IN", slicingPath + counter + ".xmi");
//
//		paths.put("JAVA", javaMetamodelPath);
//
//		Map<String, Object> options = new HashMap<String, Object>();
//
//		options.put("isRefiningTraceMode", new Boolean(true));
//
//		URL transformation  = PSLauncher.class.getResource(newDefaultSlicing);
//
//		try {
//			LauncherService.launch(
//					ILauncher.RUN_MODE, 
//					ip, 
//					launcher, 
//					Collections.<String, String> emptyMap(), 
//					inoutModels, 
//					Collections.<String, String> emptyMap(),  
//					paths, 
//					options, 
//					Collections.<String, InputStream> emptyMap(), 
//					transformation.openStream()
//					);
//
//
//		} catch (Exception e) {
//			werwa.append(e.getMessage() + "\n");
//		}

	}

	public Counter CounterInjector(String modelPath) {
//
//		ResourceSet resSet = new ResourceSetImpl();
//		resSet.getPackageRegistry().put(CounterPackage.eNS_URI, CounterPackage.eINSTANCE);
//		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new EcoreResourceFactoryImpl());
//		Resource res = resSet.getResource(URI.createURI(modelPath),true);                       
//
//		try {
//			res.load(null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Counter counter = (Counter) res.getContents().get(0);
//		return counter;
		return null;

	}

	public void CounterExtractor(String modelPath, Counter counter) {
//
//		ResourceSet resSet = new ResourceSetImpl();
//		resSet.getPackageRegistry().put(CounterPackage.eNS_URI, CounterPackage.eINSTANCE);
//		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new EcoreResourceFactoryImpl());
//		Resource res = resSet.createResource(URI.createURI(modelPath));
//		res.getContents().add(counter);
//
//		try {
//			res.save(Collections.EMPTY_MAP);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void incrementCounter(Counter counter) {
//		int old = counter.getInt().getValue();
//		counter.getInt().setValue(old+1);
	}

	public void resetCounter(Counter counter) {
//		counter.getInt().setValue(4);
	}

	public Model VariableIdentificationInjector(String modelPath) {
//
//		ResourceSet resSet = new ResourceSetImpl();
//		resSet.getPackageRegistry().put(VariableIdentificationPackage.eNS_URI, VariableIdentificationPackage.eINSTANCE);
//		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new EcoreResourceFactoryImpl());
//		Resource res = resSet.getResource(URI.createURI(modelPath),true);                   
//
//		try {
//			res.load(null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Model model = (Model) res.getContents().get(0);
//		return model;
		return null;
	}

}
