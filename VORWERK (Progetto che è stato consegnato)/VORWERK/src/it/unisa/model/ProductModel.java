package it.unisa.model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductModel<T> {

	public T doRetrieveByKey(String code) throws SQLException;
	
	public ArrayList<T> doRetrieveAll(String order) throws SQLException;
	
	public void doSave(T product) throws SQLException;
	
	public void doUpdate(T product) throws SQLException;
	
	public void doDelete(T product) throws SQLException;
	
	
}
