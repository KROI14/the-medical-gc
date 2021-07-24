<?php
    
    include 'Connection.php';

    $conObj = new Connection();
    $con = $conObj->connect();

    if($con){
        $bankSql = "SELECT * FROM bank";

        $stmt = $con->prepare($bankSql);
        $stmt->execute();
        $result = $stmt->get_result();
        $bankResult = $result->fetch_all(MYSQLI_ASSOC);

        $insureSql = "SELECT * FROM insurance";
        $stmt = $con->prepare($insureSql);
        $stmt->execute();
        $result = $stmt->get_result();
        $insureRes = $result->fetch_all(MYSQLI_ASSOC);

        $data = array($bankResult, $insureRes);
        
        echo json_encode($data);
    }

?>