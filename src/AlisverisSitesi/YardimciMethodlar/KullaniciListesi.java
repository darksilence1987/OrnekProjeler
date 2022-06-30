package AlisverisSitesi.YardimciMethodlar;

import AlisverisSitesi.Nesneler.Kullanici;

public class KullaniciListesi {
    public Kullanici[] liste;
    public KullaniciListesi()
    {
        liste = new Kullanici[0];
    }
    public void Ekle(Kullanici kullanici)
    {
        Kullanici[] yeniListe = new Kullanici[liste.length+1];
        for(int i = 0 ; i < liste.length; i++)
        {
            yeniListe[i] = liste[i];
        }

        yeniListe[liste.length] = kullanici;
        liste = yeniListe;
    }
    public void Sil(int indeks)
    {
        if(indeks >= liste.length) return;
        Kullanici[] yeniListe = new Kullanici[liste.length-1];
        for(int i = 0; i < indeks;i++)
        {
            yeniListe[i] = liste[i];
        }
        for (int i = indeks; i < yeniListe.length; i++)
        {
            yeniListe[i] = liste[i+1];
        }
        liste = yeniListe;
    }
    public int Ara(String kullaniciAdi)
    {
        for(int i = 0; i < liste.length; i++)
        {
            if(kullaniciAdi.trim().equals(liste[i].kullaniciAdi.trim())) return i;
        }
        return -1;
    }

}
