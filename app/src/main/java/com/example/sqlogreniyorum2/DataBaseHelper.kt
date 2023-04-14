package com.example.sqlogreniyorum2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name= "Veritabanim"
val table_name="Users"
val col_name="adisoyadi"
val col_age="yasi"
val col_memleket="memleket"
val col_tel="tel"
val col_id="id"

class DataBaseHelper(var context:Context):SQLiteOpenHelper(context, database_name,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable=
            "CREATE TABLE $table_name ($col_id INTEGER PRIMARY KEY AUTOINCREMENT,$col_name VARCHAR(256),$col_age INTEGER,$col_memleket VARCHAR(256),$col_tel VARCHAR(256))"
        db?.execSQL(createTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Veritabanı yükseltmek için kullanılır
    }
    //Veri kaydetmek için fonksiyon tanımlıyoruz.
    fun insertData(user:User){
        val db= this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name,user.adsoyad)
        cv.put(col_age,user.yas)
        cv.put(col_memleket,user.memleket)
        cv.put(col_tel,user.tel)
        var sonuc = db.insert(table_name,null,cv)
        if (sonuc== (-1).toLong()){
            Toast.makeText(context,"Hatalı",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Başarılı",Toast.LENGTH_SHORT).show()
        }
    }

        // Verileri okumak için fonksiyon tanımlıyoruz.

        fun readData():MutableList<User>{
            var liste : MutableList<User> = ArrayList()
            val db = this.readableDatabase
            var sorgu = " Select * from " + table_name
            var sonuc = db.rawQuery(sorgu,null)
            if (sonuc.moveToFirst()){
                do {
                    var user = User()
                    user.id=sonuc.getString(sonuc.getColumnIndexOrThrow(col_id)).toInt()
                    user.adsoyad=sonuc.getString(sonuc.getColumnIndexOrThrow(col_name))
                    user.yas=sonuc.getString(sonuc.getColumnIndexOrThrow(col_age)).toInt()
                    user.memleket=sonuc.getString(sonuc.getColumnIndexOrThrow(col_memleket))
                    user.tel=sonuc.getString(sonuc.getColumnIndexOrThrow(col_tel))
                    liste.add(user)

                }while (sonuc.moveToNext())
            }
            sonuc.close()
            db.close()
            return liste
        }

    //Verileri güncellemek için fonksiyon tanımlıyoruz.
    fun updateData(){
        val db = this.readableDatabase
        var sorgu ="Select * from $table_name"
        var sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(col_age,(sonuc.getInt(sonuc.getColumnIndexOrThrow(col_age)))+1)
                cv.put(col_name,(sonuc.getString(sonuc.getColumnIndexOrThrow(col_name)))+" "+"güncel")
                cv.put(col_memleket,(sonuc.getString(sonuc.getColumnIndexOrThrow(col_memleket)))+" "+"güncel")
                cv.put(col_tel,(sonuc.getString(sonuc.getColumnIndexOrThrow(col_tel)))+" "+"güncel")
                db.update(table_name,cv,"$col_id=? AND $col_name=? AND $col_memleket=? AND  $col_tel=?",
                    arrayOf(sonuc.getString(sonuc.getColumnIndexOrThrow(col_id)),
                        sonuc.getString(sonuc.getColumnIndexOrThrow(col_name)),
                        sonuc.getString(sonuc.getColumnIndexOrThrow(col_memleket)),
                        sonuc.getString(sonuc.getColumnIndexOrThrow(col_tel))))
            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
    }

    //Verileri silmek için fonksiyon tanımlıyoruz.
    fun deleteData(){
        var db = this.writableDatabase
        db.delete(table_name,null,null)
        db.close()

    }
}