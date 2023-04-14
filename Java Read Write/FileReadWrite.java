import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileReadWrite{

    public static void main(String[] args) {
        try {
            File file = new File("file.txt");
            File file2 = new File("file2.txt");
            Scanner s = new Scanner(file);
            FileWriter f = new FileWriter(file2);
            String rev = "";
            while (s.hasNextLine()){
            rev = s.next()+" "+rev;
            }
            f.write(rev);
            f.flush();
            f.close();
            
        } catch (Exception e) {
          e.printStackTrace();
        }
       
    }
}