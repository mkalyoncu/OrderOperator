# OrderOperator

OrderOperator is a project that has many endpoints to handle some customer-order operations.
The endpoints can be examined via swagger.

Url: http://HOST_NAME:8080/swagger-ui/
For local environment you can use: http://localhost:8080/swagger-ui/

### Requirements
* JDK8
* Maven
* MySQL database
* Elastic database
  
You need to have two tables customer and order. Table's schema can be examined below.
* customer table  
    
    `#,Field,Type,Null,Key,Default,Extra`  
    `id,int,NO,PRI,,auto_increment`  
    `name,varchar(250),NO,"",,""`  
    `age,tinyint,NO,"",,""`  
    `

* order table  
    `#,Field,Type,Null,Key,Default,Extra`  
    `id,int,NO,PRI,,auto_increment`  
    `name,varchar(250),NO,"",,""`  
    `age,tinyint,NO,"",,""`  
    
  
### Run

1. Datasource explained in the requirements section must be created with the correct schema. 
   Also, you can use the _localdb_test-2021_02_16_23_32_21-dump.sql_ file under the sqldump directory for MySQL.
2. Datasource's credentials, ports, and names should be defined correctly in the _application.properties_ file
3. Create a jar with the command below.  
`mvn clean install`
4. A jar file must be created under <project_dir>/target/ switch to target directory and run the command below.
`java -jar orderoperator-0.0.1.jar`
