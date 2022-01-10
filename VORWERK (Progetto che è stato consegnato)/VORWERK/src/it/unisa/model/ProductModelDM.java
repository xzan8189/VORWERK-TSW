package it.unisa.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unisa.DriverManagerConnectionPool;
import it.unisa.control.Controller;

public class ProductModelDM implements ProductModel<Robot>{

	@Override
	public Robot doRetrieveByKey(String code) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
	
		Robot robot= new Robot();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql="SELECT * FROM robot WHERE code= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, Integer.parseInt(code));
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				robot.setCode(rs.getInt("code"));
				robot.setName(rs.getString("name"));
				robot.setDescription(rs.getString("description"));
				robot.setPrice(rs.getString("price"));
				robot.setQuantity(rs.getString("quantity"));
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
		
		return robot;
	}

	@Override
	public ArrayList<Robot> doRetrieveAll(String order) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		ArrayList<Robot> listRobot=new ArrayList<Robot>();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT * FROM robot";
			if (order!=null && !order.equals(""))
				sql+=" ORDER BY " + order;
			preparedStatement= connection.prepareStatement(sql);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				Robot robot= new Robot();

				robot.setCode(rs.getInt("code"));
				robot.setName(rs.getString("name"));
				robot.setDescription(rs.getString("description"));
				robot.setPrice(rs.getString("price"));
				robot.setQuantity(rs.getString("quantity"));
				
				listRobot.add(robot);
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
		
		return listRobot;
	}

	@Override
	public void doSave(Robot robot) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "INSERT INTO robot(name, description, price, quantity) values (?, ?, ?, ?);";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, robot.getName());
			preparedStatement.setString(2, robot.getDescription());
			preparedStatement.setString(3, robot.getPrice());
			preparedStatement.setString(4, robot.getQuantity());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			Controller.message="Il robot <b>" + robot.getName() + "</b> è stato inserito in modo corretto";
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il robot non è stato inserito!";
			
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
	public void doUpdate(Robot robot) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE robot SET name = ?, description = ?, price = ?, quantity = ? WHERE code = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);	
			
			preparedStatement.setString(1, robot.getName());
			preparedStatement.setString(2, robot.getDescription());
			preparedStatement.setString(3, robot.getPrice());
			preparedStatement.setString(4, robot.getQuantity());
			
			preparedStatement.setInt(5, robot.getCode());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			Controller.message="Il robot <b>" + robot.getName() + "</b> è stato aggiornato in modo corretto";
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il robot non è stato aggiornato!";
			
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
	public void doDelete(Robot robot) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "DELETE FROM robot WHERE code= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, robot.getCode());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			Controller.message="Il prodotto <b>" + robot.getName() + "</b> è stato cancellato";
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il prodotto non è stato cancellato!";
			
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
	
	public int doRetrieveHighest() throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;

		int code = 0;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql="SELECT MAX(code) AS code FROM robot;";
			preparedStatement= connection.prepareStatement(sql);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				code= rs.getInt("code");
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
		
		return code;
	}
	
	public synchronized static byte[] load(String code) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		byte[] photo=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT photo FROM robot WHERE code= ?";
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(code));
			
			rs= preparedStatement.executeQuery();
			
			if (rs.next())
				photo= rs.getBytes("photo");
			
			
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
		
		return photo;
	}
	
	public synchronized static void uploadPhoto(int code, String photo) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "UPDATE robot SET photo= ? WHERE code= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			File filePhoto= new File(photo);
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filePhoto);
				preparedStatement.setBinaryStream(1, inputStream, inputStream.available());
				preparedStatement.setInt(2, code);
				
				preparedStatement.executeUpdate();
				connection.commit();

				Controller.message="Il robot con code <b>" + code + "</b> è stato aggiornato in modo corretto";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il robot non è stato aggiornato!";
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

	
	
	
	
	
	
	
	
	
	
	
	
	
}//fine classe



















