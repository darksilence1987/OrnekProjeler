package AlisverisSitesi.Nesneler;

public class Kullanici {
    public String kullaniciAdi;
    public String sifre;
    public String email;
    public String adres;
    public String krediKartiNumarasi;
    public double hesapBakiyesi;
    public boolean yonetici;
    public Kullanici(String kullaniciAdi, String sifre)
    {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        hesapBakiyesi = 0;
        yonetici = false;
    }
}
