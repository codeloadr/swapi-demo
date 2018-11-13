package com.nsoni.starwars.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = Character.TABLE_NAME)
public class Character implements Parcelable {

    public static final String TABLE_NAME = "characters";
    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("height")
    @Expose
    @Nullable
    @ColumnInfo(name = "height")
    private String height;
    @SerializedName("mass")
    @Expose
    @Nullable
    @ColumnInfo(name = "mass")
    private String mass;
    @SerializedName("hair_color")
    @Expose
    @Nullable
    @ColumnInfo(name = "hair_color")
    private String hairColor;
    @SerializedName("skin_color")
    @Expose
    @Nullable
    @ColumnInfo(name = "skin_color")
    private String skinColor;
    @SerializedName("eye_color")
    @Expose
    private String eyeColor;
    @SerializedName("birth_year")
    @Expose
    @Nullable
    @ColumnInfo(name = "birth_year")
    private String birthYear;
    @SerializedName("gender")
    @Expose
    @Nullable
    @ColumnInfo(name = "gender")
    private String gender;
    @SerializedName("homeworld")
    @Expose
    @Nullable
    @ColumnInfo(name = "homeworld")
    private String homeWorld;
    @SerializedName("films")
    @Expose
    @Nullable
    @ColumnInfo(name = "films")
    @TypeConverters({Converters.class})
    private List<String> films = null;
    @SerializedName("species")
    @Expose
    @Nullable
    @ColumnInfo(name = "species")
    @TypeConverters({Converters.class})
    private List<String> species = null;
    @SerializedName("vehicles")
    @Expose
    @Nullable
    @ColumnInfo(name = "vehicles")
    @TypeConverters({Converters.class})
    private List<String> vehicles = null;
    @SerializedName("starships")
    @Expose
    @Nullable
    @ColumnInfo(name = "starships")
    @TypeConverters({Converters.class})
    private List<String> starShips = null;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("edited")
    @Expose
    private String edited;
    @SerializedName("url")
    @Expose
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeWorld() {
        return homeWorld;
    }

    public void setHomeWorld(String homeWorld) {
        this.homeWorld = homeWorld;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getStarShips() {
        return starShips;
    }

    public void setStarShips(List<String> starShips) {
        this.starShips = starShips;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**--- Code that turns the model class into a Parcelable class ---*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.height);
        dest.writeString(this.mass);
        dest.writeString(this.hairColor);
        dest.writeString(this.skinColor);
        dest.writeString(this.eyeColor);
        dest.writeString(this.birthYear);
        dest.writeString(this.gender);
        dest.writeString(this.homeWorld);
        dest.writeStringList(this.films);
        dest.writeStringList(this.species);
        dest.writeStringList(this.vehicles);
        dest.writeStringList(this.starShips);
        dest.writeString(this.created);
        dest.writeString(this.edited);
        dest.writeString(this.url);
    }

    public Character(String name) {
        this.name = name;
    }

    protected Character(Parcel in) {
        this.name = in.readString();
        this.height = in.readString();
        this.mass = in.readString();
        this.hairColor = in.readString();
        this.skinColor = in.readString();
        this.eyeColor = in.readString();
        this.birthYear = in.readString();
        this.gender = in.readString();
        this.homeWorld = in.readString();
        this.films = in.createStringArrayList();
        this.species = in.createStringArrayList();
        this.vehicles = in.createStringArrayList();
        this.starShips = in.createStringArrayList();
        this.created = in.readString();
        this.edited = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}
