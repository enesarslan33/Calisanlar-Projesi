
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalisanIslemleri {
    
    public Connection con = null;
    
    public Statement statement = null;
    
    public PreparedStatement preparedStatement = null;
    public ArrayList<Calisan> calisanlariGetir(){
    
    ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        try {
             statement = con.createStatement();
            String sorgu ="Select * From calisanlar";
            ResultSet rs = statement.executeQuery(sorgu);
           
            while(rs.next()){
            
            int id = rs.getInt("id");
            String ad = rs.getString("ad");
            String soyad = rs.getString("soyad");
            String departman = rs.getString("departman");
            String maas = rs.getString("maas");
            
            cikti.add(new Calisan(id, ad, soyad, departman, maas));
            
            }
            return cikti;
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    
    }
    public void calisanGuncelle(int id, String yeni_ad, String yeni_soyad, String yeni_departman,String yeni_maas){
    
        String sorgu = "update calisanlar set ad =? , soyad = ?, departman = ? , maas = ? where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, yeni_ad);
            preparedStatement.setString(2, yeni_soyad); 
            preparedStatement.setString(3, yeni_departman); 
            preparedStatement.setString(4, yeni_maas);
            
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void calisanSil(int id){
        
        String sorgu = "Delete from calisanlar where id = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    
    
    }
    public void calisanEkle(String ad, String soyad, String departman, String maas){
    
        String sorgu = "Insert Into calisanlar (ad, soyad, departman, maas) values(?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    public boolean girisYap(String kullanici_adi, String parola){
    
        String sorgu = "Select * From adminler where username = ? and password = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1,kullanici_adi);
            preparedStatement.setString(2,parola);
            
            ResultSet rs = preparedStatement.executeQuery();
          return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        
    }
    public CalisanIslemleri(){
    
     String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi+ "?useUnicode=true&characterEncoding=utf8";
        
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamad??....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
            System.out.println("Ba??lant?? Ba??ar??l??...");
            
            
        } catch (SQLException ex) {
            System.out.println("Ba??lant?? Ba??ar??s??z...");
            //ex.printStackTrace();
        }
    
    }

    
    
}
