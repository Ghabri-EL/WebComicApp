package main.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class htmlCreator
{
    String test = "This is a test";
    File tempHtml = new File("C:\\testFolder\\test.html");
    BufferedWriter bw;
    {
        try {
            bw = new BufferedWriter(new FileWriter(tempHtml));
            bw.write(test);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
