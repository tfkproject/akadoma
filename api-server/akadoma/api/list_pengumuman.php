<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

/** Query lain:
 * SELECT * FROM `kompen` 
 * INNER JOIN staff_kampus ON staff_kampus.id_staff_kampus = pekerjaan.id_staff_kampus
 */

if($result = $db->query("SELECT * FROM `pengumuman`
INNER JOIN user ON pengumuman.id_user = user.id_user
order by pengumuman.id_pengumuman DESC")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			$data["id_pengumuman"] = $row->id_pengumuman;
			$data["id_user"] = $row->id_user;
			$data["nama"] = $row->nama;
			$data["judul"] = $row->judul;
			$data["tanggal"] = $row->tanggal;
			$data["waktu"] = $row->waktu;
			$data["keterangan"] = $row->keterangan;
			$data["level"] = $row->level;	
			
			array_push($response["field"], $data);
		}
		
		$response["success"] = 1;
		$response["message"] = "List data";
		
		// echoing JSON response
		echo json_encode($response);
	}
	else{
	    // no datas found
        $response["success"] = 0;
        $response["message"] = "Terjadi kesalahan pada query";
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