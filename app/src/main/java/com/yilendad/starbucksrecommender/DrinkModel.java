/**
 * for drinkmodel creation
 */
package com.yilendad.starbucksrecommender;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class DrinkModel implements Parcelable {

    String name, size, milk, whip;
    int calories;
    double totalFat;
    int cholesterol, totalCarbohydrates, sugar;
    double protein;
    int caffeine;

    /**
     * constructor
     * @param name
     * @param size
     * @param milk
     * @param servingSize
     * @param calories
     * @param totalFat
     * @param cholesterol
     * @param totalCarbohydrates
     * @param sugar
     * @param protein
     * @param caffeine
     */
    public DrinkModel(String name, String size, String milk,
                      String servingSize, int calories, double totalFat, int cholesterol,
                      int totalCarbohydrates, int sugar, double protein, int caffeine) {
        this.name = name;
        this.size = size;
        this.milk = milk;
        this.whip = servingSize;
        this.calories = calories;
        this.totalFat = totalFat;
        this.cholesterol = cholesterol;
        this.totalCarbohydrates = totalCarbohydrates;
        this.sugar = sugar;
        this.protein = protein;
        this.caffeine = caffeine;
    }

    @Override
    public String toString() {
        return "DrinkModel{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", milk='" + milk + '\'' +
                ", whip='" + whip + '\'' +
                ", calories=" + calories +
                ", totalFat=" + totalFat +
                ", cholesterol=" + cholesterol +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", sugar=" + sugar +
                ", protein=" + protein +
                ", caffeine=" + caffeine +
                '}';
    }
    //get description of this drink as a string
    public String description() {
        return "size='" + size + '\'' +
                ", milk='" + milk + '\'' +
                ", whip='" + whip + '\'' +
                ", calories=" + calories +
                ", totalFat=" + totalFat +
                ", cholesterol=" + cholesterol +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", sugar=" + sugar +
                ", protein=" + protein +
                ", caffeine=" + caffeine;
    }

    /**
     * compare drink based on menu selection
     */
    public static Comparator<DrinkModel> nameAZSort = new Comparator<DrinkModel>() {
        @Override
        public int compare(DrinkModel d1, DrinkModel d2) {
            return d1.getName().compareTo(d2.getName());
        }
    } ;

    public static Comparator<DrinkModel> calDesc = new Comparator<DrinkModel>() {
        @Override
        public int compare(DrinkModel d1, DrinkModel d2) {
            return d2.getCalories()-(d1.getCalories());
        }
    } ;

    public static Comparator<DrinkModel> calAsc = new Comparator<DrinkModel>() {
        @Override
        public int compare(DrinkModel d1, DrinkModel d2) {
            return d1.getCalories()-(d2.getCalories());
        }
    } ;
//getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMilk() {
        return milk;
    }

    public void setMilk(String milk) {
        this.milk = milk;
    }

    public String getWhip() {
        return whip;
    }

    public void setWhip(String whip) {
        this.whip = whip;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(int totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public int getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(int caffeine) {
        this.caffeine = caffeine;
    }

    /**
     * for parcel type implementation
     *
     * @param in
     */
    public DrinkModel(Parcel in) {
        name = in.readString();
        size = in.readString();
        milk = in.readString();
        whip = in.readString();
        calories = in.readInt();
        totalFat = in.readDouble();
        cholesterol = in.readInt();
        totalCarbohydrates = in.readInt();
        sugar = in.readInt();
        protein =in.readDouble();
        caffeine = in.readInt();
    }

    public static final Parcelable.Creator<DrinkModel> CREATOR = new Parcelable.Creator<DrinkModel>() {
        public DrinkModel createFromParcel(Parcel in) {
            return new DrinkModel(in) ;
        }

        public DrinkModel[] newArray(int size) {
            return new DrinkModel[size];

        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(milk);
        dest.writeString(whip);
        dest.writeInt(calories);
        dest.writeDouble(totalFat);
        dest.writeInt(cholesterol);
        dest.writeInt(totalCarbohydrates);
        dest.writeInt(sugar);
        dest.writeDouble(protein);
        dest.writeInt(caffeine);
    }
}
