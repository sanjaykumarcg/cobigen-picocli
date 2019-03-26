
import static picocli.CommandLine.*;

import java.io.File;
import java.io.IOException;

import picocli.CommandLine;
@Command(name = "TestPicocli", header = "%n@|TestPicocli Hello world demo|@")
public class TestPicocli implements Runnable {
	
	@Parameters(index = "0", paramLabel = "Input", description = "Input to load.")
	private File input;

	@Parameters(index = "1", paramLabel = "LOG DIR", description = "Directory to store log files.")
	String dir;

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
	
		System.out.println("input, " + isFilenameValid(input));
		System.out.println("logDir, " + isValidDir(dir));
	}

	public static void main(String... args) {
		CommandLine.run(new TestPicocli(), System.err, args);
	}
}
