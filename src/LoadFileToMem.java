import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import redis.clients.jedis.Jedis;

/**
 * 
 */

public class LoadFileToMem {

	static String fromString = "F:\\paper8\\model\\bak\\";
	static String file1 = ".hru";
	static String file2 = ".mgt";
	static String file3 = ".sol";
	static String file4 = ".chm";
	static String file5 = ".gw";
	static String file6 = ".ops";
	static String file7 = ".sep";

	private static Jedis jedis;

	public static void setup() {
		//连接redis服务器，192.168.0.100:6379
		jedis = new Jedis("127.0.0.1", 6379);
		//权限认证
		// jedis.auth("admin");  
	}

	public static void main(String[] args) {
		setup();
		File[] files = getFiles();

		for (int i = 0; i < files.length; i++) {
			loadFile(files[i]);
		}
	}

	public static void loadFile(File file1) {

		FileReader filereader = null;
		try {
			filereader = new FileReader(file1);

			List<String> lines = IOUtils.readLines(filereader);

			for (int i = 0; i < lines.size(); i++) {

				String tmpString = lines.get(i);

				//System.out.println(tmpString);
				String[] tokens = tmpString.split("\\|");

				//tokens[0] = tokens[0].trim();

				String tString = "" + i;
				int kk = 3 - tString.length();
				for (int j = 0; j < kk; j++) {
					tString = "0" + tString;
				}

				if (file1.getName().endsWith(".gw")) {
					jedis.set(file1.getName() + "f." + tString, tokens[0]);
				} else {
					jedis.set(file1.getName() + "." + tString, tokens[0]);
				}

				System.out.println(file1.getName() + "." + tString);
				System.out.println(jedis.get(file1.getName() + "." + tString));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static File[] getFiles() {

		File dir = new File(fromString);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.startsWith(".")
						&& !name.startsWith("output")
						&& (name.endsWith(file1) || name.endsWith(file2)
								|| name.endsWith(file3) || name.endsWith(file4)
								|| name.endsWith(file5) || name.endsWith(file6) || name
								.endsWith(file7));
			}
		};

		File[] files = dir.listFiles(filter);

		return files;

	}

}
