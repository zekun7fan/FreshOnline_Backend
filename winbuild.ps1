$ipV4 = Test-Connection -ComputerName (hostname) -Count 1  | Select IPV4Address
$content = Get-Content -Path 'src\main\resources\application.properties'
$newContent = $content -replace 'localhost', $ipV4.IPV4Address.IPAddressToString
$newContent | Set-Content -Path 'src\main\resources\application.properties'
./mvnw package
$content | Set-Content -Path 'src\main\resources\application.properties'
docker build -t springio/gs-spring-boot-docker .
docker run  -p 8080:8080 --name freshonline springio/gs-spring-boot-docker