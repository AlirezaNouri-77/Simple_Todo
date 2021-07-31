package com.example.simple_todo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class category_model implements Parcelable {

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


    public category_model() {}

    public category_model(Parcel in) {
        id = in.readInt();
        category = in.readString();
        quntity = in.readInt();
    }

    public static final Creator<category_model> CREATOR = new Creator<category_model>() {
        @Override
        public category_model createFromParcel(Parcel in) {
            return new category_model(in);
        }

        @Override
        public category_model[] newArray(int size) {
            return new category_model[size];
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
