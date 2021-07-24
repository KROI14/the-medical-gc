<?php

    class Connection{
        private $host = "localhost";
        private $user = "root";
        private $pass = "";
        private $db = "themedicalgcs";

        public function connect(){
            $con = new mysqli($this->host, $this->user, $this->pass, $this->db);

            return $con;
        }
    }

?>