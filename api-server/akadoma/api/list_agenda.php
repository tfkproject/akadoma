<?php
// array for JSON response
$response = array();
// include db connect class
require 'koneksi.php';

/** Query lain:
 * SELECT * FROM `kompen` 
 * INNER JOIN staff_kampus ON staff_kampus.id_staff_kampus = pekerjaan.id_staff_kampus
 */
$id_user = $_POST['id_user'];

if($result = $db->query("SELECT * FROM `agenda`
INNER JOIN user ON agenda.id_user = user.id_user
WHERE agenda.id_user = '$id_user'
order by agenda.id_agenda DESC")){
	if($count = $result->num_rows){
		$response["field"] = array();
		
		while($row = $result->fetch_object()){
			$data = array();
			$data["id_agenda"] = $row->id_agenda;
			$data["judul"] = $row->judul;
			$data["keterangan"] = $row->keterangan;
			$data["tanggal"] = $row->tanggal;
			$data["waktu"] = $row->waktu;

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