<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$nama = $_POST['nama'];
$kelas = $_POST['kelas'];
$judul = $_POST['judul'];
$pembimbing = $_POST['pembimbing'];
$ruangan = $_POST['ruangan'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

if($result = $db->query("INSERT INTO `jadwal_seminarkp` (
    `id_jseminarkp`, 
    `nama`, 
    `kelas`, 
    `judul`, 
    `pembimbing`,
    `ruangan`, 
    `tanggal`, 
    `waktu`
    ) VALUES (
        NULL, 
        '$nama', 
        '$kelas', 
        '$judul', 
        '$pembimbing', 
        '$ruangan', 
        '$tanggal', 
        '$waktu')")){
	// no datas found
	$response["id_jseminarkp"] = $db->insert_id;
	$response["success"] = 1;
	$response["message"] = "Berhasil ditambahkan";
	// echo no users JSON
	echo json_encode($response);
}
else {
    // no datas found
    $response["success"] = 0;
    $response["message"] = "Maaf, terdapat kesalahan";
    // echo no users JSON
    echo json_encode($response);
}
?>