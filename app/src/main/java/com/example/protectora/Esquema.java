package com.example.protectora;

import android.provider.BaseColumns;

public class Esquema {
    public Esquema() {
    }

    public static abstract class Tarea implements BaseColumns {
        public static final String TABLE_NAME = "TAREA";

        public static final String COLUMN_NAME_IDTAREA = "idTarea";
        public static final String COLUMN_NAME_NOMBRETAREA = "nombreTarea";
        public static final String COLUMN_NAME_DESCRIPCIONTAREA = "descripcionTarea";
        public static final String COLUMN_NAME_FECHAINICIO = "fechaInicio";
        public static final String COLUMN_NAME_FECHAFIN = "fechaFin";

        public static final String COLUMN_TYPE_IDTAREA = "INTEGER";
        public static final String COLUMN_TYPE_NOMBRETAREA = "TEXT";
        public static final String COLUMN_TYPE_DESCRIPCIONTAREA = "TEXT";
        public static final String COLUMN_TYPE_FECHAINICIO = "INTEGER";
        public static final String COLUMN_TYPE_FECHAFIN = "INTEGER";

    }
}
