# Basic Administration

## Mongod

1. ```mongod``` is the daemon process for Mongo DB, ```mongo``` is the interactive shell for running commands
2. Use ```mongo``` to connect to Mongo shell
3. The default dbpath is ```/data/db```, we can change this using ```mongod --dbpath <directory path>```. Indexes, .lock files are stored here. If .lock file is not empty(means another process is running or unclean shutdown) and it will not allow other mongod process to use same data folder.
4. Default port is 27017. Use ```mongod --port <port number>``` to change it.
5. The bind_ip option allows us to specify which IP addresses mongod should bind to. When mongod binds to an IP address, clients from that address are able to connect to mongod. ```mongod --bind_ip localhost,123.123.123.123```
6. Config file ```mongod --config "etc/mongod.conf```

    ```yaml
        storage:
            dbPath: "/data/db"
        systemLog:
            path: "/data/log/mongod.log"
            destination: "file"
        replication:
            replSetName: M103
        net:
            bindIp : "127.0.0.1,192.168.103.100"
        tls:
            mode: "requireTLS"
            certificateKeyFile: "/etc/tls/tls.pem"
            CAFile: "/etc/tls/TLSCA.pem"
        security:
            keyFile: "/data/keyfile"
        processManagement:
            fork: true        
    ```

7. Logging -> We can set logging levels and change verbosity levels for logs. To search in logs ```grep -i 'update' /data/db/mongod.log```
8. Profiling

## Authentication mechanisms

1. SCRAM - Password
2. X.509 Certificate
3. LDAP
4. Kerberos

## Authorization

Role based -> Each user has one or more roles, user exist per database.\
Create root user

```text
use admin
db.createUser({
  user: "root",
  pwd: "root123",
  roles : [ "root" ]
})
```

```mongo --username root --password root123 --authenticationDatabase admin```
There are some built in roles like read, readWrite, userAdmin -> User management, dbAdmin -> Collection management, dbOwner -> For a database