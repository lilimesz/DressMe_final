package com.google.dressme.clothesPage.newItem;

import java.util.ArrayList;

class ColorUtils {

    /**
     * Initialize the color list that we have.
     */
    public ArrayList<ColorName> initColorList() {
        ArrayList<ColorName> colorList = new ArrayList<>();
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("Dark slate gray", 0x2F, 0x4F, 0x4F));
        colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColorName("Dark gray", 0xA9, 0xA9, 0xA9));
        colorList.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));

        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
       // colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));

        colorList.add(new ColorName("Dark red", 0x8B, 0x00, 0x00));
        colorList.add(new ColorName("Dark orange", 0xFF, 0x8C, 0x00));
        colorList.add(new ColorName("Dark golden rod", 0xB8, 0x86, 0x0B));
        colorList.add(new ColorName("Dark green", 0x00, 0x64, 0x00));
        colorList.add(new ColorName("Dark blue", 0x00, 0x00, 0x8B));
        colorList.add(new ColorName("Deep pink", 0xFF, 0x14, 0x93));



        colorList.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
        colorList.add(new ColorName("Orange-Red", 0xFF, 0x45, 0x00));
        colorList.add(new ColorName("Golden rod", 0xDA, 0xA5, 0x20));
        colorList.add(new ColorName("Forest green", 0x22, 0x8B, 0x22));
        colorList.add(new ColorName("Royal Blue", 0x41, 0x69, 0xE1));
        colorList.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Dark violet", 0x94, 0x00, 0xD3));


        colorList.add(new ColorName("Tomato", 0xFF, 0x63, 0x47));
        colorList.add(new ColorName("Coral", 0xFF, 0x7F, 0x50));
        colorList.add(new ColorName("Green yellow", 0xAD, 0xFF, 0x2F));
        colorList.add(new ColorName("Light Blue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColorName("Salmon", 0xFA, 0x80, 0x72));
        colorList.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));
        colorList.add(new ColorName("Chartreuse", 0x7F, 0xFF, 0x00));

        colorList.add(new ColorName("Rosy Brown", 0xBC, 0x8F, 0x8F));
        colorList.add(new ColorName("Light Coral", 0xF0, 0x80, 0x80));
        colorList.add(new ColorName("Light Yellow", 0xFF, 0xFF, 0xE0));
        colorList.add(new ColorName("Light Green", 0x90, 0xEE, 0x90));
        colorList.add(new ColorName("Sky Blue", 0x87, 0xCE, 0xEB));
        colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));

        colorList.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
        colorList.add(new ColorName("Brown", 0xA5, 0x2A, 0x2A));
        colorList.add(new ColorName("Blanched almond", 0xFF, 0xEB, 0xCD));
        colorList.add(new ColorName("Tan", 0xD2, 0xB4, 0x8C));
        colorList.add(new ColorName("Beige", 0xF5, 0xF5, 0xDC));
        colorList.add(new ColorName("Floral white", 0xFF, 0xFA, 0xF0));
        colorList.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));

        return colorList;
    }

    public  String getColorNameFromRgb(int r, int g, int b) {
        ArrayList<ColorName> colorList = initColorList();
        ColorName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColorName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    public static class ColorName {
        public int r, g, b;
        public String name;

        public ColorName(String name, int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.name = name;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return ((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
                    * (pixB - b)) / 3;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public String getName() {
            return name;
        }
    }
}