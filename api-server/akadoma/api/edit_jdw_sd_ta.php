<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_jsidangta = $_POST['id_jsidangta'];
$nama = $_POST['nama'];
$kelas = $_POST['kelas'];
$judul = $_POST['judul'];
$pembimbing1 = $_POST['pembimbing1'];
$pembimbing2 = $_POST['pembimbing2'];
$penguji1 = $_POST['penguji1'];
$penguji2 = $_POST['penguji2'];
$penguji3 = $_POST['penguji3'];
$ruangan = $_POST['ruangan'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

if($result = $db->query("UPDATE `jadwal_sidangta` SET
    `nama` = '$nama', 
    `kelas` = '$kelas', 
    `judul` = '$judul', 
    `pembimbing1` = '$pembimbing1', 
    `pembimbing2` = '$pembimbing2', 
    `penguji1` = '$penguji1', 
    `penguji2` = '$penguji2', 
    `penguji3` = '$penguji3', 
    `ruangan` = '$ruangan', 
    `tanggal` = '$tanggal', 
    `waktu` = '$waktu' WHERE `id_jsidangta` = '$id_jsidangta'")){
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