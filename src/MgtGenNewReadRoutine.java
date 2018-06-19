import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class MgtGenNewReadRoutine {

	public static void main(String[] args) {
		FileReader filereader = null;
		File file1=new File("F:\\paper8\\genreadroutine\\input\\readmgt.f");
		File file2=new File("F:\\paper8\\genreadroutine\\output\\readmgt.f");
		BufferedWriter out = null;
		try {
			filereader = new FileReader(file1);
			
		
		
			out = new BufferedWriter(new FileWriter(file2, false));
				
		

			List<String> lines = IOUtils.readLines(filereader);

			for (int i = 0; i < lines.size(); i++) {

				
				if(lines.get(i).trim().startsWith("read"))
				//if(tmp.trim().startsWith("read"))
				{
					
					out.write("        string=getstr(mgtfile,hruk)");
					out.write("\r\n");
					
					out.write(lines.get(i).replace("(109", "(string"));
					out.write("\r\n");
					
					out.write("        deallocate(string)");
					out.write("\r\n");
					
				}else {
					out.write(lines.get(i));
					out.write("\r\n");
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

	}

}
