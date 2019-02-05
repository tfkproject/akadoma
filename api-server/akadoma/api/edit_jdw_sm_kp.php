<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_jseminarkp = $_POST['id_jseminarkp'];
$nama = $_POST['nama'];
$kelas = $_POST['kelas'];
$judul = $_POST['judul'];
$pembimbing = $_POST['pembimbing'];
$ruangan = $_POST['ruangan'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

if($result = $db->query("UPDATE `jadwal_seminarkp` SET 
    `nama` = '$nama', 
    `kelas` = '$kelas', 
    `judul` = '$judul', 
    `pembimbing` = '$pembimbing', 
    `ruangan` = '$ruangan', 
    `tanggal` = '$tanggal', 
    `waktu` = '$waktu' WHERE `id_jseminarkp` = '$id_jseminarkp'")){
	// no datas found
	$response["success"] = 1;
	$response["message"] = "Berhasil diperbaharui";
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