<?php

    include "Connection.php";

    $connectObj = new Connection();
    $con = $connectObj->connect();

    if($con){
        //Get patient ID
        $sql = "SELECT PatientID FROM patient";

        $stmt = $con->prepare($sql);
        $stmt->execute();

        $result = $stmt->get_result();
        $result = $result->fetch_all(MYSQLI_ASSOC);

        $patientID =  $result[$stmt->affected_rows - 1]['PatientID'];

        //Get Bill ID
        $sql = "SELECT BillID FROM bill";

        $stmt = $con->prepare($sql);
        $stmt->execute();

        $result = $stmt->get_result();
        $result = $result->fetch_all(MYSQLI_ASSOC);

        $billID = $result[$stmt->affected_rows - 1]['BillID'];

        //Get Payment ID
        $sql = "SELECT PaymentID FROM payment";

        $stmt = $con->prepare($sql);
        $stmt->execute();

        $result = $stmt->get_result();
        $result = $result->fetch_all(MYSQLI_ASSOC);

        $paymentID = $result[$stmt->affected_rows - 1]['PaymentID'];

        //Payment ID
        $paymentID = preg_replace('/\D/', '', $paymentID);
        $paymentID++;
        $paymentID = "PAY-" . sprintf('%04d', $paymentID);
        $payment =  $paymentID . "<br>";

        //Bill ID
        $billID = preg_replace('/\D/', '', $billID);
        $billID++;
        $billID =  "BIL-" . sprintf('%04d', $billID);
        $bill = $billID;

        //Patient ID
        $patientID = preg_replace('/\D/', '', $patientID);
        $patientID++;
        $patientID = "PAT-" . sprintf('%04d', $patientID);
        $patient = $patientID;

        $data = array(
            "PatientID" => $patientID,
            "BillID" => $bill,
            "PaymentID" => $paymentID
        );

        echo json_encode($data);
    }
    else{
        echo "Failed";
    }
?>