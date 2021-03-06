<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

if($result = $db->query("SELECT * FROM `jadwal_seminarkp` ORDER BY id_jseminarkp DESC")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			$data["id_jseminarkp"] = $row->id_jseminarkp;
			$data["nama"] = $row->nama;
			$data["kelas"] = $row->kelas;			
			$data["judul"] = $row->judul;			
			$data["pembimbing"] = $row->pembimbing;	
			$data["ruangan"] = $row->ruangan;			
			$data["tanggal"] = $row->tanggal;
			$data["waktu"] = $row->waktu;
			
			date_default_timezone_set("Asia/Jakarta");

			$time = date_parse ($row->tanggal." ".$row->waktu);
			
			$selisih1 =  mktime($time["hour"], $time["minute"], $time["second"], $time["month"], $time["day"], $time["year"]);

			// mencari mktime untuk current time
			$selisih2 = mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y"));

			// mencari selisih detik antara kedua waktu
			$delta = $selisih1 - $selisih2;

			// proses mencari jumlah hari
			$a = floor($delta / 86400);

			// proses mencari jumlah jam
			$sisa = $delta % 86400;
			$b  = floor($sisa / 3600);

			// proses mencari jumlah menit
			$sisa = $sisa % 3600;
			$c = floor($sisa / 60);

			// proses mencari jumlah detik
			$sisa = $sisa % 60;
			$d = floor($sisa / 1);

			if($a < 0 && $b < 0 && $c < 0 && $d < 0){
				$data["sisa_waktu"] = "Sudah berlalu";
			}else{
				//$data["sisa_waktu"] = $a." hari ".$b." jam ".$c." menit ".$d." detik lagi";
				$data["sisa_waktu"] = $a." hari ".$b." jam ".$c." menit lagi";
			}
			
			array_push($response["field"], $data);
		}
		
		$response["success"] = 1;
		$response["message"] = "List data ada";
		
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
