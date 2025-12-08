package org.s3team;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- S3.3 Escape Room Application Starting ---");

        // 1. Intentar obtener la instancia Singleton y abrir la conexión
        try {
            // El método getInstance() intentará conectar a MySQL.
            // Si falla, lanzará una RuntimeException y el programa terminará.
            MySQL_Data_Base_Connection dbInstance = MySQL_Data_Base_Connection.getInstance();

            // 2. Si llegamos aquí, la conexión fue exitosa.
            Connection conn = dbInstance.getConnection();

            System.out.println("Database Connection Status: SUCCESS!");
            System.out.println("JDBC Connection Object: " + conn);

            // Aquí iría el resto de la lógica de tu aplicación

            // 3. Dejar el programa en ejecución si es un servidor (Opcional, pero necesario si no tienes un servidor web)
            // Si este es un simple programa de consola, se cerrará después de esto.
            // Si tu aplicación es un servidor web, puedes usar un bucle infinito o esperar llamadas HTTP aquí.

        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: Application failed to initialize due to connection failure.");
            System.err.println("Check DB container status, network, and credentials.");
            e.printStackTrace();
            // Salida con código de error para que Docker sepa que falló el inicio
            System.exit(1);
        }
    }
}