package com.exc.unipi_agenda.model;

import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Admin extends User{
    public Admin(String username) {
        super(username);
    }
    public boolean update(int id, String name, String description,  String date, float duration){
        Connection conn = Db.getConnection();
        if (conn != null) {
            String sql_query = "UPDATE meeting SET name=?,description = ?,date=?,duration=? WHERE id_meeting =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql_query);
                ps.setString(1,name);
                ps.setString(2,description);
                ps.setString(3,date);
                ps.setFloat(4,duration);
                ps.setInt(4,id);
                boolean result = ps.execute();
                conn.close();
                return result;
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteParticipant(int id_meeting, String participantUsername){
        Connection conn = Db.getConnection();
        if (conn == null) {
            return false;
        }

        String sql_query = "DELETE FROM meeting_participants WHERE id_meeting = ? AND username = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql_query);
            ps.setInt(1,id_meeting);
            ps.setString(2,participantUsername);
            ps.execute();
            conn.close();
            return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean update(int id, String description){
        Connection conn = Db.getConnection();
        if (conn == null) {
            return false;
        }

        String sql_query = "UPDATE meeting SET description = ? WHERE id_meeting = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql_query);
            ps.setString(1,description);
            ps.setInt(2,id);
            ps.execute();
            conn.close();
            return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean updateTitle(int id, String title){
        Connection conn = Db.getConnection();
        if (conn == null) {
            return false;
        }

        String sql_query = "UPDATE meeting SET name = ? WHERE id_meeting = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql_query);
            ps.setString(1,title);
            ps.setInt(2,id);
            ps.execute();
            conn.close();
            return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean updateDate(int id, String date){
        Connection conn = Db.getConnection();
        if (conn == null) {
            return false;
        }

        String sql_query = "UPDATE meeting SET date = ? WHERE id_meeting = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql_query);
            ps.setString(1,date);
            ps.setInt(2,id);
            ps.execute();
            conn.close();
            return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean delete(int id){
        Connection conn = Db.getConnection();
        if (conn == null) {
            return false;
        }
        String sql_query;
        PreparedStatement ps;
        try {
            sql_query = "DELETE FROM user_notification where id_meeting = ?;" +
                        "DELETE FROM meeting_comments WHERE id_meeting = ?;"+
                        "DELETE FROM meeting_participants WHERE id_meeting = ?;" +
                        "DELETE FROM meeting WHERE id_meeting = ?;";
            ps = conn.prepareStatement(sql_query);
            ps.setInt(1,id);
            ps.setInt(2,id);
            ps.setInt(3,id);
            ps.setInt(4,id);
            boolean result = ps.execute();
            conn.close();
            return result;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public void addParticipants(int id, String[] participants) {
        Connection conn = Db.getConnection();
        if (conn != null) {
            String sql_query = "INSERT INTO meeting_participants(id_meeting, username, invitation_status,date) VALUES (?,?,?,NOW());";
            try {
                PreparedStatement ps = conn.prepareStatement(sql_query);
                for (String p : participants) {
                    ps.setInt(1, id);
                    ps.setString(2, p);
                    ps.setString(3, "open");
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
