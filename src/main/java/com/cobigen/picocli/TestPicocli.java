package com.cobigen.picocli;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.runtime.IPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.cobigen.api.CobiGen;
import com.devonfw.cobigen.api.exception.InvalidConfigurationException;
import com.devonfw.cobigen.api.extension.Merger;
import com.devonfw.cobigen.api.to.IncrementTo;

import org.apache.commons.io.Charsets;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.core.resources.ResourcesPlugin;
import com.devonfw.cobigen.eclipse.common.tools.ResourcesPluginUtil;
import com.devonfw.cobigen.eclipse.generator.CobiGenWrapper;
import com.devonfw.cobigen.eclipse.generator.java.JavaInputGeneratorWrapper;
import com.devonfw.cobigen.impl.CobiGenFactory;
import com.devonfw.cobigen.impl.extension.PluginRegistry;
import com.devonfw.cobigen.impl.util.TemplatesJarUtil;
import com.devonfw.cobigen.javaplugin.JavaTriggerInterpreter;
import com.devonfw.cobigen.javaplugin.inputreader.JavaInputReader;
import com.devonfw.cobigen.maven.GenerateMojo;
import com.devonfw.cobigen.maven.validation.InputPreProcessor;
import com.devonfw.cobigen.textmerger.TextAppender;
import com.devonfw.cobigen.textmerger.TextMergerPluginActivator;
import com.devonfw.cobigen.tsplugin.merger.TypeScriptMerger;
import com.devonfw.cobigen.xmlplugin.XmlTriggerInterpreter;
import com.google.common.collect.Lists;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
@Command(name = "TestPicocli", header = "%n@|TestPicocli Hello world demo|@")
public class TestPicocli implements Runnable {
	private static Logger logger =  LoggerFactory.getLogger(TestPicocli.class);
	GenerateMojo generateMojo = new GenerateMojo();
	//ClassLoader cl = generateMojo.getProjectClassLoader();
	/*@Parameters(index = "0", paramLabel = "Input", description = "Input to load.")
	private File input;

	@Parameters(index = "1", paramLabel = "LOG DIR", description = "Directory to store log files.")
	String dir;*/
    /**
     * Location of workspace root
     */
	

	public static boolean isFilenameValid(File input) {

		try {
			input.getCanonicalPath();
			System.out.println("valid file");
			String fileName = input.getName().toUpperCase();
			if (fileName.endsWith(".JAVA") || fileName.endsWith(".YML") || fileName.endsWith(".TXT")) {
				System.out.println("valid file");
				return true;
			} else {
				System.out.println("Not valid file");
				return false;
			}

		} catch (IOException e) {
			System.out.println("Not valid file");
			return false;
		}
	}

	public static boolean isValidDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("Not a valid directories");
			return false;
		}
		System.out.println("Valid directories");
		return true;
	}

	public void run() {
	
		//System.out.println("input, " + isFilenameValid(input));
		//System.out.println("logDir, " + isValidDir(dir));
	}
	

	public static void main(String... args) throws Exception {
		// logger.info("start main method");
		File jarFile = null;
		File jarPath = new File("C:\\MyData\\IDE4\\workspaces\\.metadata\\cobigen_jar");
		List<Object> inputs = Lists.newLinkedList();

		logger.info("Please enter input from command prompt");
		Scanner inputReader = new Scanner(System.in);
		String userInput = inputReader.nextLine();
		File inputFile = new File(userInput);

		if (!jarPath.exists()) {
			jarPath.mkdir();

		}
		try {

			TemplatesJarUtil.downloadLatestDevon4jTemplates(true, jarPath);

			logger.info("Successfully download source jar");

			jarFile = TemplatesJarUtil.getJarFile(true, jarPath);
			logger.info("get jar file");
			if (jarFile != null) {
				CobiGen cg = CobiGenFactory.create(jarFile.toURI());
				JavaTriggerInterpreter javaTriger = new JavaTriggerInterpreter("java");
				PluginRegistry.registerTriggerInterpreter(javaTriger);
				TypeScriptMerger tsmerger = new TypeScriptMerger("tsmerge", false);
				PluginRegistry.registerMerger(tsmerger);
				XmlTriggerInterpreter xmlTriggerInterpreter = new XmlTriggerInterpreter("xml");
				PluginRegistry.registerTriggerInterpreter(xmlTriggerInterpreter);

				List<Merger> merger = Lists.newLinkedList();
				merger.add(new TextAppender("textmergerActivator", false));

				logger.info("After create method");

				if (!inputFile.exists() || !inputFile.canRead()) {
					logger.info("The file " + inputFile.getAbsolutePath() + " is not a valid input for CobiGen.");
					throw new Exception("Could not read input file " + inputFile.getAbsolutePath());

				}

				Object input = InputPreProcessor.process(cg, inputFile, null);

				List<IncrementTo> matchingIncrements = cg.getMatchingIncrements(input);
				if (matchingIncrements.size() > 0) {

					System.out.println("Increments Available = " + matchingIncrements.get(0).getDescription());
				}
				cg.generate(input, matchingIncrements, Paths.get("C:\\Users\\syadav9\\Desktop\\temp"));
				System.out.println("Successfully generated Template");
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MojoFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("successfully call cobigen create method");
		System.out.println("successfully call cobigen create method");

		// CommandLine.run(new TestPicocli(), System.err, args);

	}
}
