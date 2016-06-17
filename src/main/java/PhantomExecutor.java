import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhantomExecutor {

    public void exec (String s) throws IOException, InterruptedException {
        System.out.println("phantom/win/phantomjs.exe "+s);
        Process process = Runtime.getRuntime().exec("phantom/win/phantomjs.exe "+s);
        int exitStatus = process.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String currentLine="";
        StringBuilder stringBuilder = new StringBuilder(exitStatus==0?"SUCCESS:":"ERROR:");
        currentLine= bufferedReader.readLine();
        while(currentLine !=null)
        {
            stringBuilder.append(currentLine);
            currentLine = bufferedReader.readLine();
        }

        System.out.println(stringBuilder.toString());

    }

    public PhantomExecutor() throws IOException, InterruptedException {
    }

}
