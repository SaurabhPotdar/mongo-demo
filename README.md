## [Installing Mongo DB](https://www.c-sharpcorner.com/article/how-to-set-up-and-starts-with-mongodb/#:~:text=Click%20on%20environment%20variables%20button,Program%20Files%5CMongoDB%5C%E2%80%9D)

## Starting Mongo DB server
Exception opening socket</br>
Open CMD in C:\Program Files\MongoDB and execute command ```mongod``` to start Mongo DB server.

## Connecting using Spring Boot
**Default credentials**: spring.data.mongodb.uri=mongodb://localhost:27017/airportdb
spring.data.mongodb.uri=mongodb://**username**:**password**@localhost:27017/airportdb

## Relational vs Mongo
| Relational  | Mongo |
| ------------- | ------------- |
| Tables  | Collections  |
| Rows  | Documents  |
| Columns  | Fields  |
| Primary Key  | Id  |

## Annotations
1.	@Document(“Name”) – Creates a document (i.e. table)
2.	@Id - Mongo DB uses special type called **object id**, so the id field type is **String** e.g. $oid": "6093a9ece188ea1ed068156a".</br> ```@Id private String id;```
3.	@Field(“Name”) – To give custom column name to a field
4.	@Transient - To exclude a field from being persisted.
5.	@Indexed
6.	@TextIndexed - To implement full text search, works on String and Array of String
7.	@CompoundIndex - To create index using multiple fields
8.	@DbRef - For relationships

```
@Document(collection = "Hotels")
@CompoundIndex(def="{'name':1, 'pricePerNight':-1}")  //1:Asc, -1:Desc
public class Hotel {
    @Id
    private String id;
    @TextIndexed
    private String name;
    @Indexed(direction = IndexDirection.DESCENDING, unique=false)
    private int pricePerNight;
    private int rooms;
    @DbRef
    private Address address;
}
```

## Query Execution
| Without indexes  | With indexes |
| ------------- | ------------- |
| Collection scan, each document is evaluated  | Does not scan every document in collections  |
| Slow searches  | Fast searches  |
| Fast inserts/updates  | Slower inserts/updates  |

## Query object
```
Query byAgeQuery = Query
    .query(Criteria.where("age").gt(18).lt(75))
    .with(Sort.by(Sort.Direction.DESC, "age"))
    .with(PageRequest.of(1,10));
List<Person> people = this.mongoTemplate.find(byAgeQuery, Person.class)
```
Multiple criteria
```
Query byAgeQuery = Query.query(new Criteria())
    .orOperator(Criteria.where("age").is(18),
               (Criteria.where("nbSeats").gte(2))
);
List<Person> people = mongoTemplate.find(byAgeQuery, Person.class)
```
### Mongo Filter Operators
is/ne(equality), lt/lte(less than), gt/gte(greater than), in(value in list), exists(has value), regex(patterns)

## Full Text Search(FTS)
Each document is scanned and a score is computed based on text index weights, results are then sorted by this score.</br>
Default weight is 1.
```
private String name;
@TextIndexed private String title;
@TextIndexed(weight=2) private String aboutMe;
```
Input:
```
{name:"Dan", title:"Java Developer", aboutMe:"I am a programmer"},
{name:"Java Guru", title:"Java Developer", aboutMe:"I am a programmer"},
{name:"John", title:"Developer", aboutMe:"I am a Java programmer"}
```
When we search by _Java_, we get
```
Jonn
Dan
```
John is first as aboutMe has more weight. Also Java Guru is not in the o/p as it is not indexed.

### Executing a FTS
```
TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
Query byText = TextQuery.queryText(textCriteria).sortByScore().with(PageRequest.of(1,10));
List<Person> people = mongoTemplate.find(byText, Person.class)
```

## [Mongo Repository](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/repository/MongoRepository.html)
[Reference](https://www.baeldung.com/spring-data-mongodb-tutorial)

### Insert vs Save
Insert with existing ID, throws an error.</br>
Save with existing ID, updates old record. Save can be use seen as **insertOrUpdate**.</br>
Use insert for new documents and save for updates.

### Batch insert : Using mongoTemplate
For saving multiple records use insertAll, as it uses only one round trip to database.
```
List<Person> people = Arrays.asList(a,b,c);
mongoTemplate.insertAll(people);
```

### Using mongoRepository
```
List<Person> people = Arrays.asList(a,b,c);
mongoRepository.saveAll(people);
```

## Object References
To link multiple documents that are related. Try to minimize the number of relationships(keep it denormalized). We can link documents using ```@DBRef```.</br>
Cascading does not work by default, so ```mongoRepository.save(parent)``` will not save the child automatically.</br>
e.g Aircraft has engine
```
engineRepository.save(engine);  //First save child entity
aircraft.setEngine(engine);
aircraftRepository.save(engine);  //save parent entity
```
We can also use ```@CascadeSave``` to enable cascade save
```
@DBRef
@CascadeSave
```

### Lazy loading
```@DBRef(lazy=true) private Engine engine;```

## Mongo Converters
Transform object before saving in DB.
```
"address":{
    "city":"Paris",
    "country":"France"
}
```
We want to store address as a concatenated String:
```
"address":"Paris,France"
```

## [Uploading image](https://www.baeldung.com/spring-boot-mongodb-upload-file)

## Life cycle events
We can implement methods in the interface AbstractMongoEventListener.
### Save/Update
1. **onBeforeConvert** is called before POJO converted to Document by MongoConverter
2. **onBeforeSave**
3. **onAfterSave**

### Load
When we call findOne,..
1. **onAfterLoad** is called after Document retrieved from database
2. **onAfterConvert** is called before Document converted to POJO

### Delete
1. **onBeforeDelete**
2. **onAfterDelete**
