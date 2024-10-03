package OV.DAO;



import OV.Domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOpsql implements ReizigerDAO {

    private Connection con;
    private OVChipkaartDAO ovChipkaartDAO;


    public ReizigerDAOpsql(Connection con ){
        this.con = con;

    }


    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO Reiziger(reiziger_id,voorletter,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());
            ps.executeUpdate();


            if (reiziger.getOvChipkaarts() != null) {
                OVChipkaartDAO.save(reiziger.getOvChipkaarts());
                return true;
            }
            return false;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            String query = "UPDATE Reiziger SET voorletter = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());
            ps.executeUpdate();


            if (reiziger.getOvChipkaarts() != null) {
                OVChipkaartDAO.save(reiziger.getOvChipkaarts());
                return true;
            }
            return false;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            String query = "DELETE FROM Reiziger WHERE reiziger_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);


                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                return reiziger;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM reiziger";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);


                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                reizigers.add(reiziger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reizigers;
    }
}
