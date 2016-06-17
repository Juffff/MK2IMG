import java.util.ArrayList;

/**
 * Created by VladimirV on 15.06.2016.
 */
public class Item {
    private String name;
    private String brand;
    private String type;
    private String colour;
    private String newPrice;
    private String oldPrice;
    private ArrayList<String> sizes = new ArrayList<>();
    private ArrayList<String> fileNames = new ArrayList<>();

    public Item(String s) {
        this.name = s;
        String[] array = s.split("=");
        this.brand = array[0];
        this.type = array[1];
        this.colour = array[2];
        this.newPrice = array[3];
        this.oldPrice = array[4];
        for (int i = 5; i < array.length - 1; i++) {
            if (array[i] != null) {
/*            System.out.println(array[i]);*/
                sizes.add(array[i]);
            }
        }
    }

    public void addFileName(String s) {
        fileNames.add(s + ".jpg");
    }

    @Override
    public String toString() {
        return "Item{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", colour='" + colour + '\'' +
                ", newPrice='" + newPrice + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", sizes=" + sizes +
                ", fileNames=" + fileNames +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getColour() {
        return colour;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public String getName() {
        return name;
    }

    public String getSizesString() {
        StringBuilder s = new StringBuilder();
        String result = "";
        for (int i = 0; i < sizes.size(); i++) {
            s.append(sizes.get(i));
            s.append(", ");
            result = s.toString();
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}
