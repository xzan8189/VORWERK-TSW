package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unisa.DriverManagerConnectionPool;
import it.unisa.control.RegisteredAccountController;

public class CarrelloModelDM{

	//metodi per i robot
	public Carrello doRetrieveByKeyRobot(String username, int codeRobot) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
		
		Carrello carrello= new Carrello();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql="SELECT * FROM carrello WHERE username= ? AND codeRobot = ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, codeRobot);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				carrello.setUsername(rs.getString("username"));
				carrello.setCodeRobot(rs.getInt("codeRobot"));
				carrello.setQuantity(rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return carrello;
	}

	//mi faccio dare tutti i robot che ha nel carrello questo username
	public ArrayList<Carrello> doRetrieveAll(String username) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		ArrayList<Carrello> listProduct=new ArrayList<Carrello>();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT * FROM carrello WHERE username = ?";
			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setString(1, username);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				Carrello carrello=  new Carrello();
				
				carrello.setUsername(username);
				carrello.setCodeRobot(rs.getInt("codeRobot"));
				carrello.setQuantity(rs.getInt("quantity"));
				
				listProduct.add(carrello);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return listProduct;
	}
	
	public void doSaveRobot(String username, Robot robot) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "INSERT INTO carrello(username, codeRobot, quantity) values (?, ?, ?);";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, robot.getCode());
			preparedStatement.setString(3, "1");
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			RegisteredAccountController.message="Il robot <b>" + robot.getName() + "</b> è stato aggiunto in modo corretto!";
		} catch (SQLException e) {
			e.printStackTrace();
			RegisteredAccountController.errorMessage="<b>ERRORE</b>, Il robot non è stato aggiunto!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}	
	}

	public void doUpdateRobot(String username, Robot robot, int quantity) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE carrello SET quantity = ? WHERE username = ? AND codeRobot = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);	
			
			preparedStatement.setInt(1, quantity);
			preparedStatement.setString(2, username);
			preparedStatement.setInt(3, robot.getCode());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			RegisteredAccountController.message="Il robot <b>" + robot.getName() + "</b> è stato aggiunto in modo corretto!";
		} catch (SQLException e) {
			e.printStackTrace();
			RegisteredAccountController.errorMessage="<b>ERRORE</b>, Il robot non è stato aggiunto!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}	
		
	}

	public void doDeleteRobot(String username, Robot robot) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "DELETE FROM carrello WHERE codeRobot = ? AND username = ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, robot.getCode());
			preparedStatement.setString(2, username);
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			RegisteredAccountController.message="Il prodotto <b>" + robot.getName() + "</b> è stato eliminato dal carrello!";
		} catch (SQLException e) {
			e.printStackTrace();
			RegisteredAccountController.errorMessage="<b>ERRORE</b>, Il prodotto non è stato eliminato dal carrello!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	//clear carrello
	public void doDeleteAll(String username) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "DELETE FROM carrello WHERE username = ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, username);
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			RegisteredAccountController.message="Il carrello è stato svuotato!";
		} catch (SQLException e) {
			e.printStackTrace();
			RegisteredAccountController.errorMessage="<b>ERRORE</b>, Il carrello non è stato svuotato.";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}
	
	
}
