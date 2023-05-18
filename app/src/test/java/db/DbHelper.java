package db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_DB = "DbRegistro.db";
    private static final int VERSION_DB = 1;

    public MiDBHelper(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla en la base de datos
        String consultaSQL = "CREATE TABLE mi_tabla (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
        db.execSQL(consultaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS mi_tabla");
            onCreate(db);
        }
    }
}

