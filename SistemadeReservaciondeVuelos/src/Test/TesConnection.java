package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TesConnection {

	public static void main(String[] args) {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
		
			final String SQL_INSERT = "INSERT INTO Cliente (usuario, nombre, email,contraseña) VALUES ('Carlos','Carlos','Carlos','Carlos')";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
	
			int row = preparedStatement.executeUpdate();
			if(row > 0) {
				System.out.print("juana");
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
