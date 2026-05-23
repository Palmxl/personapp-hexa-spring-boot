# PersonApp - Arquitectura Hexagonal con Spring Boot

## Requisitos
- Java 11
- Maven 3.x
- MariaDB en puerto 3307
- MongoDB en puerto 27017

---

## Configuración del ambiente

### Linux/Ubuntu

#### 1. Instalar Java 11
```bash
sudo apt install openjdk-11-jdk -y
sudo update-alternatives --config java
java -version # Debe mostrar openjdk 11
```

#### 2. Instalar MariaDB
```bash
sudo apt install mariadb-server -y
sudo nano /etc/mysql/mariadb.conf.d/50-server.cnf
# Agregar bajo [mysqld]:
# port = 3307
sudo systemctl start mariadb
sudo mysql --socket=/var/run/mysqld/mysqld.sock
```
Dentro de MySQL ejecutar:
```sql
CREATE DATABASE personapp;
CREATE USER 'persona'@'localhost' IDENTIFIED BY 'persona';
GRANT ALL PRIVILEGES ON personapp.* TO 'persona'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 3. Instalar MongoDB
```bash
curl -fsSL https://www.mongodb.org/static/pgp/server-7.0.asc | sudo gpg -o /usr/share/keyrings/mongodb-server-7.0.gpg --dearmor
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-7.0.gpg ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list
sudo apt update && sudo apt install -y mongodb-org
sudo systemctl start mongod
```

---

### Windows

#### 1. Instalar Java 11
- Descargar JDK 11 desde: https://adoptium.net/es/temurin/releases/?version=11
- Instalar el .msi y seguir el asistente
- Verificar en CMD:
```cmd
java -version
```

#### 2. Instalar MariaDB
- Descargar desde: https://mariadb.org/download/
- Durante la instalación cambiar el puerto a **3307**
- Agregar al PATH: `C:\Program Files\MariaDB\bin`
- Abrir CMD como administrador y ejecutar:
```cmd
mysql -u root -p
```
Dentro de MySQL ejecutar:
```sql
CREATE DATABASE personapp;
CREATE USER 'persona'@'localhost' IDENTIFIED BY 'persona';
GRANT ALL PRIVILEGES ON personapp.* TO 'persona'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 3. Instalar MongoDB
- Descargar desde: https://www.mongodb.com/try/download/community
- Instalar el .msi y seguir el asistente
- Asegurarse de que corra en puerto **27017**
- Instalar MongoDB Shell (mongosh): https://www.mongodb.com/try/download/shell

---

### macOS

#### 1. Instalar Java 11
```bash
brew install openjdk@11
sudo ln -sfn /opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk
java -version
```

#### 2. Instalar MariaDB
```bash
brew install mariadb
# Editar puerto en /opt/homebrew/etc/my.cnf agregando port=3307 bajo [mysqld]
brew services start mariadb
mysql -u root
```
Dentro de MySQL ejecutar:
```sql
CREATE DATABASE personapp;
CREATE USER 'persona'@'localhost' IDENTIFIED BY 'persona';
GRANT ALL PRIVILEGES ON personapp.* TO 'persona'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 3. Instalar MongoDB
```bash
brew tap mongodb/brew
brew install mongodb-community@7.0
brew services start mongodb-community@7.0
```

---

## Compilación

### Linux/macOS
```bash
cd personapp-hexa-spring-boot
find . -name "pom.xml" ! -path "./pom.xml" -exec sed -i 's/\${revision}/0.0.1-SNAPSHOT/g' {} \;
sed -i 's/<version>\${revision}<\/version>/<version>0.0.1-SNAPSHOT<\/version>/g' pom.xml
rm -rf ~/.m2/repository/co/edu/javeriana
mvn install -N -DskipTests
mvn clean install -DskipTests
```

### Windows (CMD o PowerShell)
```cmd
cd personapp-hexa-spring-boot
mvn install -N -DskipTests
mvn clean install -DskipTests
```
> En Windows reemplazar manualmente `${revision}` por `0.0.1-SNAPSHOT` en todos los archivos `pom.xml` de los submódulos si hay errores de resolución.

---

## Ejecución de scripts DDL y DML

### Linux/macOS
```bash
sudo mysql --socket=/var/run/mysqld/mysqld.sock personapp < scripts/persona_ddl_maria.sql
sudo mysql --socket=/var/run/mysqld/mysqld.sock personapp < scripts/persona_dml_maria.sql
mongosh < scripts/persona_ddl_mongo.js
mongosh < scripts/persona_dml_mongo.js
```

### Windows
```cmd
mysql -u persona -ppersona -P 3307 -h 127.0.0.1 personapp < scripts\persona_ddl_maria.sql
mysql -u persona -ppersona -P 3307 -h 127.0.0.1 personapp < scripts\persona_dml_maria.sql
mongosh < scripts\persona_ddl_mongo.js
mongosh < scripts\persona_dml_mongo.js
```

---

## Despliegue

### Iniciar bases de datos

**Linux:**
```bash
sudo systemctl start mariadb
sudo systemctl start mongod
```

**Windows:**
```cmd
net start MariaDB
net start MongoDB
```

**macOS:**
```bash
brew services start mariadb
brew services start mongodb-community@7.0
```

### REST (puerto 3000)

**Linux/macOS:**
```bash
cd rest-input-adapter
mvn org.springframework.boot:spring-boot-maven-plugin:2.7.11:run -Dspring-boot.run.mainClass=co.edu.javeriana.as.personapp.PersonAppRestApi
```

**Windows:**
```cmd
cd rest-input-adapter
mvn org.springframework.boot:spring-boot-maven-plugin:2.7.11:run -Dspring-boot.run.mainClass=co.edu.javeriana.as.personapp.PersonAppRestApi
```

Swagger disponible en: http://localhost:3000/swagger-ui.html

### CLI

**Linux/macOS:**
```bash
cd cli-input-adapter
mvn org.springframework.boot:spring-boot-maven-plugin:2.7.11:run -Dspring-boot.run.mainClass=co.edu.javeriana.as.personapp.PersonAppCli
```

**Windows:**
```cmd
cd cli-input-adapter
mvn org.springframework.boot:spring-boot-maven-plugin:2.7.11:run -Dspring-boot.run.mainClass=co.edu.javeriana.as.personapp.PersonAppCli
```

---

## Estructura del proyecto
```
personapp-hexa-spring-boot/
├── domain/                  # Entidades y puertos (interfaces)
├── common/                  # DTOs y clases compartidas
├── application/             # Casos de uso (servicios)
├── rest-input-adapter/      # API REST + Swagger
├── cli-input-adapter/       # Interfaz por consola
├── maria-output-adapter/    # Persistencia MariaDB (JPA)
├── mongo-output-adapter/    # Persistencia MongoDB
└── scripts/                 # DDL y DML
```

## Endpoints REST disponibles
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/persona/{database} | Listar personas |
| POST | /api/v1/persona | Crear persona |
| GET | /api/v1/profesion/{database} | Listar profesiones |
| POST | /api/v1/profesion | Crear profesion |
| GET | /api/v1/telefono/{database} | Listar telefonos |
| POST | /api/v1/telefono | Crear telefono |
| GET | /api/v1/estudios/{database} | Listar estudios |
| POST | /api/v1/estudios | Crear estudio |

El parametro `{database}` puede ser `MARIA` o `MONGO`.
