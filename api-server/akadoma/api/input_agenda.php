<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_user = $_POST['id_user'];
$judul = $_POST['judul'];
$keterangan = $_POST['keterangan'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

if($result = $db->query("INSERT INTO `agenda` (`id_agenda`, `id_user`, `judul`, `keterangan`, `tanggal`, `waktu`) VALUES (NULL, '$id_user', '$judul', '$keterangan', '$tanggal', '$waktu')")){
	// no datas found
	$response["id_agenda"] = $db->insert_id;
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