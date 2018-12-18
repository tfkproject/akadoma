<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$idn = $_POST['idn'];
$id_user = $_POST['id_user'];
$nama = $_POST['nama'];
$telp = $_POST['telp'];
$pass = $_POST['pass'];

if($result = $db->query("UPDATE `user` SET `no_identitas` = '$idn', `nama` = '$nama', `no_tlp` = '$telp', `password` = '$pass' WHERE `id_user` = '$id_user'")){
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