package ta.syifaul.akadoma.model;

/**
 * Created by user on 15/08/18.
 */

public class ItemJadwalSidangTa {
    String id_sidangta, tanggal, waktu, ruang, nama, kelas, judul, pembimbing1, pembimbing2, penguji1, penguji2, penguji3, sisa_waktu;

    public ItemJadwalSidangTa(String id_sidangta,
                              String tanggal,
                              String waktu,
                              String ruang,
                              String nama,
                              String kelas,
                              String judul,
                              String pembimbing1,
                              String pembimbing2,
                              String penguji1,
                              String penguji2,
                              String penguji3,
                              String sisa_waktu){
        this.id_sidangta = id_sidangta;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.ruang = ruang;
        this.nama = nama;
        this.kelas = kelas;
        this.judul = judul;
        this.pembimbing1 = pembimbing1;
        this.pembimbing2 = pembimbing2;
        this.penguji1 = penguji1;
        this.penguji2 = penguji2;
        this.penguji3 = penguji3;
        this.sisa_waktu = sisa_waktu;
    }

    public String getId_sidangta() {
        return id_sidangta;
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

    public String getPembimbing1() {
        return pembimbing1;
    }

    public String getPembimbing2() {
        return pembimbing2;
    }

    public String getPenguji1() {
        return penguji1;
    }

    public String getPenguji2() {
        return penguji2;
    }

    public String getPenguji3() {
        return penguji3;
    }

    public String getSisa_waktu() {
        return sisa_waktu;
    }
}
