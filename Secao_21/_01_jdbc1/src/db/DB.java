package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	// Objeto de conexão com BD do JDBC
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				// DriverManager -> conecta com o banco de dados -> Pode gerar uma exceção
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	// Método para fechar a conexão
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		// db.properties funciona porque arquivo está na pasta raiz do projeto
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			// Carregar propriedades que estão no db.properties
			props.load(fs); // load faz a leitura do arquivo apontado pela stream fs e armazena os dados no obj props
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
 