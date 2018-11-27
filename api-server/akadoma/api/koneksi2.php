<?php
include "konfig.php";
// Create connection
$connection = mysqli_connect($host, $user, $pass, $dbname) or die("Error " . mysqli_error($connection));
?>