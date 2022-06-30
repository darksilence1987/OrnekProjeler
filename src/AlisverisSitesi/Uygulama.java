package AlisverisSitesi;

import AlisverisSitesi.Nesneler.Kullanici;
import AlisverisSitesi.Nesneler.Urun;
import AlisverisSitesi.YardimciMethodlar.AlisverisSepeti;
import AlisverisSitesi.YardimciMethodlar.KullaniciListesi;
import AlisverisSitesi.YardimciMethodlar.UrunListesi;

import java.util.Scanner;

public class Uygulama {
    static KullaniciListesi kullanicilar;
    static UrunListesi urunler;
    public Uygulama()
    {
        kullanicilar = new KullaniciListesi();
        urunler = new UrunListesi();
    }
    public void Baslat()
    {
        int bagliKullanici = -1;
        while(true)
        {
            bagliKullanici = GirisEkrani();
            if(kullanicilar.liste[bagliKullanici].yonetici) YoneticiModundaCalistir(bagliKullanici);
            else MusteriModundaCalistir(bagliKullanici);
        }

    }
    public  void UrunEkle(String urunAdi,  double satisFiyati) //Test amaçlı
    {
        Urun urun = new Urun();
        urun.urunAdi = urunAdi;
        urun.satisFiyati = satisFiyati;
        urunler.Ekle(urun);
    }
    public void YoneticiEkle(String kullaniciAdi, String sifre)
    {
        Kullanici yonetici = new Kullanici(kullaniciAdi, sifre);
        yonetici.yonetici = true;
        kullanicilar.Ekle(yonetici);
    }

    private static void MusteriModundaCalistir(int bagliKullanici) {
        boolean islemDevamEdiyor = true;
        Kullanici musteri = kullanicilar.liste[bagliKullanici];
        AlisverisSepeti sepet = new AlisverisSepeti();
        while(islemDevamEdiyor)
        {
            islemDevamEdiyor = MusteriMenusu(musteri, sepet);
        }
    }

    private static boolean MusteriMenusu(Kullanici musteri, AlisverisSepeti sepet) {

        HosgeldinizEkrani(musteri, sepet);
        switch (SecimMenusu())
        {
            case 1-> UrunEkle(sepet);
            case 2-> UrunCikar(sepet);
            case 3-> AlisverisiTamamla(sepet, musteri);
            case 4-> ParaYatir(musteri);
            case 5-> BilgileriGor(musteri);
            case 0-> {return false;}
        }
        return true;
    }

    private static void BilgileriGor(Kullanici musteri) {
        System.out.println("Email : "+ musteri.email+ " Hesap bakiyesi : "+musteri.hesapBakiyesi);
        System.out.println("Kayıtlı teslimat adresi : "+ musteri.adres);
        System.out.println("Ödeme yöntemi : Kredi Kartı : "+musteri.krediKartiNumarasi);
    }

