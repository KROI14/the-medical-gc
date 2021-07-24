<?php

    include  "Connection.php";

    $conObj = new Connection();

    $con = $conObj->connect();

    if($con){
        $paymentID = $_POST['paymentID'];
        $bankAcc = $_POST['bankAcc'];
        $billID = $_POST['billID'];
        $insureName = $_POST['insureName'];
        $discount = $_POST['discount'];
        $amountPaid = $_POST['amountPaid'];
        $balance = $_POST['balance'];

        $sql = "INSERT INTO payment VALUES(?, ?, ? ,? ,? ,? ,?)";
        $stmt = $con->prepare($sql);
        $stmt->bind_param("sssssss", $paymentID, $bankAcc, $billID, $insureName, $discount, $amountPaid, $balance);
        $stmt->execute();
        if($stmt->affected_rows == 1){
            echo "Transaction success";
        }
    }
    else{
        echo "Error to Insert payment data";
    }
?>