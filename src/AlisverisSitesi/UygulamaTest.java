package AlisverisSitesi;

public class UygulamaTest {
    public static void main(String[] args) {
        Uygulama uygulama = new Uygulama();
        uygulama.YoneticiEkle("xhite", "123");
        uygulama.UrunEkle("Diş macunu",  8);
        uygulama.UrunEkle("Makarna",  1.8);
        uygulama.UrunEkle("Kola",  10);
        uygulama.Baslat();
    }
}
