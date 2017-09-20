package com.adsama.android.bakeup.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredients implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    private Ingredients() {
    }

    public Ingredients(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }


    public String getMeasure() {
        return measure;
    }


    public String getIngredient() {
        return ingredient;
    }


    public final static Parcelable.Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        public Ingredients createFromParcel(Parcel in) {
            Ingredients instance = new Ingredients();
            instance.quantity = ((int) in.readValue((int.class.getClassLoader())));
            instance.measure = ((String) in.readValue((String.class.getClassLoader())));
            instance.ingredient = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Ingredients[] newArray(int size) {
            return (new Ingredients[size]);
        }

    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }

    public int describeContents() {
        return 0;
    }

}
