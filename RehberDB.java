package com.example.rehberim;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RehberDB {
    private static final String DBName = "Rehber";
    private static final String TableName = "Kisiler";
    private static final int DBVersion = 1;

    public static final String KeyRowId = "_id";
    public static final String KeyAd = "ad";
    public static final String KeySoyad = "soyad";
    public static final String KeyTel = "tel";
    public static final String KeyKonum = "konum";
    public static final String KeyFoto = "foto";


    private final Context DBContext;

    private RehberDBHelper rehberDBHelper;

    private SQLiteDatabase rehberDB;

    public RehberDB(Context c)
    {
        this.DBContext = c;
    }

    public RehberDB connectRehber()
    {
        rehberDBHelper = new RehberDBHelper(DBContext);
        rehberDB = rehberDBHelper.getWritableDatabase();
        return this;
    }

    public long ekle(String ad,String soyad, String tel, String konum, String foto)
    {
        ContentValues cv = new ContentValues();

        cv.put(KeyAd,ad);
        cv.put(KeySoyad,soyad);
        cv.put(KeyTel,tel);
        cv.put(KeyKonum,konum);
        cv.put(KeyFoto,foto);

        return rehberDB.insert(TableName,null,cv);
    }
    public String dataGetir()
    {
        String[] sutunlar =  new String[]{KeyRowId,KeyAd,KeySoyad,KeyTel,KeyKonum,KeyFoto};
        Cursor c = rehberDB.query(TableName,sutunlar,null,null,null,null,null);
        String records="";

        int idindex = c.getColumnIndex(KeyRowId);
        int adindex = c.getColumnIndex(KeyAd);
        int soyadindex = c.getColumnIndex(KeySoyad);
        int telindex = c.getColumnIndex(KeyTel);
        int konumindex = c.getColumnIndex(KeyKonum);
        int fotoindex = c.getColumnIndex(KeyFoto);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            records += c.getString(idindex)+ " - " +
                    c.getString(adindex)+" "+
                    c.getString(soyadindex)+" - " +
                    c.getString(telindex)+"\n";
        }

        return records;
    }
    public void sil (long _id){
        String wClause =KeyRowId + "=" +_id;
        rehberDB.delete(this.TableName,wClause,null);
        //String queryString = "DELETE FROM"+TableName+"WHERE"+ wClause;



    }
    public  void dataGuncelle(String _id,String _ad,String _soyad,String _tel,String _foto, String _konum) {
        ContentValues cv =new ContentValues();
    if(_soyad!=null )
        if(! _soyad.isEmpty())
            cv.put(KeySoyad, _soyad);

    if(_ad!=null )
        if(! _ad.isEmpty())
            cv.put(KeyAd, _ad);

    if(_tel!=null )
        if(! _tel.isEmpty())
            cv.put(KeyTel, _tel);

    if(_id!=null )
        if(! _id.isEmpty())
            cv.put(KeyRowId, _id);

    if(_foto!=null )
        if(! _foto.isEmpty())
            cv.put(KeyFoto, _foto);

    }

    public void disconnectRehber(){
        rehberDBHelper.close();
    }

    private static class RehberDBHelper extends SQLiteOpenHelper {
        public RehberDBHelper(Context DBContext) {
            super(DBContext,DBName,null,DBVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TableName+ " ("+
                            KeyRowId + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                            KeyAd +" TEXT NOT NULL , "+
                            KeySoyad + " TEXT NOT NULL , "+
                            KeyTel + " TEXT NOT NULL , "+
                            KeyKonum + " TEXT , "+
                            KeyFoto  + " TEXT)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TableName);
            onCreate(db);
        }
    }
}
