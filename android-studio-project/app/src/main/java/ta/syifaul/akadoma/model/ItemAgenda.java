package ta.syifaul.akadoma.model;

/**
 * Created by user on 15/08/18.
 */

public class ItemAgenda {
    String id_agenda, judul, tanggal, waktu, keterangan, sisa_waktu;

    public ItemAgenda(String id_agenda,
                      String judul,
                      String tanggal,
                      String waktu,
                      String keterangan,
                      String sisa_waktu){
        this.id_agenda = id_agenda;
        this.judul = judul;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.keterangan = keterangan;
        this.sisa_waktu = sisa_waktu;
    }

    public String getId_agenda() {
        return id_agenda;
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

    public String getSisa_waktu() {
        return sisa_waktu;
    }
}
