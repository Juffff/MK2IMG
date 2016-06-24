import java.io.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Item> itemList = new ArrayList<>();

    public Main() throws IOException, InterruptedException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        cleanDir();
        System.out.println("Press Enter");

        //reading images directory and creating itemList
        File directory = new File("images");
        System.out.println(directory.getAbsolutePath());
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            String itemName = files[i].getName().split(".jpg")[0];
            if (itemName.charAt(itemName.length() - 1) == '0') {
                Item item = new Item(itemName);
                itemList.add(item);
                item.addFileName(itemName);

            } else {
                itemList.get(itemList.size() - 1).addFileName(itemName);
            }

        }

        for (Item i : itemList) {
            System.out.println(i.toString());
        }

        System.out.println(itemList);
        new BufferedReader(new InputStreamReader(System.in)).readLine();

        for (int i = 0; i < itemList.size(); i++) {
            try {
                createHTML(i);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        createJS();

        File jsFiles = new File("outJS");
        File[] arrayFiles = jsFiles.listFiles();
        PhantomExecutor executor = new PhantomExecutor();
        for (int i = 0; i < arrayFiles.length; i++) {
            executor.exec("\"" + arrayFiles[i].getAbsolutePath() + "\"");
        }
    }


    public static void createHTML(int i) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader("htmlcollagetemplate/index.html"));

        String s = "";

        Item item = itemList.get(i);
        File f = new File("outHtml/"+new Translit().toTranslit(item.getName())+".html");
        if (!f.exists()){
            System.out.println(f.getName());
            System.out.println(f.getPath());
            f.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
        while (reader.ready()) {
            s = reader.readLine();
            if (s.contains("$listprice")) {
                s = s.replace("$listprice", item.getOldPrice());
            }

            if (s.contains("$price")) {
                try {
                    s = s.replace("$price", String.valueOf(Integer.parseInt(item.getNewPrice())+30));

                }catch (Exception e){}

            }

            if (s.contains("$brand")) {
                s = s.replace("$brand", item.getBrand());
            }

            if (s.contains("$type")) {
                s = s.replace("$type", item.getType());
            }

            if (s.contains("$colour")) {
                s = s.replace("$colour", item.getColour());
            }

            if (s.contains("$sizes")) {
                s = s.replace("$sizes", item.getSizesString());
            }

            if (s.contains("$url1")) {
                try {
                    s = s.replace("$url1", item.getFileNames().get(0));
                } catch (IndexOutOfBoundsException e){
                    s = s.replace("$url1", "../htmlcollagetemplate/img/blogo.png");
                }
            }

            if (s.contains("$url2")) {
                try {
                    s = s.replace("$url2", item.getFileNames().get(1));
                } catch (IndexOutOfBoundsException e){
                    s = s.replace("$url2", "../htmlcollagetemplate/img/blogo.png");
                }
            }

            if (s.contains("$url3")) {

                try {
                    s = s.replace("$url3", item.getFileNames().get(2));
                } catch (IndexOutOfBoundsException e){
                    s = s.replace("$url3", "../htmlcollagetemplate/img/blogo.png");
                }
            }

            if (s.contains("$url4")) {
                try {
                    s = s.replace("$url4", item.getFileNames().get(3));
                } catch (IndexOutOfBoundsException e){
                    s = s.replace("$url4", "../htmlcollagetemplate/img/blogo.png");
                }

            }
            bufferedWriter.write(s);
        }
        bufferedWriter.close();
        reader.close();
    }

    public static void createJS() throws IOException, InterruptedException {

        File dir = new File("outHTML");
        File[] fileArray = dir.listFiles();
        for(File f:fileArray){
            String fileName = "";
            fileName = f.getAbsolutePath().replace(".html",".js");
            fileName =fileName.replace("outHTML","outJS");



            File jsFile = new File(fileName);
            if (!jsFile.exists()){
                jsFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader("phantom/js/html2imgTemplate.js"));
            String s = "";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(jsFile));
            while (reader.ready()) {
                s = reader.readLine();
                if (s.contains("$path")) {
                    s = s.replace("$path", "outHTML/"+f.getName());
                }

                if (s.contains("$imgName")) {
                    try {
                        s = s.replace("$imgName", f.getAbsolutePath().replaceAll("\\\\", "/").replaceAll("\\.html",".png")).replace("outHTML","outIMG");

                    }catch (Exception e){}

                }


                bufferedWriter.write(s);
            }
            bufferedWriter.close();
            reader.close();
        }

    }

    public static void cleanDir(){
        File outHtmlFile = new File("outHTML");
        File[] outHtmlFiles = outHtmlFile.listFiles();
        for(File f:outHtmlFiles){
            if(f.isFile()) f.delete();
        }

        File images = new File("images");
        File[] imagesFiles = images.listFiles();
        for(File f:imagesFiles){
            if(f.isFile()) f.delete();
        }

        File outJsFile = new File("outJS");
        File[] outJsFiles = outJsFile.listFiles();
        for(File f:outJsFiles){
            if(f.isFile()) f.delete();
        }

        File outImgFile = new File("outIMG");
        File[] outImgFiles = outImgFile.listFiles();
        for(File f:outImgFiles){
            if(f.isFile()) f.delete();
        }
    }





}
