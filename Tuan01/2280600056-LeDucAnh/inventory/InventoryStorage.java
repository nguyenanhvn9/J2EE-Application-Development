package Tuan01;

import java.io.*;
import java.util.*;

public class InventoryStorage {
    public static void saveToFile(List<Product> products, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Product> loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Product>) ois.readObject();
        }
    }
} 