package com.bh183.anggiastuti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_buku";
    private final static String TABLE_BUKU = "t_buku";
    private final static String KEY_ID_BUKU = "ID_Buku";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_Gambar = "Gambar";
    private final static String KEY_PENGARANG = "Pengarang";
    private final static String KEY_PENERBIT = "Penerbit";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_Gambar + " TEXT, " + KEY_PENGARANG + " TEXT, "
                + KEY_PENERBIT + " TEXT, " + KEY_SINOPSIS + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_Gambar, dataBuku.getGambar());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_Gambar, dataBuku.getGambar());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_Gambar, dataBuku.getGambar());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});

        db.close();
    }

    public void hapusBuku(int idBuku) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }

        return dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db) {
        int idBuku= 0;
        Date tempDate = new Date();

        // buku 1
        try {
            tempDate = sdFormat.parse("2016");
        } catch (ParseException er) {
            er.printStackTrace();
        }



        Buku buku1 = new Buku(
                idBuku,
                "Dilan, Dia adalah Dilanku 1990",
                tempDate,
                storeImageFile(R.drawable.buku1),
                "Pidi Baiq",
                "PT Mizan Pustaka",
                "Novel ini bercerita tentang kisah cinta yang terjadi di antara kedua tokoh utamanya, yaitu Dilan dan Milea. Sudut pandang novel ini adalah kilas balik dari orang pertama, yang menyebut dirinya sebagai ‘aku’, yakni tokoh yang bernama Milea Adnan Husain. Milea lah yang menuturkan keseluruhan cerita di novel ini, tokoh Dilan di novel ini adalah Dilan menurut sudut pandang Milea, itulah mengapa judulnya adalah Dilan, Dia adalah Dilanku 1990.\n" +
                        "\n"+
                        "Tokoh Milea dikisahkan tinggal di kawasan Jakarta Pusat, dan sedang mengingat kembali kenangan masa remajanya ketika bersekolah pada waktu sekitar tahun 1990 di kota Bandung. Penuturannya mengenai kisah cinta lamanya semasa bersekolah di Bandung itu diawali dengan penjelasan mengenai siapa dirinya–yakni Milea- seorang anak gadis dari prajurit TNI AD yang mendapat pindah tugas ke Bandung di tahun 1990 tersebut.\n" +
                        "\n"+
                        "Milea yang berasal dari Jakarta tersebut adalah anak sulung. Ibunya Melia, sebelum menikah, ialah penyanyi dari salah satu grup band di Bandung. Milea dengan wajahnya yang cantik, menjadi anak baru yang cepat populer di tengah banyak sekali laki-laki teman sekolahnya. Hingga pada suatu hari, muncullah Dilan, sosok anak lelaki yang pintar namun bandel, salah satu anggota geng motor yang juga berusaha mendekati dan tertarik dengan Milea. Namun cara pendekatan Dilan ini cukup unik, inilah menariknya novel ini.\n" +
                        "\n"+
                        "Ya, tokoh Dilan dengan segala tingkah dan kata-kata celetukannya yang unik dan jenaka dalam mendekati Milea, menjadi daya tarik novel ini. Dituturkan oleh Milea, pada saat mereka duduk di kelas 2 SMA lah kisah cinta mereka berawal. Jika Milea dikenal sebagai murid yang cantik, maka Dilan dikenal sebagai murid lelaki yang bandel berandalan dan sering dipanggil oleh guru Bimbingan dan Penyuluhan atau BP. Julukan atau pangkatnya di kelompok geng motornya adalah sebagai si Panglima Tempur, meski sebenarnya Dilan adalah murid yang cerdas juga, dan selalu menduduki rangking tiga besar.\n" +
                        "\n"+
                        "Ayah Dilan sama-sama anggota TNI, sementara ibu Dilan adalah Kepala Sekolah di tempat yang berbeda dari tempat sekolah Dilan. Dia memiliki adik perempuan bernama Disa yang juga tak kalah jenakanya dengan Dilan. Dikisahkan oleh Milea bahwa pada awalnya Milea tidak tertarik dengan si bandel Dilan, namun cara pendekatan Dilan kepada Milea yang aneh, lucu dan khas mampu melelehkan hati Milea dan membuatnya jatuh cinta. \n" +
                        "\n"+
                        "Dilan dengan penuh percaya diri mengekspresikan ketertarikannya kepada Milea dengan kata dan ucapannya yang khas dan unik, yang membuatnya jadi berbeda dari teman-teman lelaki Milea lainnya. Dilan mendekati Milea, bukan dengan rayuan manis pujian atau seikat bunga tetapi dengan ucapan berupa ramalan seperti yang tertulis di halaman 20 novel ini, “Aku ramal, nanti kita bertemu di kantin.” demikian kata Dilan kepada Milea.\n" +
                        "\n"+
                        "Namun lucunya ramalan Dilan itu kemudian meleset. Pada hari itu Miela tidak dapat pergi ke kantin karena dia ada pembicaraan mengenai urusan kelas bersama teman-teman di kelas mereka. Tapi Dilan tidak putus asa, kembali dia berusaha menarik perhatian Milea dengan mengirim Piyan untuk menyampaikan pesan yang berisi kata-kata ramalannya berikutnya, yang tertulis di halaman 22 sebagai berikut, “Milea, ramalanku, kita akan bertemu di kantin. Ternyata salah. Maaf, tapi ingin meramal lagi : besok kita akan bertemu.”\n" +
                        "\n" +
                        "Begitu pesan Dilan. Namun berhubung besoknya adalah hari Minggu, maka Dilan tak kurang akal, dia pada hari Minggu tersebut lalu mendatangi rumah Milea dengan dalih menyampaikan undangan, sebagaimana yang tertulis pada halaman 27 novel ini, berisi kalimat sebagai berikut, “Bismillahirrahmanirrahim. Dengan nama Allah Yang Maha Pengasih lagi Maha Penyayang. Dengan ini, dengan penuh perasaan, mengundang Milea Adnan untuk sekolah pada : Hari Senin, Selasa, Rabu, Kamis, Jumat, dan Sabtu.\n" +
                        "\n"+
                        "Begitu isi surat undangan Dilan kepada Milea. Hal-hal unik dan rasa penuh percaya diri Dilan pun akhirnya dapat membuat Milea menjadi tertarik dengan Dilan. Sehingga membuat Milea lupa, bahwa meski sebenarnya Milea telah mempunyai pacar di Jakarta, bernama Beni, anak orang kaya. Namun pada akhirnya hubungan Lia, nama panggilan Melia, dengan Beni pun akhirnya putus, antara lain karena sifat Beni yang manja dan egois, selain juga karena kehadiran Dilan, maka akhirnya hubungan jarak jauh mereka pun kandas.\n" +
                        "\n"+
                        "Hubungan Dilan dengan Milea pun mulai berkembang, seiring selalu saja ada cara unik Dilan untuk membuat Melia dapat tertawa bahagia, seperti hadiah ulang tahunnya berupa halaman TTS atau Teka Teki Silang yang sudah diisi penuh, lengkap dengan ucapannya yang jenaka dan unik sebagaimana terdapat pada quote atau kutipan di atas. Hingga akhirnya mereka pun resmi berpacaran, dengan cara yang unik pula; dengan pernyataan cinta Dilan yang dilengkapi dengan materai yang ia serahkan pada Milea di warung Bu Eem setelah Dilan disidang oleh guru BP dan terancam akan dikeluarkan dari sekolah, karena sudah berkali-kali melanggar aturan sekolah, akibat pengaruh buruk dari geng motornya.\n" +
                        "\n"+
                        "Keanggotaan Dilan pada geng motor itulah satu-satunya yang tidak disukai oleh Melia dari Dilan, meski pada saat itu Dilan dapat meyakinkan Milea bahwa ia akan baik-baik saja meski tergabung dalam suatu geng motor. Setelah Melia menerima pernyataan cinta yang unik dari Dilan tersebut maka mereka lantas berboncengan bersama dengan motor CB Dilan di tengah guyuran hujan yang turun deras.\n",
                "https://tibuku.com/sinopsis-novel-dilan-1990/"
        );

        tambahBuku(buku1, db);
        idBuku++;

        // buku 2
        try {
            tempDate = sdFormat.parse("05/2005");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku2 = new Buku(
                idBuku,
                "5 cm",
                tempDate,
                storeImageFile(R.drawable.buku2),
                "Dhonny Dhirgantoro",
                "PT. Grasindo",
                "Didalam Buku 5cm ini menceritakan tentang persahabatan lima orang anak manusia yang bernama Arial, Riani, Zafran, Ian, dan Genta. Dimana mereka memiliki obsesi dan impian masing-masing. Arial adalah sosok yang paling ganteng diantara mereka, berbadan tinggi besar. \n"+
                        "\n"+
                        "Arial selalu tampak rapi dan sport. Riani adalah sosok wanita berkacamata, cantik, dan cerdas. Ia mempunyai cita-cita bekerja di salah satu stasiun TV. Zafran seorang picisan yang berbadan kurus, anak band, orang yang apa adanya dan kocak. Ian memiliki postur tubuh yang tidak ideal, penggila bola, dan penggemar Happy Salma. \n"+
                        "\n"+
                        "Yang terakhir adalah Genta. Genta selalu dianggap sebagai “the leader” oleh teman-temannya, berbadan agak besar dengan rambut agak lurus berjambul, berkacamata, aktivitas kampus dan teman yang easy going. Lima sahabat ini telah menjalin persahabatan selama tujuh tahun. Suatu ketika mereka jenuh akan aktivitas yang selalu mereka lakukan bersama. \n"+
                        "\n"+
                        "Terbesit ide untuk tidak saling berkomunikasi dan bertemu satu sama lain selama tiga bulan. Ide tersebut pun disepakati. Selama tiga bulan berpisah itulah terjadi banyak hal yang membuat hati mereka lebih kaya dari sebelumnya. Pertemuan setelah tiga bulan yang penuh dengan rasa kangen akhirnya terjadi dan dirayakan dengan sebuah perjalanan.\n" +
                        "\n"+
                        "Didalam perjalanan tersebut mereka menemukan arti manusia sesungguhnya. Perubahannya itu mulai dari pendidikan, karir, idealisme, dan tentunya love life. Semuanya terkuak dalam sebuah perjalanan ‘reuni’ mereka mendaki gunung tertinggi di Pulau Jawa, Mahameru. \n"+
                        "\n"+
                        "Dan di sanalah cerita bergulir, bukan hanya seonggok daging yang dapat berbicara, berjalan, dan punya nama. Mereka pun pada akhirnya dapat menggapai cita-cita yang mereka impikan sejak dulu. Setengah dari buku 5 cm bercerita tentang keseharian lima sahabat ini, dari sifat-sifat mereka yang berbeda satu dengan yang lain sampai dengan perilaku dan aktifitas mereka yang penuh canda tawa, diselingi cerita tentang permasalahan antar-sahabat. \n"+
                        "\n"+
                        "Setengahnya lagi, buku ini menuliskan petualangan kelima sahabat dalam mendaki gunung Semeru. “…Biarkan keyakinan kamu, 5 centimeter menggantung mengambang di depan kamu. Dan…sehabis itu yang kamu perlu cuma kaki yang akan berjalan lebih jauh dari biasanya, tangan yang akan berbuat lebih banyak dari biasanya, mata yang akan menatap lebih lama dari biasanya, leher yang akan lebih sering melihat ke atas. \n"+
                        "\n"+
                        "Lapisan tekad yang seribu kali lebih keras dari baja, hati yang akan bekerja lebih keras dari biasanya serta mulut yang akan selalu berdoa, percaya pada 5 centimeter di depan kening kamu.”\n",
                "https://sahabatnesia.com/resensi-novel-5-cm/"
        );

        tambahBuku(buku2, db);
        idBuku++;

        // buku 3
        try {
            tempDate = sdFormat.parse("2005");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku3 = new Buku(
                idBuku,
                "Laskar Pelangi",
                tempDate,
                storeImageFile(R.drawable.buku3),
                "Andrea Hirata",
                "Bentang Pustaka, Yogyakarta",
                "Cerita terjadi di desa Gantung, Kabupaten Gantung, Belitung Timur. Dimulai ketika sekolah Muhammadiyah terancam akan dibubarkan oleh Depdikbud Sumsel jikalau tidak mencapai siswa baru sejumlah 10 anak. Ketika itu baru 9 anak yang menghadiri upacara pembukaan, akan tetapi tepat ketika Pak Harfan, sang kepala sekolah, hendak berpidato menutup sekolah, Harun dan ibunya datang untuk mendaftarkan diri di sekolah kecil itu.\n" +
                        "\n"+
                        "Mulai darisanalah dimulai cerita mereka. Mulai dari penempatan tempat duduk, pertemuan mereka dengan Pak Harfan, perkenalan mereka yang luar biasa di mana A Kiong yang malah cengar-cengir ketika ditanyakan namanya oleh guru mereka, Bu Mus. Kejadian bodoh yang dilakukan oleh Borek, pemilihan ketua kelas yang diprotes keras oleh Kucai, kejadian ditemukannya bakat luar biasa Mahar, pengalaman cinta pertama Ikal, sampai pertaruhan nyawa Lintang yang mengayuh sepeda 80 km pulang pergi dari rumahnya ke sekolah!\n" +
                        "\n"+
                        "Mereka, Laskar Pelangi – nama yang diberikan Bu Muslimah akan kesenangan mereka terhadap pelangi – pun sempat mengharumkan nama sekolah dengan berbagai cara. Misalnya pembalasan dendam Mahar yang selalu dipojokkan kawan-kawannya karena kesenangannya pada okultisme yang membuahkan kemenangan manis pada karnaval 17 Agustus, dan kejeniusan luar biasa Lintang yang menantang dan mengalahkan Drs. Zulfikar, guru sekolah kaya PN yang berijazah dan terkenal, dan memenangkan lomba cerdas cermat. \n"+
                        "\n"+
                        "Laskar Pelangi mengarungi hari-hari menyenangkan, tertawa dan menangis bersama. Kisah sepuluh kawanan ini berakhir dengan kematian ayah Lintang yang memaksa Einstein cilik itu putus sekolah dengan sangat mengharukan, dan dilanjutkan dengan kejadian 12 tahun kemudian di mana Ikal yang berjuang di luar pulau Belitong kembali ke kampungnya. Kisah indah ini diringkas dengan kocak dan mengharukan oleh Andrea Hirata, kita bahkan bisa merasakan semangat masa kecil anggota sepuluh Laskar Pelangi ini! \n",
                "https://luthfan.com/resensi-novel-laskar-pelangi-dan-sang-pemimpi/"
        );

        tambahBuku(buku3, db);
        idBuku++;

        // berita 4
        try {
            tempDate = sdFormat.parse("2010");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku4 = new Buku(
                idBuku,
                "Perahu Kertas",
                tempDate,
                storeImageFile(R.drawable.buku4),
                "Dewi Lestari",
                "Bentang Pustaka dan Truedee Pustaka Sejati",
                "Kisah ini dimulai dari seorang anak remaja bernama Keenaan. Ia telah menghabiskan waktunya selama enam tahun di Amsterdam, Belanda bersama dengan neneknya. Setelah enam tahun di Belanda, akhirnya Keenan pulang ke Indonesia atas permintaan dari ayahnya. Keenan pun berkuliah di Fakultas Ekonomi pada salah satu Universitas di kota Bandung. Berkuliah di Fakultas Ekonomi sebenarnya bukanlah kemauannya, melainkan paksaan dari ayah Keenan. \n"+
                        "\n"+
                        "Keenan sendiri sebetulnya lebih berminat dalam dunia seni rupa yakni melukis namun keinginannya untuk menjadi pelukis ditentang keras oleh ayahnya. Sementara itu Kuggy, seorang gadis lucu, unik, dan agak sedikit urakan yang juga merupakan tokoh dalam novel ini juga berkuliah di tempat yang sama di tempat Keenan menimba ilmu di perguruan tinggi. Hanya saja mereka berkuliah di fakultas yang berbeda. Kugy di fakultas Ekonomi sedangkan Keenan di fakultas Sastra. Dalam beberapa hal, Kugy dan Keenan memiliki banyak persamaan diantara mereka. \n"+
                        "\n"+
                        "Kugy bercita-cita untuk menjadi pendongeng yang hebat. Ia memiliki berbagai jenis buku bacaan dongeng di taman baca yang ia miliki. Minatnya sangat besar di dunia menulis dongeng, meskipun ia sadar bahawa menjadi seorang pendongeng bukanlah sebuah profesi yang realistis. Pertemuan antara Kugy dan Keenan diperantarai oleh dua sahabat mereka yakni Eko dan Noni. Eko sendiri sebenarnya masih keluarga jauh dari Keenan. \n"+
                        "\n"+
                        "Sedangkan Noni adalah sahabat baik dari Kugy. Mereka berempat lambat laun menjadi sahabat karib yang mengerti satu sama lain. Seiring berjalannya waktu, Keenan dan Kugy semakin mengagumi satu sama lain. Tanpa mereka sadari, kebersamaan mereka berdua telah merubah arti persahabatan menjadi cinta. Baik Kugy ataupun Keenan, keduanya sama-sama tak memiliki keberanian untuk mengungkapkan perasaan masing-masing. \n"+
                        "\n"+
                        "Salah satu penyebabnya adalah karena Keenan telah memiliki seorang kekasih yang sering ia panggil dengan sebuatan “Ojos.” Sebutan itu bukan nama sebenarnya dari kekasih Kugy. Nama itu hanya sebatas panggilan khusus dari Kugy terhadap kekasihnya tersebut. Di samping itu Keenan juga sedang dekat dengan seorang gadis bernama Wanda, seorang kolektor barang seni muda yang secara kebetulan adalah sepupu dari Noni. Kedekatan Keenan dan Wanda tidak berlangsung lama. Kandasnya hubungan mereka dibarengi dengan kekecewaan yang teramat dalam tentang kariernya di dunia seni lukis. \n"+
                        "\n"+
                        "Dengan hati dan perasaan yang hancur, Keenan memutuskan untuk meninggalkan kota Bandung dan pergi ke Ubud. Tujuannya pergi ke Ubud adalah untuk menemui pak Wayan, seorang seniman terkemuka di Bali yang tak lain merupakan sahabat dari ibunda Keenan. Keseharian Keenan bersama dengan Pak Wayan dan seniman Bali lainnya sedikit banyak mampu mengobati hatinya. Salah satu sosok manusia yang turut berpengaruh pada penyembuhan hati Keenan adalah Ludhe Laksmi. Ludhe adalah seorang gadis Bali yang cantik, lemah lembut, dan baik hati yang tak lain adalah keponakan dari Pak Wayan. Seiring dengan membaiknya perasaan hati Keenan, ia pun memutuskan untuk melukis kembali. \n"+
                        "\n"+
                        "Keenan menciptakan sebuah lukisan yang terinspirasi dari kisah Jenderal Pilik dan Pasukan Alit yang tak lain adalah judul dongeng hasil karangan Kugy. Mulai saat ini Keenan berhasil menciptakan lukisan-lukisan yang bernilai seni tinggi. Lukisan hasil tangan dinginnya menjadi terkenal dan menjadi buruan para kolektor benda seni. Di sisi lain, Kugi merasa sangat kesepian dan kehilangan karena sahabat-sahabatnya yang telah pergi meninggalkan kota Bandung. Ia tak ingin berada dalam kesepian dan rasa sedih yang mendalam. \n"+
                        "\n"+
                        "Kugy mulai menata kembali kehidupannya dengan segera menyelesaikan kuliahnya. Setelah lulus kuliah, ia bekerja sebagai copywriter di sebuah perusahaan jasa biro iklan ternama di Jakarta. Di tempat tersebut Kugy bertemu dengan Remigius Aditya, seorang pendiri sekaligus pemilik perusahaan di tempat Kugy bekerja. Beberapa waktu berlalu sejak Kugy mulai bekerja di perusahaan jasa tersebut, ia mampu membukutikan dirinya sebagai talenta yang berkualitas. Karenanya ia dengan cepat naik daun dan menjadi orang yang cukup diperhitungkan di perusahaan tersebut.\n" +
                        "\n"+
                        "Di sisi lain, Remi memperhatikan Kugy dan diam-diam mengaguminya dari perspektif lain. Remi tak hanya menyukai kecerdasan yang dimiliki oleh Kugy, lebih dari itu terdapat sisi unik dalam diri Kugy yang membuat Remi semakin tertarik pada Kugy. Pada akhirnya Remi mengungkapkan perasaannya dengan jujur kepada Kugy bahwa Remi mencintainya. Kugy pun menanggapi perasaan Remi tersebut dengan sepenuh hati.\n" +
                        "\n"+
                        "Kondisi kesehatan ayah Keenan semakin memburuk dan memaksanya untuk pulang ke Jakarta. Ia harus meninggalkan pak Wayan dan Ludhe di Bali. Kekosongan posisi pimpinan perusahaan milik ayahnya juga turut memaksa Keenan untuk mengambil alih perusahaan tersebut. Sepulangya Keenan ke Jakarta juga turut menjadi sebab pertemuannya dengan Kugy. Ia juga bertemu dengan Eko dan Noni sahabat lamanya. Akan tetapi pertemuan empat sekawan tersebut terjadi dalam kondisi yang berbeda. Selanjutnya takdirlah yang menguji hati mereka. Pada akhirnya cinta dan persahabatan karus berkompromi dan memilih hendak seperti apa kisah ini dilalui.\n",
                "https://soalterbaru.com/contoh-resensi-novel-perahu-kertas/#"
        );

        tambahBuku(buku4, db);

    }
}
