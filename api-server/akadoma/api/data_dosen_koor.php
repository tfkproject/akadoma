<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';
$id_user = $_POST['id_user'];

if($result = $db->query("SELECT * FROM user WHERE level = 'dsn' OR level = 'koor' ORDER BY nama ASC")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			
			$data["nama"] = $row->nama;				
			
			array_push($response["field"], $data);
		}
		
		$response["success"] = 1;
		$response["message"] = "Data ditemukan";
		
		// echoing JSON response
		echo json_encode($response);
	}
	else{
	    // no datas found
        $response["success"] = 0;
        $response["message"] = "Data tidak ditemukan";
        // echo no users JSON
        echo json_encode($response);
	}
		
		$result->free();
}
else {
    // no datas found
    $response["success"] = 0;
    $response["message"] = "Maaf, terdapat error pada database";
    // echo no users JSON
    echo json_encode($response);
}
?>