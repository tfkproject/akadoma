-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 27, 2018 at 01:29 AM
-- Server version: 10.0.34-MariaDB-0ubuntu0.16.04.1
-- PHP Version: 7.0.30-1+ubuntu16.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_akadoma`
--

-- --------------------------------------------------------

--
-- Table structure for table `agenda`
--

CREATE TABLE `agenda` (
  `id_agenda` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `judul` text NOT NULL,
  `keterangan` text NOT NULL,
  `tanggal` date NOT NULL,
  `waktu` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `agenda`
--

INSERT INTO `agenda` (`id_agenda`, `id_user`, `judul`, `keterangan`, `tanggal`, `waktu`) VALUES
(35, 10, 'hhg', 'ggg', '2018-09-01', '13:23:00'),
(88, 1, 'bimbingan dengan dosen pembimbing 2', 'hari ini', '2018-09-10', '17:29:00');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal_seminarta`
--

CREATE TABLE `jadwal_seminarta` (
  `id_jseminarta` int(11) NOT NULL,
  `nama` text NOT NULL,
  `kelas` text NOT NULL,
  `judul` text NOT NULL,
  `pembimbing1` text NOT NULL,
  `pembimbing2` text NOT NULL,
  `penguji1` text NOT NULL,
  `penguji2` text NOT NULL,
  `ruangan` text NOT NULL,
  `tanggal` date NOT NULL,
  `waktu` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jadwal_seminarta`
--

INSERT INTO `jadwal_seminarta` (`id_jseminarta`, `nama`, `kelas`, `judul`, `pembimbing1`, `pembimbing2`, `penguji1`, `penguji2`, `ruangan`, `tanggal`, `waktu`) VALUES
(1, 'Nanda Febriandi', 'C', 'Aplikasi Penyewaan Ruko\r\nBerbasis Android', 'Desi Amirullah, M.T', 'Fajar Ratnawati, M.Cs', 'Lidyawati, M.Kom', 'Sri Mawarni, M.Si', 'Lab. Sistem Cerdas', '2018-05-15', '08:00:00'),
(2, 'Jurais', 'C', 'Aplikasi Monitoring Kompensasi Berbasis Android', 'Sri Mawarni, M.Si', 'Tengku Musri, M.Kom', 'Nurul Fahmi, M.T', 'Supria, M.Kom', 'Lab. Basis Data', '2018-05-17', '07:30:00'),
(3, 'Rahmad Hidayat', 'C', 'Implementasi Fuzzy Rough Set Berbasiskan Algortima Fuzzy Untuk Mendeteksi Kebakaran Hutan Pada Real Hardware Wireless Sensor Network', 'Eko Prayitno, M.Kom', 'Nurul Fahmi, M.T', 'Diah Anggraina Fitri, M.Si', 'Tengku Musri, M.Kom', 'Lab. Pemrograman Lanjut', '2018-05-18', '09:00:00'),
(4, 'Juniarto', 'C', 'Sistem monitoring kepenuhan tempat sampah di daerah bengkalis berbasis arduino', 'Tengku Musri, M.Kom', 'Fajar Ratnawati, M.Cs', 'Desi Amirullah, M.T', 'Nurul Fahmi, M.T', 'Lab. Rekayasa Perangkat Lunak', '2018-05-19', '10:20:00'),
(5, 'Bunga Trisna', 'A', 'Sistem Antrian Puskesmas Bengkalis Berbasis Website', 'Mansur, M.Kom', 'Rezki Kurniati, M.Kom', 'Sri Mawarni, M.Si', 'Sri Mawarni, M.Si', 'Lab. Basis Data', '2018-01-13', '09:31:00'),
(6, 'Sri Wulan Pratiwi Ningsih', 'A', 'Sarung Tangan Pintar Penerjemah Bahasa Isyarat Sebagai Alat Bantu Komunikasi Penyandang Disabilitas Tunarungu dan Tunawicara Menggunakan Teknologi IOT', 'Eko Prayitno, M.Kom', 'Muhammad Nasir, M.Kom', 'Supria, M.Kom', 'Fajar Ratnawati, M.Cs', 'Lab. Jaringan KOmputer', '2018-01-14', '10:00:00'),
(7, 'Santika Suryani', 'A', 'Aplikasi Simulasi Rukun Haji Berbasis Android Sebagai sarana Pembelajaran', 'Rezki Kurniati, M.Kom', 'Sri Mawarni, M.Si', 'Kasmawi, M.Kom', 'Mansur, M.Kom', 'Lab. Basis Data', '2018-01-15', '10:00:00'),
(8, 'Halimah Juniar', 'A', 'Aplikasi Pencetakan Print Online Berbasis Web', 'Desi Amirullah, M.T', 'Supria, M.Kom', 'Mansur, M.Kom', 'Sri Mawarni, M.Si', 'Lab. Jaringan Komputer', '2018-01-14', '13:05:00'),
(9, 'M. Arfan', 'B', 'Booking Futsal dan Mencari Lawan Tanding Berbasis Android', 'Lidya Wati, M.kom', 'Desi Amirullah, M.T', 'Mansur, M.Kom', 'Nurul Fahmi, M.T', 'Lab. Sistem Cerdas', '2018-01-08', '10:00:00'),
(10, 'Tika Astuti', 'B', 'Sistem Pendukung Keputusan Pemilihan media Promosi Menggunakan Metode SAW\r\nUntuk Usaha Kecil Menengah', 'Rezki Kurniati, M.Kom', 'Mansur, M.Kom', 'Lidyawati, M.Kom', 'Supria, M.Kom', 'Lab. Jaringan Komputer', '2018-01-12', '07:15:00'),
(11, 'Yunior Fernando', 'B', 'Smart Mirroring Sebagai Pengganti Papan Pengumuman Akademik Kampus Dengan IOT', 'Eko Prayitno, M.Kom', 'Diah Anggraina Fitri, M.Si', 'Jaroji, M.Kom', 'Nurul Fahmi, M.T', 'Lab. Basis Data', '2018-01-15', '10:00:00'),
(12, 'Mashudi', 'B', 'Aplikasi Penyedia Jasa Layanan Service Online Berbasis Android', 'Jaroji, M.Kom', 'Fajri Profesio Putra, M.Cs', 'Desi Amirullah, M.T', 'Eko Prayitno, M.Kom', 'Lab. Basis Data', '2018-01-15', '09:15:00'),
(13, 'Devita Irzal', 'A', 'Aplikasi Pembelajaran Tengkorak Manusia Berbasis Augmented Realty Untuk Mahasiswa Kedokteran', 'Muhammad Nasir, M.Kom', 'Lidya Wati, M.kom', 'Rezki Kurniati, M.Kom', 'Danuri, M.Cs', 'Lab. Jaringan Komputer', '2018-01-19', '11:15:00'),
(14, 'Heri Agustiawan', 'B', 'Sistem Pembuatan Gambar Dikertas Dengan Mesin CNC Berbasis Mobile', 'Eko Prayitno, M.Kom', 'Nurul Fahmi, M.T', 'Desi Amirullah, M.T', 'Jaroji, M.Kom', 'Lab. Jaringan Komputer', '2018-01-20', '10:30:00'),
(22, 'Syifaul Muammar', 'C', 'Aplikasi Pengingat Kegiatan Akademik Dosen dan Mahasiswa Berbasis Android', 'Lidya Wati, M.Kom', 'Jaroji, M.Kom', 'Fajri Profesio Putra, M.Cs', 'Fajar Ratnawati, M.Cs', 'Lab. Jaringan Komputer', '2018-09-29', '13:45:00');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal_sidangta`
--

CREATE TABLE `jadwal_sidangta` (
  `id_jsidangta` int(11) NOT NULL,
  `nama` text NOT NULL,
  `kelas` text NOT NULL,
  `judul` text NOT NULL,
  `pembimbing1` text NOT NULL,
  `pembimbing2` text NOT NULL,
  `penguji1` text NOT NULL,
  `penguji2` text NOT NULL,
  `penguji3` text NOT NULL,
  `ruangan` text NOT NULL,
  `tanggal` date NOT NULL,
  `waktu` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jadwal_sidangta`
--

INSERT INTO `jadwal_sidangta` (`id_jsidangta`, `nama`, `kelas`, `judul`, `pembimbing1`, `pembimbing2`, `penguji1`, `penguji2`, `penguji3`, `ruangan`, `tanggal`, `waktu`) VALUES
(1, 'Nanda Febriandi', 'C', 'Aplikasi Penyewaan Ruko Berbasis Android', 'Desi Amirullah, M.T', 'Fajar Ratnawati, M.Cs', 'Lidya Wati, M.Kom', 'Sri Mawarni, M.Si', 'Rezki Kurniawati, M.Kom', 'Lab. Basis Data', '2018-08-15', '10:15:00'),
(2, 'Jurais', 'C', 'Aplikasi Monitoring Kompensasi Berbasis Android', 'Sri Mawarni, M.Si', 'Tengku Musri, M.Kom', 'Nurul Fahmi, M.T', 'Supria, M.Kom', 'Eko Prayitno, M.Kom', 'Lab. Pemrograman Lanjut', '2018-08-15', '13:30:00'),
(3, 'Juniarto', 'C', 'Sistem monitoring kepenuhan tempat sampah di daerah bengkalis berbasis arduino\r\n', 'Tengku Musri, M.Kom', 'Fajar Ratnawati, M.Cs', 'Desi Amirullah, M.T', 'Nurul Fahmi, M.T', 'Eko Prayitno, M.Kom', 'Lab. Pemrograman Lanjut', '2018-08-15', '15:00:00'),
(4, 'Rahmad Hidayat', 'C', 'Implementasi Fuzzy Rough\r\nSet Berbasiskan Algortima\r\nFuzzy Untuk Mendeteksi\r\nKebakaran Hutan Pada Real\r\nHardware Wireless Sensor\r\nNetwork.\r\n', 'Eko Prayitno, M.Kom', 'Nurul Fahmi, M.T', 'Diah Angraina Fitri, M.Si', 'Tengku Musri, M.Kom', 'Supria, M.Kom', 'Lab. Basis Data', '2018-08-14', '09:15:00'),
(5, 'Bunga Trisna', 'A', 'Sistem Antrian Puskesmas Bengkalis Berbasis Website', 'Mansur, M.Kom', 'Rezki Kurniati, M.Kom', 'Sri Mawarni, M.Si', 'Fajar Ratnawati, M.Cs', 'Jaroji, M.Kom', 'Lab. Basis Data', '2018-08-13', '08:15:00'),
(6, 'Sri Wulan Pratiwi Ningsih', 'A', 'Sarung Tangan Pintar Penerjemah Bahasa Isyarat Sebagai Alat Bantu Komunikasi Penyandang Disabilitas Tunarungu dan Tunawicara Menggunakan Teknologi IOT', 'Eko Prayitno, M.Kom', 'Muhammad Nasir, M.Kom', 'Supria, M.Kom', 'Fajar Ratnawati, M.Cs', 'Nurul Fahmi, M.T', 'Lab. Jaringan KOmputer', '2018-08-14', '10:15:00'),
(7, 'Santika Suryani', 'A', 'Aplikasi Simulasi Rukun Haji Berbasis Android Sebagai sarana Pembelajaran', 'Rezki Kurniati, M.Kom', 'Sri Mawarni, M.Si', 'Kasmawi, M.Kom', 'Mansur, M.Kom', 'Lidya Wati, M.kom', 'Lab. Basis Data', '2018-08-15', '17:00:00'),
(8, 'Halimah Juniar', 'A', 'Aplikasi Pencetakan Print Online Berbasis Web', 'Desi Amirullah, M.T', 'Supria, M.Kom', 'Mansur, M.Kom', 'Sri Mawarni, M.Si', 'Fajar Ratnawati, M.Cs', 'Lab. Jaringan Komputer', '2018-08-14', '09:15:00'),
(9, 'Devita Irzal', 'A', 'Aplikasi Pembelajaran Tengkorak Manusia Berbasis Augmented Realty Untuk Mahasiswa Kedokteran', 'Muhammad Nasir, M.Kom', 'Lidya Wati, M.kom', 'Rezki Kurniati, M.Kom', 'Danuri, M.Cs', 'Eko Prayitno, M.Kom', 'Lab. Basis Data', '2018-08-05', '15:00:00'),
(10, 'Nurul Shopiani', 'A', 'Pengukur Denyut Jantung Dengan Menggunakan Sensor Pulse Sebagai Peringatan Dini Kondisi Jantung ', 'Nurul Fahmi, M.T', 'Eko Prayitno, M.Kom', 'Tengku Musri, M.Kom', 'Danuri, M.Cs', 'Supria, M.Kom', 'Lab. Jaringan Komputer', '2018-08-14', '05:00:00'),
(11, 'Siti Fitiriyani', 'A', 'Alat Ukur Kondisi Tanah dan Penyiraman Otomatis Menggunakan Teknologi IOT Pada Pembibitan', 'Nurul Fahmi, M.T', 'Eko Prayitno, M.Kom', 'Muhammad Nasir, M.Kom', 'Desi Amirullah, M.T', 'Jaroji, M.Kom', 'Lab. Jaringan Komputer', '2018-07-30', '09:15:00'),
(12, 'Yunior Fernando', 'B', 'Smart Mirroring Sebagai Pengganti Papan Pengumuman Akademik Kampus Dengan IOT', 'Eko Prayitno, M.Kom', 'Diah Anggraina Fitri, M.Si', 'Jaroji, M.Kom', 'Nurul Fahmi, M.T', 'Desi Amirullah, M.T\r\n', 'Lab. Pemrograman Lanjut', '2018-08-15', '08:15:00'),
(13, 'Heri Agustiawan', 'B', 'Sistem Pembuatan Gambar Dikertas Dengan Mesin CNC Berbasis Mobile\r\n', 'Eko Prayitno, M.Kom', 'Nurul Fahmi, M.T', 'Desi Amirullah, M.T', 'Jaroji, M.Kom', 'Tengku Musri, M.Kom', 'Lab. Basis Data ', '2018-08-15', '11:15:00'),
(14, 'Mashudi', 'B', 'Aplikasi Penyedia Jasa Layanan Service Online Berbasis Android', 'Jaroji, M.Kom', 'Fajri Profesio Putra, M.Cs', 'Desi Amirullah, M.T', 'Eko Prayitno, M.Kom', 'Lidyawati, M.Kom', 'Lab. Jaringan Komputer', '2018-08-15', '14:30:00'),
(15, 'Tika Astuti', 'B', 'Sistem Pendukung Keputusan Pemilihan media Promosi Menggunakan Metode SAW\r\nUntuk Usaha Kecil Menengah', 'Rezki Kurniati, M.Kom', 'Mansur, M.Kom', 'Lidyawati, M.Kom', 'Supria, M.Kom', 'Sri Mawarni, M.Si', 'Lab. Pemrograman Lanjut', '2018-08-15', '08:40:00'),
(34, 'Fazri Nurfaris', 'B', 'Sistem Deteksi Jumlah Penumpang Pejalan Kaki Di Pelabuhan Roro Bengkalis', 'muhamad Nasir, M.Kom', 'Supria, M.Kom', 'Desi Amirullah, MT', 'Tengku Musri, M.Kom', 'Nurul Fahmi, MT', 'Lab. Jaringan Komputer', '2018-08-13', '03:30:00'),
(37, 'Syifaul Muammar', 'C', 'Aplikasi Pengingat Kegiatan Akademik Dosen dan Mahasiswa Berbasis Android', 'Lidya Wati, M.Kom', 'Jaroji, M.Kom', '1', '2', '3', 'Lab. Jaringan Komputer', '2018-09-29', '13:50:00'),
(38, 'Syifaul Muammar', 'C', 'Aplikasi Pengingat Kegiatan Akademik Dosen dan Mahasiswa Berbasis Android', 'Lidya Wati, M.Kom', 'Jaroji, M.Kom', '1', '2', '3', 'Lab. Jaringan Komputer', '2018-09-29', '13:55:00');

-- --------------------------------------------------------

--
-- Table structure for table `pengumuman`
--

CREATE TABLE `pengumuman` (
  `id_pengumuman` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `judul` text NOT NULL,
  `keterangan` text NOT NULL,
  `tanggal` date NOT NULL,
  `waktu` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengumuman`
--

INSERT INTO `pengumuman` (`id_pengumuman`, `id_user`, `judul`, `keterangan`, `tanggal`, `waktu`) VALUES
(1, 12, 'Perpanjangan Pendaftaran Sidang Tugas Akhir', '1. Seluruh mahasiswa dihimbau untuk segera menyelesaikan dan melaksanakan asistensi TA dengan pembimbing minimal 8 kali.\r\n2. Pelaksanaan ujian/sidang TA diperpanjang hingga 15 Agustus 2018.\r\n3. Sidang TA yang dilaksanakan melewati tangal tersebut akan mengikuti yudisium priode berikutnya.', '2018-08-15', '04:00:00'),
(2, 20, 'Sertifikasi Pelatihan Junior Web Programming Oleh TUK-BNSP TI', 'Berhubungan  akan diadakannya sertifikasi pelatihan junior web programming oleh TUK-BNSP TI yang akan dilaksanakan pada tanggal 23-26 Agustus 2018 maka mahasiswa teknik informatika semester 6 diwajibkan untuk melengkapi persyaratan dan pengisian form pendaftaran.', '2018-08-23', '08:00:00'),
(3, 2, 'Yudisium dan Penyerahan Laporan Tugas AKhir/Skripsi', 'Diberitahukan kepada Bapak/Ibu dosen, Koordinator Tugas Akhir dan mahasiswa yang menyelesaikan Tugas Akhir bagi mahasiwa Program Studi Diploma III dan skripsi bagi mahasiswa Sarjana Terapan Tahun Akademik 2017/2018', '2018-08-30', '08:00:00'),
(4, 1, 'Penyerahan Laporan Tugas Akhir/Skripsi', 'Penyaaerahan laporan akhir Tugas/Akhir pada tanggal 24 agustus 2018. dengan melampirkan :\r\n\r\na. Laporan Tugas Akhir/Skripsi yang sudah di revisi 2 (dua) rangkap belum jilid. \r\nb. Laporan dalam bentuk softfile/CD 2 buah.\r\nc. Untuk Program Studi Diploma III menyerahkan draf artikal 2 (dua) rangkap.\r\nd. Untuk Program Studi Sarjana Terapan menyerahkan bukti Submit artikel/Jurnal Proseding 2 (dua) rangkap.', '2018-08-28', '16:00:00'),
(15, 12, 'Periode 3 Bulan', 'Diberitahukan kepada Bapak lbu Ketua Jurusan, Dosen Koordinator Tugas akhir dan Mahasiswa yang menyelesaikan Tugas Akhir Periode 3 Bulan, bahwa batas akhir pelaksanaan Registrasi Tugas Akhir Periode 3 Bulan paling lambat tanggal 07 September 2018 dengan membayar UKT sebesar 50%. \n\nDemikian pengumuman ini disampaikan untuk dapat dilaksanakan dengan baik. Atas perhatian diucapkan terimakasih.', '2018-09-07', '04:00:00'),
(26, 2, 'Test', 'Pengumuman', '2018-09-27', '03:31:00');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `no_identitas` varchar(20) NOT NULL,
  `nama` text NOT NULL,
  `no_tlp` varchar(12) NOT NULL,
  `password` text NOT NULL,
  `level` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `no_identitas`, `nama`, `no_tlp`, `password`, `level`) VALUES
(1, '1200149', 'Muhammad Nasir, M.Kom', '085292749389', 'Koordinator', 'koor'),
(2, '1200144', 'Supria, M.Kom', '085222068817', 'Koordinator', 'koor'),
(3, '0909136', 'Mansur, M.Kom', '085265378750', 'Dosen', 'dsn'),
(4, '0911156', 'Desi Amirullah, M.T', '085271777577', 'Dosen', 'dsn'),
(5, '0903031', 'Sri Mawarni, M.Si', '081378589880', 'Dosen', 'dsn'),
(6, '0911151', 'Eko Prayitno, M.Kom', '08127600362', 'Dosen', 'dsn'),
(7, '1200149', 'Muhammad Nasir, M.Kom', '085292749389', 'DOsen', 'dsn'),
(8, '1200145', 'Tengku Musri, M.Kom', '085322838055', 'Dosen', 'dsn'),
(9, '1200146', 'Nurul Fahmi, M.T', '085264499431', 'Dosen', 'dsn'),
(10, '1200144', 'Supria, M.Kom', '085222068817', 'Dosen', 'dsn'),
(11, '198508122014041001', 'Danuri, M.Cs', '081365649657', 'Dosen', 'dsn'),
(12, '197706072014041001', 'Kasmawi, M.Kom', '085265584464', 'Dosen', 'dsn'),
(13, '0911155', 'Rezki Kurniati, M.Kom', '085265516425', 'Dosen', 'dsn'),
(14, '198908222014042001', 'Lidya Wati, M.kom', '085274894098', 'Dosen', 'dsn'),
(15, '198611072015041002', 'Jaroji, M.Kom', '085355257400', 'Dosen', 'dsn'),
(16, '198805072015041003', 'Fajri Profesio Putra, M.Cs', '081363386044', 'Dosen', 'dsn'),
(17, '198510052015041001', 'Agus Tedyyana, M.Kom', '085289866666', 'Dosen', 'dsn'),
(19, '1200148', 'Diah Angraina Fitri, M.Si', '085216610626', 'Dosen', 'dsn'),
(20, '1200155', 'Fajar Ratnawati, M.Cs', '081325068220', 'Dosen', 'dsn'),
(21, '6103151085', 'Syifaul Muammar', '082285864744', 'Mahasiswa', 'mhs'),
(22, '6103151094', 'Juniarto', '', 'Mahasiswa', 'mhs'),
(23, '6103151071', 'Jurais', '', 'Mahasiswa', 'mhs');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`id_agenda`);

--
-- Indexes for table `jadwal_seminarta`
--
ALTER TABLE `jadwal_seminarta`
  ADD PRIMARY KEY (`id_jseminarta`);

--
-- Indexes for table `jadwal_sidangta`
--
ALTER TABLE `jadwal_sidangta`
  ADD PRIMARY KEY (`id_jsidangta`);

--
-- Indexes for table `pengumuman`
--
ALTER TABLE `pengumuman`
  ADD PRIMARY KEY (`id_pengumuman`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agenda`
--
ALTER TABLE `agenda`
  MODIFY `id_agenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT for table `jadwal_seminarta`
--
ALTER TABLE `jadwal_seminarta`
  MODIFY `id_jseminarta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `jadwal_sidangta`
--
ALTER TABLE `jadwal_sidangta`
  MODIFY `id_jsidangta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `pengumuman`
--
ALTER TABLE `pengumuman`
  MODIFY `id_pengumuman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
