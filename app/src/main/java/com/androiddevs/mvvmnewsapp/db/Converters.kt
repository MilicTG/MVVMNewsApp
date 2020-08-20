package com.androiddevs.mvvmnewsapp.db

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.models.Source

//Room can only handle primitive types
//for custom types like Source class you need convert it to primitive type
class Converters {

    //define what happens when you convert from Source
    //returns a String
    //Annotation tell Room this is converter
    @TypeConverter
    fun fromSource(source: Source): String {
        //just extract string value
        return source.name
    }

    //Convert Source object to String
    @TypeConverter
    fun toSource(name: String): Source {
        return (Source(name, name))
    }
}