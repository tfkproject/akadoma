<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_agenda = $_POST['id_agenda'];

if($result = $db->query("DELETE FROM `agenda` WHERE `id_agenda` = '$id_agenda'")){
	// no datas found
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