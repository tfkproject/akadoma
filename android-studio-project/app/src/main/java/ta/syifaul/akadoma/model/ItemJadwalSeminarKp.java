package ta.syifaul.akadoma.model;

/**
 * Created by user on 15/08/18.
 */

public class ItemJadwalSeminarKp {
    String id_seminarkp, tanggal, waktu, ruang, nama, kelas, judul, pembimbing, sisa_waktu;

    public ItemJadwalSeminarKp(String id_seminarkp,
                               String tanggal,
                               String waktu,
                               String ruang,
                               String nama,
                               String kelas,
                               String judul,
                               String pembimbing,
                               String sisa_waktu){
        this.id_seminarkp = id_seminarkp;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.ruang = ruang;
        this.nama = nama;
        this.kelas = kelas;
        this.judul = judul;
        this.pembimbing = pembimbing;
        this.sisa_waktu = sisa_waktu;
    }

    public String getId_seminarkp() {
        return id_seminarkp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getRuang() {
        return ruang;
    }

    public String getNama() {
        return nama;
    }

    public String getKelas() {
        return kelas;
    }

    public String getJudul() {
        return judul;
    }

    public String getPembimbing() {
        return pembimbing;
    }

    public String getSisa_waktu() {
        return sisa_waktu;
    }
}
