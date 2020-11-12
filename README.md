# boot-parent
Java maven parent to manage dependency and plugin config

# Test Data
* https://localhost:8080/addresses?query=M33NW
* https://localhost:8080/direct/addresses?query=M33NW
* https://localhost:8080/customers/1234
* https://localhost:8080/direct/customers/1234

# SSL Shizzle
* keytool -genkeypair -alias compositegateway -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore compositegateway.p12 -validity 10000 -ext SAN=dns:localhost,ip:127.0.0.1 
* keytool -exportcert -keystore compositegateway.p12 -storepass password -alias compositegateway -rfc -file compositegateway.pem
* keytool -exportcert -keystore compositegateway.p12 -storepass password -alias compositegateway -rfc -file compositegateway.pem
* keytool -exportcert -keystore compositegateway.p12 -storepass password -alias compositegateway -file compositegateway.crt
* openssl x509 -fingerprint -sha1 -in compositegateway.crt
