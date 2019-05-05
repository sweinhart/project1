package com.revature.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revature.domain.Aircraft;

public class AircraftDAO {

	private static AircraftDAO dao = new AircraftDAO();
	private static final String url = "jdbc:postgresql://localhost:5432/postgresProject1";
	private static final String user = "sweinhart";
	private static final String password = "Password";

	public static AircraftDAO getDAO() {
		return dao;
	}

	public void setup() {
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String sql = "CREATE TABLE IF NOT EXISTS aircraft (\n" + "	id SERIAL PRIMARY KEY,\n"
					+ "	manufacturer text NOT NULL,\n" + "	name text NOT NULL,\n" + "	type text NOT NULL,\n"
					+ "	range integer NOT NULL,\n" + "	speed integer NOT NULL,\n" + "	load integer NOT NULL,\n"
					+ "	passengers integer NOT NULL,\n" + "	engine_power text NOT NULL,\n"
					+ "	engine_manufacturer text NOT NULL,\n" + "	engine_model text NOT NULL,\n"
					+ "	num_engines integer NOT NULL,\n" + "	length real NOT NULL,\n" + "	height real NOT NULL,\n"
					+ "	wing_span real NOT NULL,\n" + "	wing_area real NOT NULL,\n" + "	ceiling integer NOT NULL\n"
					+ ");";
			try {
				Statement statement = conn.createStatement();
				statement.execute(sql);
				close(conn);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public Aircraft addAircraft(Aircraft aircraft) {
		String sql = "INSERT INTO aircraft (manufacturer, name, type, range, speed, load, passengers, "
				+ "engine_power, engine_manufacturer, engine_model, num_engines, length, height, wing_span, wing_area, ceiling) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, aircraft.getManufacturer());
				pstmt.setString(2, aircraft.getAircraftName());
				pstmt.setString(3, aircraft.getAircraftType());
				pstmt.setInt(4, aircraft.getRangeNM());
				pstmt.setInt(5, aircraft.getMaxCruiseSpeedKtas());
				pstmt.setInt(6, aircraft.getUsefulLoad());
				pstmt.setInt(7, aircraft.getNumPassengers());
				pstmt.setString(8, aircraft.getEnginePower());
				pstmt.setString(9, aircraft.getEngineManufacturer());
				pstmt.setString(10, aircraft.getEngineModel());
				pstmt.setInt(11, aircraft.getNumEngines());
				pstmt.setDouble(12, aircraft.getLengthM());
				pstmt.setDouble(13, aircraft.getHeightM());
				pstmt.setDouble(14, aircraft.getWingSpanM());
				pstmt.setDouble(15, aircraft.getWingAreaSqM());
				pstmt.setInt(16, aircraft.getServiceCeilingFt());
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Aircraft insertion failed, no rows affected.");
				}
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						aircraft.setId(generatedKeys.getInt(1));
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return aircraft;
	}

	public Aircraft getAircraft(Integer id) {
		Aircraft aircraft = new Aircraft();
		String sql = "SELECT id, manufacturer, name, type, range, speed, "
				+ "load, passengers, engine_power, engine_manufacturer, engine_model, num_engines, "
				+ "length, height, wing_span, wing_area, ceiling " + "FROM aircraft WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					aircraft.setId(rs.getInt("id"));
					aircraft.setManufacturer(rs.getString("manufacturer"));
					aircraft.setAircraftName(rs.getString("name"));
					aircraft.setAircraftType(rs.getString("type"));
					aircraft.setRangeNM(rs.getInt("range"));
					aircraft.setMaxCruiseSpeedKtas(rs.getInt("speed"));
					aircraft.setUsefulLoad(rs.getInt("load"));
					aircraft.setNumPassengers(rs.getInt("passengers"));
					aircraft.setEnginePower(rs.getString("engine_power"));
					aircraft.setEngineManufacturer(rs.getString("engine_manufacturer"));
					aircraft.setEngineModel(rs.getString("engine_model"));
					aircraft.setNumEngines(rs.getInt("num_engines"));
					aircraft.setLengthM(rs.getDouble("length"));
					aircraft.setHeightM(rs.getDouble("height"));
					aircraft.setWingSpanM(rs.getDouble("wing_span"));
					aircraft.setWingAreaSqM(rs.getDouble("wing_area"));
					aircraft.setServiceCeilingFt(rs.getInt("ceiling"));
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return aircraft;
	}

	public Aircraft getAircraft(String manufacturer, String name) {
		Aircraft aircraft = new Aircraft();
		String sql = "SELECT id, manufacturer, name, type, range, speed, "
				+ "load, passengers, engine_power, engine_manufacturer, engine_model, num_engines, "
				+ "length, height, wing_span, wing_area, ceiling " + "FROM aircraft WHERE manufacturer = ? AND name = ?";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, manufacturer);
				pstmt.setString(2, name);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					aircraft.setId(rs.getInt("id"));
					aircraft.setManufacturer(rs.getString("manufacturer"));
					aircraft.setAircraftName(rs.getString("name"));
					aircraft.setAircraftType(rs.getString("type"));
					aircraft.setRangeNM(rs.getInt("range"));
					aircraft.setMaxCruiseSpeedKtas(rs.getInt("speed"));
					aircraft.setUsefulLoad(rs.getInt("load"));
					aircraft.setNumPassengers(rs.getInt("passengers"));
					aircraft.setEnginePower(rs.getString("engine_power"));
					aircraft.setEngineManufacturer(rs.getString("engine_manufacturer"));
					aircraft.setEngineModel(rs.getString("engine_model"));
					aircraft.setNumEngines(rs.getInt("num_engines"));
					aircraft.setLengthM(rs.getDouble("length"));
					aircraft.setHeightM(rs.getDouble("height"));
					aircraft.setWingSpanM(rs.getDouble("wing_span"));
					aircraft.setWingAreaSqM(rs.getDouble("wing_area"));
					aircraft.setServiceCeilingFt(rs.getInt("ceiling"));
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return aircraft;
	}
	
