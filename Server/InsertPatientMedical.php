<?php

    include "Connection.php";

    $conObj = new Connection();
    $con = $conObj->connect();

    if($con){
        $patientID = $_POST['patientID'];
        $fName = $_POST['firstname'];
        $lName = $_POST['lastname'];
        $age = $_POST['age'];
        $gender = $_POST['gender'];
        $address = $_POST['address'];
        $contact = $_POST['contact'];

        $sqlPatient = "INSERT INTO patient VALUES(?, ?, ?, ?, ?, ?, ?)";

        $stmt = $con->prepare($sqlPatient);
        $stmt->bind_param("sssssss", $patientID, $fName, $lName, $age, $gender, $address, $contact);
        $stmt->execute();

        if($stmt->affected_rows == 1){
            $billID = $_POST['billID'];
            $illness = $_POST['illness'];
            $condition = $_POST['condition'];
            $xRay = $_POST['xRay'];
            $ctScan = $_POST['ctScan'];
            $covidTest = $_POST['covidTest'];
            $room = $_POST['room'];
            $days = $_POST['days'];
            $tax = $_POST['tax'];
            $totalPrice = $_POST['totalPrice'];

            $sqlBill = "INSERT INTO bill VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            $stmt = $con->prepare($sqlBill);
            $stmt->bind_param("sssssssssss", $billID, $patientID, $illness, $condition, $xRay, $ctScan, $covidTest, $room, $days, $tax, $totalPrice);
            $stmt->execute();

            if($stmt->affected_rows == 1){
                echo "Success";
            }
            else{
                echo "Bill failed";
            }
        }
        else{
            echo "Patient info insert failed";
        }
    }
?>