package com.example.simple_todo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category_Model implements Parcelable {

    public Category_Model(String category, int quantity) {
        this.category = category;
        this.quntity = quantity;
    }

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public int quntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }


    public Category_Model() {}

    public Category_Model(Parcel in) {
        id = in.readInt();
        category = in.readString();
        quntity = in.readInt();
    }

    public static final Creator<Category_Model> CREATOR = new Creator<Category_Model>() {
        @Override
        public Category_Model createFromParcel(Parcel in) {
            return new Category_Model(in);
        }

        @Override
        public Category_Model[] newArray(int size) {
            return new Category_Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(category);
        dest.writeInt(quntity);
    }

}
