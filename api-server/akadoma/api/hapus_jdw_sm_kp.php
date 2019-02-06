<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_jseminarkp = $_POST['id_jseminarkp'];

if($result = $db->query("DELETE FROM `jadwal_seminarkp` WHERE `id_jseminarkp` = '$id_jseminarkp'")){

	$response["success"] = 1;
	$response["message"] = "Berhasil dihapus";
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