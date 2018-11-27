<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

$id_jsidangta = $_POST['id_jsidangta'];

if($result = $db->query("DELETE FROM `jadwal_sidangta` WHERE `id_jsidangta` = '$id_jsidangta'")){
    
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