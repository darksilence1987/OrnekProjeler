package AlisverisSitesi.YardimciMethodlar;

import AlisverisSitesi.Nesneler.Urun;

public class UrunListesi {
    public static Urun[] liste;

    public UrunListesi()
    {
        liste = new Urun[0];
    }
    public UrunListesi(Urun[] urunListesi)
    {
        liste = urunListesi;
    }
    public void Ekle(Urun urun)
    {
        Urun[] yeniListe = new Urun[liste.length+1];
        for(int i = 0 ; i < liste.length; i++)
        {
            yeniListe[i] = liste[i];
        }

        yeniListe[liste.length] = urun;
        liste = yeniListe;
    }
    public void Sil(int indeks)
    {
        if(indeks >= liste.length) return;
        Urun[] yeniListe = new Urun[liste.length-1];
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
    public int Ara(String urunAdi)
    {
        for(int i = 0; i < liste.length; i++)
        {
            if(urunAdi.trim().equals(liste[i].urunAdi.trim())) return i;
        }
        return -1;
    }
    public static double UrunFiyatiOgren(int indeks)
    {
        return liste[indeks].satisFiyati;
    }
}