	public List<Aircraft> getAll() {
		List<Aircraft> list = new ArrayList<Aircraft>();
		String sql = "SELECT id, manufacturer, name, type, range, speed, "
				+ "load, passengers, engine_power, engine_manufacturer, engine_model, num_engines, "
				+ "length, height, wing_span, wing_area, ceiling " + "FROM aircraft";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Aircraft aircraft = new Aircraft();
					aircraft.setId(rs.getInt("id"));
					aircraft.setManufacturer(rs.getString("manufacturer"));
					aircraft.setAircraftName(rs.getString("name"));
					aircraft.setAircraftType(rs.getString("type"));
					aircraft.setRangeNM(rs.getInt("range"));
					aircraft.setMaxCruiseSpeedKtas(rs.getInt("speed"));
					aircraft.setUsefulLoad(rs.getInt("load"));
					aircraft.setNumPassengers(rs.getInt("passengers"));
					aircraft.setEnginePower(rs.getString("engine_power"));
					aircraft.setEngineManufacturer(rs.getString("engine_manufacturer"));
					aircraft.setEngineModel(rs.getString("engine_model"));
					aircraft.setNumEngines(rs.getInt("num_engines"));
					aircraft.setLengthM(rs.getDouble("length"));
					aircraft.setHeightM(rs.getDouble("height"));
					aircraft.setWingSpanM(rs.getDouble("wing_span"));
					aircraft.setWingAreaSqM(rs.getDouble("wing_area"));
					aircraft.setServiceCeilingFt(rs.getInt("ceiling"));
					list.add(aircraft);
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Collections.sort(list);
		return list;
	}

	public Boolean deleteAircraft(Aircraft aircraft) {
		String sql = "DELETE FROM aircraft WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, aircraft.getId());
				pstmt.executeUpdate();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	public Aircraft updateAircraft(Aircraft aircraft) {
		String sql = "UPDATE aircraft SET manufacturer = ? , name = ? , "
				+ "type = ? , range = ? , speed = ? , load = ? , "
				+ "passengers = ? , engine_power = ? , engine_manufacturer = ? , engine_model = ? , num_engines = ? , "
				+ "length = ? , height = ? , wing_span = ? , wing_area = ? , ceiling = ? " + "WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, aircraft.getManufacturer());
				pstmt.setString(2, aircraft.getAircraftName());
				pstmt.setString(3, aircraft.getAircraftType());
				pstmt.setInt(4, aircraft.getRangeNM());
				pstmt.setInt(5, aircraft.getMaxCruiseSpeedKtas());
				pstmt.setInt(6, aircraft.getUsefulLoad());
				pstmt.setInt(7, aircraft.getNumPassengers());
				pstmt.setString(8, aircraft.getEnginePower());
				pstmt.setString(9, aircraft.getEngineManufacturer());
				pstmt.setString(10, aircraft.getEngineModel());
				pstmt.setInt(11, aircraft.getNumEngines());
				pstmt.setDouble(12, aircraft.getLengthM());
				pstmt.setDouble(13, aircraft.getHeightM());
				pstmt.setDouble(14, aircraft.getWingSpanM());
				pstmt.setDouble(15, aircraft.getWingAreaSqM());
				pstmt.setInt(16, aircraft.getServiceCeilingFt());
				pstmt.setInt(17, aircraft.getId());
				pstmt.executeUpdate();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return aircraft;
	}

	private static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
