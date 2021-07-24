<?php

    include "Connection.php";

    $conObj = new Connection();
    $con = $conObj->connect();

    $sql = "SELECT PaymentID, Firstname, Lastname, Address, Contact, Xray, CTScan, CovidTest, Room, Days, Tax, TotalPrice, InsuranceName, Discount, AmountPaid, Balance FROM patient INNER JOIN bill ON patient.PatientID = bill.PatientID inner JOIN payment ON bill.BillID = payment.BillID WHERE PaymentID = ?";

    if($con){
        $paymentID = $_POST['paymentID'];

        $stmt = $con->prepare($sql);
        $stmt->bind_param("s", $paymentID);
        $stmt->execute();
        $fetch = $stmt->get_result();
        $array[] = $fetch->fetch_assoc();

        echo json_encode($array);
    }

?>