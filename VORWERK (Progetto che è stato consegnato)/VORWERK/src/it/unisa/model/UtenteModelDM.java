package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unisa.DriverManagerConnectionPool;

public class UtenteModelDM implements ProductModel<Utente>{

	@Override
	public Utente doRetrieveByKey(String username) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
	
		Utente utente= new Utente();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql="SELECT * FROM utente WHERE username= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, username);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setPoints(rs.getString("punti"));
				utente.setRole(rs.getString("role"));
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
		
		return utente;
	}

	@Override
	public ArrayList<Utente> doRetrieveAll(String order) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		ArrayList<Utente> listUtenti=new ArrayList<Utente>();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT * FROM utente";
			if (order!=null && !order.equals(""))
				sql+=" ORDER BY " + order;
			preparedStatement= connection.prepareStatement(sql);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				Utente utente= new Utente();

				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setPoints(rs.getString("punti"));
				utente.setRole(rs.getString("role"));
				
				listUtenti.add(utente);
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
		
		return listUtenti;
	}

	@Override
	public void doSave(Utente utente) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "INSERT INTO utente values (?, ?, ?, ?);";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, utente.getUsername());
			preparedStatement.setString(2, utente.getPassword());
			preparedStatement.setString(3, utente.getPoints());
			preparedStatement.setString(4, utente.getRole());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
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
	}

	@Override
	public void doUpdate(Utente utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE utente SET password = ?, punti = ?, role = ? WHERE username = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);	
			
			preparedStatement.setString(1, utente.getPassword());
			preparedStatement.setString(2, utente.getPoints());
			preparedStatement.setString(3, utente.getRole());
			preparedStatement.setString(4, utente.getUsername());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
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
		
	}

	@Override
	public void doDelete(Utente utente) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "DELETE FROM utente WHERE username= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, utente.getUsername());
			
			preparedStatement.executeUpdate();

			connection.commit();
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
	}

}
