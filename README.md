# Heimdall - Secure Password Hashing

This library implements a secure and upgradeable password hashing mechanism. See [this blog post](http://qaware.blogspot.de/2015/03/secure-password-storage-and.html) for details.

## Why not just use PBKDF2, scrypt, bcrypt, etc.?

Actually, this library uses (some of) these algorithms. But it makes it easier for you: no need to worry about iterations, salt
generation and the same. And if a flaw is discovered in one of the algorithms, the library makes sure that the hashes
in your database are automatically updated to a secure format (provided you use the pattern as shown in the usage block
down below).

## Usage

### Dependencies

If you are using Maven to build your project, add the following to the `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>heimdall-github</id>
        <url>https://raw.githubusercontent.com/qaware/heimdall/master/repository/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>de.qaware</groupId>
        <artifactId>heimdall</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

In case you are using Gradle to build your project, add the following to the `build.gradle` file:
```groovy
repositories {
    maven {
        url 'https://raw.githubusercontent.com/qaware/heimdall/master/repository/'
    }
}

dependencies {
	compile 'de.qaware:heimdall:1.2'
}
```

### Create a hash
```java
    Password password = PasswordFactory.create();

    try(SecureCharArray cleartext = new SecureCharArray(...)) { // Read cleartext password from user
        String hash = password.hash(cleartext);
        // Persist the hash in a database etc...
    }
```

### Verify the hash
```java
    Password password = PasswordFactory.create();

    String hash = ... // Load hash from persistent storage
    try(SecureCharArray cleartext = new SecureCharArray(...)) { // Read cleartext password from user
        if (password.verify(cleartext, hash)) {
            if (password.needsRehash(hash)) { // Check if the hash uses an old hash algorithm, insecure parameters, etc.
                String newHash = password.hash(cleartext);
                // Persist the new hash in a database etc...
            }

            // Password is correct, proceed...
        } else {
            // Password is incorrect
        }
    }
```

## Technical details

By default this library uses the PBKDF2 SHA-1 HMAC (`PBKDF2WithHmacSHA1`) with 20000 iterations and 192 bit (24 byte) of salt.

## Maintainer

Moritz Kammerer (@phxql), <moritz.kammerer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE.txt` file for details.
