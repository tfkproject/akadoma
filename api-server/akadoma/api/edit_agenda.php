<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_agenda = $_POST['id_agenda'];
$id_user = $_POST['id_user'];
$judul = $_POST['judul'];
$keterangan = $_POST['keterangan'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

if($result = $db->query("UPDATE `agenda` SET `id_user` = '$id_user', `judul` = '$judul', `keterangan` = '$keterangan', `tanggal` = '$tanggal', `waktu` = '$waktu' WHERE `id_agenda` = '$id_agenda'")){
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