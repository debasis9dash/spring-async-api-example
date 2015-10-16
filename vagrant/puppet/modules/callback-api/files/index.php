<?php

header('Content-type: application/json;charset=UTF-8');

$requestId = preg_replace("/[^-a-zA-Z0-9]+/", "", $_GET['requestId']);
$timeout = rand(1, 6000) / 1000;
$payload = '{"timestamp": "' . (new DateTime())->format("c") . '", "response": {"requestId": "'. $requestId  . '", "data": "'.strrev($requestId).'"}}';

chdir('/var/www/');
exec('./callback.sh '.$timeout.' \''.$payload.'\' > /dev/null 2>&1 &');

echo $payload;
?>