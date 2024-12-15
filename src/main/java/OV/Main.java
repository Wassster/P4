package OV;

import OV.DAO.OVChipkaartDAOpsql;
import OV.DAO.ReizigerDAOpsql;
import OV.Domein.OVChipkaart;
import OV.Domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Setup database connection
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "postgres");) {

            ReizigerDAOpsql reizigerDAO = new ReizigerDAOpsql(conn, new OVChipkaartDAOpsql(conn));
            OVChipkaartDAOpsql ovChipkaartDAO = new OVChipkaartDAOpsql(conn);

            // Run the tests
            testReizigerDAO(reizigerDAO, ovChipkaartDAO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testReizigerDAO(ReizigerDAOpsql reizigerDAO, OVChipkaartDAOpsql ovChipkaartDAO) {



        System.out.println("[Setup] Voeg reizigers en OV-chipkaarten toe aan de database:");
        Reiziger reiziger1 = new Reiziger(1, "A", "van", "Dijk", Date.valueOf("1990-01-01"));
        OVChipkaart kaart1 = new OVChipkaart(12345, Date.valueOf("2024-12-31"), 2, 50.0);
        kaart1.setReiziger(reiziger1);
        reiziger1.addOVChipkaart(kaart1);
        reizigerDAO.save(reiziger1);

        Reiziger reiziger2 = new Reiziger(2, "B", "de", "Vries", Date.valueOf("1985-05-15"));
        OVChipkaart kaart2 = new OVChipkaart(67890, Date.valueOf("2025-06-30"), 1, 75.0);
        kaart2.setReiziger(reiziger2);
        reiziger2.addOVChipkaart(kaart2);
        reizigerDAO.save(reiziger2);


        System.out.println("\n[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        List<Reiziger> reizigers = reizigerDAO.findAll();
        if (reizigers.isEmpty()) {
            System.out.println("Geen reizigers gevonden.");
        } else {
            reizigers.forEach(System.out::println);
        }


        System.out.println("\n[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        List<OVChipkaart> ovChipkaarten = ovChipkaartDAO.findAll();
        if (ovChipkaarten.isEmpty()) {
            System.out.println("Geen OV-chipkaarten gevonden.");
        } else {
            ovChipkaarten.forEach(System.out::println);
        }


        System.out.println("\n[Test] Update de saldo van OV-chipkaart:");
        kaart1.setSaldo(100.0);
        boolean kaartUpdated = ovChipkaartDAO.update(kaart1);
        System.out.println(kaartUpdated ? "Saldo bijgewerkt." : "Bijwerken mislukt.");


        System.out.println("\n[Test] Verwijder de reiziger:");
        boolean reizigerDeleted = reizigerDAO.delete(reiziger1);
        System.out.println(reizigerDeleted ? "Reiziger verwijderd." : "Verwijderen mislukt.");


    }
}
