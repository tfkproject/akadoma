<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';
$identitas = $_POST['no_id'];
$password = $_POST['pass'];
$level = $_POST['level'];
if($result = $db->query("SELECT * FROM user where no_identitas = '$identitas' and password = '$password' and level = '$level'")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			$data["id_user"] = $row->id_user;
			$data["no_identitas"] = $row->no_identitas;	
			$nama = $data["nama"] = $row->nama;
			$data["no_tlp"] = $row->no_tlp;	
			$data["level"] = $row->level;					
			
			array_push($response["field"], $data);
		}
		
		$response["success"] = 1;
		$response["message"] = "Selamat datang $nama";
		
		// echoing JSON response
		echo json_encode($response);
	}
	else{
	    // no datas found
        $response["success"] = 0;
        $response["message"] = "Maaf, password anda salah";
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