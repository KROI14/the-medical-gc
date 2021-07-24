<?php

    include 'Connection.php';

    $conObj = new Connection();
    $con = $conObj->connect();

    if($con){
        $accNum = $_POST['accNum'];
        $money = $_POST['amount'];

        $prep = $con->prepare("UPDATE bank SET Money = ((SELECT Money FROM bank WHERE AccNum = ?) - ?) WHERE AccNum = ?");
        $prep->bind_param("sds", $accNum, $money, $accNum);
        $prep->execute();
        echo $prep->affected_rows;
    }
    else{
        echo "Not Connected";
    }

?>