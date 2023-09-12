# Microservice BFF Service (api gateway)

Microservicio que en su BL permite al usuario hacer login ante la aplicacion que lo consume.

Generar el projecto desde la terminal:
- **dev** desarrollo
- **prod** produccion
- **local** local

```text
mvn clean package
java -jar .\target\bff-svc.jar --spring.profiles.active=local --captcha-svc=http://localhost:8082 --ldap-svc=http://localhost:8081
```

Desde el IDE de desarrollo agregar la variable de entorno:

- local:
```text
spring.profiles.active=local
captcha-svc=http://localhost:8082
ldap-svc=http://localhost:8081
```

Desde la terminal o desde la kubernetes:
- dev:
```text
java -jar .\target\bff-svc.jar --spring.profiles.active=dev --captcha-svc=http://captcha-svc.namespace.svc.cluster.local:8080 --ldap-svc=http://ldap-svc.namespace.svc.cluster.local:8080
```
```text
spring.profiles.active=dev
captcha-svc=http://captcha-svc.<namespace>.svc.cluster.local:8080
ldap-svc=http://ldap-svc.<namespace>.svc.cluster.local:8080
```
- prod:
```text
java -jar .\target\bff-svc.jar --spring.profiles.active=prod --captcha-svc=http://captcha-svc.namespace.svc.cluster.local:8080 --ldap-svc=http://ldap-svc.namespace.svc.cluster.local:8080
```
```text
spring.profiles.active=prod
captcha-svc=http://captcha-svc.<namespace>.svc.cluster.local:8080
ldap-svc=http://ldap-svc.<namespace>.svc.cluster.local:8080
```
