package ta.syifaul.akadoma.model;

/**
 * Created by user on 15/08/18.
 */

public class ItemPengumuman {
    String id_pengumuman, id_user, nama, judul, tanggal, waktu, keterangan, level, tujuan;

    public ItemPengumuman(String id_pengumuman,
                          String id_user,
                          String nama,
                          String judul,
                          String tanggal,
                          String waktu,
                          String keterangan,
                          String level,
                          String tujuan){
        this.id_pengumuman = id_pengumuman;
        this.id_user = id_user;
        this.nama = nama;
        this.judul = judul;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.keterangan = keterangan;
        this.level = level;
        this.tujuan = tujuan;
    }

    public String getId_pengumuman() {
        return id_pengumuman;
    }

    public String getId_user() {
        return id_user;
    }

    public String getNama() {
        return nama;
    }

    public String getJudul() {
        return judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLevel() {
        return level;
    }

    public String getTujuan() {
        return tujuan;
    }
}
