package Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.sax.RootElement;

import Model.Users;


/**
 * Created by Desarrollo on 8/29/2016.
 */
public class Connection extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Moviles.db";

    public Connection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static abstract class DataTable implements BaseColumns {
        //Table Questions
        public static final String TABLE_NAME_QUESTIONS = "Questions";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_THEME = "Theme";
        public static final String COLUMN_Question = "Question";
        //Table Users
        public static final String TABLE_NAME_USERS = "Users";
        public static final String COLUMN_ID_USER = "Id";
        public static final String COLUMN_FULL_NAME= "FullName";
        public static final String COLUMN_LAST_NAME = "LastName";
        public static final String COLUMN_USERNAME= "UserName";
        public static final String COLUMN_PASSWORD = "Password";
        //TOOLS
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_DELETE_TABLE_QUESTIONS= "DROP TABLE IF EXISTS " + DataTable.CREAR_TABLA_QUESTIONS;
        private static final String SQL_DELETE_TABLE_USERS = "DROP TABLE IF EXISTS " + DataTable.CREATE_TABLE_USERS;

        private static final String CREAR_TABLA_QUESTIONS =
                "CREATE TABLE " + DataTable.TABLE_NAME_QUESTIONS + " (" +
                        DataTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DataTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataTable.COLUMN_THEME + TEXT_TYPE + COMMA_SEP +
                        DataTable.COLUMN_Question + TEXT_TYPE + " )";

        private static final String CREATE_TABLE_USERS =
                "CREATE TABLE " + DataTable.TABLE_NAME_USERS + " (" +
                        DataTable.COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DataTable.COLUMN_FULL_NAME + TEXT_TYPE + COMMA_SEP +
                        DataTable.COLUMN_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                        DataTable.COLUMN_USERNAME + TEXT_TYPE + COMMA_SEP +
                        DataTable.COLUMN_PASSWORD + TEXT_TYPE + " )";

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataTable.CREAR_TABLA_QUESTIONS);
        sqLiteDatabase.execSQL(DataTable.CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DataTable.CREAR_TABLA_QUESTIONS);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(DataTable.CREATE_TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

    private ContentValues ParameterValues;
    private String sMessage;
    private String sTransaction;
    private String sTableTransaction;


    public void setsTransaction(String sTransaction) {
        this.sTransaction = sTransaction;
    }

    private void setParameterValues(ContentValues parameterValues) {
        ParameterValues = parameterValues;
    }

    public String getsMessage() {
        return sMessage;
    }


    public boolean ExecuteStatements(Users User){
        try {
            SQLiteDatabase Conex = this.getWritableDatabase();
            boolean Bandera;

            Bandera = Conex.insert(sTableTransaction,null,ParameterValues) > 0;
            Conex.close();
            sMessage = "Value: " + Bandera;
            return Bandera;
        }catch (Exception e){
            sMessage = e.toString();
            return false;
        }
    }

    private boolean ChooseTable(Users User){
        ParameterValues = new ContentValues();
        try {
            if (sTransaction.toString().equals("")) {
                sMessage = "you need to select a type of transaction";
                return false;
            }
            switch (sTransaction)
            {
                case "Register":
                    sTableTransaction = Connection.DataTable.TABLE_NAME_USERS;
                    ParameterValues.put(Connection.DataTable.COLUMN_FULL_NAME, User.COLUMN_FULL_NAME);
                    ParameterValues.put(Connection.DataTable.COLUMN_LAST_NAME, User.COLUMN_LAST_NAME);
                    ParameterValues.put(Connection.DataTable.COLUMN_USERNAME, User.COLUMN_USERNAME);
                    ParameterValues.put(Connection.DataTable.COLUMN_PASSWORD, User.COLUMN_PASSWORD);
                    break;
                case "Question":
                    sTableTransaction = DataTable.TABLE_NAME_QUESTIONS;
                    ParameterValues.put(DataTable.COLUMN_FULL_NAME, User.COLUMN_FULL_NAME);
                    ParameterValues.put(DataTable.COLUMN_LAST_NAME, User.COLUMN_FULL_NAME);
                    ParameterValues.put(DataTable.COLUMN_USERNAME, User.COLUMN_FULL_NAME);
                    ParameterValues.put(DataTable.COLUMN_PASSWORD, User.COLUMN_FULL_NAME);
                    break;
            }
            return true;
        }catch (Exception e){
            sMessage = e.toString();
            return false;
        }
    }

}
