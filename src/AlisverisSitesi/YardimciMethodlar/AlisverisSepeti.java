package AlisverisSitesi.YardimciMethodlar;

import AlisverisSitesi.Nesneler.Urun;

public class AlisverisSepeti {
    public int[] sepet;
    public AlisverisSepeti()
    {
        sepet = new int[UrunListesi.liste.length];
    }
    public void SepeteEkle(int indeks, int adet)
    {
        sepet[indeks] += adet;
    }
    public void SepettenCikar(int indeks)
    {
        sepet[indeks] = 0;
    }
    public void SepetiBosalt()
    {
        sepet = new int[UrunListesi.liste.length];
    }
    public double ToplamFiyat()
    {
        double toplam = 0;
        for(int i = 0; i < sepet.length; i++)
        {
            toplam += UrunListesi.UrunFiyatiOgren(i) * sepet[i];
        }
        return toplam;
    }
}