    private static void ParaYatir(Kullanici musteri) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Merhaba "+ musteri.kullaniciAdi);
        System.out.println("Mevcut bakiyeniz : "+ musteri.hesapBakiyesi + " TRY");
        System.out.println("Yatırmak istediğiniz miktarı giriniz");
        musteri.hesapBakiyesi += Double.parseDouble(scanner.nextLine());
        System.out.println("Hesabınıza para başarı ile yatırıldı. Yeni bakiyeniz : "+ musteri.hesapBakiyesi + " TRY");
    }

    private static void AlisverisiTamamla(AlisverisSepeti sepet, Kullanici musteri) {
        SepetiYazdir(sepet);
        System.out.println("Müşteri bakiyeniz : "+ musteri.hesapBakiyesi);
        System.out.println("Alışverişinizi tamamlamak için gerekli miktar : "+sepet.ToplamFiyat());
        if(sepet.ToplamFiyat()> musteri.hesapBakiyesi)
        {
            System.out.println("Üzgünüm bakiyeniz yeterli değil. Lütfen hesabına para yatırın ya da bazı ürünleri sepetinizden çıkarın.");
        }
        else
        {
            System.out.println("İşlemi onaylamak için sifrenizi giriniz");
            Scanner scanner = new Scanner(System.in);
            if(!scanner.nextLine().trim().equals(musteri.sifre))
            {
                System.out.println("Müşteri şifreniz doğru girilmedi");
                return;
            }
            musteri.hesapBakiyesi -= sepet.ToplamFiyat();
            sepet.SepetiBosalt();
            System.out.println("Siparişiniz tamamlandı. Ürünleriniz en yakın zamanda kargoya verilecektir");
        }
    }

    private static void UrunCikar(AlisverisSepeti sepet) {
        Scanner scanner = new Scanner(System.in);
        SepetiYazdir(sepet);
        System.out.println("0 - İşlemi iptal et");
        int secim = Integer.parseInt(scanner.nextLine());
        if(secim == 0) return;
        sepet.SepettenCikar(secim-1);
        System.out.println("Urun sepetten kaldırıldı.");

    }
    private static void SepetiYazdir(AlisverisSepeti sepet)
    {
        Urun[] urunler = UrunListesi.liste;
        System.out.println("Sepetinizdeki ürünler");
        for(int i = 0; i < sepet.sepet.length;i++)
        {
            if(sepet.sepet[i] != 0)
            {
                System.out.println("Urun kodu : "+ (i+1)+ " - "+urunler[i].urunAdi+ " adet : "+ sepet.sepet[i]+
                        " toplam fiyatı : "+ (sepet.sepet[i]* UrunListesi.UrunFiyatiOgren(i)));
            }
        }
    }

    private static void UrunEkle(AlisverisSepeti sepet)
    {
        Scanner scanner = new Scanner(System.in);
        Urun[] urunler = UrunListesi.liste;
        System.out.println("Almak istediğiniz urunun kodunu giriniz");
        for(int i = 0; i < urunler.length; i++)
        {
            System.out.println((i+1)+" - "+urunler[i].urunAdi +" "+urunler[i].satisFiyati+" TRY");
        }
        System.out.println("0 - İptal");
        int secim = Integer.parseInt(scanner.nextLine());
        if(secim == 0) return;
        secim--;
        if(secim >= urunler.length){
            System.out.println("Hatalı ürün seçimi");
            return;
        }
        System.out.println("Eklemek istediğiniz miktarı giriniz");
        int adet = Integer.parseInt(scanner.nextLine());
        sepet.SepeteEkle(secim, adet);
        System.out.println("Istediğiniz ürün sepetinize eklenmiştir");

    }

    private static int SecimMenusu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Sepete Yeni Urun Ekle");
        System.out.println("2- Sepetten Urun Cikar");
        System.out.println("3- Alışverişi Tamamla");
        System.out.println("4- Hesaba Para Yatır");
        System.out.println("5- Kullanıcı Bilgilerini Gör");
        System.out.println("0- Kullanici Cıkışı");
        return scanner.nextInt();
    }

    private static void HosgeldinizEkrani(Kullanici musteri, AlisverisSepeti sepet) {
        System.out.println();
        System.out.println("Hoşgeldiniz " + musteri.kullaniciAdi);
        System.out.println("Mevcut bakiyeniz : "+ musteri.hesapBakiyesi+ " TRY");
        System.out.println("Sepetinizdeki urunlerin toplam fiyatı : "+ sepet.ToplamFiyat());
        System.out.println("Hangi işlemi gerçekleştirmek istersiniz");
        System.out.println();
    }

    private static void YoneticiModundaCalistir(int bagliKullanici) {
        boolean islemDevamEdiyor = true;
        Kullanici yonetici = kullanicilar.liste[bagliKullanici];
        while(islemDevamEdiyor)
        {
            islemDevamEdiyor = YoneticiMenusu(yonetici);
        }
    }

    private static boolean YoneticiMenusu(Kullanici yonetici) {
        System.out.println();
        System.out.println("Hoşgeldiniz " + yonetici.kullaniciAdi);
        System.out.println("1- Yeni ürün ekle");
        System.out.println("2- Mevcut ürünü sil");
        System.out.println("0- Yönetici çıkışı");
        System.out.println();
        switch (SecimIste())
        {
            case 1->UrunEkle();
            case 2->UrunSil();
            case 0-> {return false;}
        }
        return true;
    }

    private static void UrunSil() {
        Scanner scanner = new Scanner(System.in);
        Urun[] liste = UrunListesi.liste;
        System.out.println("Silmek istediğiniz ürünün kodunu giriniz");
        for(int i = 0; i < liste.length; i++)
        {
            System.out.println((i+1)+" - "+liste[i].urunAdi +" "+liste[i].satisFiyati+" TRY");
        }
        System.out.println("0 - İptal");
        int secim = Integer.parseInt(scanner.nextLine());
        if(secim == 0) return;
        secim--;
        if(secim >= liste.length){
            System.out.println("Hatalı ürün kodu");
            return;
        }
        urunler.Sil(secim);
        System.out.println("Ürün başarı ile silindi");
    }

    private static void UrunEkle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Eklenecek ürünün adını giriniz");
        String urunAdi = scanner.nextLine().trim();
        System.out.println("Eklenecek ürünün fiyatını giriniz");
        double fiyat = Double.parseDouble(scanner.nextLine());
        Urun yeniUrun = new Urun();
        yeniUrun.urunAdi = urunAdi;
        yeniUrun.satisFiyati = fiyat;
        urunler.Ekle(yeniUrun);
        System.out.println("Yeni ürün başarı ile eklendi.");
    }

    private static int GirisEkrani(){
        int bagliKullanici = -1;
        while(bagliKullanici == -1) {
            AnaMenuYazdir();
            switch (SecimIste())
            {
                case 1 ->{
                    bagliKullanici = GirisYap();
                }
                case 2 ->{
                    KullaniciOlustur();
                }
                case 0 ->{
                    System.exit(0);
                }
                default ->{
                    System.out.println("Hatalı giriş yaptınız tekrar deneyin");
                }
            }
        }
        return bagliKullanici;

    }
    private static void AnaMenuYazdir()
    {
        System.out.println("----------------------------------------");
        System.out.println("   AlışverişSitesi.com'a hoşgeldiniz    ");
        System.out.println("----------------------------------------");
        System.out.println("1 - Kullanici girisi yap");
        System.out.println("2 - Yeni kullanici oluştur");
        System.out.println("0 - Çıkış yap");
    }
    private static int SecimIste()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seçiminiz : ");
        return Integer.parseInt(scanner.nextLine());
    }
    private static void KullaniciOlustur() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("AlışverişSitesi.com'a hoşgeldiniz");
        String kullaniciAdi;
        do {
            System.out.println("Bir kullanici adı girin : ");
            kullaniciAdi = scanner.nextLine().trim();
            if(kullanicilar.Ara(kullaniciAdi) != -1)
            {
                System.out.println("Girdiğiniz isimde bir kullanici zaten mevcuttur");
                System.out.println("Lutfen başka bir kullanıcı adı deneyiniz");
            }
            else break;
        }while(true);
        System.out.println("Lutfen bir şifre giriniz");
        String sifre = scanner.nextLine().trim();
        Kullanici kullanici = new Kullanici(kullaniciAdi, sifre);
        System.out.println("Lutfen email adresinizi giriniz");
        kullanici.email = scanner.nextLine().trim();
        System.out.println("Lutfen teslimat adresinizi giriniz");
        kullanici.adres = scanner.nextLine().trim();
        System.out.println("Lutfen kredi kartı numaranızı giriniz");
        kullanici.krediKartiNumarasi = scanner.nextLine().trim();
        kullanicilar.Ekle(kullanici);
        System.out.println("Kullanici : "+kullanici.kullaniciAdi+" başarı ile oluşturuldu.");
        System.out.println();
    }

    private static int GirisYap() {
        if(kullanicilar == null || kullanicilar.liste.length == 0) return -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lutfen kullanici adinizi giriniz");
        String kullanici = scanner.nextLine().trim();
        int kullaniciID = kullanicilar.Ara(kullanici);
        if(kullaniciID == -1)
        {
            System.out.println("Girdiğiniz kullanici adi bulunamadı");
            return -1;
        }
        int hak = 3;
        while(hak > 0)
        {
            System.out.println("Lutfen sifrenizi giriniz (Kalan Hak : "+hak+" )");
            String sifre = scanner.nextLine().trim();
            if(sifre.equals(kullanicilar.liste[kullaniciID].sifre)) {
                System.out.println("Giriş başarılı...");
                return kullaniciID;
            }
            hak--;
            System.out.println("Hatalı sifre girisi... Kalan hak : "+ hak);
        }
        return -1;
    }
}
