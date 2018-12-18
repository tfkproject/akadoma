<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';
$id_user = $_POST['id_user'];

if($result = $db->query("SELECT * FROM user where id_user = '$id_user'")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			$data["id_user"] = $row->id_user;
			$data["no_identitas"] = $row->no_identitas;	
			$data["nama"] = $row->nama;
			$data["no_tlp"] = $row->no_tlp;	
			$data["password"] = $row->password;
			$data["level"] = $row->level;					
			
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