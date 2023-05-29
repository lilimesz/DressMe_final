package com.google.dressme.clothesPage.newItem;

import java.util.ArrayList;

class ColorUtils {
    public ArrayList<ColorName> colorList = new ArrayList<>();

    public ArrayList<ColorName> initColorList() {
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColorName("Dark gray", 0xA9, 0xA9, 0xA9));
        colorList.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
        colorList.add(new ColorName("Light gray", 0xF0, 0xF0, 0xF0));
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));

        colorList.add(new ColorName("Dark red", 0x8B, 0x00, 0x00)); //FIX
        colorList.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("Light Coral", 0xF0, 0x80, 0x80));
        colorList.add(new ColorName("Light Red", 0xFF, 0xCB, 0xCB));
        colorList.add(new ColorName("Salt", 0xF8, 0xEE, 0xEC));


        colorList.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
        colorList.add(new ColorName("Dark orange", 0xFF, 0x8C, 0x00));
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("Peach Puff", 0xFF, 0xDA, 0xB9));
        colorList.add(new ColorName("Papaya Whip", 0xFF, 0xEF, 0xD5));
        colorList.add(new ColorName("Coconut", 0xFF, 0xF1, 0xE6));

        colorList.add(new ColorName("Dark golden rod", 0xB8, 0x86, 0x0B));
        colorList.add(new ColorName("Golden rod", 0xDA, 0xA5, 0x20));
        colorList.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("Cream", 0xFF, 0xFF, 0xC0));
        colorList.add(new ColorName("Light Yellow", 0xFF, 0xFF, 0xE0));

        colorList.add(new ColorName("Dark green", 0x00, 0x64, 0x00));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("Lime Green", 0x32, 0xCD, 0x32));
        colorList.add(new ColorName("Green yellow", 0xAD, 0xFF, 0x2F));
        colorList.add(new ColorName("Tea green", 0xD0, 0xF0, 0xC0));
        colorList.add(new ColorName("Light tea", 0xE5, 0xF6, 0xDF));

        colorList.add(new ColorName("Dark blue", 0x00, 0x00, 0x8B));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        colorList.add(new ColorName("Royal Blue", 0x41, 0x69, 0xE1));
        colorList.add(new ColorName("Sky Blue", 0x87, 0xCE, 0xEB));
        colorList.add(new ColorName("Light Blue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColorName("Mint Cream", 0xF5, 0xFF, 0xFA));

        colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));
        colorList.add(new ColorName("Dark violet", 0x94, 0x00, 0xD3));
        colorList.add(new ColorName("Dark orchid", 0x99, 0x32, 0xCC));
        colorList.add(new ColorName("Medium orchid", 0xBA, 0x55, 0xD3));
        colorList.add(new ColorName("Plum", 0xDD, 0xA0, 0xDD));
        colorList.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));


        colorList.add(new ColorName("Violet-Red", 0xC7, 0x15, 0x85));
        colorList.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Orchid", 0xDA, 0x70, 0xD6));
        colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
        colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        colorList.add(new ColorName("Lavender Blush", 0xFF, 0xF0, 0xF5));


        colorList.add(new ColorName("Peanut", 0x79, 0x5C, 0x34));
        colorList.add(new ColorName("Peru", 0xCD, 0x85, 0x3F));
        colorList.add(new ColorName("Burly wood", 0xDE, 0xB8, 0x87));
        colorList.add(new ColorName("Bisque", 0xFF, 0xE4, 0xC4));
        colorList.add(new ColorName("Almond", 0xFF, 0xEB, 0xCD));
        colorList.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));

        return colorList;
    }

    public String getColorNameFromRgb(int r, int g, int b) {
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